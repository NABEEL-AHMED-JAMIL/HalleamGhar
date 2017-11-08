package hg.repository;

import org.springframework.data.repository.CrudRepository;
import hg.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);
}
