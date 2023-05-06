package edu.obya.blueprint.customer.adapter.jpa;

import edu.obya.blueprint.customer.domain.model.CustomerState;
import edu.obya.blueprint.customer.domain.model.CustomerId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JpaCustomer {

    @Id
    UUID pk;
    @Column(name = "id")
    CustomerId logicalId;
    @Column(name = "firstname")
    String firstName;
    @Column(name = "lastname")
    String lastName;

    public static JpaCustomer from(CustomerState customer, CustomerId customerId, UUID pk) {
        return new JpaCustomer(
            pk,
            customerId,
            customer.getFirstName(),
            customer.getLastName()
        );
    }

    public JpaCustomer set(CustomerState customer, CustomerId customerId) {
        setLogicalId(customerId);
        setLastName(customer.getLastName());
        setFirstName(customer.getFirstName());
        return this;
    }
}
