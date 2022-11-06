package me.kimyounghan.async.report.service;

import me.kimyounghan.async.report.model.Report;

/**
 * 비동기 서비스
 *
 * @author 김영한
 */
public interface AsyncService {
    Report save(Long id);
}
