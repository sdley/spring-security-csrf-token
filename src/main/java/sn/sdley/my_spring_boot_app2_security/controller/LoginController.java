package sn.sdley.my_spring_boot_app2_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

@Controller
public class LoginController {

    @GetMapping(value = "/login", produces = TEXT_HTML_VALUE)
    @ResponseBody
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout) {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Login</title>
                    <style>
                        body { font-family: Arial, sans-serif; max-width: 420px; margin: 48px auto; padding: 0 16px; }
                        .card { border: 1px solid #ddd; border-radius: 12px; padding: 24px; box-shadow: 0 2px 10px rgba(0,0,0,.06); }
                        .message { padding: 12px; border-radius: 8px; margin-bottom: 16px; }
                        .success { background: #edf7ed; color: #1e4620; }
                        .error { background: #fdecea; color: #611a15; }
                        label { display: block; margin: 12px 0 6px; }
                        input { width: 100%%; padding: 10px; box-sizing: border-box; }
                        button { margin-top: 16px; padding: 10px 14px; width: 100%%; }
                    </style>
                </head>
                <body>
                <div class="card">
                    <h1>Sign in</h1>
                    %s
                    %s
                    <form method="post" action="/login">
                        <label for="username">Username</label>
                        <input type="text" id="username" name="username" autocomplete="username" required>

                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" autocomplete="current-password" required>

                        <button type="submit">Login</button>
                    </form>
                </div>
                </body>
                </html>
                """.formatted(
                logout != null ? "<div class=\"message success\">You have been logged out successfully.</div>" : "",
                error != null ? "<div class=\"message error\">Invalid username or password.</div>" : ""
        );
    }
}

