package studyFire.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import studyFire.schedule.domain.Member;
import studyFire.schedule.domain.ScheduleContent;
import studyFire.schedule.domain.form.ScheduleForm;
import studyFire.schedule.service.MemberService;
import studyFire.schedule.service.ScheduleService;

import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final MemberService memberService;


    @GetMapping("/user/createSchedule")
    public String createSchedulePage(Model model, Principal principal) {
        Member byEmail = memberService.findByEmail(principal.getName());
        model.addAttribute("name", byEmail.getName());
        model.addAttribute("form", new ScheduleForm());
        return "schedule/createSchedule";
    }

    @PostMapping("/user/createSchedule")
    public String createSchedule(ScheduleForm form, Principal principal) {
        Member member = memberService.findByEmail(principal.getName());
        ScheduleContent content = ScheduleContent.createContent(form.getContent_header(), form.getContent_body());
        scheduleService.contentSave(content, member, form.getDate());
        return "redirect:/user/createSchedule";
    }

    @GetMapping("/user/todaySchedule")
    public String todaySchedule(Model model, Principal principal) {
        Member member = memberService.findByEmail(principal.getName());
        List<ScheduleContent> scheduleContents = scheduleService.todaySchedule(member);

        model.addAttribute("schedules", scheduleContents);
        model.addAttribute("name", member.getName());
        return "/schedule/todaySchedule";
    }

    @RequestMapping(value = "/changeCheckboxStatus", method = RequestMethod.GET)
    @ResponseBody
    public String checkCheckbox(@RequestParam(value = "checkArr[]", required = false) List<String> checkArr,
                                @RequestParam(value = "uncheckArr[]", required = false) List<String> uncheckArr) {
        //이렇게하는 이유는 빈 배열을 넘겨줄때 500에러를 내기때문이다.
        //무조건 배열이 하나 있도록 한뒤 controller에서 없애주면 아무 이상 X
        checkArr.remove(0);
        uncheckArr.remove(0);
        scheduleService.changeSchedule_isEnd(checkArr, uncheckArr);
        return "home";
    }

    @GetMapping("/user/administrateSchedule")
    public String administrateSchedulePage(Model model, Principal principal) {
        Member member = memberService.findByEmail(principal.getName());

        model.addAttribute("name", member.getName());
        return "/schedule/administrateSchedule";
    }

    @RequestMapping(value = "/bringSchedule", method = RequestMethod.GET)
    @ResponseBody
    public String bringSchedule(@RequestParam(value = "bringDate")String date) {

        LocalDate parseDate = LocalDate.parse(date);
        System.out.println("parseDate = " + parseDate);
        List<ScheduleContent> scheduleByDate = scheduleService.findScheduleByDate(parseDate);
        System.out.println("scheduleByDate.size() = " + scheduleByDate.size());

        JSONArray ja = new JSONArray();

        for (int i = 0; i < scheduleByDate.size(); i++) {
            JSONObject jo = new JSONObject();
            jo.put("id", scheduleByDate.get(i).getId());
            jo.put("header", scheduleByDate.get(i).getContent_header());
            jo.put("body", scheduleByDate.get(i).getContent_body());
            jo.put("isEnd", scheduleByDate.get(i).getIsEnd());
            ja.add(jo);
        }
        return ja.toJSONString();
    }

    @RequestMapping(value = "/deleteSchedule", method = RequestMethod.GET)
    @ResponseBody
    public String deleteSchedule(@RequestParam(value = "bringDate")String date,
                                 @RequestParam(value = "bringId")String bringId) {
        //삭제
        scheduleService.deleteSchedule(bringId);

        //조회
        LocalDate parseDate = LocalDate.parse(date);
        System.out.println("parseDate = " + parseDate);
        List<ScheduleContent> scheduleByDate = scheduleService.findScheduleByDate(parseDate);
        System.out.println("scheduleByDate.size() = " + scheduleByDate.size());

        JSONArray ja = new JSONArray();

        for (int i = 0; i < scheduleByDate.size(); i++) {
            JSONObject jo = new JSONObject();
            jo.put("id", scheduleByDate.get(i).getId());
            jo.put("header", scheduleByDate.get(i).getContent_header());
            jo.put("body", scheduleByDate.get(i).getContent_body());
            jo.put("isEnd", scheduleByDate.get(i).getIsEnd());
            ja.add(jo);
        }
        return ja.toJSONString();
    }

    @RequestMapping(value = "/saveSchedule", method = RequestMethod.GET)
    @ResponseBody
    public String saveSchedule(@RequestParam(value = "bringDate")String date,
                                 @RequestParam(value = "arr")List<String> scheduleList) {
        //저장
        System.out.println(scheduleList.size());
        for (String s : scheduleList) {
            System.out.println(s);
        }

        //조회
        LocalDate parseDate = LocalDate.parse(date);
        System.out.println("parseDate = " + parseDate);
        List<ScheduleContent> scheduleByDate = scheduleService.findScheduleByDate(parseDate);
        System.out.println("scheduleByDate.size() = " + scheduleByDate.size());

        JSONArray ja = new JSONArray();

        for (int i = 0; i < scheduleByDate.size(); i++) {
            JSONObject jo = new JSONObject();
            jo.put("id", scheduleByDate.get(i).getId());
            jo.put("header", scheduleByDate.get(i).getContent_header());
            jo.put("body", scheduleByDate.get(i).getContent_body());
            jo.put("isEnd", scheduleByDate.get(i).getIsEnd());
            ja.add(jo);
        }
        return ja.toJSONString();
    }



//    @RequestMapping(value = "/changeCheckboxStatus", method = RequestMethod.POST)
//    @ResponseBody
//    public String checkCheckbox(ChangeCheckboxStatusForm form) {
//        System.out.println("여기옴");
//        scheduleService.changeIsEnd(checkbox, id);
//        return form.getStatus();
//    }


//    @GetMapping("/user/calendar")
//    public String calendar(Model model, Principal principal) {
//        Member member = memberService.findByEmail(principal.getName());
//        model.addAttribute("name", member.getName());
//        return "/schedule/calendar";
//    }


}
