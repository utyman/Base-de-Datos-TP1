package ubadb;

import ubadb.components.bufferManager.BufferManager;
import ubadb.components.catalogManager.CatalogManager;
import ubadb.components.diskManager.DiskManager;

public class Database
{
	private CatalogManager catalogManager;
	private DiskManager diskManager;
	private BufferManager bufferManager;
	
	public Database(CatalogManager catalogManager, DiskManager diskManager, BufferManager bufferManager)
	{
		this.catalogManager = catalogManager;
		this.diskManager = diskManager;
		this.bufferManager = bufferManager;
	}

	public CatalogManager getCatalogManager()
	{
		return catalogManager;
	}

	public DiskManager getDiskManager()
	{
		return diskManager;
	}

	public BufferManager getBufferManager()
	{
		return bufferManager;
	}
}
