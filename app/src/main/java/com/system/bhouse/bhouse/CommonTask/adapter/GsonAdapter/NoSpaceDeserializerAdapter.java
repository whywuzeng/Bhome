package com.system.bhouse.bhouse.CommonTask.adapter.GsonAdapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018-08-10.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.adapter.GsonAdapter
 */

public class NoSpaceDeserializerAdapter implements JsonDeserializer<List<?>> {

    @Override
    public List<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonArray()) {
            JsonArray array = json.getAsJsonArray();

            JsonStrTrim(array);

            Type itemType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
            List list = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                //如何处理element 字段
                JsonElement element = array.get(i);
                Object item = context.deserialize(element, itemType);
                list.add(item);
            }
            return list;
        } else {
            //和接口类型不符，返回空List
            return Collections.EMPTY_LIST;
        }
    }

    public JsonArray JsonStrTrim(JsonArray arr){

        if( arr != null && arr.size() > 0){
            JsonObject obj=null;
            for (int i = 0; i < arr.size(); i++) {

                obj = (JsonObject) arr.get(i);
                // 取出 jsonObject 中的字段的值的空格
                Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
                for (Map.Entry<String,JsonElement> entry:entries)
                {
                    String key = entry.getKey();
                    String value= entry.getValue().getAsString();

                    if(value == null){
                        continue ;
                    }else if("".equals(value.trim())){
                        continue ;
                    }else{
                        obj.addProperty(key, value.replace(" ", ""));
                    }
                }
            }
        }
        return arr;
    }

}
