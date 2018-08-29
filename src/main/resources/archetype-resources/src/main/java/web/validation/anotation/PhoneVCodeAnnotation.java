package ${groupId}.web.validation.anotation;


import ${groupId}.web.validation.validator.PhoneVCodeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = PhoneVCodeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PhoneVCodeAnnotation {

    String message() default "手机验证码有误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
