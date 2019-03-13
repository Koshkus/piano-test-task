package com.pavlik.service;

import com.pavlik.exception.EmptySearchStringException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchServiceTest {

    @Autowired
    SearchService searchService;

    @Test(expected = EmptySearchStringException.class)
    public void testEmptyStringThrowsException() {
        searchService.searchString("  ", 1);
    }
}