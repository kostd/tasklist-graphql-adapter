package org.kostd.bpms.tasklistgraphqladapter.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.SecurityFilterChain

/**
 * basic auth для graphql + заведены пара учеток для экспериментов
 */
@EnableWebSecurity
@Configuration
//из-за того, что конфигурация у нас в одном package, а beans в другом, приходится явно указывать, откуда сканировать бины
@ComponentScan(basePackages = ["org.kostd.bpms"])
class WebSecurityConfiguration {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        // надо отключать csrf, чтобы post`ы могли успешно аутентифицироваться по basic
        http.csrf().disable().authorizeRequests().antMatchers("/graphql")
                .hasRole("USER").and().httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    fun userDetailsService(): UserDetailsManager {
        val kostdUser: UserDetails = User.withDefaultPasswordEncoder()
                .username("kostd")
                .password("kostd_pass")
                .roles("USER")
                .build();
        val testUser = User.withDefaultPasswordEncoder()
                .username("test")
                .password("test_pass")
                .roles("USER")
                .build();

        return InMemoryUserDetailsManager(kostdUser, testUser);
    }

}