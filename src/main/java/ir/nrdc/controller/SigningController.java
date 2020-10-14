package ir.nrdc.controller;

import ir.nrdc.model.dto.UserDto;
import ir.nrdc.service.UserService;
import ir.nrdc.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@PropertySource("classpath:messages.properties")
public class SigningController {
    @Autowired
    UserValidator userValidator;
    @Autowired
    UserService userService;
    @Autowired
    Environment env;

    @GetMapping(value = "/register")
    public ModelAndView showRegisterPage(HttpServletRequest request) {
        if (isSignedIn(request))
            return getUserPanel(request.getSession(false));
        ModelAndView register = new ModelAndView("register");
        register.addObject("user", new UserDto());
        return register;
    }

    @GetMapping(value = "/login")
    public ModelAndView showLoginPage(Model model, HttpServletRequest request) {
        if (isSignedIn(request))
            return getUserPanel(request.getSession(false));
        ModelAndView login = new ModelAndView("login");
        login.addObject("user", new UserDto());
        login.addObject("message", model.getAttribute("message"));
        return login;
    }

    @PostMapping(value = "/register-process")
    public String registerProcess(@ModelAttribute UserDto userDto, Model model, BindingResult bindingResult) {
        userValidator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors())
            return "register";
        else {
            userService.registerUser(userDto);
            model.addAttribute("message", env.getProperty("Registration.Successful"));
            return "redirect:/login";
        }
    }

    @PostMapping(value = "/login-process")
    public ModelAndView loginProcess(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String nationalId = request.getParameter("nationalId");
        UserDto desiredUser = userService.findByNationalId(nationalId);
        session.setAttribute("user", desiredUser);
        ModelAndView dashboard = new ModelAndView(desiredUser.getRole() + "Dashboard");
        dashboard.addObject("user", desiredUser);
        return dashboard;
    }

    private ModelAndView getUserPanel(HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("user");
        return new ModelAndView(userDto.getRole().toLowerCase() + "Dashboard");
    }

    private boolean isSignedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("user") != null;
    }
}
