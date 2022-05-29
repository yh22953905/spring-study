package me.kimyounghan.validation.service.impl;

import lombok.RequiredArgsConstructor;
import me.kimyounghan.validation.dao.PaymentDao;
import me.kimyounghan.validation.dto.PaymentDto;
import me.kimyounghan.validation.service.PaymentService;
import me.kimyounghan.validation.vo.PaymentVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentDao paymentDao;

    @Override
    public List<PaymentVO> selectList(PaymentDto.SelectListReq req) {
        return paymentDao.selectList(req);
    }

    @Override
    public void updateList(PaymentDto.UpdateListReq req) {
        paymentDao.updatePayments(req);
    }

    @Override
    public List<PaymentVO> selectListForExcel(PaymentDto.SelectListReq req) {
        return paymentDao.selectListForExcel(req);
    }

    @Override
    public void insert(PaymentDto.InsertReq req) {
        paymentDao.insertPayment(req);
    }
}
