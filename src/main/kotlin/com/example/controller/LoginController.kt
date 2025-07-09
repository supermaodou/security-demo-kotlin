package com.example.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Controller
//@RequestMapping
class LoginController {

    @RequestMapping("/login")
    fun login(): String {
        return "login"
    }

    @GetMapping("/")
    fun index(): String {
        return "index"
    }
}