package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.service.UserService;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SuccessUserHandler successUserHandler;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserService userService, PasswordEncoder passwordEncoder) {
        this.successUserHandler = successUserHandler;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {// конфигурируем сам спринг секурити и авторизацию
        http
                .authorizeRequests()
                .antMatchers("/", "/index", "/api/**").permitAll() // аутентификация не требуется
                .antMatchers("/user").hasAnyRole("ADMIN", "USER")
                .antMatchers("/admin").hasRole("ADMIN")  // доступ только админу
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/")
                .loginProcessingUrl("/process_login")
                .successHandler(successUserHandler) // обработчик успешной аутентификации
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")// будут стираться все куки
                .logoutSuccessUrl("/")
                .and()
                .csrf()
                .disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userService);
//        provider.setPasswordEncoder(getPasswordEncoder());
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    // аутентификация inMemory
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {// Инофррмация о юзере
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("user")
//                        .roles("USER")
//                        .build();
//        //сам написал админа
//        UserDetails admin =
//                User.withDefaultPasswordEncoder()
//                        .username("admin")
//                        .password("admin")
//                        .roles("ADMIN", "USER")
//                        .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }
}