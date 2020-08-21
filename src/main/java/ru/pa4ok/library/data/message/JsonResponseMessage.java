package ru.pa4ok.library.data.message;

public class JsonResponseMessage extends JsonBaseMessage
{
	private static final long serialVersionUID = -7704427164023052068L;
	
	public static final JsonResponseMessage NO_ERROR_MESSAGE = new JsonResponseMessage();
	public static final JsonResponseMessage ACCESS_DENIED_MESSAGE = new JsonResponseMessage("Access denied!");
	
	public String error;
	
	public JsonResponseMessage(String error)
	{
		this.error = error;
	}
	
	public JsonResponseMessage()
	{
		this(null);
	}
}
