package ec.com.danylassosolution.users.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ec.com.danylassosolution.users.models.entities.User;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	List<User> findByNumDocument(String numDocument);
}
