package ru.pa4ok.library.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil
{
	private static final GsonBuilder gsonBuilder;  
	public static final Gson gson;
	public static final Gson gsonPretty;
	
	static {
		gsonBuilder = new GsonBuilder();
		gsonBuilder.serializeNulls();  
		
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
}
