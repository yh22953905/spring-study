package me.kimyounghan.validation.valid.validator;

import lombok.RequiredArgsConstructor;
import me.kimyounghan.validation.dao.PaymentDao;
import me.kimyounghan.validation.dto.PaymentDto;
import me.kimyounghan.validation.valid.constraint.PaymentExists;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class PaymentExistsValidator implements ConstraintValidator<PaymentExists, PaymentDto.InsertReq> {
    private final PaymentDao paymentDao;

    @Override
    public boolean isValid(PaymentDto.InsertReq req, ConstraintValidatorContext context) {
        int cnt = paymentDao.paymentExists(req.getTid(), req.getStatusCode());

        return cnt <= 0;
    }
}
