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
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @PostMapping(value = "members/searchProcess/{pageNumber}")
    @ResponseBody
    public void searchProcess(HttpServletRequest request, @ModelAttribute("member") UserDto member,
                              @PathVariable(required = false) int pageNumber) {
        HttpSession session = request.getSession(false);
        session.setAttribute("searchedMember", member);
        if (member == null)
            member = (UserDto) session.getAttribute("searchedMember");
        long totalPages = userService.getTotalNumberOfPages(member);
        ModelAndView searchMember = new ModelAndView("searchMember");
        if (totalPages == 0)
            searchMember.addObject("message", env.getProperty("No.Member.Found"));
        else {
            List<UserDto> matchedMembers = userService.findMaxMatch(member, pageNumber - 1,
                    Integer.parseInt(env.getProperty("Page.Rows")));
            searchMember.addObject("members", matchedMembers)
                    .addObject("pageNumber", pageNumber)
                    .addObject("totalPages", totalPages)
                    .addObject("member", member);
        }
//        return searchMember;
    }

    @GetMapping(value = "books/searchPage")
    public ModelAndView showSearchPage() {
        ModelAndView search = new ModelAndView("searchBook");
        search.addObject("book", new BookDto())
                .addObject("pageNumber", 1);
        return search;
    }

    @PostMapping(value = "books/searchProcess/{pageNumber}")
    @ResponseBody
    public List<BookDto> bookSearchProcess(@RequestBody BookDto bookDto,
                                           @PathVariable(required = false) int pageNumber) {
        long totalPages = bookService.getTotalNumberOfPages(bookDto);
        if (totalPages == 0)
            return null;
        if (pageNumber > totalPages)
            pageNumber = (int) totalPages;
        return bookService.findMaxMatch(bookDto, pageNumber - 1, Integer.parseInt(env.getProperty("Page.Rows")));
    }

    @PostMapping(value = "books/deleteBooks", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void deleteBooksProcess(@RequestBody List<BookDto> bookDtos) {
        bookService.deleteBooks(bookDtos);
    }
}