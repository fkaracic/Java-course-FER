package hr.fer.zemris.java.tecaj.hw05.db;

import org.junit.Test;
import org.junit.Assert;

public class FieldValueGettersTest {
	private static IFieldValueGetter FIRST_NAME = FieldValueGetters.FIRST_NAME;
	private static IFieldValueGetter LAST_NAME = FieldValueGetters.LAST_NAME;
	private static IFieldValueGetter JMBAG = FieldValueGetters.JMBAG;
	
	
	@Test
	public void firstName() {
		StudentRecord record = new StudentRecord("0000000005", "John", "Doe", "4");
		
		Assert.assertEquals(record.getFirstName(), FIRST_NAME.get(record));
	}
	
	@Test
	public void lastName() {
		StudentRecord record = new StudentRecord("0000000005", "John", "Doe", "4");
		
		Assert.assertEquals(record.getLastName(), LAST_NAME.get(record));
	}
	
	@Test
	public void jmbag() {
		StudentRecord record = new StudentRecord("0000000005", "John", "Doe", "4");
		
		Assert.assertEquals(record.getJmbag(), JMBAG.get(record));
	}
	
	
}
