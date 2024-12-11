package com.rogosienski.onlinebank.backend.account;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class BalanceModification {
    public enum Operation {
        ADD,
        SUBTRACT
    }

    private final BigDecimal value;
    private final Operation operation;
}
