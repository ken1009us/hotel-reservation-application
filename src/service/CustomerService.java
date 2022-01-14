package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ken Wu
 *
 */
public class CustomerService {

    // provide a static reference
    private static CustomerService INSTANCE;

    private static Map<String, Customer> customers = new HashMap<>();

    // provide a static reference
    public static CustomerService getInstance() {
        if (INSTANCE == null){
            INSTANCE = new CustomerService();
        }
        return INSTANCE;
    }

    public void addCustomer(final String email, final String firstName, final String lastName) {
        customers.put(email, new Customer(firstName, lastName, email));
    }

    public Customer getCustomer(final String customerEmail) {
        return customers.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
