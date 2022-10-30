# Auto-Check
졸업작품으로 진행한 얼굴인식을 이용한 자동출석체크 시스템 입니다.

CCTV를 이용하여 강의실에 들어오는 학생들의 얼굴을 인식한 후 자동으로 출석을 해줍니다.

## 시스템 구성도
![](https://user-images.githubusercontent.com/26520312/170995202-00b849de-d583-4766-b0b1-37fe43ec357d.PNG)

## 담당한 부분
자바, 스프링부트, AWS 를 이용하여 서버를 구축하였습니다.
얼굴인식과 학습부분은 컴퓨터 자원을 많이 사용하는 작업이므로 출석체크, 회원가입, 강의 등 비즈니스 로직을 담당하는 서버와 
얼굴인식을 담당하는 서버를 분리했습니다.

## 사용 기술
JAVA, SpringBoot, Spring Data JPA, QueryDSL, Mysql, Nginx, EC2, Route53, cerbot

## 얼굴인식
얼굴 탐지는 Retina face, 얼굴 인식은 FaceNet을 이용했습니다.

출석체크는 인식률을 늘리는 것보다는 오인식률을 줄이는 것이 중요하기 때문에 오인식률을 줄이기 위해 노력했습니다.
얼굴의 해상도가 낮거나 전체 얼굴이 나오지 않은 프레임은 제외하였고, FaceNet을 통해 구한 유클리디안 거리값이 0.65 미만으로 4번 이상 인식된 경우에만 출석을 인정하였습니다.

## 안드로이드 주소
https://github.com/jmxx219/AttendanceCheckApp

## 어려웠던 점
nginx를 통한 리버스 프록시와 cerbot을 이용한 https의 적용, route53을 이용한 도메인 서비스 등 직접 모든 인프라를 구성해보는 것이 처음이었고 많이 해맸습니다.

[시연영상](https://www.youtube.com/watch?v=Ptl3tJmzxtE)

