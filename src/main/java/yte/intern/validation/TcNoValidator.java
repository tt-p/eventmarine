package yte.intern.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TcNoValidator implements ConstraintValidator<TcNo, Long> {

    @Override
    public void initialize(TcNo constraint) {
    }

    @Override
    public boolean isValid(Long tcNo, ConstraintValidatorContext context) {
        if (tcNo == null || tcNo == 0)
            return false;
        else
            return tcNo.toString().length() == 11;
    }
}
