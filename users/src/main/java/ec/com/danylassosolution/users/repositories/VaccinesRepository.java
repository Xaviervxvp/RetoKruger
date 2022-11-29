package ec.com.danylassosolution.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ec.com.danylassosolution.users.models.entities.Vaccines;

public interface VaccinesRepository  extends JpaRepository<Vaccines, Long>, JpaSpecificationExecutor<Vaccines>{

}
