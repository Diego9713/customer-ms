package bootcamp.com.customerms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "customer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    private String id;
    @Field(name = "document_type")
    private String documentType;
    @Field(name = "document_number")
    private String documentNumber;
    @Field(name = "customer_type")
    private String customerType;
    @Field(name = "first_name")
    private String firstName;
    @Field(name = "last_name")
    private String lastName;
    private String address;
    private String references;
    @Field(name = "phone_number")
    private String phoneNumber;
    @Field(name = "civil_status")
    private String civilStatus;
    @Field(name = "email")
    private String email;
    @Field(name = "is_owner")
    private boolean isOwner;
    @Field(name = "status")
    private String status;
}
