package bootcamp.com.customerms.expose;

import bootcamp.com.customerms.business.ICustomerService;
import bootcamp.com.customerms.model.Customer;
import bootcamp.com.customerms.model.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/customers")
public class CustomerController {
  @Autowired
  @Qualifier("CustomerRepository")
  private ICustomerService customerService;

  /**
   * Method to find all customers.
   *
   * @return a list of clients.
   */
  @GetMapping("")
  public Flux<CustomerDto> findAllCustomer() {
    return customerService.findAllCustomer();
  }

  /**
   * Method to search by document number.
   *
   * @param id -> is the identifier of the customer to search for.
   * @return the wanted customer.
   */
  @GetMapping("/documentnumber/{id}")
  public Mono<ResponseEntity<Customer>> findOneCustomerByDni(@PathVariable String id) {
    return customerService.findByDocumentNumber(id)
      .flatMap(p -> Mono.just(ResponseEntity.ok().body(p)))
      .switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()));

  }

  /**
   * Method to search for a customer by id.
   *
   * @param id -> is the identifier of the customer.
   * @return the wanted customer.
   */
  @GetMapping("/{id}")
  public Mono<ResponseEntity<CustomerDto>> findOneCustomer(@PathVariable String id) {
    return customerService.findByIdCustomer(id)
      .flatMap(p -> Mono.just(ResponseEntity.ok().body(p)))
      .switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()));

  }

  /**
   * Method to save a customer.
   *
   * @param customer -> It is a collection of customer data.
   * @return the saved customer.
   */
  @PostMapping("")
  public Mono<ResponseEntity<CustomerDto>> saveCustomer(@RequestBody CustomerDto customer) {
    return customerService.createCustomer(customer)
      .flatMap(p -> Mono.just(ResponseEntity.ok().body(p)))
      .switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()));
  }

  /**
   * Method to update a customer.
   *
   * @param customer -> It is a collection of customer data.
   * @param id       -> is the identifier of the customer.
   * @return updated customer.
   */
  @PutMapping("/{id}")
  public Mono<ResponseEntity<CustomerDto>> updateCustomer(@PathVariable String id, @RequestBody CustomerDto customer) {
    return customerService.updateCustomer(customer, id)
      .flatMap(p -> Mono.just(ResponseEntity.ok().body(p)))
      .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }

  /**
   * Method to change the customer state.
   *
   * @param id -> is the identifier of the customer.
   * @return client with status change.
   */
  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<CustomerDto>> removeCustomer(@PathVariable("id") String id) {
    return customerService.removeCustomer(id)
      .flatMap(p -> Mono.just(ResponseEntity.ok().body(p)))
      .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }
}
