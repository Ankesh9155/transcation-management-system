package com.example.microservice.model.serlizer;

import com.example.microservice.exception.GenericInvalidFormatException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.microservice.util.Constants.GENERIC_DATE_TIME_FORMAT;

@Slf4j
@JsonComponent
public class CustomDateTimeDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        try {
            return new SimpleDateFormat(GENERIC_DATE_TIME_FORMAT).parse(jsonParser.getText());
        }catch (ParseException ex){
            log.error("Invalid date time format");
            throw new GenericInvalidFormatException("Invalid date time formate ");
        }
    }
}
