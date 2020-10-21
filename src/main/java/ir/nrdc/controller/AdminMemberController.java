package ir.nrdc.controller;

import ir.nrdc.model.UserIds;
import ir.nrdc.model.dto.UserDto;
import ir.nrdc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin/members")
@PropertySource("classpath:messages.properties")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminMemberController {
    @Autowired
    UserService userService;
    @Autowired
    Environment env;

    @GetMapping(value = "")
    public ModelAndView showSearchMemberPage() {
        ModelAndView search = new ModelAndView("searchMember");
        search.addObject("member", new UserDto())
                .addObject("pageNumber", 1);
        return search;
    }

    @PostMapping(value = "searchProcess/{pageNumber}")
    @ResponseBody
    public List<UserDto> searchProcess(@RequestBody UserDto member, @PathVariable(required = false) int pageNumber) {
        long totalPages = userService.getTotalNumberOfPages(member);
        if (totalPages == 0)
            return null;
        if (pageNumber > totalPages)
            pageNumber = (int) totalPages;
        return userService.findMaxMatch(member, pageNumber - 1, Integer.parseInt(env.getProperty("Page.Rows")));
    }

    @PostMapping(value = "deleteMembers", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void deleteMembersProcess(@RequestBody List<UserIds> memberIds) {
        userService.deleteMembers(memberIds);
    }

    @PostMapping(value = "editMember", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String editMembersProcess(@RequestBody UserDto member) {
        userService.editMember(member);
        return env.getProperty("Edit.Member.Successful");
    }
}