package com.example.ems.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ems.dto.EmployeeDto;
import com.example.ems.entity.Employee;
import com.example.ems.exception.EmployeeException;
import com.example.ems.mapper.EmployeeMapper;
import com.example.ems.repository.EmployeeRepository;
import com.example.ems.service.EmployeeService;
@Service
public class EmployeeServiceImpl  implements EmployeeService{
    
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public EmployeeDto createEmployee(EmployeeDto employeeDto) {
		Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
		Employee savedEmployee = employeeRepository.save(employee);
		return EmployeeMapper.mapToEmployeeDto(savedEmployee);
	}

	@Override
	public EmployeeDto getEmployeeById(Long employeeId) {
		Employee emp = employeeRepository.findById(employeeId)
				.orElseThrow(()-> new EmployeeException("Employee is not exist with given id :"+employeeId));
		return EmployeeMapper.mapToEmployeeDto(emp);
	}

	@Override
	public List<EmployeeDto> getAllEmployees() {
	List<Employee> employees = employeeRepository.findAll();
		return employees.stream().map((emp)->EmployeeMapper.mapToEmployeeDto(emp)).collect(Collectors.toList());
	}

	@Override
	public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updateEmployee) {
	Employee emp =	employeeRepository.findById(employeeId).orElseThrow(()-> new EmployeeException("Employee does not exist with given id:"+employeeId));
	emp.setFirstName(updateEmployee.getFirstName());
	emp.setLastName(updateEmployee.getLastName());
	emp.setEmail(updateEmployee.getEmail());
	Employee updatedEmployee=employeeRepository.save(emp);
		return EmployeeMapper.mapToEmployeeDto(updatedEmployee);
	}

	@Override
	public void deleteEmployee(Long employeeId) {
		Employee emp =	employeeRepository.findById(employeeId).orElseThrow(()-> new EmployeeException("Employee does not exist with given id:"+employeeId));

		employeeRepository.deleteById(employeeId);

	}

}
