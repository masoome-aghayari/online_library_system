package ir.nrdc.controller;

import ir.nrdc.model.dto.BookDto;
import ir.nrdc.model.dto.LendItemDto;
import ir.nrdc.service.BookService;
import ir.nrdc.service.LendItemService;
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

import java.util.List;

@Controller
@RequestMapping(value = "/admin/books")
@PropertySource("classpath:messages.properties")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminBookController {

    @Autowired
    Environment env;

    @Autowired
    LendItemService lendItemService;
    @Autowired
    BookValidator bookValidator;
    @Autowired
    BookService bookService;

    @GetMapping(value = "bookMenu")
    public ModelAndView showBookOperationsMenu() {
        return new ModelAndView("bookMenu");
    }

    @GetMapping(value = "addBook")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new BookDto())
                .addAttribute("ageGroups", AgeGroup.getStringList());
        return "addBook";
    }

    @PostMapping(value = "addBook-process")
    public String addBookProcess(@ModelAttribute("book") BookDto book, Model model, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        model.addAttribute("ageGroups", AgeGroup.getStringList());
        if (bindingResult.hasErrors())
            return "addBook";
        bookService.addNewBook(book);
        model.addAttribute("message", env.getProperty("Add.Book.Successful"));
        return showAddBookForm(model);
    }

    @GetMapping(value = "search")
    public ModelAndView showSearchBookPage() {
        ModelAndView search = new ModelAndView("searchBook");
        search.addObject("book", new BookDto())
                .addObject("pageNumber", 1);
        return search;
    }

    @PostMapping(value = "searchProcess/{pageNumber}")
    @ResponseBody
    public List<BookDto> bookSearchProcess(@RequestBody BookDto bookDto, @PathVariable(required = false) int pageNumber) {
        long totalPages = bookService.getTotalNumberOfPages(bookDto);
        if (totalPages == 0)
            return null;
        if (pageNumber > totalPages)
            pageNumber = (int) totalPages;
        return bookService.findMaxMatch(bookDto, pageNumber - 1, Integer.parseInt(env.getProperty("Page.Rows")));
    }

    @PostMapping(value = "deleteBooks", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void deleteBooksProcess(@RequestBody List<BookDto> bookDtos) {
        bookService.deleteBooks(bookDtos);
    }

     @PostMapping(value = "lend-book-process", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String lendBookProcess(@RequestBody LendItemDto lendItemDto, BindingResult bindingResult) {
        lendItemValidator.validate(lendItemDto, bindingResult);
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors().toString());
            return bindingResult.getAllErrors().toString();
        }
        return lendItemService.lendBook(lendItemDto);
    }
}
