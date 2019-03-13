package com.pavlik.controller;

import com.pavlik.dto.SearchResult;
import com.pavlik.exception.EmptySearchStringException;
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
                         @RequestParam(name = "pageSize", required = false, defaultValue = "30") int pageSize,
                         Model model) {
        SearchResult searchResult = searchService.searchString(stringToSearch, page);
        model.addAttribute("items", searchResult.getItems());
        model.addAttribute("total", searchResult.getTotal());
        int totalPages = (int) Math.ceil((double) searchResult.getTotal() / pageSize);
        if (totalPages > 1) {
            List<Integer> pages = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pages", pages);
        }
        return "index";
    }

    @ExceptionHandler(EmptySearchStringException.class)
    public String handleWrongInputException(Model model, Exception ex) {
        log.error("Exception raised:", ex);
        model.addAttribute("error", ex.getMessage());
        return "index";
    }

}
