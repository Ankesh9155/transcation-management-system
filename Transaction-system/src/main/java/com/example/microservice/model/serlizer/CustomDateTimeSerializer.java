package com.example.microservice.model.serlizer;

import com.example.microservice.exception.GenericInvalidFormatException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.microservice.util.Constants.GENERIC_DATE_TIME_FORMAT;

@Slf4j
@JsonComponent
public class CustomDateTimeSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize (Date vale, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException{
        try {
            jsonGenerator.writeString(new SimpleDateFormat(GENERIC_DATE_TIME_FORMAT).format(vale));
        }catch (Exception ex){
            log.error("Invalid date time format : ", ex);
            throw new GenericInvalidFormatException("Invalid date time formate ");
        }
    }
}
