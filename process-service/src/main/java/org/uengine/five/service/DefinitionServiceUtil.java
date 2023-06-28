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
               
                String json = "{\"value\": {\"_type\": \"org.uengine.five.service.Person\", \"name\": \"person name\", \"age\": 30, \"addresses\": [{\"_type\": \"org.uengine.five.service.Address\", \"city\":\"seoul\"},{\"_type\": \"org.uengine.five.service.GlobalAddress\", \"country\":\"Korea\", \"city\": \"yongin\"}]}}";
                ObjectMapper mapper = new ObjectMapper();

                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                TypeResolverBuilder<?> typeResolver = new CustomTypeResolverBuilder();
                typeResolver.init(JsonTypeInfo.Id.CLASS, null);
                typeResolver.typeIdVisibility(false);
                typeResolver.inclusion(JsonTypeInfo.As.PROPERTY);
                typeResolver.typeProperty("_type");
                mapper.setDefaultTyping(typeResolver);

                Root root = mapper.readValue(json, Root.class);

                Person person = (Person)root.getValue();
                System.out.println("Name: " + person.getName());
                System.out.println("Age: " + person.getAge());
        
                System.out.println("Address1: " + person.getAddresses().get(0).getClass());
                System.out.println("Address2: " + person.getAddresses().get(1).getClass());

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