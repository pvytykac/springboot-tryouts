package net.pvytykac.jsonpatch;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import net.pvytykac.jsonpatch.JsonPatchOperation.Operation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Paly
 * @since 2021-10-31
 */
public class JsonPatchDeserializer extends JsonDeserializer<JsonPatch> {

    @Override
    public JsonPatch deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = ctxt.readTree(p);

        if (!node.isArray()) {
            ctxt.reportWrongTokenException(this, JsonToken.START_ARRAY, "expected array of json-patch operations");
        }

        Iterator<JsonNode> operations = node.elements();
        List<JsonPatchOperation> parsed = new ArrayList<>();

        while (operations.hasNext()) {
            JsonPatchOperation operation = parseOperation(operations.next(), ctxt);
            parsed.add(operation);
        }

        return new JsonPatch(List.copyOf(parsed));
    }

    private JsonPatchOperation parseOperation(JsonNode operation, DeserializationContext ctxt)
            throws IOException {
        if (!operation.isObject()) {
            ctxt.reportWrongTokenException(this, JsonToken.START_OBJECT,
                    "expected a JSON object representing the patch operation");
        }

        Operation op;
        try {
            op = operation.has("op") ? Operation.valueOf(operation.get("op").asText().toUpperCase()) : null;
        } catch (IllegalArgumentException ex) {
            op = Operation.UNKNOWN;
        }

        String path = operation.has("path") ? operation.get("path").asText() : null;
        String from = operation.has("from") ? operation.get("from").asText() : null;
        JsonNode value = operation.has("value") ? operation.get("value"): null;

        return new JsonPatchOperation(op, path, from, value);
    }
}
