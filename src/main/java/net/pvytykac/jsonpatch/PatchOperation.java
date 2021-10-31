package net.pvytykac.jsonpatch;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Paly
 * @since 2021-10-31
 */
@Documented
@Constraint(validatedBy = JsonPatchValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PatchOperation {

    String message() default "invalid patch operation";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
