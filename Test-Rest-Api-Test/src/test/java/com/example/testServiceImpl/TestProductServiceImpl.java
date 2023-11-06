package com.example.testServiceImpl;

import com.example.entity.Product;
import com.example.repository.IProductRepo;
import com.example.serviceImple.ProductServiceImpl;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TestProductServiceImpl
{

    @InjectMocks
    private ProductServiceImpl iProductService;

    @Mock
    private IProductRepo iProductRepo;


    //**************************************************** Test createProduct*********************************************************************//


    @Test
    public void test_createProduct()
    {

        Product product = new Product();
        product.setPrice(100.00);
        product.setProductDescription("So nice product");
        product.setQuantityInStock(1);
        product.setProductName("Fan");

        when(iProductRepo.save(product)).thenReturn(product);

        Product result = iProductService.createProduct(product);

        assertEquals(product, result);
        verify(iProductRepo).save(product);

    }

    //**************************************************** Test createProductList*****************************************************************//


    @Test
    public void test_createProductList()
    {
        // Create a list of products to save
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        product.setProductCode(2);
        product.setPrice(100.00);
        product.setProductDescription("Smartphone with great camera");
        product.setProductName("Phone");
        product.setQuantityInStock(100);
        product.setImageData(null);

        Product product2 = new Product();
        product2.setProductCode(3);
        product2.setPrice(100.00);
        product2.setProductDescription("High-performance laptop");
        product2.setProductName("Laptop");
        product2.setQuantityInStock(100);
        product2.setImageData(null);

        Product product3 = new Product();
        product3.setProductCode(1);
        product3.setPrice(100.00);
        product3.setProductDescription("Smartphone with great camera");
        product3.setProductName("Computer");
        product3.setQuantityInStock(100);
        product3.setImageData(null);

        productList.add(product3);
        productList.add(product2);
        productList.add(product);

        // Mock the repository's saveAll method to return the list of products
        when(iProductRepo.saveAll(productList)).thenReturn(productList);

        // Call the service method to save the list of products
        List<Product> savedProducts = iProductService.createProductList(productList);

        // Verify that the saveAll method was called with the correct argument
        verify(iProductRepo).saveAll(productList);

        // Verify that the saved products match the expected products
        assertEquals(productList, savedProducts);
    }


    //**************************************************** Test GetOneProduct*****************************************************************//

    @Test
    @Order(1)
    public void test_getOneProduct()
    {

        Product product = new Product();
        product.setProductCode(1);
        product.setPrice(100.00);
        product.setProductDescription("So nice product");
        product.setQuantityInStock(1);
        product.setProductName("Fan");
        when(iProductRepo.findById(1)).thenReturn(Optional.of(product));

        Product actualCustomer = iProductService.getProductById(1);
        assertEquals(1, actualCustomer.getProductCode());
    }


    //**************************************************** Test GetAllProducts*****************************************************************//

    @Test
    @Order(2)
    public void test_getAllProducts()
    {

        List<Product> list = new ArrayList<>();
        Product product = new Product();
        //product.setProductCode(1);
        product.setPrice(100.00);
        product.setProductDescription("So nice Product");
        product.setQuantityInStock(100);
        product.setProductName("AC");
        list.add(product);
        when(iProductRepo.findAll()).thenReturn(list);
        assertEquals(1, iProductService.getAllProducts().size());
    }

    //**************************************************** Test DeleteProductById*****************************************************************//

    @Test
    @Order(3)
    public void test_deleteProductById()
    {
        Integer productCode = 1;

        IProductRepo iProductRepository = mock(IProductRepo.class);

        doNothing().when(iProductRepository).deleteById(productCode);

        String result = iProductService.deleteProductById(productCode);

        assertEquals("One product deleted " + productCode, result);

    }


    //**************************************************** Test UpdateProduct*****************************************************************//

    @Test
    @Order(4)
    public void test_updateProduct()
    {
        Integer productCode = 1;
        Product product = new Product();
        product.setPrice(100.00);
        product.setProductDescription("So nice Product");
        product.setQuantityInStock(100);
        product.setProductName("AC");
        product.setImageData(null);

        Optional<Product> optional = Optional.of(product);

        Product updateProduct = new Product();
        updateProduct.setPrice(1000.00);
        updateProduct.setProductDescription("nice Product");
        updateProduct.setQuantityInStock(10);
        updateProduct.setProductName("Fridge");
        updateProduct.setImageData(null);

        when(iProductRepo.findById(productCode)).thenReturn(optional);
        when(iProductRepo.save(product)).thenReturn(updateProduct);

        Product result = iProductService.updateProduct(productCode, product);

        assertEquals(updateProduct, result);
        verify(iProductRepo).save(product);
    }


    //**************************************************** Test DeleteAllProducts*****************************************************************//

    @Test
    @Order(5)
    public void test_deleteAllProducts()
    {
        // Set up
        Product product1 = new Product();
        product1.setProductName("Product 1");
        product1.setPrice(10.00);
        product1.setQuantityInStock(5);
        iProductRepo.save(product1);

        Product product2 = new Product();
        product2.setProductName("Product 2");
        product2.setPrice(20.00);
        product2.setQuantityInStock(10);
        iProductRepo.save(product2);

        // Execute the method
        String result = iProductService.deleteAllProducts();

        // Verify the result
        assertEquals("All products are deleted ", result);
        assertTrue(iProductRepo.findAll().isEmpty());
    }

    //**************************************************** Test SearchByProductName*****************************************************************//

    @Test
    @Order(6)
    public void test_searchByProductName()
    {
        // Create sample data
        List<Product> products = new ArrayList<>();

        Product product = new Product();
        product.setProductCode(2);
        product.setPrice(100.00);
        product.setProductDescription("Smartphone with great camera");
        product.setProductName("Phone");
        product.setQuantityInStock(100);
        product.setImageData(null);

        Product product2 = new Product();
        product2.setProductCode(3);
        product2.setPrice(100.00);
        product2.setProductDescription("High-performance laptop");
        product2.setProductName("Laptop");
        product2.setQuantityInStock(100);
        product2.setImageData(null);

        Product product3 = new Product();
        product3.setProductCode(1);
        product3.setPrice(100.00);
        product3.setProductDescription("Smartphone with great camera");
        product3.setProductName("Computer");
        product3.setQuantityInStock(100);
        product3.setImageData(null);

        products.add(product2);
        products.add(product);
        products.add(product3);
        // Mock the repository to return the sample data
        when(iProductRepo.findByProductNameContainsAllIgnoreCase("o")).thenReturn(Arrays.asList(products.get(1), products.get(2)));

        when(iProductRepo.findAllByOrderByProductNameAsc()).thenReturn(products);

        // Call the method to test
        List<Product> result1 = iProductService.searchByProductName("o");
        List<Product> result2 = iProductService.searchByProductName(null);

        // Assertions
        assertEquals(2, result1.size());
        assertTrue(result1.contains(products.get(1)));
        assertTrue(result1.contains(products.get(2)));

        assertEquals(3, result2.size());
        assertEquals(products, result2);
    }

    //**************************************************** Test UpdateProductQuantityInStock*****************************************************************//

    @Test
    @Order(7)
    public void test_UpdateProductQuantityInStock()
    {
        // Arrange
        Integer productCode = 1;
        Integer originalQuantityInStock = 50;
        Integer quantity = 10;
        Product existingProduct = new Product();
        existingProduct.setProductCode(productCode);
        existingProduct.setQuantityInStock(originalQuantityInStock);
        when(iProductRepo.findById(productCode)).thenReturn(Optional.of(existingProduct));
        when(iProductRepo.save(existingProduct)).thenReturn(existingProduct);

        // Act
        Product updatedProduct = iProductService.updateProductQuantityInStock(productCode, quantity);

        // Assert
        assertEquals(originalQuantityInStock - quantity, updatedProduct.getQuantityInStock());
        verify(iProductRepo).findById(productCode);
        verify(iProductRepo).save(existingProduct);
    }


}
