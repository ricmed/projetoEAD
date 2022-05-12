package com.ead.authuser.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameConstraintImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD}) // onde pode ser usada a anotação
@Retention(RetentionPolicy.RUNTIME) // quando a validação irá ocorrer
public @interface UsernameConstraint {
    // parâmetros default do BeanValidation
    String message() default "Invalid username";
    Class<?>[] groups() default {}; // grupos de validação
    Class<? extends Payload>[] payload() default {};
}
