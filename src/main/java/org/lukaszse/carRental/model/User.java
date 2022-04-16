package org.lukaszse.carRental.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = "userName")
@NoArgsConstructor
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @Column(name = "user_name")
    @NotBlank(message = "Login cannot be blank")
    @Pattern(regexp = "^[a-zA-Z]+\\w{1,19}", message = "Login must start with a letter and contain 2 - 20 word characters (digits, letters, _)")
    private String userName;

    @Column(name = "first_name")
    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    private String userRole;

    @NotBlank(message = "Password cannot be blank")
    @Pattern(regexp = "^.{6,}", message = "Password must contain at least 6 characters")
    private String password;
}
