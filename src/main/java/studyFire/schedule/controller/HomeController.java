package studyFire.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import studyFire.schedule.domain.Member;
import studyFire.schedule.domain.form.MemberForm;
import studyFire.schedule.service.MemberService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @GetMapping("/")
    public String home() {
        return "main/home";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("form", new MemberForm());
        return "main/login";
    }

//    @PostMapping("/login")
//    public String login(@ModelAttribute("model") MemberForm form) {
//
//    }

    @GetMapping("/signUp")
    public String signUpPage(Model model) {
        model.addAttribute("form", new MemberForm());
        return "main/signUp";
    }

    @PostMapping("/signUp")
    public String signUp(MemberForm form) {
        Member member = Member.createMember(form.getEmail(), form.getPassword(), form.getName(), form.getAge());
        memberService.join(member);
        return "redirect:/login";
    }

    @GetMapping("/user/home")
    public String userHome(Model model, Principal principal) {
        Member byEmail = memberService.findByEmail(principal.getName());
        model.addAttribute("name", byEmail.getName());
        return "main/afterHome";
    }
}
