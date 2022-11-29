package ec.com.danylassosolution.users.controllers.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ec.com.danylassosolution.users.models.constants.VaccinationState;
import ec.com.danylassosolution.users.models.dtos.EmployeeRegisterReq;
import ec.com.danylassosolution.users.models.dtos.EmployeeUpdateReq;
import ec.com.danylassosolution.users.models.dtos.UserUpdateReq;
import ec.com.danylassosolution.users.models.entities.User;
import ec.com.danylassosolution.users.models.entities.Vaccines;
import ec.com.danylassosolution.users.repositories.UserRepository;
import ec.com.danylassosolution.users.services.models.DeleteUser;
import ec.com.danylassosolution.users.services.models.FindUserById;
import ec.com.danylassosolution.users.services.models.FindUserByNumDocument;
import ec.com.danylassosolution.users.services.models.SaveAllAdress;
import ec.com.danylassosolution.users.services.models.SaveAllVaccines;
import ec.com.danylassosolution.users.services.models.SaveEmployee;
import ec.com.danylassosolution.users.services.models.UpdateUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Conjunction;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping("/v1.0.0/employees")
@Api(tags = "Administracion de Empleados")
public class EmployeeController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SaveEmployee saveEmployee;
	
	@Autowired
	private UpdateUser updateUser;

	@Autowired
	private SaveAllVaccines saveAllVaccines;
	
	@Autowired
	private SaveAllAdress saveAllAdress;
	
	@Autowired
	private FindUserById findUserById;
	
	@Autowired
	private FindUserByNumDocument findUserByNumDocument;
	
	@Autowired
	private DeleteUser deleteUser;
	
	
	@GetMapping
	@ApiOperation(value = "Filtrar empleados", notes = "<b>La libreria Swagger no reconoce API que manejan las especificacion. Ejemplo de consumo:</b>"
			+ "<p>{base_url}?page=numeroPagina&size=numeroFilas&sort= atributoAOrdenar,desc"
			+ "?vaccinationState=''"
			+ "&vaccineType=''"
			+ "&dateVaccineAfter=''&dateVaccineBefore=''</p>"
			+ "<ul><li>vaccinationState: puede tomar los valores de VACCINATED y NOT_VACCINATED</li>"
			+ "<li>vaccineType:  puede tomar los valores de SPUTNIK,ASTRAZENECA, PFIZER, JHONSON_JHONSON  </li>"
			+ "</ul>" )
	public Page<User> getAll(
			@Join(path = "vaccines", alias = "vac")
			@Conjunction(value = {
					@Or({ @Spec(path = "isVaccinated", params = "vaccinationState", spec = Equal.class ) }),
					@Or({ @Spec(path = "vac.type", params = "vaccineType", spec = Equal.class ) }),
					@Or({ @Spec(path = "vac.dateVaccine", params = {"dateVaccineAfter", "dateVaccineBefore" }, spec = Between.class ) }),
					@Or({ @Spec(path = "rol", params = " ",constVal = "emp", spec = Equal.class ) })
			}) Specification<User> userSpec,Pageable pageable	){
		
		return userRepository.findAll(userSpec, pageable);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Encontrar un empleado por el ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "empleado existente"),
        @ApiResponse(code = 404, message = "No existen registros")
    })
	public User getById(Long id){
		return findUserById.find(id);
	}
	
	@GetMapping("/{ci}/by-ci")
	@ApiOperation(value = "Encontrar un empleado por la CI")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "empleado existente"),
        @ApiResponse(code = 404, message = "No existen registros")
    })
	public User getById(String ci){
		return findUserByNumDocument.find(ci);
	}
	
	@PostMapping
	@ApiOperation(value = "Registrar un empleado")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "registro exitoso del empleado"),
        @ApiResponse(code = 409, message = "Cédula registrada previamente"),
        @ApiResponse(code = 500, message = "Error al registrar los datos")
    })
	public User create(@RequestBody @Validated EmployeeRegisterReq request) {
		return saveEmployee.create(request);
	}
	
	@PatchMapping
	@ApiOperation(value = "Agregar Información personal del empleado")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "actualización exitosa del empleado"),
        @ApiResponse(code = 500, message = "Error al registrar los datos")
    })
	public User update(@RequestBody @Validated EmployeeUpdateReq request) {
		List<Vaccines> vaccines = request.getVaccines();
		User user = updateUser.update(request);
		
		if(user.getIsVaccinated().equals(VaccinationState.VACCINATED)){
			saveAllVaccines.saveAll(vaccines, user);	
		}
		
		saveAllAdress.saveAll(request.addressAsList(), user);
		
		return user;
	}
	
	
	@PutMapping
	@ApiOperation(value = "Actualizar un empleado")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "actualización exitosa del empleado"),
        @ApiResponse(code = 500, message = "Error al registrar los datos")
    })
	public User update(@RequestBody @Validated UserUpdateReq request) {
		List<Vaccines> vaccines = request.getVaccines();
		User user = updateUser.update(request);
		
		if(user.getIsVaccinated().equals(VaccinationState.VACCINATED)){
			saveAllVaccines.saveAll(vaccines, user);	
		}
		
		saveAllAdress.saveAll(request.addressAsList(), user);
		
		return user;
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteById(Long id){
		 deleteUser.delete(id);
	}
}
