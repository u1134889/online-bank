package com.rogosienski.onlinebank.backend.account;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountInfo(UUID id, BigDecimal balance) {
}
