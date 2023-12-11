package org.uengine.five.service;

import java.util.Iterator;

import org.springframework.stereotype.Component;
import org.uengine.kernel.ProcessInstance;

@Component
public class InstanceServiceUtil {
    public ProcessInstance mappedOptions(ProcessInstance instance, ExecutionOption options) throws Exception {
        Iterator<String> iterator = options.roleMapping.keySet().iterator();
        while(iterator.hasNext()) {
            String key = iterator.next();
            String value = options.roleMapping.get(key);
            instance.putRoleMapping(key, value);
        }
        return instance;
    }

}
