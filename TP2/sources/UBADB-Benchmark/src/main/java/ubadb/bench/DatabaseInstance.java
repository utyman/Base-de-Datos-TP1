package ubadb.bench;

import org.springframework.context.support.GenericXmlApplicationContext;

import ubadb.Database;

public class DatabaseInstance {

	private final Database database;

	public DatabaseInstance(Database database) {
		super();
		this.database = database;
	}

	public Database getDatabase() {
		return database;
	}

	public static DatabaseInstance fromSpring(String resource) {
		GenericXmlApplicationContext context = new GenericXmlApplicationContext(
				resource);
		Database database = (Database) context.getBean("database");
		return new DatabaseInstance(database);
	}

}
