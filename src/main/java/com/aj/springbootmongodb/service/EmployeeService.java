package com.aj.springbootmongodb.service;

import com.aj.springbootmongodb.domain.Employee;

import java.util.List;

public interface EmployeeService {

    void save(Employee employee);
    Iterable<Employee> findAll();
    Employee findByName(String name);
    int updateEmployee(Employee employeeRequest);
    void delete(String name);
    List<Employee> findByKeySort(String key);
}
