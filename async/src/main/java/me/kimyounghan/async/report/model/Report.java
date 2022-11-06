package me.kimyounghan.async.report.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
public class Report {
    @Id
    private Long id;

    private String name;

    private BigDecimal amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return id.equals(report.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
