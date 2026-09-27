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

    

}
