package edu.obya.blueprint.customer.infra.data.jpa;

import edu.obya.blueprint.customer.domain.CustomerId;
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
}
