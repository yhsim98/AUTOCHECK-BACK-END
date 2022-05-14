package com.auto.check.schedule;

import com.auto.check.domain.lectureinfo.LectureInfo;
import com.auto.check.service.AttendanceService;
import com.auto.check.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Schedule {

    private final LectureService lectureService;
    private final AttendanceService attendanceService;

    // 매일 매시 50분 실행
    @Scheduled(cron="0 50 * * * ?")
    public void autoFaceCheckSchedule(){
        int week = getWeekOfSemester();
        String dayOfWeek = getDayOfWeek();
        LocalTime current = LocalTime.now();
        current = current.plusMinutes(10);
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

       List<LectureInfo> lectureInfoList = lectureService.getStartLectureInfoList(currentTime, dayOfWeek);

        lectureInfoList.parallelStream()
                .forEach(id->{attendanceService.startFaceCheck(id, week);});

    }

    private String getDayOfWeek(){
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();

        switch (dayOfWeek){
            case MONDAY:
                return "월";
            case TUESDAY:
                return "화";
            case WEDNESDAY:
                return "수";
            case THURSDAY:
                return "목";
            case FRIDAY:
                return "금";
            case SATURDAY:
                return "토";
            case SUNDAY:
                return "일";
            default:
                return null;
        }
    }

    private int getWeekOfSemester() {
        LocalDate currentDate = LocalDate.now();
        Integer year = currentDate.getYear();
        LocalDate semesterStart = LocalDate.of(year, 3, 1);

        int currentWeekOfYear = currentDate.get(WeekFields.ISO.weekOfYear());
        int startWeekOfYear = semesterStart.get(WeekFields.ISO.weekOfYear());

        return currentWeekOfYear - startWeekOfYear + 1;
    }
}
