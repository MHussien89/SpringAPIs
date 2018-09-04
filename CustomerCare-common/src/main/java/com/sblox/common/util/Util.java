package com.sblox.common.util;

import com.sblox.common.exception.BaseException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
//import org.apache.log4j.Logger;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 *
 * @author Mostafa Hussien
 */
public class Util {

//    private static Logger logger = Logger.getLogger(Util.class);
    private static ObjectMapper jsonMapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false).setSerializationInclusion(Inclusion.NON_NULL);


    
    public static String toJson(Object object) throws BaseException {
        try {
            String jsonObject = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
//            logger.info(String.format("Returned jsonObject = %s", jsonObject));
            return jsonObject;
        } catch (IOException ex) {
            throw new BaseException(ErrorCodes.GENERAL_ERROR, ex.getMessage(), ex);
        }
    }

    public static <T> T fromJson(String jsonObject, Class<T> valueType) throws BaseException {
        try {
//            logger.info(String.format("Converting jsonObject = %s to Class of type %s", jsonObject, valueType));
            T readValue = jsonMapper.readValue(jsonObject, valueType);
//            logger.info(String.format("Converted jsonObject = %s", jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(readValue)));
            return readValue;
        } catch (IOException ex) {
            throw new BaseException(ErrorCodes.GENERAL_ERROR, ex.getMessage(), ex);
        }
    }

    public static long diffDate(Date date) {
        return System.currentTimeMillis() - date.getTime();
    }

}
