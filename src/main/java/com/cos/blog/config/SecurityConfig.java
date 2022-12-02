package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.config.auth.PrincipalDetailService;

//빈 등록: 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것

@Configuration // 빈등록(IoC관리)
@EnableWebSecurity // 시큐리터 필터가 등록이 된다. = 스프링 시큐리티가 활성화가 되어 있는데 어떤 실행을 해달 파일에서 할 것이다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻.
public class SecurityConfig {

	@Autowired
	private PrincipalDetailService principalDetailService;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean // IoC
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}

	// 시큐리티가 대신 로그인해주는데 password를 가로채고
	// 해당 password가 뭘로 해쉬가 되어 가입이 되었는지 알아야
	// 같은 해쉬로 암호화해서 DB와 비교가능
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable() // csrf 토큰 비활성화(테스트시 걸어두는게 좋음)
				.authorizeHttpRequests().antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**")
				.permitAll().anyRequest().authenticated().and().formLogin().loginPage("/auth/loginForm")
				.loginProcessingUrl("/auth/loginProc").defaultSuccessUrl("/");// 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 수행.

		return http.build();
	}
}
