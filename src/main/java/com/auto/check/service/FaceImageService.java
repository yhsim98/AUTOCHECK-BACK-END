package com.auto.check.service;

import com.auto.check.domain.face.FaceImage;
import com.auto.check.domain.face.FaceImageRepository;
import com.auto.check.domain.lecture.Lecture;
import com.auto.check.domain.user.User;
import com.auto.check.enums.ErrorMessage;
import com.auto.check.exception.NonCriticalException;
import com.auto.check.service.dto.RequestFaceCheckDTO;
import com.auto.check.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FaceImageService {

    private final UserService userService;
    private final S3Util s3Util;
    private final FaceImageRepository faceImageRepository;
    private final LectureService lectureService;
    private final WebClient webClient;

    @Value("${face-server.address}")
    private String faceServerAddress;

    public List<FaceImage> saveImagesAndSendToFaceServerForTrain(List<MultipartFile> images){
        List<FaceImage> savedImages = saveImages(images);

        List<Lecture> lectures = lectureService.findUserLectureList();

        List<RequestFaceCheckDTO> requestData = lectures.stream()
                .map(l->new RequestFaceCheckDTO(l, userService.getLectureRelatedUsers(l)))
                .collect(Collectors.toList());

        webClient.mutate()
                .baseUrl(faceServerAddress)
                .build()
                .post()
                .uri("/face_recognition")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestData)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError()
                        , clientResponse -> clientResponse.bodyToMono(String.class)
                                .map(body->new RuntimeException()))
                .bodyToMono(String.class)
                        .subscribe();

        return savedImages;
    }

    public List<FaceImage> saveImages(List<MultipartFile> images) {
        User user = userService.getLoginUserInfo();

        for (MultipartFile image : images) {
            String savedUrl = s3Util.uploader(image);

            faceImageRepository.save(
                    FaceImage.builder()
                            .savedUrl(savedUrl)
                            .fileName(image.getOriginalFilename())
                            .user(user)
                            .build()
            );
        }

        return faceImageRepository.findByUser(user);
    }

    public void deleteImage(Long faceImageId){
        User user = userService.getLoginUserInfo();
        FaceImage faceImage = faceImageRepository.findByIdAndUser(faceImageId, user)
                .orElseThrow(()->new NonCriticalException(ErrorMessage.IMAGE_NOT_EXIST));

        s3Util.deleteFile(faceImage.getSavedUrl());
        faceImageRepository.delete(faceImage);
    }
}
