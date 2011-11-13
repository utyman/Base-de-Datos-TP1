package ubadb.exceptions;

@SuppressWarnings("serial")
public class BufferFrameException extends Exception
{
	public BufferFrameException(String message)
	{
		super(message);
	}
	
	public BufferFrameException(String message, Exception e)
	{
		super(message,e);
	}
}
