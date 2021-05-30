package sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.security.oauth2.gateway.TokenRelayGatewayFilterFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@SpringBootApplication
public class GatewayApplication {

	@Autowired
	private TokenRelayGatewayFilterFactory filterFactory;

	@GetMapping("/session")
	public Mono<String> index(WebSession session) {
		return Mono.just(session.getId());
	}

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@GetMapping("/")
	public Map<?,?> index(
			@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
			@AuthenticationPrincipal OAuth2User oauth2User
	) {
		return Map.of(
				"userName", oauth2User.getName(),
				"clientName", authorizedClient.getClientRegistration().getClientName(),
				"userAttributes", oauth2User.getAttributes()
				);
	}

}