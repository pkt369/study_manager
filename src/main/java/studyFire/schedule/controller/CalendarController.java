package studyFire.schedule.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import studyFire.schedule.domain.Member;
import studyFire.schedule.domain.ScheduleContent;
import studyFire.schedule.service.MemberService;
import studyFire.schedule.service.ScheduleService;

@Controller
@RequiredArgsConstructor
public class CalendarController {

	@Autowired
	MemberService memberService;
	@Autowired
	ScheduleService scheduleService;

	
	@GetMapping("/user/calendar")
	public String calendar(Model model, DateData dateData, Principal principal){

		Calendar cal = Calendar.getInstance();
		DateData calendarData;
		//검색 날짜
		//데이터가 비어있으면 새롭게 넣기
		if(dateData.getDate().equals("")&&dateData.getMonth().equals("")){
			dateData = new DateData(String.valueOf(cal.get(Calendar.YEAR)),String.valueOf(cal.get(Calendar.MONTH)),String.valueOf(cal.get(Calendar.DATE)),null);
		}
		//검색 날짜 end

		Map<String, Integer> today_info =  dateData.today_info(dateData);
		List<DateData> dateList = new ArrayList<DateData>();


		
		//실질적인 달력 데이터 리스트에 데이터 삽입 시작.
		//일단 시작 인덱스까지 아무것도 없는 데이터 삽입
		for(int i=1; i<today_info.get("start"); i++){
			calendarData= new DateData(null, null, null, null);
			dateList.add(calendarData);
		}
		
		//날짜 삽입
		for (int i = today_info.get("startDay"); i <= today_info.get("endDay"); i++) {
			/*
			* 스케줄 가져오기
			* */
			LocalDate date = LocalDate.of(today_info.get("search_year"), today_info.get("search_month"), i);
			System.out.println("date = " + date);
			List<ScheduleContent> scheduleByDate = scheduleService.findScheduleByDate(date);


			if(i==today_info.get("today")){
				if (scheduleByDate.isEmpty()) {
					calendarData= new DateData(String.valueOf(dateData.getYear()), String.valueOf(dateData.getMonth()), String.valueOf(i), "today");
				}else{
					calendarData= new DateData(String.valueOf(dateData.getYear()), String.valueOf(dateData.getMonth()), String.valueOf(i), "today", scheduleByDate);
				}
			}else{
				if (scheduleByDate.isEmpty()) {
					calendarData= new DateData(String.valueOf(dateData.getYear()), String.valueOf(dateData.getMonth()), String.valueOf(i), "normal_date");
				}else{
					calendarData= new DateData(String.valueOf(dateData.getYear()), String.valueOf(dateData.getMonth()), String.valueOf(i), "normal_date", scheduleByDate);
				}
			}
			dateList.add(calendarData);
		}



		//달력 빈곳 빈 데이터로 삽입
		int index = 7-dateList.size()%7;
		
		if(dateList.size()%7!=0){
			
			for (int i = 0; i < index; i++) {
				calendarData= new DateData(null, null, null, null);
				dateList.add(calendarData);
			}
		}
		System.out.println(dateList);
		System.out.println("search_year : " + today_info.get("search_year"));

		Member member = memberService.findByEmail(principal.getName());
        model.addAttribute("name", member.getName());




		
		//배열에 담음
		model.addAttribute("dateList", dateList);		//날짜 데이터 배열
		model.addAttribute("today_info", today_info);
		return "schedule/calendar";
	}
}
