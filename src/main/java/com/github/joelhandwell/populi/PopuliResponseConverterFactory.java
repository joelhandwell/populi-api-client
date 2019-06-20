package com.github.joelhandwell.populi;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import javax.xml.bind.annotation.XmlRootElement;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public final class PopuliResponseConverterFactory extends Converter.Factory {

    public static PopuliResponseConverterFactory create() {
        return new PopuliResponseConverterFactory();
    }

    @Override public Converter<ResponseBody, ?> responseBodyConverter(
            Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type instanceof Class && ((Class<?>) type).isAnnotationPresent(XmlRootElement.class)) {
            return new PopuliResponseConverter<>((Class<?>) type);
        }
        return null;
    }
}
