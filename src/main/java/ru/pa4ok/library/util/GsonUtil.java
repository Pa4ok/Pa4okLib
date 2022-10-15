package ru.pa4ok.library.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil
{
	public static final Gson GSON;
	public static final Gson GSON_PRETTY;

	static
	{
		GsonBuilder builder = new GsonBuilder();

		builder.serializeNulls();
		builder.enableComplexMapKeySerialization();

		GSON = builder.create();
		GSON_PRETTY = builder.setPrettyPrinting().create();
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
}
