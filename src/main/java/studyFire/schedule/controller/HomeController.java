package studyFire.schedule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import studyFire.schedule.domain.form.MemberForm;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "main/home";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "main/login";
    }

//    @PostMapping("/login")
//    public String login(@ModelAttribute("model") MemberForm form) {
//
//    }
}
