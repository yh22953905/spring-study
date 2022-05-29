package me.kimyounghan.validation.valid.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
@Component
public class ValidBean {
    @Validated
    public void isValidPayment(@Valid @NotNull String tid) {
    }
}
