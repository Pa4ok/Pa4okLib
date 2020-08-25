package ru.pa4ok.library.data.message;

public class JsonServerMessage extends JsonBaseMessage
{
	public static final JsonServerMessage NO_ERROR_MESSAGE = new JsonServerMessage();
	public static final JsonServerMessage ACCESS_DENIED_MESSAGE = new JsonServerMessage("Access denied!");
	
	public String error;
	
	public JsonServerMessage(String error)
	{
		this.error = error;
	}
	
	public JsonServerMessage()
	{
		this(null);
	}
}