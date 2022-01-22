package bootcamp.com.customerms.expose;

import static org.mockito.Mockito.when;

import bootcamp.com.customerms.business.impl.CustomerService;
import bootcamp.com.customerms.model.Customer;
import bootcamp.com.customerms.model.CustomerDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "20000")
class CustomerControllerTest {

  @MockBean
  private CustomerService customerService;
  @Autowired
  private WebTestClient webTestClient;
  @Autowired
  private CustomerController customerController;

  private static final CustomerDto customerMock = new CustomerDto();
  private static final Customer customer = new Customer();
  private static final List<CustomerDto> customerDtoList = new ArrayList<>();
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
    BeanUtils.copyProperties(customerMock, customer);
    customerDtoList.add(customerMock);
  }

  @Test
  @DisplayName("GET -> /api/v1/customers")
  void findAllCustomer() {
    when(customerService.findAllCustomer()).thenReturn(Flux.fromIterable(customerDtoList));
    Assertions.assertNotNull(customerController.findAllCustomer());
  }

  @Test
  @DisplayName("GET -> /api/v1/customers/{id}")
  void byId() {

    when(customerService.findByIdCustomer(id)).thenReturn(Mono.just(customerMock));

    WebTestClient.ResponseSpec responseSpec = webTestClient.get().uri("/api/v1/customers/" + id)
      .accept(MediaType.APPLICATION_JSON)
      .exchange();

    responseSpec.expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON);
    responseSpec.expectBody()
      .jsonPath("$.firstName").isEqualTo(customerMock.getFirstName());

  }

  @Test
  @DisplayName("GET -> /api/v1/customers/documentnumber/{id}")
  void documentNumberById() {

    when(customerService.findByDocumentNumber(id)).thenReturn(Mono.just(customer));

    WebTestClient.ResponseSpec responseSpec = webTestClient.get().uri("/api/v1/customers/documentnumber/" + id)
      .accept(MediaType.APPLICATION_JSON)
      .exchange();

    responseSpec.expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON);
    responseSpec.expectBody()
      .jsonPath("$.documentNumber").isEqualTo(customerMock.getDocumentNumber());

  }

  @Test
  @DisplayName("POST -> /api/v1/customers")
  void save() {
    when(customerService.createCustomer(customerMock)).thenReturn(Mono.just(customerMock));
    Assertions.assertNotNull(customerController.saveCustomer(customerMock));
  }

  @Test
  @DisplayName("UPDATE -> /api/v1/customers/{id}")
  void update() {
    when(customerService.updateCustomer(customerMock, id)).thenReturn(Mono.just(customerMock));
    Assertions.assertNotNull(customerController.updateCustomer(id,customerMock));
  }

  @Test
  @DisplayName("DELETE -> /api/v1/customers/{id}")
  void remove() {

    when(customerService.removeCustomer(id)).thenReturn(Mono.just(customerMock));

    WebTestClient.ResponseSpec responseSpec = webTestClient.delete().uri("/api/v1/customers/" + id)
      .accept(MediaType.APPLICATION_JSON)
      .exchange();

    responseSpec.expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON);
    responseSpec.expectBody()
      .jsonPath("$.id").isEqualTo(customerMock.getId());

  }
}
