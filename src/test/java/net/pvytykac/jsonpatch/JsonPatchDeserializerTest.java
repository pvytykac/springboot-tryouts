package net.pvytykac.jsonpatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.TextNode;
import net.pvytykac.jsonpatch.JsonPatchOperation.Operation;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Paly
 * @since 2021-10-31
 */
class JsonPatchDeserializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void deserializeTest() throws Exception {
        JSONArray patch = new JSONArray().put(
                new JSONObject()
                    .put("op", "test")
                    .put("path", "field")
                    .put("value", "vajce"));

        JsonPatch deserialized = MAPPER.reader().readValue(patch.toString(), JsonPatch.class);

        assertJsonPatch(deserialized, Operation.TEST, "field", TextNode.valueOf("vajce"), null);
    }

    @Test
    void deserializeRemove() throws Exception {
        JSONArray patch = new JSONArray().put(
                new JSONObject()
                    .put("op", "remove")
                    .put("path", "field"));

        JsonPatch deserialized = MAPPER.reader().readValue(patch.toString(), JsonPatch.class);

        assertJsonPatch(deserialized, Operation.REMOVE, "field", null, null);
    }

    @Test
    void deserializeAdd() throws Exception {
        JSONArray patch = new JSONArray().put(
                new JSONObject()
                    .put("op", "add")
                    .put("path", "field")
                    .put("value", new JSONArray(List.of(1, 2))));

        JsonPatch deserialized = MAPPER.reader().readValue(patch.toString(), JsonPatch.class);

        assertJsonPatch(deserialized, Operation.ADD, "field", MAPPER.createArrayNode().add(1).add(2), null);
    }

    @Test
    void deserializeReplace() throws Exception {
        JSONArray patch = new JSONArray().put(
                new JSONObject()
                    .put("op", "replace")
                    .put("path", "field")
                    .put("value", "vajce"));

        JsonPatch deserialized = MAPPER.reader().readValue(patch.toString(), JsonPatch.class);

        assertJsonPatch(deserialized, Operation.REPLACE, "field", TextNode.valueOf("vajce"), null);
    }

    @Test
    void deserializeMove() throws Exception {
        JSONArray patch = new JSONArray().put(
                new JSONObject()
                    .put("op", "move")
                    .put("from", "source")
                    .put("path", "target"));

        JsonPatch deserialized = MAPPER.reader().readValue(patch.toString(), JsonPatch.class);

        assertJsonPatch(deserialized, Operation.MOVE, "target", null, "source");
    }

    @Test
    void deserializeCopy() throws Exception {
        JSONArray patch = new JSONArray().put(
                new JSONObject()
                    .put("op", "copy")
                    .put("from", "source")
                    .put("path", "target"));

        JsonPatch deserialized = MAPPER.reader().readValue(patch.toString(), JsonPatch.class);

        assertJsonPatch(deserialized, Operation.COPY, "target", null, "source");
    }

    @Test
    void deserializeNotArray() throws Exception {
        JSONObject json = new JSONObject().put("op", "test")
                .put("path", "someField")
                .put("value", "someValue");

        assertThrows(MismatchedInputException.class, () -> MAPPER.reader().readValue(json.toString(), JsonPatch.class));
    }

    @Test
    void deserializeArrayElementNotObject() {
        JSONArray json = new JSONArray(Set.of(1, 2, 3));

        assertThrows(MismatchedInputException.class, () -> MAPPER.reader().readValue(json.toString(), JsonPatch.class));
    }

    @Test
    void deserializeUnknownOp() throws Exception {
        JSONArray patch = new JSONArray().put(
                new JSONObject()
                        .put("op", "vajce")
                        .put("from", "source")
                        .put("path", "target")
                        .put("value", 1));

        JsonPatch deserialized = MAPPER.reader().readValue(patch.toString(), JsonPatch.class);

        assertJsonPatch(deserialized, Operation.UNKNOWN, "target", IntNode.valueOf(1), "source");
    }

    private static void assertJsonPatch(JsonPatch deserialized, Operation op, String path, Object value, String from) {
        assertThat(deserialized.getOperations(), notNullValue());
        assertThat(deserialized.getOperations().size(), is(1));

        JsonPatchOperation operation = deserialized.getOperations().get(0);
        assertThat(operation, notNullValue());

        assertThat(operation.getOp(), is(op));
        assertThat(operation.getPath(), is(path));
        assertThat(operation.getValue(), is(value));
        assertThat(operation.getFrom(), is(from));
    }
}