package org.lukaszse.carRental.service

import org.lukaszse.carRental.model.User
import org.lukaszse.carRental.model.dto.PasswordChangeDto
import org.lukaszse.carRental.repository.UserRepository
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification


@SpringBootTest
class UserServiceSpec extends Specification {

    PasswordEncoder passwordEncoder = Mock()
    UserRepository userRepository = Mock()
    UserService userService = new UserService(userRepository)

    def "ChangePassword"() {

        given: "Preparing all required Mocks"
        userRepository.findUserByUserName(_ as String) >> Optional.of(new User())
//        if (newPassword == newPasswordConfirm) {
//            passwordEncoder.matches(_ as String, _ as String)  >> Boolean.TRUE
//        } else {
//            passwordEncoder.matches(_ as String, _ as String)  >> Boolean.FALSE
//        }


        and:
        def passwordChangeDto = prepareChangePasswordDto(oldPassword, newPassword, newPasswordConfirm)

        when:
        userService.changePassword(userName, passwordChangeDto)

        then:
        noExceptionThrown()
        1 * userRepository.changePassword(_ as String, _ as String)

        where:
        no | userName | oldPassword | newPassword | newPasswordConfirm
        1  | "joe"    | "password"    | "qwerty"    | "qwerty"
    }

    def static prepareChangePasswordDto(String oldPassword, String newPassword, String newPasswordConfirm) {
        def passwordChangeDto = new PasswordChangeDto()
        passwordChangeDto.setOldPassword(oldPassword)
        passwordChangeDto.setNewPassword(newPassword)
        passwordChangeDto.setNewPasswordConfirm(newPasswordConfirm)
        passwordChangeDto
    }

    def static prepareUser(String userName){
        def user = new User()
        user.setUserName("Joe")
        user.setPassword('{bcrypt}\$2a\$12$feoSS.Dx/rRdQWfWHeWYZu8txsYcy8Dxt89MWd9U3O8r4CaAKKY3S') // Password = password
    }
}
