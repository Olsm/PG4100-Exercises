package no.westerdals.pg4100.mockitolecture.assignment2;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doReturn;

import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import no.westerdals.pg4100.mockitolecture.Warehouse;
import no.westerdals.pg4100.mockitolecture.WarehouseImpl;

public class OrderRevisitetTest {
	
	private OrderRevisitet fillableOrder;
	private OrderRevisitet nonFillableOrder;
	private String productName = "ProductName";
	private int productQuantity = 10;
	
	public Warehouse getWarehouse() {
		Warehouse warehouse = new WarehouseImpl();
		addProductsToWarehouse(warehouse);		
		return warehouse;
	}
	
	public Warehouse getMockWarehouse() {
		Warehouse warehouse = mock(Warehouse.class);
		addProductsToWarehouse(warehouse);
		return warehouse;
	}
	
	public void addProductsToWarehouse(Warehouse warehouse) {
		warehouse.add(productName, productQuantity);
	}
	
	public Logger setMockLogger(OrderRevisitet order) {
		Logger logger = mock(Logger.class);
		order.setLogger(logger);
		return logger;
	}
	
	@Before
	public void SetUp() {
		//Arrange
		fillableOrder = new OrderRevisitet(productName, 1);
		nonFillableOrder = new OrderRevisitet(productName, 11);
	}

	@Test
	/**
	 * Assignment 2a: Using a stub (not mock).
	 * Implement MailServiceStub and use it here.
	 * Assert that a mail is sent when the order is not filled.
	 */
	public void mailSentIfOrderIsNotFilled() {
		// ARRANGE
		Warehouse warehouse = getWarehouse();
		MailServiceStub mailer = new MailServiceStub();
		nonFillableOrder.setMailService(mailer);
		// ACT
		nonFillableOrder.fill(warehouse);
		// ASSERT
		assertEquals(1, mailer.messageCount());
	}

	@Test
	/**
	 * Assignment 2b: Using a stub (not mock).
	 * Implement MailServiceStub and use it here.
	 * Assert that a mail is NOT sent when the order is filled.
	 */
	public void mailNotSentWhenOrderIsFilled() {
		// ARRANGE
		Warehouse warehouse = getWarehouse();
		MailServiceStub mailer = new MailServiceStub();
		// ACT
		fillableOrder.fill(warehouse);
		// ASSERT
		assertEquals(0, mailer.messageCount());
	}

	@Test
	/**
	 * Assignment 2c: Using mocks.
	 * Assert that a possible runtime exception thrown
	 * when filling order is written to log.
	 */
	public void runtimeExceptionOnFillIsWrittenToLog() {
		// ARRANGE
		Warehouse mockWarehouse = getMockWarehouse();
		Logger logger = setMockLogger(fillableOrder);
		when(mockWarehouse.hasInventory(anyString(), anyInt())).thenReturn(true);
		doThrow(new RuntimeException()).when(mockWarehouse).remove(anyString(), anyInt());
		// ACT
		fillableOrder.fill(mockWarehouse);
		// ASSERT/VERIFY
		assertFalse(fillableOrder.isFilled());
		verify(logger).info(anyString());
	}

	@Test
	/**
	 * Assignment 2d: Using a spy.
	 * Assert that a possible runtime exception thrown
	 * when filling order is written to log.
	 */
	public void runtimeExceptionOnFillIsWrittenToLogSpy() {
		// ARRANGE
		Warehouse warehouse = getWarehouse();
		Warehouse warehouseSpy = spy(warehouse);
		Logger logger = setMockLogger(fillableOrder);
		// stubbing the remove-method in the spy
		doThrow(new RuntimeException()).when(warehouseSpy).remove(anyString(), anyInt());
		// ACT
		fillableOrder.fill(warehouseSpy);
		// ASSERT/VERIFY
		assertFalse(fillableOrder.isFilled());
		verify(logger).info(anyString());
	}

	@Test
	/**
	 * Assignment 2e: CORRECT THE ERROR(S).
	 * Mockist testing: When warehouse has enough inventory
	 * for a specific order:
	 * 		- verify that the order is filled
	 */
	public void orderIsFilledWhenWarehouseCanProvide() {
		// ARRANGE
		Warehouse mockWarehouse = getMockWarehouse();
		//	when(mockWarehouse.hasInventory("TALISKER", anyInt())).thenReturn(true);
		when(mockWarehouse.hasInventory(anyString(), anyInt()))
				.thenReturn(true);
		// ACT
		fillableOrder.fill(mockWarehouse);
		// ASSERT/VERIFY
		assertTrue(fillableOrder.isFilled());
	}
	
	@Test
	/**
	 * Assignment 2f: CORRECT THE ERROR(S).
	 * Why do I see a panel when running this one?
	 * Please correct it.
	 */
	public void semanticsNotImportant() {
		OrderRevisitet order = new OrderRevisitet("TALISKER", 20);
		OrderRevisitet orderSpy = spy(order);
		//when(orderSpy.returnStringMethod()).thenReturn("something");
		doReturn("something").when(orderSpy).returnStringMethod();
		assertTrue(orderSpy.returnStringMethod().equals("something"));
	}
}
