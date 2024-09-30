package com.iti.jets.providers;

import com.iti.jets.model.MyDate;
import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;


@Provider
public class MyDateParamConverterProvider implements ParamConverterProvider {
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType.getName().equals(MyDate.class.getName())) {
            return new ParamConverter<T>() {
                @Override
                public T fromString(String value) {
                    LocalDate requestedDate = LocalDate.now();
                    if (value.equals("tomorrow")) requestedDate = requestedDate.plusDays(1);
                    else if (value.equals("yesterday")) requestedDate = requestedDate.minusDays(1);
                    MyDate myDate = new MyDate(requestedDate);
                    return rawType.cast(myDate);
                }

                @Override
                public String toString(T value) {
                    if (value == null) return null;
                    return value.toString();
                }
            };
        }
        return null;
    }
}
