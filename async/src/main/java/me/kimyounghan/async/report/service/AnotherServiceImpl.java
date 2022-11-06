package me.kimyounghan.async.report.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.kimyounghan.async.report.model.Report;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 다른 서비스
 *
 * @author 김영한
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AnotherServiceImpl implements AnotherService {
    private final AsyncService asyncService;

    @Override
    public List<Report> save(List<Long> ids) {
        List<Report> savedReports = new ArrayList<>();

        for (Long id : ids) {
            Report savedReport = asyncService.save(id);
            savedReports.add(savedReport);
        }

        return savedReports;
    }

//    @SneakyThrows
//    @Async
//    Report asyncSave(Long id) {
//        Thread.sleep(1000L);
//        log.info("##### thread name? - {}", Thread.currentThread().getName());
//        log.info("##### id? - {}", id);
//
//        Report savedReport = asyncService.save(id);
//        return savedReport;
//    }
}
