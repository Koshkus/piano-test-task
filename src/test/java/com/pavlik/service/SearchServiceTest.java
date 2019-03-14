package com.pavlik.service;

import com.pavlik.dto.SearchResult;
import com.pavlik.exception.SearchException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchServiceTest {

    @Autowired
    private SearchService searchService;

    @MockBean
    private RestTemplate restTemplate;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testEmptyStringThrowsException() {
        expectedException.expect(SearchException.class);
        expectedException.expectMessage("String to search is empty");
        searchService.searchString("  ", 1);
    }

    @Test
    public void testNotExistingPage() {
        expectedException.expect(SearchException.class);
        expectedException.expectMessage("Requested page is not found");
        SearchResult searchResult = new SearchResult()
                .setItems(Collections.emptyList())
                .setTotal(10);
        ResponseEntity<SearchResult> response = ResponseEntity.ok(searchResult);
        when(restTemplate.getForEntity(anyString(), eq(SearchResult.class)))
                .thenReturn(response);
        searchService.searchString("string", 2);
    }
}