package me.kimyounghan.validation.valid.validator;

import com.vdurmont.emoji.EmojiParser;
import me.kimyounghan.validation.valid.constraint.NoEmoji;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoEmojiValidator implements ConstraintValidator<NoEmoji, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return EmojiParser.parseToAliases(value).equals(value);
    }
}