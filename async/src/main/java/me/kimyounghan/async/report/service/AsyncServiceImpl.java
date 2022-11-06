package me.kimyounghan.async.report.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.kimyounghan.async.report.model.Report;
import me.kimyounghan.async.report.repository.ReportReporsitory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 비동기 서비스 impl
 *
 * @author 김영한
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncServiceImpl implements AsyncService {
    private final ReportReporsitory reportReporsitory;

    @SneakyThrows
//    @Async
    @Override
    public Report save(Long id) {
        log.info("##### id - {}", id);
        log.info("##### thread name - {}", Thread.currentThread().getName());

        Report savedReport = reportReporsitory.save(Report.builder()
            .id(id)
            .name("name" + id)
            .amount(BigDecimal.valueOf(1000L))
            .build());

        Thread.sleep(100L);

        return savedReport;
    }
}
