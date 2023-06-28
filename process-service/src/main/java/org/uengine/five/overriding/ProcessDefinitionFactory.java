package org.uengine.five.overriding;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.uengine.five.framework.ProcessTransactionContext;
import org.uengine.five.service.DefinitionServiceUtil;
import org.uengine.kernel.Activity;
import org.uengine.processmanager.SimulatorTransactionContext;

/**
 * Created by uengine on 2017. 10. 2..
 */
@Component
public class ProcessDefinitionFactory extends org.uengine.kernel.ProcessDefinitionFactory {
    public static final String unstructuredProcessDefinitionLocation = "Unstructured.process";
    public static final String codiProcessDefinitionFolder = "codi";

    public ProcessDefinitionFactory() {
        super(new SimulatorTransactionContext());
    }

    public ProcessDefinitionFactory(ProcessTransactionContext tc) {
        super(tc);
    }


    @Autowired
    DefinitionServiceUtil definitionService;

    public Activity getActivity(String pdvid, boolean caching, boolean withoutInheritance) throws Exception {
        // TODO: 1. 해당 부분 Process Service Methods로 변경
        /**
         * 
         * 구조
         * 1. json으로 조회
        @RequestMapping(value = DEFINITION + "/{defPath:.+}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
        @Override
        public RepresentationModel getDefinition(@PathVariable("defPath") String definitionPath) throws Exception {

            //case of directory:
            IResource resource = new DefaultResource(RESOURCE_ROOT + "/" + definitionPath);
            if (resourceManager.exists(resource) && resourceManager.isContainer(resource)) { // is a folder
                return listDefinition(definitionPath);
            }

            //case of file:
            definitionPath = UEngineUtil.getNamedExtFile(definitionPath, "xml");

            resource = new DefaultResource(RESOURCE_ROOT + "/" + definitionPath);

            if (!resourceManager.exists(resource)) {
                throw new ResourceNotFoundException(); // make 404 error
            }

            DefinitionResource halDefinition = new DefinitionResource(resource);

            return halDefinition;
        }
         */
        // String definition = getDefinition(pdvid);
        // DefinitionWrapper definitionWrapper = objectMapper.readValue(definition, DefinitionWrapper.class);
        return (Activity) definitionService.getDefinition(pdvid); //TODO: definition reference problematic someday
    }

//    protected Object getDefinitionSourceImpl(String location, boolean fromCompilationVersion, boolean shouldBeObjectResult) throws Exception {
//
//    }

    public void removeDefinition(String pdvid) throws Exception {
        throw new Exception("Not implemented.");
    }

    public String[] addDefinitionImpl(String belongingPdid, String defId, int version, String name, String description, boolean isAdhoc, Object definition, String folder, boolean overwrite, Map options) throws Exception {
        throw new Exception("Dont\' use this method.");
    }
}
