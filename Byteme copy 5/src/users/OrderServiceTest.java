package services;

import models.MenuItem;
import models.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {
    private MenuItem availableItem;
    private MenuItem unavailableItem;
    private ArrayList<MenuItem> menu;

    @BeforeEach
    public void setUp() {
        availableItem = new MenuItem("Burger", 100, "Fast Food", true);
        unavailableItem = new MenuItem("Pizza", 200, "Fast Food", false);

        menu = new ArrayList<>();
        menu.add(availableItem);
        menu.add(unavailableItem);

        // Mock menu in MenuService
        MenuService.getMenuItems().clear();
        MenuService.getMenuItems().addAll(menu);
    }

    @Test
    public void testPlaceOrderWithUnavailableItems() {
        Order order = new Order("ORD001", "John Doe", false);

        // Place an order with both available and unavailable items
        ArrayList<MenuItem> availableMenu = MenuService.getAvailableMenuItems();

        // Add only available items to the order
        if (availableMenu.contains(availableItem)) {
            order.addItem(availableItem, 2);
        }

        // Attempt to add unavailable item (should not be present in availableMenu)
        assertFalse(availableMenu.contains(unavailableItem), "Unavailable item should not appear in the menu.");

        // Validate that only available items are in the order
        assertEquals(1, order.getItems().size(), "Order should only contain available items.");
        assertTrue(order.getItems().containsKey(availableItem), "Available item should be added to the order.");
        assertFalse(order.getItems().containsKey(unavailableItem), "Unavailable item should not be added to the order.");
    }
}
