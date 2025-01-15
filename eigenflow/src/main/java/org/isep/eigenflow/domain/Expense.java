package org.isep.eigenflow.domain;

import java.time.LocalDate;

public record Expense(
        String description,
        double amount,
        String category,
        LocalDate date
) {}