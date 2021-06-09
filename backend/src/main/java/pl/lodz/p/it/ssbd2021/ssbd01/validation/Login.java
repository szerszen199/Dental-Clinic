package pl.lodz.p.it.ssbd2021.ssbd01.validation;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

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
@Size(min = 3, max = 60, message = I18n.LOGIN_INVALID_SIZE)
@Pattern(regexp = "[a-zA-Z0-9]+([-._][a-zA-Z0-9]+)*", message = I18n.LOGIN_NOT_IN_PATTERN)
public @interface Login {
    /**
     * Wiadomość.
     *
     * @return string
     */
    String message() default "{validation.error.login}";

    /**
     * Grupy [ ].
     *
     * @return class [ ]
     */
    Class<?>[] groups() default {};

    /**
     * Payload klasa [ ].
     *
     * @return class [ ]
     */
    Class<? extends Payload>[] payload() default {};
}
