package sn.sdley.my_spring_boot_app2_security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home(HttpServletRequest request) {
        return "<h1>Welcome to spring security!</h1>" +
                "<h2>Session ID: " + request.getSession().getId() + "</h2>" +
                "<p>Request URI: " + request.getRequestURI() + "</p>" +
                "<p>Method: " + request.getMethod() + "</p>" +
                "<p>Remote Address: " + request.getRemoteAddr() + "</p>" +
                "<p>Remote Host: " + request.getRemoteHost() + "</p>" +
                "<p>Remote User: " + request.getRemoteUser() + "</p>" +
                "<p>Request Parameters: " + request.getParameterMap() + "</p>" +
                "<p>Request Headers: " + request.getHeaderNames() + "</p>" +
                "<p>Request Protocol: " + request.getProtocol() + "</p>" +
                "<p>Request Scheme: " + request.getScheme() + "</p>" +
                "<p>Request Server Name: " + request.getServerName() + "</p>" +
                "<p>Request Server Port: " + request.getServerPort() + "</p>";

    }
}
