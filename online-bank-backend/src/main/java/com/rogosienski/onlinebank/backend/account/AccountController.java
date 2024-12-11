package com.rogosienski.onlinebank.backend.account;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
    @GetMapping("/{id}")
    AccountInfo getAccount(@PathVariable String id) {
        return new AccountInfo(UUID.randomUUID(), BigDecimal.TEN);
    }

    @PostMapping("/open")
    AccountInfo openAccount() {
        return new AccountInfo(UUID.randomUUID(), BigDecimal.ZERO);
    }

    @PutMapping("/{id}")
    AccountInfo modifyBalance(@PathVariable String id, @RequestBody BalanceModification balanceModification) {
        var delta = balanceModification.getValue();
        var originalVal = BigDecimal.TEN;
        var newBalance = switch (balanceModification.getOperation()) {
            case BalanceModification.Operation.ADD -> originalVal.add(delta);
            case BalanceModification.Operation.SUBTRACT -> originalVal.subtract(delta);
        };
        return new AccountInfo(UUID.randomUUID(), newBalance);
    }
}
