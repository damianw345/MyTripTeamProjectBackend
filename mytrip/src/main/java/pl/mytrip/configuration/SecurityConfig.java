package pl.mytrip.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pl.mytrip.user.MyTripUserDetailsService;
import pl.mytrip.user.UserRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;
    private final MyTripAuthenticationEntryPoint authEntryPoint;

    private static final String UUID_REGEXP = "{[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}}";
    private static final String ID_REGEXP = "{[0-9]+}";
    private static final String TRIP_URL = "/api/trips/" + UUID_REGEXP;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean())
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new MyTripUserDetailsService(userRepository);
    }

    @Bean
    public BasicAuthenticationFilter baseAuthFilter() throws Exception {
        return new BasicAuthenticationFilter(this.authenticationManagerBean());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, TRIP_URL + "/posterCreated").permitAll()
                    .antMatchers(HttpMethod.POST, TRIP_URL + "/presentationCreated").permitAll()
                    .antMatchers(HttpMethod.POST, TRIP_URL + "/photos/" + ID_REGEXP + "/thumbnail").permitAll()
                    .antMatchers("/api/**").fullyAuthenticated()
                    .and()
                .httpBasic()
                    .authenticationEntryPoint(authEntryPoint)
                    .and()
                .addFilter(baseAuthFilter());
    }
}