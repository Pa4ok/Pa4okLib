package ru.pa4ok.library.data.message;

import ru.pa4ok.app.util.GsonUtil;

public class JsonBaseMessage extends BaseMessage
{
	private static final long serialVersionUID = 4687047105254848328L;
	
	public JsonBaseMessage()
	{
		super();
	}
	
	@Override
	public String toString() {
		return GsonUtil.gson.toJson(this);
	}
}
