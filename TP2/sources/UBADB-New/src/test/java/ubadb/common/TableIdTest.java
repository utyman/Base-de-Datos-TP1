package ubadb.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class TableIdTest {
	@Test
	public void equalsTrueSameObject() {
		TableId tableId1 = new TableId("a.txt");
		assertEquals(tableId1, tableId1);
	}

	@Test
	public void equalsTrueOtherObject() {
		TableId tableId1 = new TableId("a.txt");
		TableId tableId2 = new TableId("a.txt");

		assertEquals(tableId1, tableId2);
		assertEquals(tableId1.hashCode(), tableId2.hashCode());
	}

	@Test
	public void equalsFalseNullObject() {
		TableId tableId1 = new TableId("a.txt");

		assertFalse(tableId1.equals(null));
	}

	@Test
	public void equalsFalseDifferentType() {
		TableId tableId1 = new TableId("a.txt");

		assertFalse(tableId1.equals("aaaaaaaaaaaa"));
	}

	@Test
	public void equalsFalseDifferentObject() {
		TableId tableId1 = new TableId("a.txt");
		TableId tableId2 = new TableId("baaaaaaaaaaaaaaaa.txt");

		assertFalse(tableId1.equals(tableId2));
	}
}
