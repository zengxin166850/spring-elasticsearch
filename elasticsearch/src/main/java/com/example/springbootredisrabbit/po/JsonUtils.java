/**
 *
 */
package com.example.springbootredisrabbit.po;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import sun.management.Sensor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * @author Dell
 *
 */
public class JsonUtils {
	private static ObjectMapper mapper = null;

	public static ObjectMapper getMapper() {
		if (null == mapper) {
			mapper = new ObjectMapper();
			mapper.setLocale(Locale.getDefault());
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		}
		return mapper;
	}

	public static String toString(Object obj) {
		try {
			return getMapper().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static <T> T toObjectBean(String jsonStr, Class<T> objClass) {
		try {

			return getMapper().readValue(jsonStr, objClass);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public static <T> T  toCollectionBean(String jsonStr, Class<?> collectionClass , Class<?>... elementClasses) {
		try {
			JavaType javaType = getMapper().getTypeFactory().constructParametricType(collectionClass, elementClasses);
       		return getMapper().readValue(jsonStr, javaType);
		} catch (Exception e) {
			e.printStackTrace();
			return  null;
		}
	}

	public static List<Sensor> parseSensor(String str) {
		try {
			return getMapper().readValue(str, new TypeReference<List<Sensor>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ObjectNode newObjectNode() {
		return getMapper().createObjectNode();
	}

	public static ArrayNode newArrayNode() {
		return getMapper().createArrayNode();
	}
}
