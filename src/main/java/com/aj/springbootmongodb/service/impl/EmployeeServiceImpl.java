package com.aj.springbootmongodb.service.impl;

import com.aj.springbootmongodb.domain.Employee;
import com.aj.springbootmongodb.repository.EmployeesRepository;
import com.aj.springbootmongodb.service.EmployeeService;
import com.mongodb.WriteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(Employee employeeRequest) {
        logger.info("Entering EmployeeServiceImpl.save Method with Employee Details: {}", employeeRequest.toString());
        Employee employee = new Employee();
        employee.setName(employeeRequest.getName());
        employee.setDepartment(employeeRequest.getDepartment());
        employee.setSalary(employeeRequest.getSalary());
        employeesRepository.save(employee);
        logger.info("Leaving EmployeeServiceImpl.save Method");
    }

    public Iterable<Employee> findAll() {
        return employeesRepository.findAll();
    }

    public Employee findByName(String name) {
        return employeesRepository.findByName(name);
    }

    public List<Employee> findByKeySort(String key) {
        return employeesRepository.findAll(new Sort(Sort.Direction.ASC, key));
    }


    public int updateEmployee(Employee employeeRequest) {
        logger.info("Entering EmployeeServiceImpl.updateEmployee Method with Employee Details: {}", employeeRequest.toString());
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(employeeRequest.getName()));
        Update update = new Update();
        update.set("department", employeeRequest.getDepartment());
        update.set("salary", employeeRequest.getSalary());
        WriteResult result = mongoTemplate.updateFirst(query, update, Employee.class);
        if (result != null) {
            return result.getN();
        } else {
            return 0;
        }
    }

    public void delete(String name) {
        Employee employee = employeesRepository.findByName(name);
        employeesRepository.delete(employee);
    }
}
