package hg.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import hg.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    List<Category> findAll();
    List<Category> findByNameLike(String name);
    List<Category> findByNameOrCode(String name,String code);
}