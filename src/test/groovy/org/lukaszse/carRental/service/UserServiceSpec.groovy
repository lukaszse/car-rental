package org.lukaszse.carRental.service

import org.lukaszse.carRental.exceptions.WrongPayloadException
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

    def "should change password"() {

        given: "Mock all required methods"
        userRepository.findUserByUserName(_ as String) >> Optional.of(prepareUser())
        passwordEncoder.matches(_ as String, _ as String) >> true
        passwordEncoder.encode(_ as String) >> "some encrypted password"
        userService.passwordEncoder = passwordEncoder


        and: "prepare prepareChangePasswordDto object"
        def passwordChangeDto = prepareChangePasswordDto(oldPassword, newPassword, newPasswordConfirm)

        when: "try to change password"
        userService.changePassword(userName, passwordChangeDto)

        then: "should throw now exception and invoke changePassword() method once"
        noExceptionThrown()
        1 * userRepository.changePassword(_ as String, _ as String)

        where:
        no | userName | oldPassword | newPassword | newPasswordConfirm
        1  | "joe"    | "password"  | "qwerty"    | "qwerty"
        2  | "joe"    | "password"  | "123456"    | "123456"
    }

    def "should throw exception while trying to change password with wrong data"() {

        given: "Mock all required methods"
        userRepository.findUserByUserName(_ as String) >> Optional.of(prepareUser())
        passwordEncoder.matches(_ as String, _ as String) >> passwordEncoderMatch
        passwordEncoder.encode(_ as String) >> "some encrypted password"
        userService.passwordEncoder = passwordEncoder


        and: "prepare prepareChangePasswordDto object"
        def passwordChangeDto = prepareChangePasswordDto(oldPassword, newPassword, newPasswordConfirm)

        when: "try to change password"
        userService.changePassword(userName, passwordChangeDto)

        then: "should throw exception and not change password"
        thrown(WrongPayloadException.class)
        0 * userRepository.changePassword(_ as String, _ as String)

        where:
        no | userName | oldPassword | newPassword | newPasswordConfirm | passwordEncoderMatch
        1  | "joe"    | "password"  | "qwerty2"   | "qwerty"           | true
        2  | "joe"    | "password"  | "1234562"   | "123456"           | true
        3  | "joe"    | "password"  | "123456"    | "123456"           | false
    }

    def static prepareChangePasswordDto(String oldPassword, String newPassword, String newPasswordConfirm) {
        def passwordChangeDto = new PasswordChangeDto()
        passwordChangeDto.setOldPassword(oldPassword)
        passwordChangeDto.setNewPassword(newPassword)
        passwordChangeDto.setNewPasswordConfirm(newPasswordConfirm)
        passwordChangeDto
    }

    def static prepareUser() {
        def user = new User()
        user.setUserName("Joe")
        user.setPassword('{bcrypt}\$2a\$12$feoSS.Dx/rRdQWfWHeWYZu8txsYcy8Dxt89MWd9U3O8r4CaAKKY3S')
        // Password = password
        user
    }
}
