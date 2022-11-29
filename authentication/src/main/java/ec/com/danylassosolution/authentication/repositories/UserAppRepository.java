package ec.com.danylassosolution.authentication.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.com.danylassosolution.authentication.models.entities.UserApp;

public interface UserAppRepository extends JpaRepository<UserApp, Long> {

	Optional<UserApp> findByNickname(String nickname);
	
}
