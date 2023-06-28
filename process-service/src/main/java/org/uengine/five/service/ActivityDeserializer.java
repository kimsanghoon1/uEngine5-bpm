package org.uengine.five.service;

import java.io.IOException;

import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.bpmn.EndEvent;
import org.uengine.kernel.bpmn.StartEvent;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ActivityDeserializer extends StdDeserializer {
    public ActivityDeserializer() {
        this(null);
    }

    public ActivityDeserializer(Class<?> vc) {
        super(vc);
    }
    
    @Override
    public Object deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        JsonNode node = parser.getCodec().readTree(parser);
        node = removeNullFields(node);
        // Implement your custom deserialization logic here
        // Create an instance of the appropriate subclass based on the JSON data
        // List<Activity> aa = child
        // For example:
        String type = node.get("_type").asText();
        Class clazz;
        try {
            clazz = Class.forName(type);
            return parser.getCodec().treeToValue(node, clazz);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    //     if (type.equals("org.uengine.kernel.bpmn.StartEvent")) {
    //     } if (type.equals("org.uengine.kernel.HumanActivity")) {
    //         return parser.getCodec().treeToValue(node, HumanActivity.class);
    //     } if (type.equals("org.uengine.kernel.bpmn.EndEvent")) {
    //         return parser.getCodec().treeToValue(node, EndEvent.class);
    //     } else {
    //         // Handle other cases or throw an exception
    //         throw new UnsupportedOperationException("Unknown activity type: " + type);
    //     }
    // }

    // public Object classParser(JsonParser parser) throws IOException {
    //     JsonNode node = parser.getCodec().readTree(parser);
    //     node = removeNullFields(node);
    //     String type = node.get("_type").asText();
    //     switch (type) {
    //         case "org.uengine.kernel.bpmn.StartEvent":
    //             return parser.getCodec().treeToValue(node, StartEvent.class);
    //     }
    //     return null;
    // }

    public static JsonNode removeNullFields(JsonNode node) {
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
            objectNode.remove((String) null); // Remove null field if exists

            // Recursively remove null fields from child nodes
            objectNode.fields().forEachRemaining(entry -> {
                JsonNode childNode = entry.getValue();
                if (childNode.isNull()) {
                    objectNode.remove(entry.getKey());
                } else {
                    removeNullFields(childNode);
                }
            });
        } else if (node.isArray()) {
            // Recursively remove null fields from array elements
            node.forEach(ActivityDeserializer::removeNullFields);
        }

        return node;
    }


}
