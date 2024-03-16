package org.example.migapi.controller

import org.example.migapi.domain.dto.auth.Restore
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class TestController {

    @GetMapping("auth/restore")
    fun restoreWindow(@RequestParam token: String, model: Model): String {
        model.addAttribute("restore", Restore(""))

        return "restore"
    }
}