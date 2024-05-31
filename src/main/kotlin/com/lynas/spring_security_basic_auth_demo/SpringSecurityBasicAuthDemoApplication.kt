package com.lynas.spring_security_basic_auth_demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication
class SpringSecurityBasicAuthDemoApplication

fun main(args: Array<String>) {
	runApplication<SpringSecurityBasicAuthDemoApplication>(*args)
}


@Configuration
@EnableWebSecurity
class SecurityConfig {

	@Bean
	fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
		http {
			authorizeHttpRequests {
				authorize("/open", permitAll)
				authorize(anyRequest, authenticated)
			}
			httpBasic { }
		}

		return http.build()
	}

	@Bean
	fun userDetailsService(): UserDetailsService {
		val user = User.withDefaultPasswordEncoder()
			.username("user")
			.password("password")
			.roles("USER")
			.build()

		return InMemoryUserDetailsManager(user)
	}

}

@RestController
class DemoController {
	@GetMapping("/secured")
	fun secured() = "Secured hello world"

	@GetMapping("/open")
	fun open() = "Open hello world"
}