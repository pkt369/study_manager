package studyFire.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import studyFire.schedule.domain.Member;
import studyFire.schedule.domain.Schedule;
import studyFire.schedule.domain.ScheduleContent;
import studyFire.schedule.domain.form.ScheduleForm;
import studyFire.schedule.service.MemberService;
import studyFire.schedule.service.ScheduleService;

import java.security.Principal;
import java.time.LocalDate;
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

    @GetMapping("/user/calendar")
    public String calendar(Model model, Principal principal) {
        Member member = memberService.findByEmail(principal.getName());
        model.addAttribute("name", member.getName());
        return "/schedule/calendar";
    }
}
