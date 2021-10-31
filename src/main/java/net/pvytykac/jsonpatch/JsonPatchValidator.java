package net.pvytykac.jsonpatch;

import net.pvytykac.jsonpatch.JsonPatchOperation.Operation;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Paly
 * @since 2021-10-31
 */
public class JsonPatchValidator implements ConstraintValidator<PatchOperation, JsonPatchOperation> {

    @Override
    public boolean isValid(JsonPatchOperation operation, ConstraintValidatorContext context) {
        if (operation == null) {
            context.buildConstraintViolationWithTemplate("operation object cannot be null")
                    .addBeanNode()
                    .addConstraintViolation();
            return false;
        }

        boolean valid = true;
        Operation op = operation.getOp();
        Object value = operation.getValue();
        String path = operation.getPath();
        String from = operation.getFrom();

        if (op == null || op == Operation.UNKNOWN) {
            context.buildConstraintViolationWithTemplate("null or unknown operation type")
                    .addPropertyNode("op")
                    .addConstraintViolation();
            valid = false;
        }

        if ((op == Operation.TEST || op == Operation.ADD || op == Operation.REPLACE)
                && (value == null || !StringUtils.hasText(path))) {
            context.buildConstraintViolationWithTemplate("missing required fields for operation '"
                            + op.toString().toLowerCase() + "': 'value' and/or 'path'")
                    .addBeanNode()
                    .addConstraintViolation();
            valid = false;
        }

        if (op == Operation.REMOVE && !StringUtils.hasText(path)) {
            context.buildConstraintViolationWithTemplate("missing required field for operation 'remove': 'path'")
                    .addBeanNode()
                    .addConstraintViolation();
            valid = false;
        }

        if ((op == Operation.MOVE || op == Operation.COPY) && (!StringUtils.hasText(path) || !StringUtils.hasText(from))) {
            context.buildConstraintViolationWithTemplate("missing required field for operation '"
                            + op.toString().toLowerCase() + "': 'path' and/or 'from'")
                    .addBeanNode()
                    .addConstraintViolation();
            valid = false;
        }

        if (!valid) {
            context.disableDefaultConstraintViolation();
        }

        return valid;
    }
}
