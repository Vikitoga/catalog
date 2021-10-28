package ru.netology.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.domain.Book;
import ru.netology.domain.Product;
import ru.netology.domain.Smartphone;
import ru.netology.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

class ProductManagerTest {
    private ProductRepository repository = Mockito.mock(ProductRepository.class);
    private ProductManager manager = new ProductManager(repository);

    private Book first = new Book(1, "All about Apple", 800, "Anonymous");
    private Smartphone second = new Smartphone(2, "Apple iPhone 13", 90000, "Apple");
    private Book third = new Book(3, "Java Forever", 500, "Ivan Ivanov");
    private Smartphone fourth = new Smartphone(4, "Samsung Galaxy S21", 70000, "Samsung Electronics");

    @BeforeEach
    public void createCatalog() {
        Product[] catalogOfProduct = {first, second, third, fourth};
        doReturn(catalogOfProduct).when(repository).findAll();
    }

    @AfterEach
    public void verifyMock() {
        verify(repository).findAll();
    }

    @Test
    public void shouldSearchWithEmptyResult() {
        Product[] actual = manager.searchBy("Sony");
        Product[] expected = new Product[0];
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchWithOneResultBySmartphoneProducer() {
        Product[] actual = manager.searchBy("Samsung");
        Product[] expected = new Product[]{fourth};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchWithOneResultByNameSmartphone() {
        Product[] actual = manager.searchBy("Galaxy");
        Product[] expected = new Product[]{fourth};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchWithOneResultByNameBook() {
        Product[] actual = manager.searchBy("Java");
        Product[] expected = new Product[]{third};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchWithOneResultByAuthorBook() {
        Product[] actual = manager.searchBy("Ivan");
        Product[] expected = new Product[]{third};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchWithSomeResult() {
        Product[] actual = manager.searchBy("Apple");
        Product[] expected = new Product[]{first, second};
        assertArrayEquals(expected, actual);
    }

}