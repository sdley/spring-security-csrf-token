package sn.sdley.my_spring_boot_app2_security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private int id;
    private String firstName;
    private String lastName;
}
