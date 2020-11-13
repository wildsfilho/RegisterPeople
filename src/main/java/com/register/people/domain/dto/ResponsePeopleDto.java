package com.register.people.domain.dto;

import com.register.people.domain.People;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Setter
@Getter
public class ResponsePeopleDto {

    public People people;
    public String message;

}
