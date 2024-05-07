import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        OrderRepo orderRepo = new OrderMapRepo();
        ProductRepo productRepo = new ProductRepo();

        Product mueckenspray = productRepo.addProduct(new Product(UUID.randomUUID().toString(), "Mückenspray"));
        Product kuechenrolle = productRepo.addProduct(new Product(UUID.randomUUID().toString(), "Küchenrolle"));
        Product klopapier = productRepo.addProduct(new Product(UUID.randomUUID().toString(), "Klopapier"));
        Product fernseher = productRepo.addProduct(new Product(UUID.randomUUID().toString(), "Fernseher"));

        ShopService shopService = new ShopService(productRepo, orderRepo);

        System.out.println(shopService);

        shopService.addOrder(List.of(mueckenspray.id()));
        shopService.addOrder(List.of(kuechenrolle.id(), fernseher.id()));
        shopService.addOrder(List.of(fernseher.id(), mueckenspray.id(), klopapier.id()));

        System.out.println(shopService);

    }
}
