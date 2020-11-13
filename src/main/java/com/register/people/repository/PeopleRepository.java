package com.register.people.repository;

import com.register.people.domain.People;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends CrudRepository<People, Integer> {

    People findByNome(String name);

    Page<People> findAll(Pageable pageRequest);

}
