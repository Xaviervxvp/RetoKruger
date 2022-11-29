package ec.com.danylassosolution.users.models.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ec.com.danylassosolution.users.models.constants.VaccineType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vaccines")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vaccines {
	
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vaccines")
	private Long id;
	
	@NotNull(message = "Type Vaccine is required")
	@Column(name = "type_vacc", nullable = false)
	private VaccineType type;

	@NotNull(message = "Date Vaccine is required")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@PastOrPresent(message = "Date Vaccine is not a date in the past or present")
    @Column(name = "date_vacc", nullable = false)
	private LocalDate dateVaccine;
    
	@NotNull(message = "Dose Vaccine is required")
	@Min(value = 1, message = "number of dose invalid")
    @Column(name = "dose_vacc", nullable = false)
	private Integer dose;
    
    @JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_id_user")
	private User user;
}
