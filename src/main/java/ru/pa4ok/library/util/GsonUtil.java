package ru.pa4ok.library.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class GsonUtil
{
	private static final GsonBuilder gsonBuilder;

	public static final Gson gson;
	public static final Gson gsonPretty;

	static {
		gsonBuilder = new GsonBuilder();
		gsonBuilder.serializeNulls();
		gsonBuilder.setExclusionStrategies(new ExclusionStrategy() {
			@Override
			public boolean shouldSkipClass(Class<?> clazz) {
				return false;
			}

			@Override
			public boolean shouldSkipField(FieldAttributes field) {
				return field.getAnnotation(Exclude.class) != null;
			}
		});

		gson = gsonBuilder.create();
		gsonPretty = gsonBuilder.setPrettyPrinting().create();
	}

	public static class Jsonable
	{
		@Override
		public String toString()
		{
			return gson.toJson(this);
		}

		public String toPrettyString()
		{
			return gsonPretty.toJson(this);
		}
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public static @interface Exclude {}
}
