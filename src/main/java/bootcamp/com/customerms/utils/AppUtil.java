package bootcamp.com.customerms.utils;
import bootcamp.com.customerms.model.Customer;
import bootcamp.com.customerms.model.CustomerDto;
import org.springframework.beans.BeanUtils;

public class AppUtil {

    public static CustomerDto entityToProductDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer,customerDto);
        return customerDto;
    }
}
