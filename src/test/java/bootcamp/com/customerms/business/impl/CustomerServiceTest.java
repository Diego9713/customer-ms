package bootcamp.com.customerms.business.impl;

import static org.mockito.Mockito.when;

import bootcamp.com.customerms.model.Customer;
import bootcamp.com.customerms.model.CustomerDto;
import bootcamp.com.customerms.repository.ICustomerRepository;
import bootcamp.com.customerms.utils.ConstantsCustomer;
import com.google.gson.Gson;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CustomerServiceTest {
  @Autowired
  private CustomerService customerService;
  @MockBean
  private ICustomerRepository customerRepository;

  public static MockWebServer mockBackEnd;
  private static final Customer customerMock = new Customer();
  private static final CustomerDto customerMockDto = new CustomerDto();
  private static final List<Customer> customerList = new ArrayList<>();
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
  static void setUp(@Value("${server.port}") int port) throws IOException {
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
    customerList.add(customerMock);
    BeanUtils.copyProperties(customerMock, customerMockDto);
    mockBackEnd = new MockWebServer();
    mockBackEnd.start(port);
  }

  @AfterAll
  static void tearDown() throws IOException {
    mockBackEnd.shutdown();
  }


  @BeforeEach
  void setUp() {
    Gson gson = new Gson();
    mockBackEnd.url("http://localhost:9090/customer");
    mockBackEnd.enqueue(new MockResponse()
      .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .setBody(gson.toJson(customerList))
      .setResponseCode(HttpStatus.OK.value()));
  }

  @Test
  void findAllCustomer() {
    when(customerRepository.findAll()).thenReturn(Flux.fromIterable(customerList));
    Assertions.assertNotNull(customerService.findAllCustomer());
  }

  @Test
  void findByIdCustomer() {
    when(customerRepository.findById(id)).thenReturn(Mono.just(customerMock));
    Assertions.assertNotNull(customerService.findByIdCustomer(id));
  }

  @Test
  void findByDocumentNumber() {
    when(customerRepository.findByDocumentNumber(documentNumber)).thenReturn(Mono.just(customerMock));
    Assertions.assertNotNull(customerService.findByDocumentNumber(documentNumber));
  }

  @Test
  void createCustomer() {
    when(customerRepository.findByDocumentNumber(documentNumber)).thenReturn(Mono.just(new Customer()));
    when(customerRepository.save(customerMock)).thenReturn(Mono.just(customerMock));
    Assertions.assertNotNull(customerService.createCustomer(customerMockDto));
  }

  @Test
  void updateCustomer() {
    when(customerRepository.findById(id)).thenReturn(Mono.just(customerMock));
    when(customerRepository.save(customerMock)).thenReturn(Mono.just(customerMock));
    Assertions.assertNotNull(customerService.updateCustomer(customerMockDto,id));
  }

  @Test
  void removeCustomer() {
    when(customerRepository.findById(id)).thenReturn(Mono.just(customerMock));
    when(customerRepository.save(customerMock)).thenReturn(Mono.just(customerMock));
    Assertions.assertNotNull(customerService.removeCustomer(id));
  }
}
