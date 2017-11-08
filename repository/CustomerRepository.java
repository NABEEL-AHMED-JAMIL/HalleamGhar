package hg.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import hg.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    List<Customer> findAll();
    Customer findById(Integer id);
    List<Customer> findByNameIgnoreCaseLikeOrCellNoLike(String name,String cellNo);
}