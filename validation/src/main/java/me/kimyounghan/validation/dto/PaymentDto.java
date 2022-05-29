package me.kimyounghan.validation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.kimyounghan.validation.valid.constraint.NoEmoji;
import me.kimyounghan.validation.valid.constraint.PaymentExists;
import me.kimyounghan.validation.valid.group.Excel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class PaymentDto {
    @Data
    @AllArgsConstructor
    public static class SelectListReq {
        @NotEmpty
        private String startDate;

        @NotEmpty
        private String endDate;

        @NotEmpty(groups = Excel.class)
        private String statusCode;
    }

    @Data
    @AllArgsConstructor
    public static class UpdateListReq {
        private List<@Length(max = 10) @NotEmpty String> tids;

        @NoEmoji
        private String statusCode;
    }


    @Data
    @AllArgsConstructor
    @PaymentExists
    public static class InsertReq {
        @NotEmpty
        private String tid;

        @NotEmpty
        private String statusCode;

        @NotEmpty
        private String paymentDateTime;
    }

    @Data
    @AllArgsConstructor
    public static class TestReq {
        @NotEmpty
        private String tid1;

        @org.hibernate.validator.constraints.NotEmpty
        private String tid2;
    }
}
