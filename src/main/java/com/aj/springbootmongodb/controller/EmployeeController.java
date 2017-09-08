package com.aj.springbootmongodb.controller;

import com.aj.springbootmongodb.domain.Employee;
import com.aj.springbootmongodb.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Iterable<Employee>> getAllEmployees() {
        Iterable<Employee> employees = employeeService.findAll();
        return new ResponseEntity<>(employees, HttpStatus.OK);
	}

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<Employee> findByName(@PathVariable String name) {
        Employee employee = employeeService.findByName(name);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @RequestMapping(value = "/sort/{key}", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> findByKeySort(@PathVariable String key) {
        List<Employee> employees = employeeService.findByKeySort(key);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> addEmployee(@RequestBody Employee employeeRequest) {
        logger.info("Request received in EmployeeController.addEmployee is: " + employeeRequest.toString());
        Map<String, String> response = new HashMap<>();
        employeeService.save(employeeRequest);
        response.put("message", "Employee saved successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> updateEmployee(@RequestBody Employee employeeRequest) {
        logger.info("Request received in EmployeeController.updateEmployee is: " + employeeRequest.toString());
        Map<String, String> response = new HashMap<>();
        int rowsUpdated = employeeService.updateEmployee(employeeRequest);
        if(rowsUpdated > 0) {
            response.put("message", "Employee updated successfully");
        }
        else{
            response.put("message", "Employee not updated");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String, String>> deleteEmployee(@PathVariable String name) {
        Map<String, String> response = new HashMap<>();
        employeeService.delete(name);
        response.put("message", "Employee deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
