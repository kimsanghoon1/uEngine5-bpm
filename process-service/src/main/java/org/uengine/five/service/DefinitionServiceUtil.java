package org.uengine.five.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.uengine.kernel.Activity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.bpmn.SequenceFlow;
import org.uengine.modeling.resource.Serializer;
import org.uengine.kernel.Condition;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Created by uengine on 2017. 11. 15..
 */
@Component
public class DefinitionServiceUtil {

    // @Autowired
    // DefinitionXMLService definitionService;

    static ObjectMapper objectMapper = createTypedJsonObjectMapper();

    public Object getDefinition(String pdvid) throws Exception {
        return getDefinition(pdvid, false);
    }

    public URI getDefinitionPath(String pdvid) throws URISyntaxException {
        return new URI("http://localhost:5758/api/definitions/"+pdvid);
    }

    public Object getDefinition(String pdvid, boolean production) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // String version = getDefinition(pdvid);
            HttpUriRequest httpget = RequestBuilder.get()
                    .setUri(getDefinitionPath(pdvid))
                    .build();

            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // todolist example
                // String json = "{\"value\": {\"_type\": \"org.uengine.five.service.Person\", \"name\": \"person name\", \"age\": 30, \"addresses\": [{\"_type\": \"org.uengine.five.service.Address\", \"city\":\"seoul\"},{\"_type\": \"org.uengine.five.service.GlobalAddress\", \"country\":\"Korea\", \"city\": \"yongin\"}]}}";
                
                // bpm data
                String json = "{\"_type\":\"org.uengine.kernel.ProcessDefinition\",\"processVariableDescriptors\":[{\"name\":\"var1\",\"displayName\":{\"text\":\"var1\",\"_type\":\"org.uengine.contexts.TextContext\"},\"defaultValueInString\":\"var1\",\"global\":false,\"persistOption\":\"BPMS\",\"typeClassName\":\"java.lang.String\",\"_type\":\"[Lorg.uengine.kernel.ProcessVariable\"},{\"name\":\"var2\",\"displayName\":{\"text\":\"var2\",\"_type\":\"org.uengine.contexts.TextContext\"},\"defaultValueInString\":\"var2\",\"global\":false,\"persistOption\":\"BPMS\",\"typeClassName\":\"java.lang.String\",\"_type\":\"[Lorg.uengine.kernel.ProcessVariable\"}],\"name\":\"6c22cfcddedbc88ce32b8ad3550121a4\",\"version\":3,\"scm\":{\"tag\":null,\"org\":null,\"repo\":null,\"forkedOrg\":null,\"forkedRepo\":null},\"_changedByLocaleSelector\":false,\"childActivities\":[{\"_type\":\"org.uengine.kernel.bpmn.StartEvent\",\"name\":{\"_type\":\"org.uengine.contexts.TextContext\",\"text\":\"start\"},\"tracingTag\":\"1\",\"selected\":false},{\"_type\":\"org.uengine.kernel.bpmn.EndEvent\",\"name\":{\"_type\":\"org.uengine.contexts.TextContext\",\"text\":\"end\"},\"tracingTag\":\"4\",\"selected\":false},{\"_type\":\"org.uengine.kernel.HumanActivity\",\"name\":{\"_type\":\"org.uengine.contexts.TextContext\",\"text\":\"task1\"},\"role\":{\"name\":null},\"parameters\":[{\"direction\":\"IN-OUT\",\"variable\":{\"name\":\"var1\"},\"argument\":{\"text\":\"arg\"}}],\"tracingTag\":\"2\",\"selected\":false,\"oldName\":\"\"},{\"_type\":\"org.uengine.kernel.HumanActivity\",\"name\":{\"_type\":\"org.uengine.contexts.TextContext\",\"text\":\"task2\"},\"role\":{\"name\":null},\"parameters\":[{\"direction\":\"IN-OUT\",\"variable\":{\"name\":\"var2\"},\"argument\":{\"text\":\"arg\"}}],\"tracingTag\":\"3\",\"selected\":false,\"oldName\":\"\"}],\"sequenceFlows\":[{\"name\":\"\",\"_type\":\"org.uengine.kernel.bpmn.SequenceFlow\",\"selected\":false,\"sourceRef\":\"1\",\"targetRef\":\"2\",\"from\":\"8690e0ac-6521-a173-8451-0c525316c714\",\"to\":\"31aaa217-3f34-7366-3422-2714f89497a1\",\"priority\":1,\"condition\":{\"_type\":\"org.uengine.kernel.Evaluate\",\"pv\":{\"_type\":\"org.uengine.kernel.ProcessVariable\",\"name\":\"\"},\"condition\":\"==\",\"val\":\"\"}},{\"name\":\"\",\"_type\":\"org.uengine.kernel.bpmn.SequenceFlow\",\"selected\":false,\"sourceRef\":\"2\",\"targetRef\":\"3\",\"from\":\"31aaa217-3f34-7366-3422-2714f89497a1\",\"to\":\"21abf127-709f-c1c2-5573-7882157048e5\",\"priority\":1,\"condition\":{\"_type\":\"org.uengine.kernel.Evaluate\",\"pv\":{\"_type\":\"org.uengine.kernel.ProcessVariable\",\"name\":\"\"},\"condition\":\"==\",\"val\":\"\"}},{\"name\":\"\",\"_type\":\"org.uengine.kernel.bpmn.SequenceFlow\",\"selected\":false,\"sourceRef\":\"3\",\"targetRef\":\"4\",\"from\":\"21abf127-709f-c1c2-5573-7882157048e5\",\"to\":\"d95a0124-9edf-2423-9627-eeba33d660ce\",\"priority\":1,\"condition\":{\"_type\":\"org.uengine.kernel.Evaluate\",\"pv\":{\"_type\":\"org.uengine.kernel.ProcessVariable\",\"name\":\"\"},\"condition\":\"==\",\"val\":\"\"}}]}";
                ObjectMapper mapper = new ObjectMapper();

                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                TypeResolverBuilder<?> typeResolver = new CustomTypeResolverBuilder();
                typeResolver.init(JsonTypeInfo.Id.CLASS, null);
                typeResolver.typeIdVisibility(false);
                typeResolver.inclusion(JsonTypeInfo.As.PROPERTY);
                typeResolver.typeProperty("_type");
                mapper.setDefaultTyping(typeResolver);

                // Root root = mapper.readValue(json, Root.class);
                ProcessDefinition test = mapper.readValue(json, ProcessDefinition.class);
                
                // Person person = (Person)root.getValue();
                // System.out.println("Name: " + person.getName());
                // System.out.println("Age: " + person.getAge());
        
                // System.out.println("Address1: " + person.getAddresses().get(0).getClass());
                // System.out.println("Address2: " + person.getAddresses().get(1).getClass());

                String result = EntityUtils.toString(response.getEntity());
                System.out.println(result);
        
                String defString = result.toString();
                ProcessDefinition pd = mapper.readValue(defString, ProcessDefinition.class);
             
                return pd;

            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

    public static ObjectMapper createTypedJsonObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // objectMapper.setVisibilityChecker(objectMapper.getSerializationConfig()
        //         .getDefaultVisibilityChecker()
        //         .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
        //         .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
        //         .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
        //         .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

        // objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // TypeResolverBuilder<?> typeResolver = new CustomTypeResolverBuilder();
        // typeResolver.init(JsonTypeInfo.Id.CLASS, null);
        // typeResolver.typeIdVisibility(false);
        // typeResolver.inclusion(JsonTypeInfo.As.PROPERTY);
        // typeResolver.typeProperty("_type");
        // objectMapper.setDefaultTyping(typeResolver);
        // objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // ignore null
        // objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT); // ignore zero and false when it is int or boolean
        // objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // SimpleModule activityModule = new SimpleModule();
        // SimpleModule sequenceModule = new SimpleModule();
        // activityModule.addDeserializer(Activity.class, new ActivityDeserializer());
        // sequenceModule.addDeserializer(Condition.class, new SequenceFlowDeserializer());
        // objectMapper.registerModule(activityModule);
        // objectMapper.registerModule(sequenceModule);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        TypeResolverBuilder<?> typeResolver = new CustomTypeResolverBuilder();
        typeResolver.init(JsonTypeInfo.Id.CLASS, null);
        typeResolver.typeIdVisibility(false);
        typeResolver.inclusion(JsonTypeInfo.As.PROPERTY);
        typeResolver.typeProperty("_type");
        objectMapper.setDefaultTyping(typeResolver);
        // objectMapper.enableDefaultTypingAsProperty(ObjectMapper.DefaultTyping.NON_FINAL, "_type");
        // objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), DefaultTyping.NON_FINAL);
        // Activity activity = new Activity();
        return objectMapper;
    }

}