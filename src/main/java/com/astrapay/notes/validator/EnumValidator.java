package com.astrapay.notes.validator;

import com.astrapay.notes.validator.annotation.ValidEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
  private Enum<?>[] enumValues;

  @Override
  public void initialize(ValidEnum constraintAnnotation) {
    enumValues = constraintAnnotation.enumClass().getEnumConstants();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true; // Biarkan @NotNull yang menangani validasi null
    }
    return Arrays.stream(enumValues).anyMatch(enumValue -> enumValue.name().equals(value));
  }
}
