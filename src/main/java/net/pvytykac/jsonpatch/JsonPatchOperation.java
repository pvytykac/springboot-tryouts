package net.pvytykac.jsonpatch;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Paly
 * @since 2021-10-31
 */
@Data
@PatchOperation
public class JsonPatchOperation {

    @NotNull
    private final Operation op;

    @NotBlank
    private final String path;

    private final String from;
    private final JsonNode value;

    enum Operation {
        ADD, COPY, MOVE, REMOVE, REPLACE, TEST, UNKNOWN;
    }
}
