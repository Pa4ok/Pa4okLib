package ru.pa4ok.library.data.message;

import ru.pa4ok.library.util.GsonUtil;

public class JsonBaseMessage extends GsonUtil.Jsonable
{
	public JsonBaseMessage()
	{
		super();
	}
	
	@Override
	public String toString() {
		return GsonUtil.gson.toJson(this);
	}
}
