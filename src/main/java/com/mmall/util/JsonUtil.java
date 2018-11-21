package com.mmall.util;

import com.google.common.collect.Lists;
import com.mmall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 对象的所有字段全部列入
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);

        // 取消时间默认转换成时间戳
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

        // 忽略空bean转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

        // 所有的日期格式都统一为以下样式，即yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));

        // 忽略在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> String obj2String(T obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof String) {
            return (String) obj;
        }

        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("Parse object to String error", e);
            return null;
        }
    }

    public static <T> String obj2StringPretty(T obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof String) {
            return (String) obj;
        }

        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            log.error("Parse object to String error", e);
            return null;
        }
    }

    public static <T> T string2Obj(String string, Class<T> clazz) {
        if (StringUtils.isEmpty(string) || clazz == null) {
            return null;
        }

        if (clazz.equals(String.class)) {
            return (T) string;
        }

        try {
            return objectMapper.readValue(string, clazz);
        } catch (Exception e) {
            log.error("Parse String to object error", e);
            return null;
        }
    }

    public static <T> T string2Obj(String string, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(string) || typeReference == null) {
            return null;
        }

        if (typeReference.getType().equals(String.class)) {
            return (T) string;
        }

        try {
            return objectMapper.readValue(string, typeReference);
        } catch (Exception e) {
            log.error("Parse String to object error", e);
            return null;
        }
    }

    public static <T> T string2Obj(String string, Class<?> collectionClass, Class<?>... elementsClasses) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementsClasses);

        try {
            return objectMapper.readValue(string, javaType);
        } catch (Exception e) {
            log.error("Parse String to object error", e);
            return null;
        }
    }

    public static void main(String[] args) {
        User u1 = new User();
        u1.setId(1);
        u1.setUsername("kevin");
        User u2 = new User();
        u2.setId(2);
        u2.setUsername("geely");

        String user1Json = JsonUtil.obj2String(u1);
        String user1JsonPretty = JsonUtil.obj2StringPretty(u1);
        log.info("user1Json:{}", user1Json);
        log.info("user1JsonPretty:{}", user1JsonPretty);

        log.info("======================");

        List<User> list = Lists.newArrayList();
        list.add(u1);
        list.add(u2);
        String userListStr = JsonUtil.obj2StringPretty(list);
        log.info("userListStr:{}", userListStr);

        List<User> userListObj = JsonUtil.string2Obj(userListStr, List.class);
        List<User> userListObj2 = JsonUtil.string2Obj(userListStr, new TypeReference<List<User>>() {
        });
        List<User> userListObj3 = JsonUtil.string2Obj(userListStr, List.class, User.class);
        System.out.println("end");

    }
}
