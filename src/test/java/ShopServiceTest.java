import exceptions.NoSuchOrderException;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")), Instant.now());
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        //THEN
        assertThrowsExactly(NoSuchOrderException.class, () -> shopService.addOrder(productsIds));
    }

    @Test
    void getOrdersByStatusTest_whenStatusInDelivery_expectOneOrders() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        Order order1 = shopService.addOrder(productsIds);
        Order order2 = shopService.addOrder(productsIds);
        Order order3 = shopService.addOrder(productsIds);

        Order updatedOrder1 = shopService.updateOrderStatus(order1.id(), OrderStatus.IN_DELIVERY);

        //WHEN
        List<Order> actual = shopService.getOrdersByOrderStatus(OrderStatus.IN_DELIVERY);

        //THEN
        int expectedLength = 1;
        assertEquals(expectedLength, actual.size());
        assertEquals(updatedOrder1.id(), actual.get(0).id());
    }

    @Test
    void updateOrderStatusTest_whenOrderExists_expectStatusUpdated() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");
        Order order1 = shopService.addOrder(productsIds);
        OrderStatus newStatus = OrderStatus.IN_DELIVERY;

        //WHEN
        Order actual = shopService.updateOrderStatus(order1.id(), newStatus);

        //THEN
        assertEquals(newStatus, actual.status());
    }

    @Test
    void updateOrderStatusTest_whenOrderDoesNotExist_expectException() {
        //GIVEN
        ShopService shopService = new ShopService();
        String invalidOrderId = "-1";
        OrderStatus newStatus = OrderStatus.IN_DELIVERY;

        //THEN
        assertThrowsExactly(NoSuchOrderException.class,
                            () -> shopService.updateOrderStatus(invalidOrderId, newStatus));
    }
}
