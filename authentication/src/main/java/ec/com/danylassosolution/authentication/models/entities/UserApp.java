package ec.com.danylassosolution.authentication.models.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "userApp")
@Data
public class UserApp {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
	private Long id;
	
	@Column(name = "nickname_usr", length = 253, nullable = false, unique = true)
	private String nickname;
	
	@Column(name = "password_usr", length = 253, nullable = false)
	private String password;
	
	@Column(name = "rol_usr", length = 511, nullable = false)
	private String rol;
	
	@Column(name = "enable_usr")
	private Boolean userEnable;
	
	@Column(name = "credentials_non_expired_usr")
	private Boolean credentialsNonExpired;
	
	@Column(name = "account_non_locked_usr")
	private Boolean accountNonLocked;
	
	@Column(name = "account_non_expired_usr")
	private Boolean accountNonExpired;
	
	@Column(name = "modif_pass_usr", nullable = false)
	private LocalDateTime modifiedPassword;
	
	@Column(name = "failed_auth_usr")
	private Integer failedAuthentications;
	
	
}
