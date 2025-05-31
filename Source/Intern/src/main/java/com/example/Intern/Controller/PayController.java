package com.example.Intern.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pay")
public class PayController {
    @GetMapping("")
    public String pay() {
        return "pay";
    }
}
