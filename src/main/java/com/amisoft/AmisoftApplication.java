package com.amisoft;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

import java.util.Arrays;

@SpringBootApplication
public class AmisoftApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(AmisoftApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {

		System.out.println("cron job started");

		ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
		resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.header);
		resourceDetails.setAccessTokenUri("http://localhost:9000/services/oauth/token");

		resourceDetails.setScope(Arrays.asList("read"));

		resourceDetails.setClientId("clientamisoft");
		resourceDetails.setClientSecret("clientamisoftsecret");

		resourceDetails.setUsername("john");
		resourceDetails.setPassword("johnpass");

		OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetails);
		String token =  template.getAccessToken().toString();//.getValue();

		System.out.println("Token: " + token);

		String s = template.getForObject("http://localhost:9001/services/getemployee", String.class);

		System.out.println("Result: " + s);
	}


	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
