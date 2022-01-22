package bootcamp.com.customerms.utils;

import bootcamp.com.customerms.model.Customer;
import bootcamp.com.customerms.model.CustomerDto;
import org.springframework.beans.BeanUtils;
import reactor.core.publisher.Mono;

/**
 * Method to modify the customer attributes.
 */
public class AppUtil {

  private AppUtil() {
  }

  /**
   * Method to modify the return of data.
   *
   * @param customer -> is object with data complete.
   * @return object customer modified.
   */
  public static CustomerDto entityToProductDto(Customer customer) {
    CustomerDto customerDto = new CustomerDto();
    BeanUtils.copyProperties(customer, customerDto);
    return customerDto;
  }

  /**
   * Method to modify the return of data.
   *
   * @param customerDto -> is object with data complete.
   * @return object customer modified.
   */
  public static Customer productDtoToEntity(CustomerDto customerDto) {
    Customer customer = new Customer();
    BeanUtils.copyProperties(customerDto, customer);
    return customer;
  }

  /**
   * Method to create object the return of data.
   *
   * @param customer -> is object with data complete.
   * @return object customer modified.
   */
  public static Mono<CustomerDto> createObjectCustomer(CustomerDto customer) {
    CustomerDto customerDto = new CustomerDto();
    BeanUtils.copyProperties(customer, customerDto);
    customerDto.setCustomerType(customerDto.getCustomerType().toUpperCase());
    customerDto.setStatus(ConstantsCustomer.ACTIVE.name());
    return Mono.just(customerDto);
  }

  /**
   * Method to update object the return of data.
   *
   * @param customer -> is object with data complete.
   * @return object customer modified.
   */
  public static Mono<CustomerDto> updateObjectCustomer(CustomerDto customer,String id) {
    CustomerDto customerDto = new CustomerDto();
    BeanUtils.copyProperties(customer, customerDto);
    customerDto.setCustomerType(customerDto.getCustomerType().toUpperCase());
    customerDto.setId(id);
    customerDto.setStatus(ConstantsCustomer.ACTIVE.name());
    return Mono.just(customerDto);
  }

}
