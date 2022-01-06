package bootcamp.com.customerms.business;

import bootcamp.com.customerms.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerService {
    Flux<Customer> findAllCustomer();
    Mono<Customer> findByIdCustomer(String customerId);
    Mono<Customer> createCustomer(Customer customer);
    Mono<Customer> updateCustomer(Customer customer,String customerId);
    Mono<Customer> removeCustomer(String customerId);

}
