package bootcamp.com.customerms.utils;

import bootcamp.com.customerms.model.Customer;
import bootcamp.com.customerms.model.CustomerDto;
import org.springframework.beans.BeanUtils;

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
}
