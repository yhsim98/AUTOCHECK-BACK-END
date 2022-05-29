package com.auto.check.service;

import com.auto.check.domain.attendance.Attendance;
import com.auto.check.domain.attendance.AttendanceRepository;
import com.auto.check.domain.lecture.Lecture;
import com.auto.check.domain.lectureinfo.LectureInfo;
import com.auto.check.domain.user.User;
import com.auto.check.enums.ErrorMessage;
import com.auto.check.exception.NonCriticalException;
import com.auto.check.service.dto.FaceCheckResponseDTO;
import com.auto.check.service.dto.RequestFaceCheckDTO;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserService userService;
    private final LectureService lectureService;
    private final RegistrationService registrationService;
    private final WebClient webClient;

    @Value("${face-server.address}")
    private String faceServerAddress;

    public void startFaceCheck(Long lectureInfoId, int week){
        requestFaceCheck(lectureService.getLectureInfoById(lectureInfoId), week);
    }

    public void requestFaceCheck(LectureInfo lectureInfo, int week) {
        RequestFaceCheckDTO requestFaceCheckDTO = new RequestFaceCheckDTO(lectureInfo.getLecture(), lectureInfo.getId()
                , userService.getLectureRelatedUsers(lectureInfo.getLecture()), week);

        List<Long> studentList = webClient.mutate()
                .baseUrl(faceServerAddress)
                .build()
                .post()
                .uri("/face_recognition")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestFaceCheckDTO)
                .retrieve()
                .bodyToMono(FaceCheckResponseDTO.class)
                .flux()
                .toStream()
                .findFirst()
                .orElseThrow(() -> new NonCriticalException(ErrorMessage.FACE_SERVER_EXCEPTION))
                .getStudentList();

        //this.patchStudentsAttendance(lectureInfoId, studentList, week);
    }

    public void alterStudentAttendance(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                        .orElseThrow(() -> new NonCriticalException(ErrorMessage.UNDEFINED_EXCEPTION));

        attendance.updateIsAttend();
    }

    public void singUpClass(Long lectureId){
        User user = userService.getLoginUserInfo();
        Lecture lecture = lectureService.getLectureById(lectureId);
        registrationService.singUpClass(user, lecture);

        for (int i = 1; i <= 14; i++) {
            for (var l : lecture.getLectureInfoList()) {
                Attendance attendance = Attendance.builder()
                        .user(user)
                        .week(i)
                        .isAttend((short) 0)
                        .lectureInfo(l).build();

                attendanceRepository.save(attendance);
            }
        }
    }

    public List<Attendance> getStudentLectureAllAttendance(Long lectureId) {
        User loginUser = userService.getLoginUserInfo();
        Lecture lecture = lectureService.getLectureById(lectureId);

        if(!registrationService.isRegistration(loginUser, lecture)){
            throw new NonCriticalException(ErrorMessage.STUDENT_NOT_REGISTRATION);
        }

        return attendanceRepository.findByUserAndLectureInfoIn(loginUser, lecture.getLectureInfoList());
    }

    public List<Attendance> getLectureStudentsAttendanceList(int week, Long lectureInfoId){
        LectureInfo lectureInfo = lectureService.getLectureInfoById(lectureInfoId);
        return attendanceRepository.findAttendanceByLectureInfoAndWeek(lectureInfo, week);
    }

    public void patchStudentsAttendance(Long lectureInfoId, List<Long> studentsId, int week){
        if(studentsId == null){
            return;
        }
        LectureInfo lectureInfo = lectureService.getLectureInfoById(lectureInfoId);
        List<User> students = userService.getUsersByIds(studentsId);
        attendanceRepository.updateStudentsAttendance(students, lectureInfo, week);
    }
}
