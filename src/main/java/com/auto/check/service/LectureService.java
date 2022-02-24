package com.auto.check.service;

import com.auto.check.domain.lecture.Lecture;
import com.auto.check.domain.lecture.LectureRepository;
import com.auto.check.domain.lectureinfo.LectureInfo;
import com.auto.check.domain.lectureinfo.LectureInfoRepository;
import com.auto.check.domain.user.User;
import com.auto.check.domain.user.UserType;
import com.auto.check.enums.ErrorMessage;
import com.auto.check.exception.NonCriticalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class LectureService {

    private final UserService userService;
    private final LectureRepository lectureRepository;
    private final LectureInfoRepository lectureInfoRepository;

    public List<Lecture> findUserLectureList() {
        User user = userService.getLoginUserInfo();

        if (user.getUserType().equals(UserType.STUDENT)) {
             return lectureRepository.findUserLectureList(user.getId());

        } else{
            return lectureRepository.findLectureByProfessor(user);
        }
    }

    public List<LectureInfo> getLectureInfoList(Long lectureId) {
        Lecture lecture = getLectureById(lectureId);
        lecture.getLectureInfoList().forEach(LectureInfo::getLectureTime);
        return lecture.getLectureInfoList();
    }

    @Transactional
    public Lecture createLecture(Lecture lecture) {

        User user = userService.getLoginUserInfo();

        if (!user.getUserType().equals(UserType.PROFESSOR)) {
            throw new NonCriticalException(ErrorMessage.USER_NOT_PROFESSOR);
        }

        lectureRepository.save(lecture);

        return lecture;
    }


    public Lecture getLectureById(Long lectureId){
        return lectureRepository.findById(lectureId)
                .orElseThrow(() -> new NonCriticalException(ErrorMessage.LECTURE_NOT_EXIST));
    }

    public LectureInfo getLectureInfoById(Long lectureInfoId){
        return lectureInfoRepository.findById(lectureInfoId)
                .orElseThrow(()->new NonCriticalException(ErrorMessage.LECTURE_NOT_EXIST));
    }
}
