package com.register.people.service;

import com.register.people.domain.People;
import com.register.people.domain.dto.ResponsePeopleDto;
import com.register.people.repository.PeopleRepository;
import com.register.people.service.validator.ValidatePeople;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PeopleService {

    @Autowired
    private PeopleRepository peopleRepository;
    @Autowired
    private ValidatePeople validatePeople;

    public Page<People> findPage(Integer page, Integer linesPerPage) {

        PageRequest pageRequest = PageRequest.of(page.intValue(), linesPerPage.intValue());
        return peopleRepository.findAll(pageRequest);
    }

    public People getPeopleByName(String name) {
        return peopleRepository.findByNome(name);
    }

    public People getPeopleById(Integer id) {
        Optional<People> peopleResult = peopleRepository.findById(id);
        if (peopleResult.isPresent()) {
            return peopleResult.get();
        }
        return null;
    }


    public void deletePeople(Integer id) {
        peopleRepository.deleteById(id);
    }

    public ResponsePeopleDto createPeople(People people) {
        ResponsePeopleDto peopleDto = new ResponsePeopleDto();
        List<String> messageErrors = validatePeople.validatePeople(people);
        if (messageErrors.size() == 0) {
            peopleDto.setPeople(peopleRepository.save(people));
            return peopleDto;
        }
        peopleDto.setMessage(messageErrors.stream().collect(Collectors.joining(",")));
        return peopleDto;
    }

    public People update(People newPeople, Integer id) {
        People people = getPeopleById(id);
        updateData(people, newPeople);
        return peopleRepository.save(people);
    }

    private void updateData(People newObj, People obj) {
        newObj.setNome(obj.getNome());
        newObj.setCpf(obj.getCpf());
        newObj.setData_nascimento(obj.getData_nascimento());
        newObj.setContact(obj.getContact());
    }

}
