package ir.nrdc.controller;

import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin")
@PropertySource("classpath:messages.properties")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @GetMapping(value = "")
    public ModelAndView adminDashboard() {
        return new ModelAndView("adminDashboard");
    }
}