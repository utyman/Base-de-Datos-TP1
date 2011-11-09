package ubadb.mocks;

import ubadb.common.Page;
import ubadb.common.PageId;
import ubadb.common.TableId;

public class MockObjectFactory
{
	public static final TableId TABLE_ID = new TableId("a.txt");
	public static final PageId PAGE_ID = new PageId(0, TABLE_ID);
	public static final Page PAGE = new Page(PAGE_ID, "abc".getBytes());
}
