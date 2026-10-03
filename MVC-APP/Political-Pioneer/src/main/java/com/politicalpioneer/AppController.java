package com.politicalpioneer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


// import ch.qos.logback.core.model.Model;

@Controller 
public class AppController {
    


    @GetMapping("/")
    public Object showHomePage(Model model) {
        return "home";
    }

    @GetMapping("/account")
    public Object showAccountPage(Model model) {
        return "account";
    }

    @GetMapping("/questions")
    public Object showQuestionsPage(Model model) {
        return "questions";
    }

    @GetMapping("/questionnaire")
    public Object showQuestionnairePage(Model model) {
        return "questionnaire";
    }

}
