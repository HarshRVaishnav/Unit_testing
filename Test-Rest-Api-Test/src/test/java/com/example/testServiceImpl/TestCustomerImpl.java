package com.example.testServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.entity.Customer;
import com.example.repository.ICustomerRepo;
import com.example.serviceImple.CustomerServiceImpl;

@SpringBootTest
public class TestCustomerImpl
{

    @InjectMocks
    private CustomerServiceImpl iCustomerService;

    @Mock
    private ICustomerRepo customerRepository;

    public List<Customer> myCustomers;


    @Test
    @Order(1)
    public void test_getAllCustomer()
    {
        List<Customer> customerlist = new ArrayList<>();
        Customer customer = new Customer();
        customer.setCustomerNumber(1);
        customer.setCustomerFirstName("satish");
        customer.setCustomerLastName("gaikwad");
        customer.setAddressLine1("Kolpewadi");
        customer.setAddressLine2("Kolpewadi");
        customer.setPhone("902270086");
        customer.setPostalCode(423602);
        customer.setCity("ahmednagar");
        customer.setState("maharashtra");
        customer.setCountry("india");
        customerlist.add(customer);
        when(customerRepository.findAll()).thenReturn(customerlist);
        assertEquals(1, iCustomerService.getCustomerList().size());
    }

    @Test
    @Order(2)
    public void test_getById()
    {
        Customer customer = new Customer();
        customer.setCustomerNumber(1);
        customer.setCustomerFirstName("satish");
        customer.setCustomerLastName("gaikwad");
        customer.setAddressLine1("Kolpewadi");
        customer.setAddressLine2("Kolpewadi");
        customer.setPhone("902270086");
        customer.setPostalCode(423602);
        customer.setCity("ahmednagar");
        customer.setState("maharashtra");
        customer.setCountry("india");
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        Customer actualCustomer = iCustomerService.getCustomerById(1);
        assertEquals(1, actualCustomer.getCustomerNumber());
    }


    @Test
    @Order(3)
    public void testDeleteCustomer()
    {
        // Given
        Integer customerNumber = 1;
        ICustomerRepo iCustomerRepo = mock(ICustomerRepo.class);

        doNothing().when(iCustomerRepo).deleteById(customerNumber);

        // When
        String result = iCustomerService.deleteCustomer(customerNumber);

        // Then
        assertEquals("One Customer deleted " + customerNumber, result);
    }


    @Test
    @Order(4)
    public void test__UpdateCustomerDetail()
    {
        // Given
        Integer customerNumber = 1;
        Customer customer = new Customer();

        customer.setCustomerFirstName("John");
        customer.setCustomerLastName("Doe");
        customer.setAddressLine1("123 Main St");
        customer.setAddressLine2("");
        customer.setCity("Anytown");
        customer.setCountry("USA");
        customer.setPostalCode(12345);
        customer.setState("CA");
        customer.setPhone("555-1234");
        Optional<Customer> optionalCustomer = Optional.of(customer);
        Customer updatedCustomer = new Customer();
        updatedCustomer.setCustomerFirstName("Jahn");
        updatedCustomer.setCustomerLastName("Doae");
        updatedCustomer.setAddressLine1("123 Maina St");
        updatedCustomer.setAddressLine2("");
        updatedCustomer.setCity("Anytoawn");
        updatedCustomer.setCountry("USaA");
        updatedCustomer.setPostalCode(123485);
        updatedCustomer.setState("CaA");
        updatedCustomer.setPhone("555-1234");

        when(customerRepository.findById(customerNumber)).thenReturn(optionalCustomer);
        when(customerRepository.save(customer)).thenReturn(updatedCustomer);

        // When
        Customer result = iCustomerService.updateCustomerDetail(customerNumber, customer);

        // Then
        assertEquals(updatedCustomer, result);
        verify(customerRepository).save(customer);
    }


    @Test
    @Order(5)
    public void testRegisterCustomer()
    {
        // Given
        Customer customer = new Customer();
        customer.setCustomerFirstName("Jahn");
        customer.setCustomerLastName("Doae");
        customer.setAddressLine1("123 Maina St");
        customer.setAddressLine2("");
        customer.setCity("Anytoawn");
        customer.setCountry("USaA");
        customer.setPostalCode(123485);
        customer.setState("CaA");
        customer.setPhone("555-1234");
        when(customerRepository.save(customer)).thenReturn(customer);
        //  ICustomerService customerService = new CustomerService(iCustomerRepo);

        // When
        Customer result = iCustomerService.registerCustomer(customer);

        // Then
        assertEquals(customer, result);
        verify(customerRepository).save(customer);
    }


}
