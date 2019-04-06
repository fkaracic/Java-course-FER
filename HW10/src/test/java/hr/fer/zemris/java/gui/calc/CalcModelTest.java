package hr.fer.zemris.java.gui.calc;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalcModelTest {
	
	private CalcModel model;
	
	private static CalcModel newCalcModel() {
		return new CalcModelImpl();
	}

	@Before
	public void setup() {
		model = newCalcModel();
	}

	@Test
	public void valueOfNewModel() {
		assertEquals(0.0, model.getValue(), 1E-10); 
	}
	
	@Test
	public void toStringOfNewModel() {
		assertEquals("0", model.toString()); 
	}
	
	@Test
	public void enterWholeNumber() {
		model.insertDigit(1);
		model.insertDigit(1);
		model.insertDigit(9);
		assertEquals(119, model.getValue(), 1E-10); 
		assertEquals("119", model.toString()); 
	}

	@Test
	public void enterDecimalNumber() {
		model.insertDigit(1);
		model.insertDigit(1);
		model.insertDigit(9);
		model.insertDecimalPoint();
		model.insertDigit(3);
		model.insertDigit(2);
		assertEquals(119.32, model.getValue(), 1E-10); 
		assertEquals("119.32", model.toString()); 
	}

	@Test
	public void checkDecimalNumberWithZeroWhole() {
		model.insertDigit(0);
		model.insertDecimalPoint();
		model.insertDigit(0);
		model.insertDigit(3);
		model.insertDigit(0);
		model.insertDigit(2);
		assertEquals(0.0302, model.getValue(), 1E-10); 
		assertEquals("0.0302", model.toString()); 
	}

	@Test
	public void startWithDecimalPoint() {
		model.insertDecimalPoint();
		model.insertDigit(3);
		model.insertDigit(2);
		assertEquals(0.32, model.getValue(), 1E-10); 
		assertEquals("0.32", model.toString()); 
	}

	@Test
	public void signHasNoEffectOfEmptyValue() {
		model.swapSign();
		assertEquals(0.0, model.getValue(), 1E-10); 
		assertEquals("0", model.toString()); 
	}

	@Test
	public void signHasEffectOnZeroValue() {
		model.insertDigit(0);
		model.swapSign();
		assertEquals(0.0, model.getValue(), 1E-10); 
		assertEquals("-0", model.toString()); 
	}

	@Test
	public void swapSign() {
		model.insertDecimalPoint();
		model.insertDigit(3);
		model.swapSign();
		model.insertDigit(2);
		assertEquals(-0.32, model.getValue(), 1E-10); 
		assertEquals("-0.32", model.toString()); 
	}

	@Test
	public void swapSignTwice() {
		model.insertDecimalPoint();
		model.insertDigit(3);
		model.swapSign();
		model.swapSign();
		model.insertDigit(2);
		assertEquals(0.32, model.getValue(), 1E-10); 
		assertEquals("0.32", model.toString()); 
	}
	
	@Test
	public void multipleInsertDecimalPoint() {
		model.insertDigit(4);
		model.insertDecimalPoint();
		model.insertDigit(3);
		model.insertDecimalPoint(); // this has no effect!
		model.insertDigit(2);
		assertEquals(4.32, model.getValue(), 1E-10); 
		assertEquals("4.32", model.toString()); 
	}

	@Test
	public void safeWithTooBigNumbers() {
		for(int i = 1; i <= 308; i++) {
			model.insertDigit(9);
		}
		
		String value1 = model.toString();
		
		model.insertDigit(9); // this will be ignored since resulting number is too big for double
		
		String value2 = model.toString();
		
		assertEquals(value1, value2);
	}
	
	@Test(expected=IllegalStateException.class)
	public void readingActiveOperandWhenNotSet() {
		model.getActiveOperand();
	}
	
	@Test
	public void setActiveOperand() {
		double value = 42.0;
		model.setActiveOperand(value);
		assertTrue(model.isActiveOperandSet());
		assertEquals(42.0, model.getActiveOperand(), 1E-10); 
	}

	@Test
	public void afterClearActiveOperandActiveOperandIsNotSet() {
		model.setActiveOperand(42);
		model.clearActiveOperand();
		assertFalse(model.isActiveOperandSet());
	}
	
	@Test
	public void activeOperandIsInitiallyNotSet() {
		assertFalse(model.isActiveOperandSet());
	}

	@Test
	public void afterClearAllActiveOperandIsNotSet() {
		model.setActiveOperand(42);
		model.clearAll();
		assertFalse(model.isActiveOperandSet());
	}
	
	@Test
	public void afterClearActiveOperandRemainsSet() {
		model.setActiveOperand(42);
		model.clear();
		assertTrue(model.isActiveOperandSet());
	}

	@Test
	public void multipleZerosStartingNumberAreIgnored() {
		model.insertDigit(0);
		model.insertDigit(0);
		model.insertDigit(0);
		model.insertDigit(0);
		
		assertEquals(0.0, model.getValue(), 1E-10); 
		assertEquals("0", model.toString()); 
	}

	@Test
	public void leadingZerosAreIgnored() {
		model.insertDigit(0);
		model.insertDigit(0);
		model.insertDigit(3);
		model.insertDigit(4);
		
		assertEquals(34.0, model.getValue(), 1E-10); 
		assertEquals("34", model.toString()); 
	}
	
	@Test
	public void preferedSize() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize(); 
		
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}
	
	@Test
	public void preferedSize2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108, 15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16, 30));
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize(); 
		
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}
}
