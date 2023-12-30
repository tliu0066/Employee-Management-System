package com.example.CRUD.controller;

import com.example.CRUD.exception.ResourceNotFoundException;
import com.example.CRUD.model.Employee;
import com.example.CRUD.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    //get All employee
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/employees")
    public List<Employee>getAll(){
        return employeeRepository.findAll();
    }

    //create employee rest api
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/employees")
    public Employee createdEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    // Get employee by id rest api
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee>  getEmployeeById(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee not exist with id :" + id));
        return ResponseEntity.ok(employee);
    }

    //update employee rest api
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee>updateemployee(@PathVariable Long id, @RequestBody Employee employeeDtails){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee not exist with id :" + id));
        employee.setFirstName((employeeDtails.getFirstName()));
        employee.setLastName((employeeDtails.getLastName()));
        employee.setEmailId((employeeDtails.getEmailId()));
        Employee updateEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updateEmployee);
    }

    //delete employee rest api
    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee not exist with id :" + id));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


}
