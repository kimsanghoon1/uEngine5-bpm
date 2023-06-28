package org.uengine.five.events;

/**
 * Created by uengine on 2018. 11. 17..
 */
public class ActivityInfo extends BusinessEvent{

    private String instanceId;
    private String tracingTag;
    private Object result;

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setTracingTag(String tracingTag) {
        this.tracingTag = tracingTag;
    }

    public String getTracingTag() {
        return tracingTag;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
