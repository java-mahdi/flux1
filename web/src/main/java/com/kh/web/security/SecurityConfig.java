package com.kh.web.security;

import com.kh.web.application.Consts;
import com.kh.web.application.CustomException;
import com.kh.web.helper.Helper;
import com.kh.web.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService(UserService userService){
        return new ReactiveUserDetailsService() {
            @Override
            public Mono<UserDetails> findByUsername(String username) {
              return  userService.findByUsername(username)
                        .map(user -> User
                                .withUsername(username)
                                .password(user.getPassword())
                                .authorities(user.getAuthorities().stream().map(auth -> new SimpleGrantedAuthority(auth.getName()) ).toList())
                                .build()
                        )
                        .onErrorMap(WebClientResponseException.class, ex -> new CustomException(ex.getStatusCode().value(), ex.getMessage()));
            }
        };
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager( ReactiveUserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder ){
        UserDetailsRepositoryReactiveAuthenticationManager authManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authManager.setPasswordEncoder(passwordEncoder);
        return authManager;
    }

    @Bean
    public JwtEncoder jwtEncoder(){
        return NimbusJwtEncoder.withKeyPair(Helper.loadPublicKey(), Helper.loadPrivateKey())
                .algorithm(SignatureAlgorithm.RS256)
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(Helper.loadPublicKey())
                .build();
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http){
        JwtGrantedAuthoritiesConverter granteTConverter = new JwtGrantedAuthoritiesConverter();
        granteTConverter.setAuthorityPrefix("");
        granteTConverter.setAuthoritiesClaimName(Consts.authority_claim_name);
        ReactiveJwtAuthenticationConverter authConverter =  new ReactiveJwtAuthenticationConverter();
        authConverter.setJwtGrantedAuthoritiesConverter(new ReactiveJwtGrantedAuthoritiesConverterAdapter(granteTConverter));

      return  http
                .csrf( c -> c.disable())
                .authorizeExchange( auth -> auth
                        .pathMatchers("/login" ,"/register" , "/" , "/index").permitAll()
                        .anyExchange().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .oauth2ResourceServer( res -> res.jwt( j -> j.jwtAuthenticationConverter(authConverter) ))
                .build();
    }


}
