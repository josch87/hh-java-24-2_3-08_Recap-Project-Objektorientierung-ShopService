import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@ToString
public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Product productToOrder = productRepo.getProductById(productId);
            if (productToOrder == null) {
                System.out.println("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
                return null;
            }
            products.add(productToOrder);
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products);

        return orderRepo.addOrder(newOrder);
    }

    public Order updateOrderStatus(String orderId, OrderStatus status) {
        Order orderToUpdate = orderRepo.getOrderById(orderId);
        Order updatedOrder = orderToUpdate.withStatus(status);
        orderRepo.removeOrder(orderId);
        orderRepo.addOrder(updatedOrder);
        return updatedOrder;
    }

    public List<Order> getOrdersByOrderStatus(OrderStatus orderStatus) {
        return orderRepo.getOrders().stream()
                .filter((order) -> order.status().equals(orderStatus))
                .collect(Collectors.toList());
    }
}
