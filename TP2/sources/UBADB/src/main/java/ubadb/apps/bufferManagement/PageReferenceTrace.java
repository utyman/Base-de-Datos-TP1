package ubadb.apps.bufferManagement;

import java.util.ArrayList;
import java.util.List;

public class PageReferenceTrace
{
	private List<PageReference> pageReferences;

	public PageReferenceTrace()
	{
		pageReferences = new ArrayList<PageReference>();
	}

	public void addPageReference(PageReference pageReference)
	{
		pageReferences.add(pageReference);
	}
	
	public List<PageReference> getPageReferences()
	{
		return pageReferences;
	}
	
	public PageReferenceTrace concatenate(PageReferenceTrace otherTrace)
	{
		pageReferences.addAll(otherTrace.pageReferences);
		return this;
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		for(PageReference reference : pageReferences)
		{
			builder.append(reference.toString());
			builder.append('\n');
		}
		
		return builder.toString();
			
	}
}
