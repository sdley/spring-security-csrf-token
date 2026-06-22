package sn.sdley.my_spring_boot_app2_security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    private List<Student> students = new ArrayList<>(List.of(
            new Student(1, "John", "Doe"),
            new Student(2, "Jane", "Smith"),
            new Student(3, "Bob", "Johnson")
    ));


    @GetMapping("/students")
    public List<Student> getStudents() {
        return students;
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        students.add(student);
        return student;
    }

    @GetMapping("csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest  request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }
}
