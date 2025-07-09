package com.example.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController {

    @GetMapping("/me")
    fun me(authentication: Authentication): Any {
        return mapOf(
            "name" to authentication.name,
            "authorities" to authentication.authorities.map { it.authority }
        )
    }

    // 所有人都能查看用户
    @PreAuthorize("hasAuthority('user:view')")
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): String {
        return "查看用户 $id"
    }

    // 只有拥有 user:add 权限的角色才能新增
    @PreAuthorize("hasAuthority('user:add')")
//    @PostMapping
    @GetMapping("/add")
    fun addUser(): String {
        return "新增用户成功"
    }

    // ADMIN 角色或拥有删除权限的用户都可以删除
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('user:delete')")
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): String {
        return "删除用户 $id 成功"
    }

}