package studyFire.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import studyFire.schedule.domain.Member;
import studyFire.schedule.domain.form.ScheduleForm;
import studyFire.schedule.service.MemberService;
import studyFire.schedule.service.ScheduleService;

import java.security.Principal;

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
}
