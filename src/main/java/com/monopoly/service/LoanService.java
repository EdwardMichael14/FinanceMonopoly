package com.monopoly.service;


import com.monopoly.data.model.Player;
import com.monopoly.data.repository.PlayerRepository;
import com.monopoly.exception.InsufficientFundsException;
import com.monopoly.exception.InvalidGameActionException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoanService {
    private final PlayerRepository playerRepository;

    @Transactional
    public long makeLoanPayment(Player player, long paymentKobo) {
        if (paymentKobo < 0) {
            throw new InvalidGameActionException("Loan payment cannot be negative.");
        }

        if (paymentKobo > player.getLoanBalanceKobo()) {
            throw new InvalidGameActionException("Cannot pay more than the remaining loan balance of "
                    + formatNaira(player.getLoanBalanceKobo()));
        }

        if (paymentKobo > player.getCashBalanceKobo()) {
            throw new InsufficientFundsException("Not enough cash. Available: "
                    + formatNaira(player.getCashBalanceKobo()) + ", Trying to pay: " + formatNaira(paymentKobo));
        }


        player.setCashBalanceKobo(player.getCashBalanceKobo() - paymentKobo);
        player.setLoanBalanceKobo(player.getLoanBalanceKobo() - paymentKobo);

        playerRepository.save(player);

        return player.getLoanBalanceKobo();
    }


    @Transactional
    public long applyInterest(Player player) {
        if (player.getLoanBalanceKobo() <= 0) {
            return 0L;
        }

        long interest = Math.round(player.getLoanBalanceKobo() * 0.10);
        long newBalance = player.getLoanBalanceKobo() + interest;

        player.setLoanBalanceKobo(newBalance);
        playerRepository.save(player);

        return newBalance;
    }


    public long previewInterest(long currentLoanBalance, long proposedPayment) {
        long balanceAfterPayment = currentLoanBalance - proposedPayment;
        if (balanceAfterPayment <= 0) return 0L;
        return Math.round(balanceAfterPayment * 0.10);
    }

    private String formatNaira(long kobo) {
        return String.format("₦%,d", kobo / 100);
    }
}
