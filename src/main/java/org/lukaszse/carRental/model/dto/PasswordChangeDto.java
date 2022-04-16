package org.lukaszse.carRental.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordChangeDto {

    @NotBlank
    private String oldPassword;
    @NotBlank(message = "Password cannot be blank")
    @Pattern(regexp = "^\\w{5,20}", message = "Password must contain 6 - 20 word characters (digits, letters, _)")
    private String newPassword;
    @NotBlank(message = "Password cannot be blank")
    @Pattern(regexp = "^\\w{5,20}", message = "Password must contain 6 - 20 word characters (digits, letters, _)")
    private String newPasswordConfirm;
}
