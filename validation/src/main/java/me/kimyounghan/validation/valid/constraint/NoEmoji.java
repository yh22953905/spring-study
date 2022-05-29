package me.kimyounghan.validation.valid.constraint;

import me.kimyounghan.validation.valid.validator.NoEmojiValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = NoEmojiValidator.class)
@Documented
public @interface NoEmoji{
    String message() default "{emoji.is.not.allowed}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}