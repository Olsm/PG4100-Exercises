package no.westerdals.pg4100.mockitolecture.assignment1;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import no.westerdals.pg4100.mockitolecture.Warehouse;
import no.westerdals.pg4100.mockitolecture.WarehouseImpl;

public class OrderTest {
	private Order fillableOrder;
	private Order nonFillableOrder;
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
	
	@Before
	public void SetUp() {
		//Arrange
		fillableOrder = new Order(productName, 1);
		nonFillableOrder = new Order(productName, 11);
	}

	@Test
	/**
	 * Assignment 1a
	 * Classic testing: When warehouse has enough inventory
	 * for a specific order:
	 * 		- verify that the order is filled
	 */
	public void orderIsFilledWhenWarehouseCanProvide() {
		// ARRANGE
		Warehouse warehouse = getWarehouse();
		// ACT
		fillableOrder.fill(warehouse);
		// ASSERT
		assertTrue(fillableOrder.isFilled());
	}
	
	@Test
	/**
	 * Assignment 1b
	 * Classic testing: When warehouse has enough inventory
	 * for a specific order:
	 * 		- verify that the inventory is updated accordingly
	 */
	public void inventoryIsUpdatedWhenWarehouseFillsOrder() {
		// ARRANGE
		Warehouse warehouse = getWarehouse();
		// ACT
		fillableOrder.fill(warehouse);
		// ASSERT
		assertEquals(productQuantity, warehouse.getInventory(productName) + fillableOrder.getQuantity());
	}

	@Test
	/**
	 * Assignment 1c
	 * Classic testing: When warehouse does not have enough inventory
	 * for a specific order:
	 * 		- verify that the order is not filled
	 */
	public void orderIsNotFilledWhenWarehouseFailsToProvide() {
		// ARRANGE
		Warehouse warehouse = getWarehouse();
		// ACT
		nonFillableOrder.fill(warehouse);
		// ASSERT
		assertFalse(nonFillableOrder.isFilled());
	}
	
	@Test
	/**
	 * Assignment 1d
	 * Classic testing: When warehouse does not have enough inventory
	 * for a specific order:
	 * 		- verify that the warehouse inventory has not changed
	 */
	public void inventoryIsUnchangedWhenOrderIsNotFilled() {
		// ARRANGE
		Warehouse warehouse = getWarehouse();
		nonFillableOrder.fill(warehouse);
		assertEquals(productQuantity, warehouse.getInventory(productName));
	}

	@Test
	/**
	 * Assignment 1e
	 * Mock testing: When order is to be filled, mock a warehouse
	 * providing enough inventory. Verify that both hasInventory()
	 * and remove() is called in the warehouse.
	 * Also make sure the order status is filled.
	 */
	public void warehouseShouldCheckInventoryAndUpdateQuantityWhenNeeded() {
		// ARRANGE
		Warehouse mockWarehouse = getMockWarehouse();
		int quantity = fillableOrder.getQuantity();
		// Decide what the mockWarehouse will return using when.thenReturn
		when(mockWarehouse.hasInventory(productName, quantity)).thenReturn(true);
		// ACT
		fillableOrder.fill(mockWarehouse);
		// ASSERT/VERIFY
		verify(mockWarehouse).hasInventory(productName, quantity);
		verify(mockWarehouse).remove(productName, quantity);
		assertTrue(fillableOrder.isFilled());
	}

	@Test
	/**
	 * Assignment 1f
	 * Mock testing: When order is to be filled and warehouse
	 * cannot provide enough inventory, only hasInventory()
	 * should be called in the warehouse.
	 * Make sure remove() is NOT called.
	 * Also make sure the order status is NOT filled.
	 */
	public void warehouseShouldOnlyCheckInventoryWhenFillingIsImpossible() {
		// ARRANGE
		Warehouse mockWarehouse = getMockWarehouse();
		int quantity = nonFillableOrder.getQuantity();
		when(mockWarehouse.hasInventory(productName, quantity)).thenReturn(false);
		// ACT
		nonFillableOrder.fill(mockWarehouse);
		// ASSERT/VERIFY
		verify(mockWarehouse).hasInventory(productName, quantity);
		verify(mockWarehouse, never()).remove(productName, quantity);
		assertFalse(nonFillableOrder.isFilled());
	}

	@SuppressWarnings("rawtypes")
	@Test
	/**
	 * Examples from lecture
	 */
	public void examples() {
		List mockedList = mock(List.class);
		when(mockedList.get(0)).thenReturn("a");
		assertTrue(mockedList.get(0).equals("a"));
		assertNull(mockedList.get(1));
	}

}
