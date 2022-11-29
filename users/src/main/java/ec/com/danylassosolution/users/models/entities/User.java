package ec.com.danylassosolution.users.models.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ec.com.danylassosolution.users.models.constants.VaccinationState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
	private Long id;
	
	@Column(name = "num_document_usr", length = 10, unique = true, nullable = false)
	private String numDocument;
	
	@Column(name = "names_usr", length = 200, nullable = false)
	private String names;
	
	@Column(name = "lastnames_usr", length = 200, nullable = false)
	private String lastnames;
	
	@Column(name = "email_usr", length = 200, nullable = false)
	private String email;
	
    @Column(name = "created_date_usr", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "modified_date_usr")
    @LastModifiedDate
    private LocalDateTime modifiedDate;
    
    @Column(name = "rol_usr", length = 200, nullable = false)
	private String rol;
    
    @Column(name = "birthday_usr")
	private LocalDate birthday;
    
    @Column(name = "cellphone_usr", length = 30)
	private String cellphone;
    
    @Column(name = "vaccinated_usr")
	private VaccinationState isVaccinated;
    
    @JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Address> address;
    
    @JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Vaccines> vaccines;
    
    @PrePersist
    public void prePersistAuditEvent() {
    	this.createdDate = LocalDateTime.now();
    	this.modifiedDate = LocalDateTime.now();
    }
    
    @PreUpdate
    public void preUpdateAuditEvent() {
    	this.modifiedDate = LocalDateTime.now();
    }
}
