package ir.nrdc.controller;

import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@PropertySource("classpath:messages.properties")
@PreAuthorize("hasAuthority('MEMBER')")
public class MemberController {

}
