package co.edu.corhuila.barbersaas.platformadmin.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import org.junit.jupiter.api.Test;

/**
 * Story code-corhuila/barber-saas-docs#159, criterion 1 (STRIDE E, risk 6): the worker token that
 * relays events has no scope subscriptions:expire, so POST /internal/v1/subscriptions/expire must
 * answer 403 and no barbershop may change status.
 */
class WorkerScopeHttpTest extends HttpTest {

    @Test
    void a_worker_token_without_the_expire_scope_cannot_suspend_barbershops() throws Exception {
        UUID shop = BARBERSHOPS.add("ACTIVE", null, 40);
        String relayOnly = serviceBearer("barber-saas-worker");               // sub only, no scope claim

        http.perform(post("/internal/v1/subscriptions/expire").header("Authorization", relayOnly))
                .andExpect(status().isForbidden());

        assertEquals("ACTIVE", BARBERSHOPS.row(shop).path("status").asText());
    }
}
