package ${groupId}.web.validation.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PhoneVCodeValidator implements ConstraintValidator<${groupId}.web.validation.anotation.PhoneVCodeAnnotation, String> {

    @Autowired
    public PhoneVCodeValidator(RedisTemplate redisTemplate) {
        RedisTemplate redisTemplate1 = redisTemplate;
    }



    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return true;
    }
}
