package com.metroguards.backend.employee.controller;

import com.metroguards.backend.employee.dto.EmployeeRequest;
import com.metroguards.backend.employee.dto.EmployeeResponse;
import com.metroguards.backend.employee.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@PreAuthorize("hasRole('HR_ADMIN')")
public class EmployeeController {

    private final EmployeeService service;

    @GetMapping
    public List<EmployeeResponse> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public EmployeeResponse get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse create(@Valid @RequestBody EmployeeRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public EmployeeResponse update(@PathVariable Long id, @Valid @RequestBody EmployeeRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
