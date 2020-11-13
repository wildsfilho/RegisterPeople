package com.register.people.service.validator;

import com.register.people.domain.Contact;
import com.register.people.domain.People;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ValidatePeople {


    public List<String> validatePeople(People people) {
        List<String> errors = new ArrayList<>();
        String validateDateofBirth = validateDateofBirth(people.getData_nascimento());
        String validateContact = validateContact(people.getContact());
        if (Strings.isNotEmpty(validateDateofBirth) || Strings.isNotEmpty(validateContact)) {
            errors.add(validateDateofBirth);
            errors.add(validateContact);
        }

        return errors;
    }

    private String validateDateofBirth(LocalDate dateOfBirth) {
        String message = Strings.EMPTY;
        LocalDate actualDate = LocalDate.now();
        boolean isDateValid = dateOfBirth.isAfter(actualDate);
        if (isDateValid) {
            message = "Data de nascimento invalida.";
        }
        return message;
    }

    private String validateContact(List<Contact> contacts) {
        String message = Strings.EMPTY;
        if (contacts.isEmpty()) {
            message = "É necessário pelo menos um contato";
        }
        return message;
    }
}
