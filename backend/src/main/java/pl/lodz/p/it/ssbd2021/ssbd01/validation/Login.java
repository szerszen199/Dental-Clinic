package pl.lodz.p.it.ssbd2021.ssbd01.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * Interfejs Login służący do weryfikacji loginu BeanValidation.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {})
@Retention(RUNTIME)
@Size(min = 3, max = 30)
@Pattern(regexp = "[a-zA-Z0-9]+([-._][a-zA-Z0-9]+)*")
public @interface Login {
    /**
     * Message string.
     *
     * @return string
     */
    String message() default "{validation.error.login}";

    /**
     * Groups class [ ].
     *
     * @return class [ ]
     */
    Class<?>[] groups() default {};

    /**
     * Payload class [ ].
     *
     * @return class [ ]
     */
    Class<? extends Payload>[] payload() default {};
}
