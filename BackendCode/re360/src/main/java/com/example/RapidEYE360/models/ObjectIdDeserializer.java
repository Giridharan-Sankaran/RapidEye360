package com.example.RapidEYE360.models;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import org.bson.types.ObjectId;

import java.io.IOException;

public class ObjectIdDeserializer extends JsonDeserializer<ObjectId> {
    public ObjectIdDeserializer() {
        // Default constructor (no-arg)
    }

    @Override
    public ObjectId deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.readValueAsTree();

        // Check if the "id" field is present and not null
        JsonNode idNode = node.get("$oid");
        if (idNode != null && !idNode.isNull()) {
            String idAsString = idNode.textValue();
            return new ObjectId(idAsString);
        } else {
            // Handle the case where "id" is null or not present (return null or throw an exception)
            return null; // Adjust this line based on your requirements
        }
    }
}
