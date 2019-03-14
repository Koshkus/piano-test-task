package com.pavlik.controller;

import com.pavlik.dto.SearchResult;
import com.pavlik.exception.SearchException;
import com.pavlik.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/search")
@RequiredArgsConstructor
@Slf4j
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public String search(@RequestParam(name = "string") String stringToSearch,
                         @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                         Model model) {
        SearchResult searchResult = searchService.searchString(stringToSearch, page);
        model.addAttribute("items", searchResult.getItems());
        model.addAttribute("total", searchResult.getTotal());
        int totalPages = (int) Math.ceil((double) searchResult.getTotal() / searchResult.getPageSize());
        if (totalPages > 1) {
            List<Integer> pages;
            int pageRange = page + 5;
            if (page > 5 && totalPages > pageRange) {
                pages = IntStream.rangeClosed(page - 5, pageRange)
                        .boxed()
                        .collect(Collectors.toList());
            } else if (page > totalPages - 5) {
                pages = IntStream.rangeClosed(totalPages - 10, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
            } else {
                pages = IntStream.rangeClosed(1, totalPages > 11 ? 11 : totalPages)
                        .boxed()
                        .collect(Collectors.toList());
            }
            model.addAttribute("pages", pages);
            model.addAttribute("maxPage", totalPages);
        }
        return "index";
    }

    @ExceptionHandler(SearchException.class)
    public String handleWrongInputException(Model model, Exception ex) {
        log.error("Exception raised:", ex);
        model.addAttribute("error", ex.getMessage());
        return "index";
    }

}
