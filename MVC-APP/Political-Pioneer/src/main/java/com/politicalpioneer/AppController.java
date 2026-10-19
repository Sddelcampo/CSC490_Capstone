package com.politicalpioneer;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// import ch.qos.logback.core.model.Model;

@Controller
public class AppController {

    // When a user enters the page the first place
    // we want to send them is not to login but rather
    // the dashboard
    @GetMapping("/")
    public Object showStartPage(Model model) {
        return "platform";
    }

    @GetMapping("/account")
    public Object showAccountPage(Model model) {
        return "account";
    }

    @GetMapping("/questionnaire")
    public Object showQuestionnairePage(Model model) {
        return "questionnaire";
    }

    // @GetMapping("/home")
    // public Object showHomePage(Model model) {
    //     return "home";
    // }

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        boolean isAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("admin"));
        model.addAttribute("isAdmin", isAdmin);
        return "home";
    }

    @GetMapping("/acctlogin")
    public Object showAcctloginPage(Model model) {
        return "acctlogin";
    }

    @GetMapping("/signup")
    public Object showSignupPage(Model model) {
        return "signup";
    }

    @GetMapping("/platform")
    public Object showPlatformPage(Model model) {
        return "platform";
    }

    @GetMapping("/resources")
    public Object showResourcesPage(Model model) {
        return "resources";
    }

    @GetMapping("/events")
    public Object showEventsPage(Model model) {
        return "events";
    }

    @GetMapping("/aboutus")
    public Object showAboutusPage(Model model) {
        return "aboutus";
    }

    @GetMapping("/bugreport")
    public Object showBugreportPage(Model model) {
        return "bugreport";
    }

    @GetMapping("/contactdevteam")
    public Object showContactdevteamPage(Model model) {
        return "contactdevteam";
    }

    @GetMapping("/guide")
    public Object showGuidePage(Model model) {
        return "guide";
    }

    @GetMapping("/logout")
    public Object returnHomeAfterLogout(Model model) {
        return "/";
    }
    // @GetMapping("/login")
    // public Object loginToAccount(Model model) {
    // return "home";
    // }
}
