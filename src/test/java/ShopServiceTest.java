import exceptions.NoSuchOrderException;
import exceptions.NoSuchProductException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ProductRepo productRepo = new ProductRepo();
        productRepo.addProduct(new Product("1", "Apfel"));
        OrderRepo orderRepo = new OrderMapRepo();
        IdService idService = new IdService();
        ShopService shopService = new ShopService(productRepo, orderRepo, idService);
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")), Instant.now());
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectException() {
        //GIVEN
        ProductRepo productRepo = new ProductRepo();
        productRepo.addProduct(new Product("1", "Apfel"));
        OrderRepo orderRepo = new OrderMapRepo();
        IdService idService = new IdService();
        ShopService shopService = new ShopService(productRepo, orderRepo, idService);
        List<String> productsIds = List.of("1", "2");

        //THEN
        NoSuchProductException exception = assertThrowsExactly(NoSuchProductException.class, () -> shopService.addOrder(productsIds));
        assertEquals("Product with Id 2 not found.", exception.getMessage());
    }

    @Test
    void getOrdersByStatusTest_whenStatusInDelivery_expectOneOrders() {
        //GIVEN
        ProductRepo productRepo = new ProductRepo();
        productRepo.addProduct(new Product("1", "Apfel"));
        OrderRepo orderRepo = new OrderMapRepo();
        IdService idService = new IdService();
        ShopService shopService = new ShopService(productRepo, orderRepo, idService);
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
        ProductRepo productRepo = new ProductRepo();
        productRepo.addProduct(new Product("1", "Apfel"));
        OrderRepo orderRepo = new OrderMapRepo();
        IdService idService = new IdService();
        ShopService shopService = new ShopService(productRepo, orderRepo, idService);
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
        ProductRepo productRepo = new ProductRepo();
        productRepo.addProduct(new Product("1", "Apfel"));
        OrderRepo orderRepo = new OrderMapRepo();
        IdService idService = new IdService();
        ShopService shopService = new ShopService(productRepo, orderRepo, idService);
        String invalidOrderId = "-1";
        OrderStatus newStatus = OrderStatus.IN_DELIVERY;

        //THEN
        NoSuchOrderException exception = assertThrowsExactly(NoSuchOrderException.class,
                            () -> shopService.updateOrderStatus(invalidOrderId, newStatus));
        assertEquals("Order with Id -1 not found.", exception.getMessage());
    }
}
