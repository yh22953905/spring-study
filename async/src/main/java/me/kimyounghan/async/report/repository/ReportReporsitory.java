package me.kimyounghan.async.report.repository;

import me.kimyounghan.async.report.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportReporsitory extends JpaRepository<Report, Long> {
}
