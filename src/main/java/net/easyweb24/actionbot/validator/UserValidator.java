/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.validator;

/**
 *
 * @author zbigniewwilgosz
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.easyweb24.actionbot.dto.UserRegistrationDto;
import net.easyweb24.actionbot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserServiceImpl userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserRegistrationDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserRegistrationDto user = (UserRegistrationDto) o;

        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (user.getEmail().length() < 6 || user.getEmail().length() > 32) {
            errors.rejectValue("email","Size.userRegistrationDto.username");
        }
        if (userService.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email","Duplicate.userRegistrationDto.username");
        }
        
        if (!new EmailValidator().validate(user.getEmail())) {
            errors.rejectValue("email","Novalid.userRegistrationDto.email");
        }
        
        if(!user.getEmail().equals(user.getConfirmEmail())){
            errors.rejectValue("confirmEmail","Diff.userRegistrationDto.emailConfirm");
        }

        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password","Size.userRegistrationDto.password");
        }

        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "Diff.userRegistrationDto.passwordConfirm");
        }
    }
    
    private class EmailValidator {
    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN = 
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    /**
    * Validate hex with regular expression
    * 
    * @param hex
    *            hex for validation
    * @return true valid hex, false invalid hex
    */
    public boolean validate(final String hex) {

    matcher = pattern.matcher(hex);
    return matcher.matches();

    }
}

}

