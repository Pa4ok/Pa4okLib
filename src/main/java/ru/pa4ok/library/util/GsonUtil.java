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
	public static final Gson gson;
	public static final Gson gsonPretty;

	static
	{
		GsonBuilder builder = new GsonBuilder();

		builder.serializeNulls();
		builder.enableComplexMapKeySerialization();

		builder.setExclusionStrategies(new ExclusionStrategy() {
			@Override
			public boolean shouldSkipClass(Class<?> clazz) {
				return false;
			}

			@Override
			public boolean shouldSkipField(FieldAttributes field) {
				return field.getAnnotation(Exclude.class) != null;
			}
		});

		gson = builder.create();
		gsonPretty = builder.setPrettyPrinting().create();
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
	public @interface Exclude {}
}
