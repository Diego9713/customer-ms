package bootcamp.com.customerms.business.impl;

import bootcamp.com.customerms.business.ICustomerService;
import bootcamp.com.customerms.model.CustomerDto;
import bootcamp.com.customerms.repository.ICustomerRepository;
import bootcamp.com.customerms.model.Customer;
import bootcamp.com.customerms.utils.AppUtil;
import bootcamp.com.customerms.utils.ConstantsCustomer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service("CustomerRepository")
@Slf4j
public class CustomerService implements ICustomerService {

    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public Flux<CustomerDto> findAllCustomer() {
        log.info("findAll customer >>>");
        return customerRepository.findAll().filter(e->e.getStatus().equalsIgnoreCase(ConstantsCustomer.ACTIVE.name())).map(AppUtil::entityToProductDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<CustomerDto> findByIdCustomer(String id) {
        log.info("findOne customer >>>");
        return customerRepository.findById(id).switchIfEmpty(Mono.empty()).filter(p->p.getStatus().equalsIgnoreCase(ConstantsCustomer.ACTIVE.name())).map(AppUtil::entityToProductDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Customer> findByDocumentNumber(String dni) {
        log.info("findOne customerByDni >>>");
        return customerRepository.findByDocumentNumber(dni).switchIfEmpty(Mono.empty()).filter(p->p.getStatus().equalsIgnoreCase(ConstantsCustomer.ACTIVE.name()));
    }

    @Override
    @Transactional
    public Mono<CustomerDto> createCustomer(Customer customer) {
        log.info("save customer >>>");
        return customerRepository.findByDocumentNumber(customer.getDocumentNumber()).switchIfEmpty(Mono.just(new Customer()))
                .filter(e->e.getDocumentNumber() == null)
                .doOnNext(p->{
                    BeanUtils.copyProperties(customer,p);
                    p.setCustomerType(p.getCustomerType().toUpperCase());
                    p.setStatus(ConstantsCustomer.ACTIVE.name());
                })
                .flatMap(customerRepository::insert).map(AppUtil::entityToProductDto);
    }

    @Override
    @Transactional
    public Mono<CustomerDto> updateCustomer(Customer customer, String customerId) {
        log.info("update customer >>>");
        return customerRepository.findById(customerId).switchIfEmpty(Mono.empty())
                .doOnNext(p->{
                    BeanUtils.copyProperties(customer,p);
                    p.setCustomerType(p.getCustomerType().toUpperCase());
                    p.setId(customerId);
                    p.setStatus(ConstantsCustomer.ACTIVE.name());
                }).flatMap(customerRepository::save).map(AppUtil::entityToProductDto);
    }

    @Override
    @Transactional
    public Mono<CustomerDto> removeCustomer(String customerId) {
        log.info("delete customer >>>");
        return customerRepository.findById(customerId).switchIfEmpty(Mono.empty())
                .doOnNext(p-> p.setStatus(ConstantsCustomer.INACTIVE.name()))
                .flatMap(customerRepository::save).map(AppUtil::entityToProductDto);
    }
}
