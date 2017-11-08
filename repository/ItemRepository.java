package hg.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import hg.model.Item;

public interface ItemRepository extends CrudRepository<Item, Integer> {
    List<Item> findAll();
    List<Item> findByNameLike(String name);
    List<Item> findByNameOrCode(String name,String code);
}