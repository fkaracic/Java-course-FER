package hr.fer.zemris.java.tecaj.hw05.db;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;

public class QueryFilterTest {

	private static List<StudentRecord> students;																 

	@BeforeClass
	public static void setUpRecords() throws IOException {
		StudentDatabase database = new StudentDatabase(Files.readAllLines(Paths.get("src/test/resources/students.txt")));
		
		students = new ArrayList<>(database.filter(record -> true));
	}

	@Test
	public void testList1() {
		List<ConditionalExpression> list = new ArrayList<>();
		
		list.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Ante", ComparisonOperators.EQUALS));
		list.add(new ConditionalExpression(FieldValueGetters.JMBAG, "0000000003",
				ComparisonOperators.GREATER_OR_EQUALS));
		
		QueryFilter filter = new QueryFilter(list);

		List<StudentRecord> accepted = new ArrayList<>();

		for (StudentRecord record : students) {
			if (filter.accepts(record))
				accepted.add(record);
		}

		int expectedSize = 1;

		Assert.assertEquals(expectedSize, accepted.size());
		Assert.assertEquals("Ante", accepted.get(0).getFirstName());
		Assert.assertEquals("0000000009", accepted.get(0).getJmbag());

	}

	@Test
	public void testList2() {
		List<ConditionalExpression> list = new ArrayList<>();
		
		list.add(new ConditionalExpression(FieldValueGetters.LAST_NAME, "A*", ComparisonOperators.LIKE));
		list.add(new ConditionalExpression(FieldValueGetters.JMBAG, "0000000020", ComparisonOperators.LESS));
		
		QueryFilter filter = new QueryFilter(list);

		List<StudentRecord> accepted = new ArrayList<>();

		for (StudentRecord record : students) {
			if (filter.accepts(record))
				accepted.add(record);
		}

		int expectedSize = 2;

		Assert.assertEquals(expectedSize, accepted.size());
	}
	
	@Test
	public void testLike() {
		List<ConditionalExpression> list = new ArrayList<>();
		
		list.add(new ConditionalExpression(FieldValueGetters.LAST_NAME, "*ić", ComparisonOperators.LIKE));
		list.add(new ConditionalExpression(FieldValueGetters.JMBAG, "0000000005", ComparisonOperators.GREATER_OR_EQUALS));
		
		QueryFilter filter = new QueryFilter(list);

		List<StudentRecord> accepted = new ArrayList<>();

		for (StudentRecord record : students) {
			if (filter.accepts(record))
				accepted.add(record);
		}

		int expectedSize = 8;

		Assert.assertEquals(expectedSize, accepted.size());
	}
	
	@Test
	public void testEmptyList() {
		List<ConditionalExpression> list = new ArrayList<>();
		
		QueryFilter filter = new QueryFilter(list);

		List<StudentRecord> accepted = new ArrayList<>();

		for (StudentRecord record : students) {
			if (filter.accepts(record))
				accepted.add(record);
		}

		int expectedSize = 13;

		Assert.assertEquals(expectedSize, accepted.size());
	}

}
