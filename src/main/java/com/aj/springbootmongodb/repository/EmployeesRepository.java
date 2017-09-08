package com.aj.springbootmongodb.repository;

import com.aj.springbootmongodb.domain.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesRepository extends MongoRepository<Employee, String> {

    Employee findByName(String name);
}
