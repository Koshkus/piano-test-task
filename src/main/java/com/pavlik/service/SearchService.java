package com.pavlik.service;

import com.pavlik.dto.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class SearchService {
    private static final String API = "http://api.stackexchange.com/2.2/search?order=desc&sort=activity&intitle={title}&site=stackoverflow";

    private final RestTemplate restTemplate;

    public SearchResult searchString(String stringToSearch) {
        ResponseEntity<SearchResult> entity = restTemplate.getForEntity(API, SearchResult.class, stringToSearch);

        return entity.getBody();
    }
}
