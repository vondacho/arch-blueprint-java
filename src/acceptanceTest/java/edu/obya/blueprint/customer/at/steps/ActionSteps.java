package edu.obya.blueprint.customer.at.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.obya.blueprint.customer.application.CustomerDto;
import edu.obya.blueprint.customer.at.TestWebUser;
import edu.obya.blueprint.customer.at.infra.TestContext;
import edu.obya.blueprint.customer.domain.model.CustomerId;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RequiredArgsConstructor
public class ActionSteps {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    @PersistenceContext
    private EntityManager entityManager;

    @When("registering the new customer")
    public void registeringTheNewCustomer(List<CustomerDto> attributes) throws Exception {
        val dto = attributes.get(0);
        TestContext.set("result", mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
                .with(httpBasic(
                        TestContext.atOrDefault("userId", TestWebUser.TEST_USER_NAME),
                        TestContext.atOrDefault("userPassword", TestWebUser.TEST_USER_PASSWORD)))
        ));
        entityManager.flush();
    }

    @When("listing all existing customers")
    public void listingAllExistingCustomers() throws Exception {
        TestContext.set("result", mockMvc.perform(get("/customers")
                .with(httpBasic(
                        TestContext.atOrDefault("userId", TestWebUser.TEST_USER_NAME),
                        TestContext.atOrDefault("userPassword", TestWebUser.TEST_USER_PASSWORD)))
        ));
    }

    @When("getting existing customer {customerId}")
    public void gettingExistingCustomerWithId(CustomerId id) throws Exception {
        TestContext.set("result", mockMvc.perform(get("/customers/" + id.getId())
                .with(httpBasic(
                        TestContext.atOrDefault("userId", TestWebUser.TEST_USER_NAME),
                        TestContext.atOrDefault("userPassword", TestWebUser.TEST_USER_PASSWORD)))
        ));
    }

    @When("replacing existing customer {customerId}")
    public void replacingExistingCustomerWithId(CustomerId id, List<CustomerDto> attributes) throws Exception {
        val dto = attributes.get(0);
        TestContext.set("result", mockMvc.perform(put("/customers/" + id.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
                .with(httpBasic(
                        TestContext.atOrDefault("userId", TestWebUser.TEST_USER_NAME),
                        TestContext.atOrDefault("userPassword", TestWebUser.TEST_USER_PASSWORD)))
        ));
        entityManager.flush();
    }

    @When("removing existing customer {customerId}")
    public void removingExistingCustomerWithId(CustomerId id) throws Exception {
        TestContext.set("result", mockMvc.perform(delete("/customers/" + id.getId())
                        .with(httpBasic(
                                TestContext.atOrDefault("userId", TestWebUser.TEST_ADMIN_NAME),
                                TestContext.atOrDefault("userPassword", TestWebUser.TEST_ADMIN_PASSWORD)))
        ));
        entityManager.flush();
    }
}
