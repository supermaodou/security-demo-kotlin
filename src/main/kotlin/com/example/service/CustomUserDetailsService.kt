package com.example.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    // 可以使用上面的构造器注入(推荐)，也可以使用下面的@Autowired
//    @Autowired
//    lateinit var passwordEncoder: PasswordEncoder

    override fun loadUserByUsername(username: String?): UserDetails {
        // 假设从数据库查出
        val user = when (username) {
            "admin" -> SysUser("admin", passwordEncoder.encode("123"), listOf("ADMIN"), listOf("user:view", "user:add", "user:delete"))
            "user" -> SysUser("user", passwordEncoder.encode("123"), listOf("USER"), listOf("user:view"))
            else -> throw UsernameNotFoundException("用户不存在")
        }
        // 创建权限列表（含角色和权限）
        val authorities = mutableListOf<GrantedAuthority>()
        // 添加角色和权限
        user.roles.forEach { authorities.add(SimpleGrantedAuthority(it)) }
        user.permissions.forEach { authorities.add(SimpleGrantedAuthority(it)) }
        // 返回security的User对象
        return User(user.username, user.password, authorities)
    }

}

data class SysUser(val username: String, val password: String, val roles: List<String>, val permissions: List<String>)