package edu.obya.blueprint.customer.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.obya.blueprint.ExceptionHandling;
import edu.obya.blueprint.WebSecurityConfiguration;
import edu.obya.blueprint.WebValidationConfiguration;
import edu.obya.blueprint.customer.TestUser;
import edu.obya.blueprint.customer.application.CustomerApplicationConfiguration;
import edu.obya.blueprint.customer.application.CustomerDto;
import edu.obya.blueprint.customer.domain.CustomerId;
import edu.obya.blueprint.customer.infra.data.jpa.CustomerJpaConfiguration;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
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
        CustomerId customerId = CustomerId.parse("fcfa9add-2444-49c3-916b-988e5ddb9fac");
        when(idSupplier.get()).thenReturn(customerId);
        CustomerDto dto = CustomerDto.builder().firstName("test").lastName("test").build();
        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .with(httpBasic(TestUser.TEST_USER_NAME, TestUser.TEST_USER_PASSWORD)))
                .andExpect(status().is(201))
                .andExpect(content().string(customerId.getId().toString()));

        entityManager.flush();

        List<CustomerId> persisted = jdbcTemplate.query("select id from customer", (rs, rowNum) ->
                CustomerId.parse(rs.getString("id")));

        assertThat(persisted).containsOnly(customerId);

        dto = CustomerDto.builder().firstName("john").lastName("doe").build();
        mockMvc.perform(put("/customers/{id}", customerId.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .with(httpBasic(TestUser.TEST_USER_NAME, TestUser.TEST_USER_PASSWORD)))
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
                .id("fcfa9add-2444-49c3-916b-988e5ddb9fac")
                .firstName("john")
                .lastName("doe")
                .build());

        mockMvc.perform(delete("/customers/{id}", customerId.getId())
                        .with(httpBasic(TestUser.TEST_ADMIN_NAME, TestUser.TEST_ADMIN_PASSWORD)))
                .andExpect(status().is(204));

        entityManager.flush();
    }
}
