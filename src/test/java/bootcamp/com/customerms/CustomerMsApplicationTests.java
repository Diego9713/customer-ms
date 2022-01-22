package bootcamp.com.customerms;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerMsApplicationTests {

  @Test
  void contextLoads() {
    Assertions.assertNotNull(CustomerMsApplication.class);
  }

}
