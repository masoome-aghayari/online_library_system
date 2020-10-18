package ir.nrdc.controller;

import ir.nrdc.model.dto.BookDto;
import ir.nrdc.model.dto.UserDto;
import ir.nrdc.service.BookService;
import ir.nrdc.service.UserService;
import ir.nrdc.utils.AgeGroup;
import ir.nrdc.validator.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/admin")
@PropertySource("classpath:messages.properties")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    Environment env;
    @Autowired
    BookValidator bookValidator;
    @Autowired
    BookService bookService;

    @GetMapping(value = "")
    public ModelAndView adminDashboard() {
        return new ModelAndView("adminDashboard");
    }

    @GetMapping(value = "bookMenu")
    public ModelAndView showBookOperationsMenu() {
        return new ModelAndView("bookMenu");
    }

    @GetMapping(value = "memberMenu")
    public ModelAndView showMemberOperationsMenu() {
        return new ModelAndView("memberMenu");
    }

    @GetMapping(value = "books/addBook")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new BookDto())
                .addAttribute("ageGroups", AgeGroup.getStringList());
        return "addBook";
    }

    @PostMapping(value = "books/addBook-process")
    public String addBookProcess(@ModelAttribute("book") BookDto book, Model model, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        model.addAttribute("ageGroups", AgeGroup.getStringList());
        if (bindingResult.hasErrors())
            return "addBook";
        bookService.addNewBook(book);
        model.addAttribute("message", env.getProperty("Add.Book.Successful"));
        return showAddBookForm(model);
    }

    @GetMapping(value = "searchProcess/{pageNumber}")
    public ModelAndView searchProcess(@ModelAttribute("user") UserDto userDto,
                                      @PathVariable(required = false) int pageNumber) {
        long totalPages = userService.getTotalNumberOfPages(userDto);
        ModelAndView searchUser = new ModelAndView("searchUser");
        if (totalPages == 0)
            searchUser.addObject("message", env.getProperty("No.User.Found"));
        else {
            int limit = Integer.parseInt(env.getProperty("Page.Rows"));
            List<UserDto> matchedUsers = userService.findMaxMatch(userDto, pageNumber - 1, limit);
            searchUser.addObject("users", matchedUsers)
                    .addObject("pageNumber", pageNumber)
                    .addObject("totalPages", totalPages)
                    .addObject("userDto", userDto);
        }
        return searchUser;
    }
}