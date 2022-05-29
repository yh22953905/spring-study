package me.kimyounghan.validation.valid.util;

import me.kimyounghan.validation.dto.PaymentDto;

import javax.validation.*;
import java.util.Set;

public class ValidUtils {
    public static void validateSelectListReq(PaymentDto.SelectListReq req) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        Validator validator = factory.getValidator();

        Set<ConstraintViolation<PaymentDto.SelectListReq>> violations = validator.validate(req);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
