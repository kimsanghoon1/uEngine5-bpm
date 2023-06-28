package org.uengine.five.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;

public class CustomTypeResolverBuilder extends DefaultTypeResolverBuilder
{
    public CustomTypeResolverBuilder()
    {
        super(DefaultTyping.NON_FINAL);
    }

    @Override
    public boolean useForType(JavaType t)
    {
        if (t.getRawClass().getName().startsWith("org.uengine")) {
            return true;
        }

        return false;
    }
}