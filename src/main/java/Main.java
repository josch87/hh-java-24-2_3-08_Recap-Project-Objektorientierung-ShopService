import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        OrderRepo orderRepo = new OrderMapRepo();
        ProductRepo productRepo = new ProductRepo();

        productRepo.addProduct(new Product(UUID.randomUUID().toString(), "Mückenspray"));
        productRepo.addProduct(new Product(UUID.randomUUID().toString(), "Küchenrolle"));
        productRepo.addProduct(new Product(UUID.randomUUID().toString(), "Klopapier"));
        productRepo.addProduct(new Product(UUID.randomUUID().toString(), "Fernseher"));


        ShopService shopService = new ShopService(productRepo, orderRepo);

        System.out.println(shopService);

//        Order order1 = new Order(UUID.randomUUID().toString(), )

    }
}
