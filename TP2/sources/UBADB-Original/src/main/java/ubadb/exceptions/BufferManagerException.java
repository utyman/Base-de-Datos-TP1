package ubadb.exceptions;

@SuppressWarnings("serial")
public class BufferManagerException extends Exception
{
	public BufferManagerException(String message)
	{
		super(message);
	}
	
	public BufferManagerException(String message, Exception e)
	{
		super(message,e);
	}
}
