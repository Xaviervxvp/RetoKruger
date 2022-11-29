package ec.com.danylassosolution.users.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address")
	private Long id;
	
	@NotEmpty(message = "Principal Street is required")
	@Size(min = 2, max = 255, message = "Principal Street must have min 2 character to 255 character")
	@Column(name = "principal_street_adr", length = 255, nullable = false)
	private String principalStreet;
	
	@Size(min = 0, max = 255, message = "Secundary Street must have max 255 character")
	@Column(name = "secundary_street_adr", length = 255)
	private String secundaryStreet;
	
	@Size(min = 0, max = 255, message = "Number of Home Street must have max 10 character")
	@Column(name = "num_home_adr", length = 10)
	private String numHome;
	
	@Size(min = 0, max = 255, message = "Neighborhood must have max 255 character")
	@Column(name = "neighborhood_adr", length = 255)
	private String neighborhood;
	
	@Size(min = 0, max = 255, message = "Reference must have max 511 character")
	@Column(name = "reference_adr", length = 511)
	private String reference;
	
	@JsonIgnore
	@Column(name = "current_address_adr")
	private Boolean current;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_id_user")
	private User user;
}
