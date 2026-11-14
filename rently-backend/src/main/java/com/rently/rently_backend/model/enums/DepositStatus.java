package com.rently.rently_backend.model.enums;

public enum DepositStatus {
    AUTHORIZED_HOLD,
    ACTIVE_IN_ESCROW,
    PARTIALLY_FROZEN_DISPUTE,
    FULLY_FROZEN_DISPUTE,
    RELEASED_TO_CUSTOMER,
    SPLIT_SETTLED,
    FORFEITED_TO_PROVIDER
}

