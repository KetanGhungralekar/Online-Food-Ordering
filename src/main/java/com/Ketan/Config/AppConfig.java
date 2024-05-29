package com.Ketan.Config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class AppConfig {
    // This line is used to configure the security filter chain.
        // what does this line mean?
        // This line is used to configure the security filter chain. The sessionManagement() method is used to configure the session management. The sessionCreationPolicy() method is used to specify the session creation policy. The authorizeHttpRequests() method is used to configure the authorization rules. The requestMatchers() method is used to specify the request matchers. The hasAnyRole() method is used to specify the roles that are allowed to access the specified request matchers. The authenticated() method is used to specify that the request matchers require authentication. The anyRequest() method is used to specify the request matchers that are not specified in the previous rules. The permitAll() method is used to specify that the request matchers are allowed to be accessed by anyone.
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            // This line is used to configure the security filter chain.
        // what does this line mean?
        // This line is used to configure the security filter chain. The sessionManagement() method is used to configure the session management. The sessionCreationPolicy() method is used to specify the session creation policy. The authorizeHttpRequests() method is used to configure the authorization rules. The requestMatchers() method is used to specify the request matchers. The hasAnyRole() method is used to specify the roles that are allowed to access the specified request matchers. The authenticated() method is used to specify that the request matchers require authentication. The anyRequest() method is used to specify the request matchers that are not specified in the previous rules. The permitAll() method is used to specify that the request matchers are allowed to be accessed by anyone.
        http.sessionManagement(management->management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(authorize->authorize.requestMatchers("/api/admin/**").hasAnyRole("ADMIN","RESTAURANT_OWNER").requestMatchers("/api/**").authenticated().anyRequest().permitAll()).addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class).csrf(csrf->csrf.disable()).cors(cors->cors.configurationSource(corsConfigurationSource()));
        return http.build();
        // if jwt token is not present then what will happen?
        // if jwt token is not present then the request will be rejected.
        //but sign up and login api should be accessible to everyone right?
        //yes, the sign up and login APIs should be accessible to everyone.
        //so how will we make them accessible to everyone?
        //The permitAll() method is used to specify that the request matchers are allowed to be accessed by anyone.
        //so api/** contains all the apis?
        //yes, the /api/** path contains all the APIs.
    }
    private CorsConfigurationSource corsConfigurationSource() {
        // This line is used to configure the CORS configuration source.
        // what does this line mean?
        // This line is used to configure the CORS configuration source. The CorsConfigurationSource() method is used to create a new CorsConfigurationSource object. The addCorsMappings() method is used to add the CORS mappings. The allowedOrigins() method is used to specify the allowed origins. The allowedMethods() method is used to specify the allowed methods. The allowedHeaders() method is used to specify the allowed headers. The allowCredentials() method is used to specify whether credentials are allowed. The maxAge() method is used to specify the maximum age.
        return new CorsConfigurationSource(){
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request){
                CorsConfiguration cors = new CorsConfiguration();
                cors.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
                cors.setAllowedMethods(Collections.singletonList("*")); //
                cors.setAllowedHeaders(Collections.singletonList("*"));// which headers are allowed in the request // * means all headers are allowed like authorization, content-type etc
                cors.setExposedHeaders(Arrays.asList("Authorization")); // which headers are allowed in the response
                cors.setAllowCredentials(true); 
                cors.setMaxAge(3600L);
                return cors;
            }
        };
    }
    @Bean
    PasswordEncoder passwordEncoder(){ 
        return new BCryptPasswordEncoder(); //whenever we want to encode password we will use this object of BCryptPasswordEncoder
    }
}
// can i use this code for any project irrespective of the domain?
//so using same code will implement security for any project? no need to change as it does not depend on any entity or domain?
