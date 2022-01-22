package bootcamp.com.customerms.utils;

import bootcamp.com.customerms.model.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppUtilTest {
  private static final Customer customerMock = new Customer();
  private final static String id = "61db5ffd7610bd27a53b2b8b";
  private final static String documentType = "dni";
  private final static String documentNumber = "71528107";
  private final static String customerType = "PERSONAL";
  private final static String firstName = "Diego";
  private final static String lastName = "Tafur";
  private final static String address = "limatambo norte";
  private final static String references = "videna";
  private final static String phoneNumber = "9492507508";
  private final static String civilStatus = "soltero";
  private final static String email = "tafur232@gmail.com";
  private final static boolean owner = true;
  private final static String status = "ACTIVE";

  @BeforeAll
  static void setUp() {
    customerMock.setId(id);
    customerMock.setDocumentType(documentType);
    customerMock.setDocumentNumber(documentNumber);
    customerMock.setCustomerType(customerType);
    customerMock.setFirstName(firstName);
    customerMock.setLastName(lastName);
    customerMock.setAddress(address);
    customerMock.setReferences(references);
    customerMock.setPhoneNumber(phoneNumber);
    customerMock.setCivilStatus(civilStatus);
    customerMock.setEmail(email);
    customerMock.setOwner(owner);
    customerMock.setStatus(status);
  }

  @Test
  void entityToProductDto() {
    Assertions.assertNotNull(AppUtil.entityToProductDto(customerMock));
  }
}
