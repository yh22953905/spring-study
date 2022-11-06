package me.kimyounghan.async.report.service;

import me.kimyounghan.async.report.model.Report;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AnotherServiceTest {
    @Autowired
    AnotherService anotherService;

    @Autowired
    AsyncService asyncService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void anotherService_성공() throws InterruptedException {
        int size = 1_000;

        List<Long> ids = new ArrayList<>();
        for (long i = 0; i < size; i++) {
            ids.add(i);
        }

        List<Report> savedReports = anotherService.save(ids);
        
        Thread.sleep(5_000L);

        assertThat(savedReports.size()).isEqualTo(size);
    }

    @Test
    void asyncService_성공() throws InterruptedException {
        Report savedReport = asyncService.save(1L);
        
        Thread.sleep(5_000L);

        assertThat(savedReport).isNotNull();
    }
}