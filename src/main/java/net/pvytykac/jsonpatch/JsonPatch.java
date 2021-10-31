package net.pvytykac.jsonpatch;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Paly
 * @since 2021-10-31
 */
@Data
@JsonDeserialize(using = JsonPatchDeserializer.class)
public class JsonPatch {

    @NotEmpty
    @Valid
    private final List<JsonPatchOperation> operations;

}
