package org.uengine.five.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

public class Root {
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "_type")
    Person value;
  
    public Person getValue() {
      return value;
    }
  
    public void setValue(Person value) {
      this.value = value;
    }
  
    
  }