package ubadb.apps.catalog;

import java.util.ArrayList;
import java.util.List;

import ubadb.common.TableId;
import ubadb.components.catalogManager.FieldDescriptor;
import ubadb.components.catalogManager.FieldType;
import ubadb.components.catalogManager.TableCatalog;
import ubadb.components.catalogManager.TableDescriptor;
import ubadb.util.xml.XstreamXmlUtil;

import com.thoughtworks.xstream.XStream;

public class CatalogGenerator
{
	public static void main(String[] args) throws Exception
	{
    	
    	XstreamXmlUtil xmlUtil = new XstreamXmlUtil(new XStream());
    	List<TableDescriptor> list = new ArrayList<TableDescriptor>(); 
    	
    	addTableDescriptors(list);
    	
    	TableCatalog catalogTable = new TableCatalog(list);
    	xmlUtil.toXml(catalogTable,"generated/catalog.db");
    	
    	System.out.println("CATALOG GENERATED");
	}

	//ADD HERE
	private static void addTableDescriptors(List<TableDescriptor> list)
	{
		TableId tableId;
		String tableName;
		List<FieldDescriptor> fields = new ArrayList<FieldDescriptor>();
		
		//Table Student
		tableId = new TableId("student.table");
		tableName = "Student";
		fields.clear();
		fields.add(new FieldDescriptor("idStudent",FieldType.INT));
		fields.add(new FieldDescriptor("name", FieldType.CHAR_100));
    	list.add(new TableDescriptor(tableId,tableName,fields));
    	
    	//Table University
    	
	}

}
