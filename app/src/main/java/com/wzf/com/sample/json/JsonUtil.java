package com.wzf.com.sample.json;

import com.wzf.com.sample.util.L;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by soonlen on 2017/2/16 14:17.
 * email wangzheng.fang@zte.com.cn
 */

public class JsonUtil {

    static ObjectMapper objectMapper = new ObjectMapper();


    private JsonUtil() {

    }

    private static class Holder {
        private static JsonUtil util = new JsonUtil();
    }

    public static JsonUtil getInstance() {
        return Holder.util;
    }

    /**
     * 对象转Json字符串
     *
     * @param obj
     * @return
     */
    public String objectToStr(Object obj) {
        try {
            String jsonString = objectMapper.writeValueAsString(obj);
            return jsonString;
        } catch (IOException e) {
            L.e("object to string is error ...");
            e.printStackTrace();
        }
        return null;
    }
    public JSONArray listToArray(List<?> list, Class<?> clzz) {
        return null;
    }
    /**
     * Jsonobject转对象
     *
     * @param obj
     * @param clazz
     * @return
     */
    public Object jsonObjToObject(JSONObject obj, Class<?> clazz) {
        if (null == obj)
            return null;
        try {
            return objectMapper.readValue(obj.toString(), clazz);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public List<?> jsonArrayToList(JSONArray array, Class<?> collectionClass, Class<?> elementClass) {
        JavaType type = getCollectionType(collectionClass, elementClass);
        try {
            List<?> list = objectMapper.readValue(array.toString(), type);
            L.e("list.size:" + list.size());
            return list;
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
