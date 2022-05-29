package me.kimyounghan.validation.dao;

import me.kimyounghan.validation.dto.PaymentDto;
import me.kimyounghan.validation.vo.PaymentVO;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentDao {
    public List<PaymentVO> selectList(PaymentDto.SelectListReq req) {
        List<PaymentVO> payments = new ArrayList<>();

        PaymentVO payment = new PaymentVO("pay-1", "COMPLETE", LocalDateTime.now());
        payments.add(payment);

        return payments;
    }

    public void updatePayments(PaymentDto.UpdateListReq req) {
    }

    public List<PaymentVO> selectListForExcel(PaymentDto.SelectListReq req) {
        List<PaymentVO> payments = new ArrayList<>();

        PaymentVO payment = new PaymentVO("pay-1", "COMPLETE", LocalDateTime.now());
        payments.add(payment);

        return payments;
    }

    public void insertPayment(PaymentDto.InsertReq req) {
    }

    public int paymentExists(String tid, String statusCode) {
        return 1;
    }
}
