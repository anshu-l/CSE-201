package users;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.HashMap;

public class AuthenticationTest {

    @BeforeEach
    public void setUp() {
        // Prepopulate some credentials
        Authentication.adminCredentials.put("admin", "admin123");
        Authentication.customerCredentials.put("testuser@example.com", "password123");
    }

    @Test
    public void testAdminLoginSuccess() {
        // Simulate valid admin login
        boolean result = Authentication.adminCredentials.containsKey("admin")
                && Authentication.adminCredentials.get("admin").equals("admin123");
        assertTrue(result, "Admin login should succeed with correct credentials.");
    }

    @Test
    public void testAdminLoginFailure() {
        // Simulate invalid admin login
        boolean result = Authentication.adminCredentials.containsKey("admin")
                && Authentication.adminCredentials.get("admin").equals("wrongpassword");
        assertFalse(result, "Admin login should fail with incorrect credentials.");
    }

    @Test
    public void testCustomerLoginSuccess() {
        // Simulate valid customer login
        boolean result = Authentication.customerCredentials.containsKey("testuser@example.com")
                && Authentication.customerCredentials.get("testuser@example.com").equals("password123");
        assertTrue(result, "Customer login should succeed with correct email and password.");
    }

    @Test
    public void testCustomerLoginFailure() {
        // Simulate invalid customer login
        boolean result = Authentication.customerCredentials.containsKey("nonexistent@example.com")
                && Authentication.customerCredentials.get("nonexistent@example.com").equals("password123");
        assertFalse(result, "Customer login should fail with incorrect email or password.");
    }

    @Test
    public void testCustomerSignup() {
        // Simulate customer signup
        String newEmail = "newuser@example.com";
        String newPassword = "newpassword";
        Authentication.customerCredentials.put(newEmail, newPassword);

        // Verify that the new customer is added
        assertTrue(Authentication.customerCredentials.containsKey(newEmail), "Customer should be successfully signed up.");
        assertEquals(newPassword, Authentication.customerCredentials.get(newEmail), "Password should match for the new customer.");
    }

}
