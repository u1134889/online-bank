package com.rogosienski.onlinebank.backend.account;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
    @GetMapping("/{id}")
    AccountInfo getAccount(@PathVariable String id) {
        return new AccountInfo(UUID.randomUUID(), BigDecimal.TEN);
    }
}
