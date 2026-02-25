package com.monopoly.dto.response;


import lombok.Data;

@Data
public class LoanPreviewResponse {
    private String currentLoanBalance;
    private String proposedPayment;
    private String balanceAfterPayment;
    private String interestIfNotFullyPaid;
    private String newBalanceNextRound;
    private String tip;
}
