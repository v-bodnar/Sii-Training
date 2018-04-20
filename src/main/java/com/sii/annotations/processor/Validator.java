package com.sii.annotations.processor;

import com.sii.annotations.Length;
import com.sii.exceptions.ValidationException;
import com.sii.model.AbstractRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class Validator {

    public static<T extends AbstractRequest> void validate(T abstractRequest) throws ValidationException{
        if(abstractRequest == null){
            throw new ValidationException("Null object");
        }
        int maxSize;
        try {
            for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(abstractRequest.getClass()).getPropertyDescriptors()) {
                Method getter = propertyDescriptor.getReadMethod();

                if (propertyDescriptor.getName().equals("class")
                        || propertyDescriptor.getName().equals("type")
                        || propertyDescriptor.getName().equals("uuid")) {
                    continue;
                }

                Field field = abstractRequest.getClass().getDeclaredField(propertyDescriptor.getName());
                Length lengthAnnotation = field.getAnnotation(Length.class);

                if (field.getAnnotation(Length.class) != null) {
                    maxSize = lengthAnnotation.maxSize();
                    if(((String)getter.invoke(abstractRequest)).length() > maxSize){
                        throw new ValidationException("Message exceeds allowed limit");
                    }
                }
            }
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
