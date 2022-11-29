package ec.com.danylassosolution.authentication.webclients;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import ec.com.danylassosolution.authentication.exceptions.DataException;
import ec.com.danylassosolution.authentication.models.constants.AppErrorsCodesAndMessages;
import ec.com.danylassosolution.authentication.models.dtos.ApiError;
import ec.com.danylassosolution.authentication.models.dtos.EmployeRegisterResp;
import ec.com.danylassosolution.authentication.models.dtos.SignupEmployeeReq;
import ec.com.danylassosolution.authentication.security.JwtTokenProvider;
import lombok.Getter;
import reactor.core.publisher.Mono;

@Service
@Getter
public class WebClientUsers {

	@Value("${microservices.users.host}")
	private String baseUrl;
	
	@Value("${microservices.users.apis.register-employee}")
	private String apiRegisterEmployee;
	
	private WebClient webClient;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@PostConstruct
	public void build() {
		webClient = WebClient.builder().baseUrl(baseUrl).build();
	}
	
	public EmployeRegisterResp registerEmployee(SignupEmployeeReq request) {
		try {
			Mono<EmployeRegisterResp> response = webClient.post()
														 .uri(apiRegisterEmployee)
														 .body(Mono.just(request), SignupEmployeeReq.class)
														 .accept(MediaType.APPLICATION_JSON)
														 .header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(jwtTokenProvider.generateJwtToComunicateWithOthersMicroservices()))
														 .retrieve()
														 .bodyToMono(EmployeRegisterResp.class);
			return response.block();
		} catch (WebClientResponseException e) {
			ApiError apiError;
			switch (e.getStatusCode()) {
			case CONFLICT:
				apiError = new ApiError(e.getStatusCode(), AppErrorsCodesAndMessages.USER_NUM_DOCUMENT_ALREADY_USED.getMessage());		
				break;
			case INTERNAL_SERVER_ERROR:
			default:
				apiError = new ApiError(e.getStatusCode(), AppErrorsCodesAndMessages.USER_SAVE_ERROR.getMessage());
				break;
			}
			
			throw new DataException(apiError);
		}
			
	}
	
}
