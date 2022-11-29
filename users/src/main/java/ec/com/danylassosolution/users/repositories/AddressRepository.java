package ec.com.danylassosolution.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ec.com.danylassosolution.users.models.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {

}
