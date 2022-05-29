package me.kimyounghan.validation.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PaymentVO {
    private String tid;

    private String statusCode;

    private LocalDateTime paymentDate;
}
