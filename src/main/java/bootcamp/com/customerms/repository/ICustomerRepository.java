package bootcamp.com.customerms.repository;

import bootcamp.com.customerms.model.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ICustomerRepository extends ReactiveMongoRepository<Customer,String> {
    Mono<Customer> findByDocumentNumber(String documentNumber);
}
