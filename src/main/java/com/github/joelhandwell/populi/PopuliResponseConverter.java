package com.github.joelhandwell.populi;

import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Converter;

import javax.xml.bind.JAXB;
import java.io.IOException;
import java.io.StringReader;

final class PopuliResponseConverter<T> implements Converter<ResponseBody, T> {

    final Class<T> type;
    final Logger logger = LoggerFactory.getLogger(PopuliResponseConverter.class);

    PopuliResponseConverter(Class<T> type) {
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String bodyString = value.string();
        System.out.println(bodyString);

        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><error><code>";
        if(bodyString.startsWith(header)){
            throw new RuntimeException(bodyString);
        }else{
            logger.debug(bodyString);
        }

        return JAXB.unmarshal(new StringReader(bodyString), type);
    }
}
