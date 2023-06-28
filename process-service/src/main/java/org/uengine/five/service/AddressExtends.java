package org.uengine.five.service;

import java.util.List;

import org.uengine.kernel.Activity;

public class AddressExtends {
    List<Activity> activity;
    
    public List<Activity> getActivity() {
        return activity;
    }
    public void setActivity(List<Activity> activity) {
        this.activity = activity;
    }

    public AddressExtends(){
        // setCity("0");
        // setRetryDelay(60);
    }

    public AddressExtends(String activityName){	// for manual-coding
        this();
        
        // setCity(activityName);
    }
}
