package org.uengine.five.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.uengine.five.entity.ProcessInstanceEntity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessInstance;
import org.uengine.util.UEngineUtil;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;


/**
 * Created by uengine on 2017. 11. 11..
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Relation(value = "instance", collectionRelation = "instances")
public class InstanceResource extends RepresentationModel {

    String name;

    String instanceId;
        public String getInstanceId() {
            return instanceId;
        }
        public void setInstanceId(String instanceId) {
            this.instanceId = instanceId;
        }


    public InstanceResource(){}

    public InstanceResource(ProcessInstance processInstance) throws Exception {
        setName(processInstance.getName());
        setInstanceId(processInstance.getInstanceId());

        add(
                linkTo(
                        methodOn(InstanceService.class).getInstance(processInstance.getInstanceId())
                ).withSelfRel()
        );

        add(
                linkTo(
                        methodOn(VariableService.class).getProcessVariables(processInstance.getInstanceId())
                ).withRel("variables")
        );

        add(
                linkTo(
                        methodOn(org.uengine.five.service.RoleMappingService.class).getRoleMapping(processInstance.getInstanceId(), null)
                ).withRel("role-mapping")
        );
        // 하위 코드는 기존 Definition Service에서 코드를 받아오는 코드임
        // Acebase 경로를 넣어주는 방식으로 고려하여 작성필요.
        // ProcessInstance 값이 Null이 많은 원인 파악 필요함.
        // System.out.println(processInstance.getName());
        // add(
        //         linkTo(
        //                 methodOn(DefinitionServiceUtil.class).getDefinitionPath(processInstance.getName())
        //         ).withRel("definition")
        // );

        // add(
        //         linkTo(
        //                 methodOn(DefinitionService.class).getRawDefinition(UEngineUtil.getNamedExtFile(processInstance.getProcessDefinition().getId(), "json"))
        //         ).withRel("rawDefinition")
        // );

        if(!processInstance.isRunning(""))
            add(
                    linkTo(
                            methodOn(InstanceService.class).start(processInstance.getInstanceId())
                    ).withRel("start")
            );
        else{

            //TODO: create stop method
            add(
                    linkTo(
                            methodOn(InstanceService.class).stop(processInstance.getInstanceId())
                    ).withRel("stop")
            );

            //TODO: create resume method
            if(processInstance.isSuspended("")){
                add(
                        linkTo(
                                methodOn(InstanceService.class).resume(processInstance.getInstanceId())
                        ).withRel("resume")
                );

            }else{
                //TODO: create suspend method
                add(
                        linkTo(
                                methodOn(InstanceService.class).stop(processInstance.getInstanceId())
                        ).withRel("suspend")
                );

            }
        }

        // EntityLinks entityLinks = GlobalContext.getComponent(EntityLinks.class);

        // if(entityLinks!=null){
        //     add(
        //             entityLinks.linkForSingleResource(ProcessInstanceEntity.class, new Long(processInstance.getInstanceId())).withRel("entity")
        //     );
        // }

    }

    public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

}
