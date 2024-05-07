import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepoTest {

    @org.junit.jupiter.api.Test
    void getProducts() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        repo.addProduct(new Product("1", "Apfel"));

        //WHEN
        List<Product> actual = repo.getProducts();

        //THEN
        List<Product> expected = new ArrayList<>();
        expected.add(new Product("1", "Apfel"));
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getProductById() {
        //GIVEN

        ProductRepo repo = new ProductRepo();
        repo.addProduct(new Product("1", "Apfel"));

        //WHEN
        Optional<Product> actual = repo.getProductById("1");

        //THEN
        Optional<Product> expected = Optional.of(new Product("1", "Apfel"));
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void addProduct() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        Product newProduct = new Product("2", "Banane");

        //WHEN
        Product actual = repo.addProduct(newProduct);

        //THEN
        Product expected = new Product("2", "Banane");
        assertEquals(expected, actual);
        assertEquals(repo.getProductById("2").orElseThrow(), expected);
    }

    @org.junit.jupiter.api.Test
    void removeProduct() {
        //GIVEN
        ProductRepo repo = new ProductRepo();

        //WHEN
        repo.removeProduct("1");

        //THEN
        assertTrue(repo.getProductById("1").isEmpty());
    }
}
