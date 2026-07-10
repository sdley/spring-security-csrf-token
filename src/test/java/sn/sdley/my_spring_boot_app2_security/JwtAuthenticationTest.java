package sn.sdley.my_spring_boot_app2_security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sn.sdley.my_spring_boot_app2_security.config.JwtAuthenticationFilter;
import sn.sdley.my_spring_boot_app2_security.model.User;
import sn.sdley.my_spring_boot_app2_security.model.UserPrincipal;
import sn.sdley.my_spring_boot_app2_security.service.JWTService;
import sn.sdley.my_spring_boot_app2_security.service.MyUserDetailsService;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

class JwtAuthenticationTest {

    private static final String SECRET = "c3ByaW5nLXNlY3VyaXR5LWxvY2FsLWRldi1zZWNyZXQtMzItYnl0ZXMhIQ==";

    private final JWTService jwtService = new JWTService(SECRET, 60_000L);
    private final JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, new StubUserDetailsService());

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void generatedTokenCanBeValidated() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");

        String token = jwtService.generateToken(user);

        assertEquals("user", jwtService.extractUsername(token));
        assertTrue(jwtService.isTokenValid(token, new UserPrincipal(user)));
    }

    @Test
    void bearerTokenPopulatesSecurityContext() throws Exception {
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");

        String token = jwtService.generateToken(user);
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/students");
        request.addHeader("Authorization", "Bearer " + token);
        MockHttpServletResponse response = new MockHttpServletResponse();
        AtomicBoolean chainInvoked = new AtomicBoolean(false);

        filter.doFilter(request, response, new NoOpFilterChain(chainInvoked));

        assertEquals(200, response.getStatus());
        assertTrue(chainInvoked.get());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals("user", authentication.getName());
    }

    @Test
    void invalidBearerTokenIsRejected() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/students");
        request.addHeader("Authorization", "Bearer invalid-token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilter(request, response, new NoOpFilterChain(new AtomicBoolean(false)));

        assertEquals(401, response.getStatus());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    private static final class StubUserDetailsService extends MyUserDetailsService {

        private StubUserDetailsService() {
            super(null);
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            if (!"user".equals(username)) {
                throw new UsernameNotFoundException(username);
            }

            User user = new User();
            user.setUsername("user");
            user.setPassword("password");
            return new UserPrincipal(user);
        }
    }

    private static final class NoOpFilterChain implements FilterChain {

        private final AtomicBoolean invoked;

        private NoOpFilterChain(AtomicBoolean invoked) {
            this.invoked = invoked;
        }

        @Override
        public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response)
                throws IOException, ServletException {
            invoked.set(true);
        }
    }
}
