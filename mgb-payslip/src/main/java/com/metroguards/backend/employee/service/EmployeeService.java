package com.metroguards.backend.employee.service;

import com.metroguards.backend.common.exception.NotFoundException;
import com.metroguards.backend.employee.dto.EmployeeRequest;
import com.metroguards.backend.employee.dto.EmployeeResponse;
import com.metroguards.backend.employee.model.Employee;
import com.metroguards.backend.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository repository;

    public List<EmployeeResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public EmployeeResponse findById(Long id) {
        return toResponse(getEntity(id));
    }

    @Transactional
    public EmployeeResponse create(EmployeeRequest req) {
        Employee e = new Employee();
        apply(e, req);
        return toResponse(repository.save(e));
    }

    @Transactional
    public EmployeeResponse update(Long id, EmployeeRequest req) {
        Employee e = getEntity(id);
        apply(e, req);
        return toResponse(repository.save(e));
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(getEntity(id));
    }

    private Employee getEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Employee " + id + " not found"));
    }

    private void apply(Employee e, EmployeeRequest r) {
        e.setUsername(r.username());
        e.setFirstName(r.firstName());
        e.setLastName(r.lastName());
        e.setEmail(r.email());
        e.setJobTitle(r.jobTitle());
        e.setDepartment(r.department());
        e.setHireDate(r.hireDate());
        e.setMonthlySalary(r.monthlySalary());
    }

    private EmployeeResponse toResponse(Employee e) {
        return new EmployeeResponse(e.getId(), e.getUsername(), e.getFirstName(), e.getLastName(), e.getEmail(),
                e.getJobTitle(), e.getDepartment(), e.getHireDate(), e.getMonthlySalary());
    }
}
