package hr.fer.zemris.java.tecaj.hw05.db;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;

public class StudentDatabaseTest {
	private static StudentDatabase base;
	
	@BeforeClass
	public static void setUp() throws IOException {		
		base  = new StudentDatabase(Files.readAllLines(Paths.get("database.txt")));
		
	}
	
	@Test
	public void forExistingJmbag() {
		String jmbag = "0000000003";
		
		StudentRecord student = base.forJMBAG(jmbag);
		
		Assert.assertNotNull(student);
		Assert.assertEquals(jmbag, student.getJmbag());
	}
	
	@Test
	public void forInvalidJmbag() {
		String jmbag = "000000000a";
		
		StudentRecord student = base.forJMBAG(jmbag);
		Assert.assertNull(student);
	}
	
	@Test
	public void forNonExistingJmbag() {
		String jmbag = "0000000064";
		
		StudentRecord student = base.forJMBAG(jmbag);
		
		Assert.assertNull(student);
	}
	
	@Test
	public void forAllTrueFilter() {
		IFilter allTrue = record -> true;
		
		List<StudentRecord> list = base.filter(allTrue);
		
		int expectedSize = 63;
		
		Assert.assertEquals(expectedSize, list.size());
	}
	
	@Test
	public void forAllFalseFilter() {
		IFilter allFalse = record -> false;
		
		List<StudentRecord> list = base.filter(allFalse);
		
		int expectedSize = 0;
		
		Assert.assertEquals(expectedSize, list.size());
	}	
	
	@Test
	public void invalidRecordSkipped() {
		
		List<String> list = new ArrayList<>();
		list.add("0000000003 Miro 123 4");
		
		StudentDatabase database = new StudentDatabase(list);
		int expectedSize = 0;
		
		Assert.assertEquals(expectedSize, database.filter(rec -> true).size());
	}	
}
