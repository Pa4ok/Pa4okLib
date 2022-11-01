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
	public static final ExclusionStrategy EXCLUSION_STRATEGY;

	public static final GsonBuilder BUILDER;
	public static final GsonBuilder PRETTY_BUILDER;

	public static final Gson GSON;
	public static final Gson GSON_PRETTY;

	static
	{
		EXCLUSION_STRATEGY = new ExclusionStrategy() {
			@Override
			public boolean shouldSkipField(FieldAttributes field) {
				return field.getAnnotation(Exclude.class) != null;
			}

			@Override
			public boolean shouldSkipClass(Class<?> cls) {
				return false;
			}
		};

		BUILDER = new GsonBuilder()
				.serializeNulls()
				.setExclusionStrategies(EXCLUSION_STRATEGY)
				.enableComplexMapKeySerialization();
		GSON = BUILDER.create();

		PRETTY_BUILDER = new GsonBuilder()
				.serializeNulls()
				.setExclusionStrategies(EXCLUSION_STRATEGY)
				.enableComplexMapKeySerialization()
				.setPrettyPrinting();
		GSON_PRETTY = PRETTY_BUILDER.create();
	}

	public static class Jsonable
	{
		@Override
		public String toString()
		{
			return GSON.toJson(this);
		}

		public String toPrettyString()
		{
			return GSON_PRETTY.toJson(this);
		}
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface Exclude {
	}
}
