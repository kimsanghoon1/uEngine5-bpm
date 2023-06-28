package org.uengine.five;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertTrue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.uengine.contexts.TextContext;
import org.uengine.five.overriding.ActivityQueue;
import org.uengine.five.overriding.InstanceNameFilter;
import org.uengine.five.overriding.ServiceRegisterDeployFilter;
import org.uengine.five.rpa.RPAActivity;
import org.uengine.kernel.KeyedParameter;
import org.uengine.kernel.EndEventActivity;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.ParameterContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.ResultPayload;
import org.uengine.kernel.bpmn.SequenceFlow;
import org.uengine.kernel.bpmn.StartEvent;
import org.uengine.util.UEngineUtil;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootApplication
@EnableBinding(Streams.class)
@EnableFeignClients
public class ProcessServiceApplication {

    public static ApplicationContext applicationContext;

    public static ObjectMapper objectMapper = createTypedJsonObjectMapper();

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    public static void main(String[] args) {
        applicationContext = SpringApplication.run(ProcessServiceApplication.class, args);
        // runBasicProcess();
        // runBasicProcess();
    }


    @Bean
    public ServiceRegisterDeployFilter serviceRegisterDeployFilter(){
        return new ServiceRegisterDeployFilter();
    }

    @Bean
    public InstanceNameFilter instanceNameFilter(){
        return new InstanceNameFilter();
    }


    @Bean
    public ActivityQueue activityQueue(){return new ActivityQueue();}

    public static ObjectMapper createTypedJsonObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setVisibilityChecker(objectMapper.getSerializationConfig()
                .getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // ignore null
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT); // ignore zero and false when it is int or boolean
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.enableDefaultTypingAsProperty(ObjectMapper.DefaultTyping.NON_CONCRETE_AND_ARRAYS, "_type");
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        return objectMapper;
    }

    // @Bean
    public static void runBasicProcess() throws JsonGenerationException, JsonMappingException, IOException {

        ProcessDefinition processDefinition = new ProcessDefinition();

        ProcessVariable variable1 = new ProcessVariable();
        variable1.setName("url");
        ProcessVariable variable2 = new ProcessVariable();
        variable2.setName("alias");
        ProcessVariable variable3 = new ProcessVariable();
        variable3.setName("browser");
        ProcessVariable variable4 = new ProcessVariable();
        variable4.setName("options");
        // variable1.setVa
        StartEvent act1 = new StartEvent();
        act1.setName("start");

        processDefinition.addChildActivity(act1);

        RPAActivity act2 = new RPAActivity();
        act2.setName("act2");
        
        // Set Parameters
        ParameterContext urlContext = new ParameterContext();
        urlContext.setArgument(new TextContext());
        urlContext.getArgument().setText("url");
        urlContext.setVariable(ProcessVariable.forName("url"));

        ParameterContext aliasContext = new ParameterContext();
        aliasContext.setArgument(new TextContext());
        aliasContext.getArgument().setText("alias");
        aliasContext.setVariable(ProcessVariable.forName("alias"));

        ParameterContext browserContext = new ParameterContext();
        browserContext.setArgument(new TextContext());
        browserContext.getArgument().setText("browser");
        browserContext.setVariable(ProcessVariable.forName("browser"));

        ParameterContext optionsContext = new ParameterContext();
        optionsContext.setArgument(new TextContext());
        optionsContext.getArgument().setText("options");
        optionsContext.setVariable(ProcessVariable.forName("options"));

        ParameterContext keywordsContext = new ParameterContext();
        keywordsContext.setArgument(new TextContext());
        keywordsContext.getArgument().setText("keywords");
        keywordsContext.setVariable(ProcessVariable.forName("keywords"));

        ParameterContext methodsContext = new ParameterContext();
        methodsContext.setArgument(new TextContext());
        methodsContext.getArgument().setText("methods");
        methodsContext.setVariable(ProcessVariable.forName("methods"));

        act2.setParameters(new ParameterContext[]{
            urlContext, aliasContext, browserContext, optionsContext, methodsContext, keywordsContext
        });
        HumanActivity act3 = new HumanActivity();
        EndEventActivity end = new EndEventActivity();
        processDefinition.addChildActivity(act2);
        processDefinition.addChildActivity(act3);
        SequenceFlow sequence = new SequenceFlow();
        // sequence.setSourceRef(act1);
        // sequence.setTargetRef(act2);
        SequenceFlow sequence2 = new SequenceFlow();
        // sequence2.setSourceRef(act2);
        // sequence2.setTargetRef(act3);
        SequenceFlow sequence3 = new SequenceFlow();
        // sequence3.setSourceRef(act3);
        // sequence3.setTargetRef(end);
        processDefinition.addSequenceFlow(sequence);
        processDefinition.addSequenceFlow(sequence2);
        processDefinition.addSequenceFlow(sequence3);
        processDefinition.setProcessVariables(new ProcessVariable[]{variable1, variable2, variable3, variable4});
        processDefinition.afterDeserialization();
        // System.out.println("********");
        // System.out.println(processDefinition.toString());
        // objectMapper.writeValue(new File("example"), processDefinition);
        // ByteArrayOutputStream bao = new ByteArrayOutputStream();
        // UEngineUtil.copyStream(new FileInputStream("example.json"), bao);
        
        System.out.println("********");
        // System.out.println(processDefinition.toString());
        org.uengine.kernel.ProcessInstance instance = applicationContext.getBean(
            org.uengine.kernel.ProcessInstance.class,
            //new Object[]{
            processDefinition,
            null,
            null
            //}
        );

        try {
            // Set Process Variables
            // String testValue = "Test Value";
            String keywords = "Browser";
            String methods = "open_browser";
            String url = "http://rpachallenge.com";
            String alias = "test";
            String browser = "chrome";
            String options = "add_argument('--headless');add_argument('--no-sandbox');add_argument('--single-process')";
            // instance.set("", "var1", testValue);
            instance.set("", "keywords", keywords);
            instance.set("", "methods", methods);
            instance.set("", "url", url);
            instance.set("", "alias", alias);
            instance.set("", "browser", browser);
            instance.set("", "options", options);

            
            // ByteArrayOutputStream bao = new ByteArrayOutputStream();
            // bao.
            
            
            // Process Start
            instance.execute();
            // objectMapper.writeValue(new File("example"), processDefinition);
            // System.out.println(result);
            // return str.substring(0, str.length() - 1);
            // String defString = StringEscapeUtils.unescapeJava(result);
            // ByteArrayOutputStream bao = new ByteArrayOutputStream();
                // UEngineUtil.copyStream(new FileInputStream("example"), bao);
                // String defString = "{\"_type\":\"org.uengine.kernel.ProcessDefinition\"}";
                // String defString = result;
            // ByteArrayOutputStream bao = new ByteArrayOutputStream();
            // UEngineUtil.copyStream(new FileInputStream("example"), bao);

            //// 단계 완료시키기 (python 에서 이벤트가 왔을때 bpm 단계 완료 처리하고 다음단계 액티비티로 넘기는)
            // ResultPayload rp = new ResultPayload();
            // String changedValue = "value is changed by receiving result";
            // rp.setProcessVariableChanges(new KeyedParameter[]{
            //     new KeyedParameter("var1", changedValue)
            // });

            // act2.fireReceived(instance, rp);

            // Object value3 = instance.get("var1");

            // String statusOfAct2 = act2.getStatus(instance);

            // EventHandler[] handlers = instance.getEventHandlersInAction();

            // for(EventHandler handler : handlers){
            //     System.out.println(handler);
            // }
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            // assertTrue(false);
        }

	}

}