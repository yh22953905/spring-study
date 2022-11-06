package me.kimyounghan.async.report.service;

import me.kimyounghan.async.report.model.Report;

import java.util.List;

/**
 * 다른 서비스
 *
 * @author 김영한
 */
public interface AnotherService {
    List<Report> save(List<Long> ids);
}
