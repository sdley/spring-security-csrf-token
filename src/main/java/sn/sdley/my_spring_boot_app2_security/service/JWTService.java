package sn.sdley.my_spring_boot_app2_security.service;

import org.springframework.stereotype.Service;
import sn.sdley.my_spring_boot_app2_security.model.User;

@Service
public class JWTService {
    private final UserService userService;
    
    public JWTService(UserService userService) {
        this.userService = userService;
    }

    public String generateToken(User user) {
        // The JWT implementation will go here
        return "";
    }

    // Add JWT-related methods here
    
}
