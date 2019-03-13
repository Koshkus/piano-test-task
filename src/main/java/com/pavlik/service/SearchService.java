package com.pavlik.service;

import com.pavlik.dto.SearchResult;
import com.pavlik.exception.EmptySearchStringException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {
    @Value("${api.url}")
    private String apiString;

    @Value("${api.filter}")
    private String filter;

    private final RestTemplate restTemplate;

    public SearchResult searchString(String stringToSearch, int page) {
        if (StringUtils.isEmpty(stringToSearch.trim())) {
            log.error("String to search is empty");
            throw new EmptySearchStringException();
        }

        String uri = UriComponentsBuilder.fromHttpUrl(apiString)
                .queryParam("filter", filter)
                .queryParam("page", page)
                .queryParam("intitle", stringToSearch)
                .build().toUriString();

        log.debug("Requesting api to search string - {}", stringToSearch);
        ResponseEntity<SearchResult> entity = restTemplate.getForEntity(uri, SearchResult.class);

        return entity.getBody();
    }
}
