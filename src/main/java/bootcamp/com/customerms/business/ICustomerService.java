package bootcamp.com.customerms.business;

import bootcamp.com.customerms.model.Customer;
import bootcamp.com.customerms.model.CustomerDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerService {
    Flux<CustomerDto> findAllCustomer();

    Mono<CustomerDto> findByIdCustomer(String customerId);

    Mono<Customer> findByDocumentNumber(String dni);

    Mono<CustomerDto> createCustomer(Customer customer);

    Mono<CustomerDto> updateCustomer(Customer customer, String customerId);

    Mono<CustomerDto> removeCustomer(String customerId);

}
