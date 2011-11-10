package ubadb.apps.bufferManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ubadb.common.PageId;
import ubadb.common.TableId;

public class PageReferenceTraceGenerator
{
	private List<PageId> generateSequentialPages(String fileName, int from, int to)
	{
		List<PageId> ret = new ArrayList<PageId>();
		
		for(int i=from; i < to; i++)
			ret.add(new PageId(i, new TableId(fileName)));
		
		return ret;
	}
	
	private List<PageId> generateLoopPages(String fileName, int loopLength, int loops)
	{
		List<PageId> ret = new ArrayList<PageId>();
		
		for(int i=0; i < loops; i++)
		{
			for(int j=0; j < loopLength; j++)
				ret.add(new PageId(j, new TableId(fileName)));
		}
		
		return ret;
	}

	public List<PageId> generateRandomPages(String fileName, int pageCount, int fileTotalPagesCount)
	{
		List<PageId> ret = new ArrayList<PageId>();
		Random random = new Random(System.currentTimeMillis());
		
		for(int i=0; i < pageCount ; i++)
			ret.add(new PageId(random.nextInt(fileTotalPagesCount), new TableId(fileName)));
		
		return ret;
	}
	
	public PageReferenceTrace generateFileScan(String fileName, int start, int pageCount)
	{
		List<PageId> pages = generateSequentialPages(fileName, start, pageCount);
		
		return buildRequestAndRelease(pages);
	}

	private PageReferenceTrace buildRequestAndRelease(List<PageId> pages)
	{
		PageReferenceTrace ret = new PageReferenceTrace();
		
		for(PageId pageId : pages)
		{
			ret.addPageReference(new PageReference(pageId, PageReferenceType.REQUEST));
			ret.addPageReference(new PageReference(pageId, PageReferenceType.RELEASE));
		}
		
		return ret;
	}
	
	private PageReferenceTrace build(List<PageId> pages, PageReferenceType type)
	{
		PageReferenceTrace ret = new PageReferenceTrace();
		
		for(PageId pageId : pages)
		{
			ret.addPageReference(new PageReference(pageId, type));
		}
		
		return ret;
	}

	public PageReferenceTrace generateIndexScanClustered(String fileName, int indexHeight, int referenceStart, int referenceCount)
	{
		PageReferenceTrace index = generateFileScan(fileName + "_index", 0, indexHeight);
		PageReferenceTrace fileScanFromIndex = generateFileScan(fileName, referenceStart, referenceStart+referenceCount);
		return index.concatenate(fileScanFromIndex);
	}
	
	public PageReferenceTrace generateIndexScanUnclustered(String fileName, int indexHeight, int referenceCount, int pageCount)
	{
		PageReferenceTrace index = generateFileScan(fileName + "_index", 0, indexHeight);
		PageReferenceTrace fileAccesses = buildRequestAndRelease(generateRandomPages(fileName, referenceCount, pageCount));
		return index.concatenate(fileAccesses);
	}

	public PageReferenceTrace generateBNLJ(String fileNameOuter, int pageCountOuter, String fileNameInner, int pageCountInner, int blockSize)
	{
		PageReferenceTrace ret = new PageReferenceTrace();
		int blocks = (int)Math.ceil((double)pageCountOuter/(double)blockSize);
		int offset = 0;
		for(int i=0; i < blocks; i++)
		{
			List<PageId> outerBlock = generateSequentialPages(fileNameOuter, offset, Math.min(offset + blockSize, pageCountOuter));
			ret.concatenate(build(outerBlock,PageReferenceType.REQUEST));
			ret.concatenate(generateFileScan(fileNameInner,0, pageCountInner));
			ret.concatenate(build(outerBlock,PageReferenceType.RELEASE));
			
			offset+=blockSize;
		}
		return ret;
	}
	
	public PageReferenceTrace generateINLJ(String fileNameOuter, int pageCountOuter, String fileNameInner, int pageCountInner, int blockSize)
	{
		PageReferenceTrace ret = new PageReferenceTrace();
		int blocks = (int)Math.ceil((double)pageCountOuter/(double)blockSize);
		int offset = 0;
		for(int i=0; i < blocks; i++)
		{
			List<PageId> outerBlock = generateSequentialPages(fileNameOuter, offset, Math.min(offset + blockSize, pageCountOuter));
			ret.concatenate(build(outerBlock,PageReferenceType.REQUEST));
			ret.concatenate(generateFileScan(fileNameInner,0, pageCountInner));
			ret.concatenate(build(outerBlock,PageReferenceType.RELEASE));
			
			offset+=blockSize;
		}
		return ret;
	}

	public static void main(String[] args)
	{
		PageReferenceTraceGenerator gen = new PageReferenceTraceGenerator();
//		System.out.println(gen.generateFileScan("A",0,5));
//		System.out.println(gen.generateIndexScanClustered("A", 3, 3, 3));
//		System.out.println(gen.generateIndexScanUnclustered("A", 3, 3, 10));
		System.out.println(gen.generateBNLJ("A", 7, "B", 2, 6));
	}
}
