import exceptions.NoSuchProductException;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@ToString
public class ShopService {
    private ProductRepo productRepo;
    private OrderRepo orderRepo;

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            if (productToOrder.isEmpty()) {
                System.out.println("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
                throw new NoSuchProductException("Product with Id " + productId + " not found.");
            }
            products.add(productToOrder.get());
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, Instant.now());

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
