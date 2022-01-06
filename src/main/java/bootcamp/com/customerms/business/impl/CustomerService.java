package bootcamp.com.customerms.business.impl;

import bootcamp.com.customerms.business.ICustomerService;
import bootcamp.com.customerms.repository.ICustomerRepository;
import bootcamp.com.customerms.model.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service("CustomerRepository")
public class CustomerService implements ICustomerService {

    @Value("${variable.state}")
    private String active;

    @Autowired
    private ICustomerRepository customerDao;

    @Override
    public Flux<Customer> findAllCustomer() {
        return customerDao.findAll().filter(e->e.getStatus().equalsIgnoreCase(active));
    }

    @Override
    public Mono<Customer> findByIdCustomer(String id) {
        return customerDao.findById(id).switchIfEmpty(Mono.empty()).filter(p->p.getStatus().equalsIgnoreCase("active"));
    }

    @Override
    public Mono<Customer> createCustomer(Customer customer) {
        return customerDao.findByDocumentNumber(customer.getDocumentNumber()).switchIfEmpty(Mono.just(new Customer()))
                .filter(e->e.getDocumentNumber() == null)
                .doOnNext(p->{
                    BeanUtils.copyProperties(customer,p);
                    p.setStatus("active");
                })
                .flatMap(customerDao::insert);
    }

    @Override
    public Mono<Customer> updateCustomer(Customer customer, String customerId) {
        return customerDao.findById(customerId).switchIfEmpty(Mono.empty())
                .doOnNext(p->{
                    BeanUtils.copyProperties(customer,p);
                    p.setId(customerId);
                }).flatMap(customerDao::save);
    }

    @Override
    public Mono<Customer> removeCustomer(String customerId) {
        return customerDao.findById(customerId).switchIfEmpty(Mono.empty())
                .doOnNext(p-> p.setStatus("inactive"))
                .flatMap(customerDao::save);
    }
}
