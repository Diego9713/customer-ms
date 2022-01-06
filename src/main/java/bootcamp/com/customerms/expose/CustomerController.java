package bootcamp.com.customerms.expose;

import bootcamp.com.customerms.model.Customer;
import bootcamp.com.customerms.business.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/customers")
public class CustomerController {
    @Autowired
    @Qualifier("CustomerRepository")
    private ICustomerService customerRepository;

    @GetMapping("")
    public Flux<Customer> findAllCustomer(){
        return customerRepository.findAllCustomer();
    }
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Customer>> findOneCustomer(@PathVariable String id){
        return customerRepository.findByIdCustomer(id)
                .flatMap(p->Mono.just(ResponseEntity.ok().body(p)))
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()));

    }
    @PostMapping("")
    public Mono<ResponseEntity<Customer>> saveCustomer(@RequestBody Customer customer){
        return customerRepository.createCustomer(customer)
                .flatMap(p->Mono.just(ResponseEntity.ok().body(p)))
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()));
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Customer>> updateCustomer(@PathVariable String id,@RequestBody Customer customer){
        return customerRepository.updateCustomer(customer,id)
                .flatMap(p->Mono.just(ResponseEntity.ok().body(p)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Customer>> removeCustomer(@PathVariable("id") String id){
        return customerRepository.removeCustomer(id)
                .flatMap(p->Mono.just(ResponseEntity.ok().body(p)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}
