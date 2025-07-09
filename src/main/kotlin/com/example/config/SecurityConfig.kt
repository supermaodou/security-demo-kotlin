package com.example.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authorization.AuthenticatedAuthorizationManager.authenticated
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.http
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf ->
                csrf.disable() // 关闭csrf
            }
            // 自定义登录页面
            .formLogin { form ->
                form
                    .loginPage("/login") // 登录页面
                    .loginProcessingUrl("/login") // 处理登录请求
                    .defaultSuccessUrl("/", true) // 登录成功后跳转的页面
            }
            // 退出登录
            .logout { logout ->
                logout
                    .logoutUrl("/logout") // 退出登录的url
            }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/login", "/public/**").permitAll() // 公开接口
//                    .requestMatchers("/user/**").hasAuthority("user:view") // 需具体权限
//                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    // 其他的接口权限全都使用@PreAuthorize注解控制，不在这里写
                    .anyRequest().authenticated() // 其他接口需要认证
            }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

}