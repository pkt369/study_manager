package studyFire.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import studyFire.schedule.domain.Member;
import studyFire.schedule.domain.ScheduleContent;
import studyFire.schedule.domain.form.ScheduleForm;
import studyFire.schedule.service.MemberService;
import studyFire.schedule.service.ScheduleService;

import java.security.Principal;
import java.util.List;

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
        System.out.println("여기옴");
        System.out.println("checkArr = " + checkArr.get(0));
        System.out.println("uncheckArr = " + uncheckArr.get(0));
        //이렇게하는 이유는 빈 배열을 넘겨줄때 500에러를 내기때문이다.
        //무조건 배열이 하나 있도록 한뒤 controller에서 없애주면 아무 이상 X
        checkArr.remove(0);
        uncheckArr.remove(0);
        scheduleService.changeSchedule_isEnd(checkArr, uncheckArr);
        return "home";
    }

//    @RequestMapping(value = "/changeCheckboxStatus", method = RequestMethod.POST)
//    @ResponseBody
//    public String checkCheckbox(ChangeCheckboxStatusForm form) {
//        System.out.println("여기옴");
//        scheduleService.changeIsEnd(checkbox, id);
//        return form.getStatus();
//    }


    @GetMapping("/user/calendar")
    public String calendar(Model model, Principal principal) {
        Member member = memberService.findByEmail(principal.getName());
        model.addAttribute("name", member.getName());
        return "/schedule/calendar";
    }


}
