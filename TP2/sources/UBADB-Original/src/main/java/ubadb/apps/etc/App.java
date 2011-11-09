package ubadb.apps.etc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ubadb.Database;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    	
        Database db = (Database) applicationContext.getBean("database");
        
//        Table table = db.getCatalogManager().getTableByName("hola");
//        
//        TupleIterator iterator = table.getIterator();
//        
//        while(iterator.hasNext())
//        {
//        	Tuple tuple = iterator.next();
//        	System.out.println(tuple);
//        }
//        
//        file.insertTuple(new Tuple());
    	
    }
}
