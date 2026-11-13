package com.politicalpioneer.User;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.politicalpioneer.AuthenticatedNameModel;
import com.politicalpioneer.Party.Party;
import com.politicalpioneer.Party.PartyService;
import com.politicalpioneer.User.UserAlignment.UserAlignment;

@Controller
public class QuestionnaireController {
    private final QuestionnaireService questionnaireService;
    private final UserService userService;
    private final PartyService partyService;

    public QuestionnaireController(
            QuestionnaireService questionnaireService,
            UserService userService,
            PartyService partyService) {
        this.questionnaireService = questionnaireService;
        this.userService = userService;
        this.partyService = partyService;
    }

    @GetMapping("/questionnaire")
    public String showQuestionnaire(
            @RequestParam(defaultValue = "1") int page,
            Authentication authentication,
            Model model) {
        int totalPages = questionnaireService.getTotalPages();
        if (page < 1 || page > totalPages) {
            return "redirect:/questionnaire?page=1";
        }

        User user = userService.getUserByUserName(authentication.getName());
        List<String> sectionTitles = questionnaireService.getSectionTitles();
        Map<Integer, Integer> answers = questionnaireService.getAnswers(user.getUserId());
        Map<String, Object> section;
        if (isOwner(authentication)) {
            Party party = partyService.getPartyByUserId(user.getUserId());
            section = questionnaireService.getSectionView(page, answers, party.getPartyAlignment());
        } else {
            UserAlignment alignment = user.getUserAlignment();
            section = questionnaireService.getSectionView(page, answers, alignment);
        }

        model.addAttribute("pageNumber", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("sectionTitles", sectionTitles);
        model.addAttribute("section", section);
        AuthenticatedNameModel.addNameIfAuthenticated(model, authentication);
        return "questionnaire";
    }

    @GetMapping("/questionnaire/results")
    public String showResults(Authentication authentication, Model model) {
        User user = userService.getUserByUserName(authentication.getName());
        model.addAllAttributes(questionnaireService.getResults(user.getUserId()));
        AuthenticatedNameModel.addNameIfAuthenticated(model, authentication);
        return "results";
    }

    @PostMapping("/questionnaire/answer")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> saveAnswer(
            @RequestBody AnswerRequest request,
            Authentication authentication) {
        if (request.questionId() == null
                || request.value() == null
                || request.value() < 1
                || request.value() > 5
                || !questionnaireService.isValidQuestion(request.questionId())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid question or answer value"));
        }

        User user = userService.getUserByUserName(authentication.getName());
        int score;
        if (isOwner(authentication)) {
            Party party = partyService.getPartyByUserId(user.getUserId());
            score = questionnaireService.saveAnswerForParty(
                    user, party, request.questionId(), request.value());
        } else {
            score = questionnaireService.saveAnswer(user, request.questionId(), request.value());
        }
        return ResponseEntity.ok(Map.of("score", score));
    }

    private static boolean isOwner(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> "owner".equals(authority.getAuthority()));
    }

    public record AnswerRequest(Integer questionId, Integer value) {
    }
}
