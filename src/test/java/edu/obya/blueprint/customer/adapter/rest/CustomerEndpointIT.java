package edu.obya.blueprint.customer.adapter.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.obya.blueprint.config.ExceptionHandling;
import edu.obya.blueprint.config.WebSecurityConfiguration;
import edu.obya.blueprint.config.WebValidationConfiguration;
import edu.obya.blueprint.customer.adapter.rest.CustomerSummary;
import edu.obya.blueprint.customer.adapter.rest.CustomerWebConfiguration;
import edu.obya.blueprint.customer.application.CustomerApplicationConfiguration;
import edu.obya.blueprint.customer.application.CustomerDto;
import edu.obya.blueprint.customer.domain.model.CustomerId;
import edu.obya.blueprint.customer.adapter.jpa.CustomerJpaConfiguration;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.function.Supplier;

import static edu.obya.blueprint.customer.adapter.rest.TestUser.*;
import static edu.obya.blueprint.customer.application.TestCustomerIn.TEST_CUSTOMER_IN;
import static edu.obya.blueprint.customer.domain.model.TestCustomer.TEST_CUSTOMER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test","jpa"})
@Import({
        CustomerWebConfiguration.class,
        WebSecurityConfiguration.class,
        WebValidationConfiguration.class,
        ExceptionHandling.class,
        CustomerJpaConfiguration.class,
        CustomerApplicationConfiguration.class})
@AutoConfigureEmbeddedDatabase
@AutoConfigureMockMvc
@SpringBootTest
public class CustomerEndpointIT {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    Supplier<CustomerId> idSupplier;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @Transactional
    void shouldCreateAndModifyAndDeleteCustomer() throws Exception {
        when(idSupplier.get()).thenReturn(TEST_CUSTOMER_ID);
        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TEST_CUSTOMER_IN))
                        .with(httpBasic(TEST_USER_NAME, TEST_USER_PASSWORD)))
                .andExpect(status().is(201))
                .andExpect(content().string(TEST_CUSTOMER_ID.toString()));

        entityManager.flush();

        List<CustomerId> persisted = jdbcTemplate.query("select id from customer", (rs, rowNum) ->
                CustomerId.parse(rs.getString("id")));

        assertThat(persisted).containsOnly(TEST_CUSTOMER_ID);

        val dto = CustomerDto.builder().firstName("Bob").lastName("Dylan").build();
        mockMvc.perform(put("/customers/{id}", TEST_CUSTOMER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .with(httpBasic(TEST_USER_NAME, TEST_USER_PASSWORD)))
                .andExpect(status().is(204));

        entityManager.flush();

        List<CustomerSummary> all = jdbcTemplate.query("select id, firstname, lastname from customer", (rs, rowNum) ->
                CustomerSummary.builder()
                        .id(rs.getString("id"))
                        .firstName(rs.getString("firstname"))
                        .lastName(rs.getString("lastname"))
                        .build()
        );

        assertThat(all).containsOnly(CustomerSummary.builder()
                .id(TEST_CUSTOMER_ID.getId().toString())
                .firstName("Bob")
                .lastName("Dylan")
                .build());

        mockMvc.perform(delete("/customers/{id}", TEST_CUSTOMER_ID)
                        .with(httpBasic(TEST_ADMIN_NAME, TEST_ADMIN_PASSWORD)))
                .andExpect(status().is(204));

        entityManager.flush();
    }
}
