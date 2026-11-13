package com.politicalpioneer;

import java.util.List;

import com.politicalpioneer.Party.Party;
import com.politicalpioneer.Party.PartyService;
import com.politicalpioneer.User.User;
import com.politicalpioneer.User.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import ch.qos.logback.core.model.Model;

@Controller
public class AppController {

    private final PartyService partyService;
    private final UserService userService;

    public AppController(
            PartyService partyService,
            UserService userService) {
        this.partyService = partyService;
        this.userService = userService;
    }

    // When a user enters the page the first place
    // we want to send them is not to login but rather
    // the dashboard
    @GetMapping("/")
    public Object showStartPage(Model model, Authentication authentication) {
        addNavigationRoleAttributes(model, authentication);
        AuthenticatedNameModel.addNameIfAuthenticated(model, authentication);
        return "home";
    }

    @GetMapping("/account")
    public Object showAccountPage(Model model, Authentication authentication) {
        addNavigationRoleAttributes(model, authentication);
<<<<<<< Updated upstream
        model.addAttribute("name", name);
        boolean isProfileUser = authentication.getAuthorities().stream()
                .anyMatch(authority -> "owner".equals(authority.getAuthority())
                        || "user".equals(authority.getAuthority()));
        if (isProfileUser) {
            User user = userService.getUserByUserName(name);
            model.addAttribute("profileFirstName", user.getFirstName());
            model.addAttribute("profileLastName", user.getLastName());
            model.addAttribute("profileUserName", user.getUserName());
            model.addAttribute("profileEmail", user.getEmail());
        }
=======
        AuthenticatedNameModel.addNameIfAuthenticated(model, authentication);
>>>>>>> Stashed changes
        return "account";
    }

    @PostMapping("/account/profile")
    public String updateAccountProfile(
            Authentication authentication,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam(required = false) String email,
            RedirectAttributes redirectAttributes) {
        User user = userService.getUserByUserName(authentication.getName());
        userService.updateProfile(user.getUserId(), firstName, lastName, email);
        redirectAttributes.addFlashAttribute("profileUpdated", true);
        return "redirect:/account";
    }

    @GetMapping("/admin")
    public String adminHome(Model model, Authentication authentication) {
        addNavigationRoleAttributes(model, authentication);
        AuthenticatedNameModel.addNameIfAuthenticated(model, authentication);
        return "home";
    }

    @GetMapping("/createAdmin")
    public Object adminSignUpPage(Model model) {
        return "createAdmin";
    }

    // @GetMapping("/home")
    // public Object showHomePage(Model model) {
    // return "home";
    // }

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        addNavigationRoleAttributes(model, authentication);
        AuthenticatedNameModel.addNameIfAuthenticated(model, authentication);
        return "home";
    }

    @GetMapping("/acctlogin")
    public Object showAcctloginPage(Model model) {
        return "acctlogin";
    }

    @GetMapping("/adminLogin")
    public Object showAdminLogin(Model model) {
        return "adminLogin";
    }

    @GetMapping("/signup")
    public Object showSignupPage(Model model) {
        return "signup";
    }

    @GetMapping("/platform")
    public Object showPlatformPage(Model model, Authentication authentication) {
        AuthenticatedNameModel.addNameIfAuthenticated(model, authentication);
        return "platform";
    }

    /*@GetMapping("/resources")
    public Object showResourcesPage(Model model) {
        return "resources";
    }*/

    @GetMapping("/events")
    public Object showEventsPage(Model model, Authentication authentication) {
        addNavigationRoleAttributes(model, authentication);
        AuthenticatedNameModel.addNameIfAuthenticated(model, authentication);
        return "events";
    }

    @GetMapping("/aboutus")
    public Object showAboutusPage(Model model, Authentication authentication) {
        addNavigationRoleAttributes(model, authentication);
        AuthenticatedNameModel.addNameIfAuthenticated(model, authentication);
        return "aboutus";
    }

    @GetMapping("/bugreport")
    public Object showBugreportPage(Model model, Authentication authentication) {
        AuthenticatedNameModel.addNameIfAuthenticated(model, authentication);
        return "bugreport";
    }

    @GetMapping("/contactdevteam")
    public Object showContactdevteamPage(Model model, Authentication authentication) {
        AuthenticatedNameModel.addNameIfAuthenticated(model, authentication);
        return "contactdevteam";
    }

    @GetMapping("/guide")
    public Object showGuidePage(Model model, Authentication authentication) {
        AuthenticatedNameModel.addNameIfAuthenticated(model, authentication);
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

    @GetMapping("/search")
    public String search(
            Model model,
            Authentication authentication,
            @RequestParam(defaultValue = "alignment") String sort,
            @RequestParam(required = false) Double lng,
            @RequestParam(required = false) Double lat,
            @RequestParam(defaultValue = "1000") int radiusKm) {
        boolean isAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("admin"));
        User user = userService.getUserByUserName(authentication.getName());
        

        List<Party> parties = List.of();
        String searchError = null;
        boolean validRadius = radiusKm == 1 || radiusKm == 10 || radiusKm == 100
                || radiusKm == 1_000 || radiusKm == 10_000;
        switch (sort) {
            case "alignment" -> parties = partyService.getPartiesByAlignment(user.getUserId());
            case "location", "default" -> {
                if (!validRadius) {
                    searchError = "Choose a search radius from the available options.";
                } else if (lng == null || lat == null) {
                    searchError = "Allow location access in your browser to sort by location or default.";
                } else if (!Double.isFinite(lng) || !Double.isFinite(lat)
                        || lng < -180 || lng > 180 || lat < -90 || lat > 90) {
                    searchError = "Your browser provided invalid location coordinates.";
                } else if ("location".equals(sort)) {
                    parties = partyService.getPartiesByLocation(lng, lat, radiusKm * 1_000.0);
                } else {
                    parties = partyService.getPartiesDefault(
                            user.getUserId(), lng, lat, radiusKm * 1_000.0);
                }
            }
            default -> searchError = "Unknown party sorting option.";
        }

        model.addAttribute("parties", parties);
        model.addAttribute("sort", sort);
        model.addAttribute("radiusKm", validRadius ? radiusKm : 1_000);
        model.addAttribute("searchError", searchError);
        model.addAttribute("lng", lng);
        model.addAttribute("lat", lat);
        model.addAttribute("isAdmin", isAdmin);
        AuthenticatedNameModel.addNameIfAuthenticated(model, authentication);
        return "partysearch";
    }

    @GetMapping("/resources")
    public String voterResources(
            Model model,
            Authentication authentication) {
        addNavigationRoleAttributes(model, authentication);
        AuthenticatedNameModel.addNameIfAuthenticated(model, authentication);
        return "voterresources";
    }

    @GetMapping("/resources/voter-registration")
    public String voterRegistration(
            Model model,
            Authentication authentication) {
        addNavigationRoleAttributes(model, authentication);
        AuthenticatedNameModel.addNameIfAuthenticated(model, authentication);
        return "voterregistration";
    }

    @GetMapping("/resources/voter-lookup")
    public String voterLookup(
            Model model,
            Authentication authentication) {
        addNavigationRoleAttributes(model, authentication);
        AuthenticatedNameModel.addNameIfAuthenticated(model, authentication);
        return "voterlookup";
    }

    @GetMapping("/resources/polling-places")
    public String pollingPlaces(
            Model model,
            Authentication authentication) {
        addNavigationRoleAttributes(model, authentication);
        AuthenticatedNameModel.addNameIfAuthenticated(model, authentication);
        return "pollingplaces";
    }

    private void addNavigationRoleAttributes(Model model, Authentication authentication) {
        boolean isAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("admin"));
        boolean isOwner = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("owner"));
        boolean isUser = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("user"));
        boolean canCreateParty = false;
        if (isOwner) {
            User owner = userService.getUserByUserName(authentication.getName());
            canCreateParty = !partyService.hasPartyByUserId(owner.getUserId());
        }

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("isUser", isUser);
        model.addAttribute("canCreateParty", canCreateParty);
    }

}
