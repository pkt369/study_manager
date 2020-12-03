# study_manager

2020-11-15 프로젝트 시작 (version 0.0.1) <br>

    먼저 계획을 세운 뒤 포토샾으로 미리 스케치 후 작업 <br>
    HTML/CSS으로 Home.html(기본페이지) 완성 - 부트스트랩 이용 <br>

---------------------
![image](https://user-images.githubusercontent.com/66049273/99237542-f85da400-283b-11eb-85ff-00d72cd318bb.png)
---------------------


요구사항 분석
1.	멤버는 스케줄을 만들 수 있다.
2.	멤버는 팀에 들어갈 수 있다.
3.	멤버는 이메일, 비번, 나이, 이름을 입력해야 가입이 가능하다.
4.	팀에서 스케줄을 공유가 가능하다.
5.	팀끼리 달성률에 따라 등수가 바뀐다.
6.	멤버 개인 공부량에 따라 등수가 있다.->멤버 포인트가 있다.
7.	등수를 볼 수 있는 페이지가 있다.
8.	검색 기능을 통해 팀을 찾을 수 있다.
9.	팀안에서 채팅이 가능하다
10.	채팅은 날짜, 멤버, 내용이 있다.


개채 추출
1.	멤버 : 이메일, 비번, 나이, 이름, 멤버포인트, 스케줄, 팀
2.	팀 : 멤버, 달성률, 팀포인트, 채팅
3.	채팅 : 날짜, 멤버, 내용
4.	스케줄 : 달성여부, 스케줄글, 날짜


<hr>
2020-11-16 (version 0.0.2) <br>
요구사항 분석으로 데이터베이스 설계 <br>

데이터베이스 설계
-----------------------
![study_manager_0 0 5_20201120_20_36](https://user-images.githubusercontent.com/66049273/99829580-d21f6780-2b9f-11eb-804d-e0c99c40534a.png)
-----------------------

엔티티 : member, team, chat, chat_message, schedule, schedule_content, wise_saying (7개 설계 완료)
엔티티 설계 완료 후 테스트 작업 성공

<hr>
2020-11-17 (version 0.0.3)<br>
Repository, Service 구현 <br>
기능을 구현했지만 아직 미완성인 느낌이 듭니다. <br> 
테스트하면서 수정을 할 예정입니다.<br>

<hr>
2020-11-18 (version 0.0.35)<br>
Test 절반이상 완성 <br>
Repository, Service 부족한 부분 채웠습니다.<br>

<hr>
2020-11-19 (version 0.0.40) <br>
Test Team-Kickout메서드를 제외한 모든 Test 성공<br>

<hr>
2020-11-20 (version 0.0.41) <br>
데이터베이스 멤버와 팀의 관계에서 null값이 있어 데이터베이스 수정<br>
join_team_member 일대다, 다대일 테이블 생성 => 중간에서 연결해주는 역할 <br>
이후 전체적인 코드 수정 => Test 전체 성공 <br>

<hr>
2020-11-21 (version 0.0.42) <br>
스케줄을 따로 생성하는것이아니라 content 영역에서 날짜에 따라 Schedule이 하나만 생성되도록 구현<br>
로그인 SpringSecurity를 이용해 구현<br>
스케줄 관련 사이트 background 구현<br>

<hr>
2020-11-22 (version 0.0.43) <br>
포토샵을 이용해 먼저 프런트단을 만든뒤 그뒤에 부트스트랩을 이용해 구현<br>
스케줄만들기, 오늘의 스케줄 구현(90%)<br>
데이터 저장 확인<br>

<hr>
2020-11-29 (version 0.0.45) <br>
오늘의 스케줄 체크리스트로 만들기 - ajax 오류로 몇일동안 진행X <br>
오류 해결후 체크후 저장 및 불러오기가능 <br>
캘린더 초기작업 <br>

<hr>
2020-11-30 (version 0.0.47) <br>
달력 구현까지 완료 <br>
달력안에 스케줄보이게 구현할 예정<br>

<hr>
2020-12-01 (version.0.0.48) <br>
달력 전체 완성 <br>
스케줄 관리 페이지 생성 도중 ajax 오류 <br>

<hr>
2020-12-03 (version 0.0.49) <br>
ajax를 통해 검색 기능과 삭제 기능 구현 <br>
저장 구현 미완성 <br>







