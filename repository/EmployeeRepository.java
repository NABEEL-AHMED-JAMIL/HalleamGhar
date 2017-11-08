package hg.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import hg.model.Employee;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Integer> {
    List<Employee> findByNameLikeAndDesignation(String name,String designation);
}