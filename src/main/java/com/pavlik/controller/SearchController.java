package com.pavlik.controller;

import com.pavlik.dto.SearchResult;
import com.pavlik.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public String search(@RequestParam(name = "string") String stringToSearch, Model model) {
        SearchResult searchResult = searchService.searchString(stringToSearch);
        model.addAttribute("items", searchResult.getItems());
        return "index";
    }

}
