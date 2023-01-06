package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static ru.otus.Banknot.*;


class ATMTest {
    private ATM atm;

    @BeforeEach
    void setUp() {
        atm = new ATM();
    }

    @Test
    void receive() {
        var moneyMap = Map.of(
                BANKNOTE_50, 1,
                BANKNOTE_100, 1,
                BANKNOTE_500, 1,
                BANKNOTE_1000, 1,
                BANKNOTE_5000, 1
        );
        atm.receive(moneyMap);
        assertThat(atm.getBalanceAsMap()).isEqualTo(moneyMap);
    }

    @Test
    void receiveList() {
        var expectedMap = Map.of(
                BANKNOTE_50, 2,
                BANKNOTE_100, 2,
                BANKNOTE_500, 1
        );

        atm.receive(List.of(BANKNOTE_50, BANKNOTE_100,BANKNOTE_50,BANKNOTE_500, BANKNOTE_100));

        assertThat(atm.getBalanceAsMap()).isEqualTo(expectedMap);
    }

    @Test
    void receiveRequestIllegalAmountBanknot() {
        var moneyMap = Map.of(
                BANKNOTE_50, 1,
                BANKNOTE_100, 1,
                BANKNOTE_500, -1,
                BANKNOTE_1000, 1,
                BANKNOTE_5000, 1
        );
        
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> atm.receive(moneyMap));
    }

    @Test
    void give() {
        var moneyMap = Map.of(
                BANKNOTE_50, 1,
                BANKNOTE_100, 1,
                BANKNOTE_500, 1,
                BANKNOTE_1000, 1,
                BANKNOTE_5000, 1
        );
        atm.receive(moneyMap);

        var expectedMoney = Map.of(
                BANKNOTE_50, 1,
                BANKNOTE_100, 1,
                BANKNOTE_500, 1
        );
        assertThat(atm.give(650)).isEqualTo(expectedMoney);

        var expectedHolder = Map.of(
                BANKNOTE_50, 0,
                BANKNOTE_100, 0,
                BANKNOTE_500, 0,
                BANKNOTE_1000, 1,
                BANKNOTE_5000, 1 );
        assertThat(atm.getBalanceAsMap()).isEqualTo(expectedHolder);
    }

    @Test
    void giveRequestTooMuchMoney() {
        var moneyMap = Map.of(
                BANKNOTE_50, 1,
                BANKNOTE_100, 1,
                BANKNOTE_500, 1,
                BANKNOTE_1000, 1,
                BANKNOTE_5000, 1
        );
        atm.receive(moneyMap);

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> atm.give(8000));
    }

    @Test
    void giveWithMinimalAmountOfBanknotes() {
        var moneyMap = Map.of(
                BANKNOTE_50, 6,
                BANKNOTE_100, 5,
                BANKNOTE_500, 4,
                BANKNOTE_1000, 2,
                BANKNOTE_5000, 1
        );
        atm.receive(moneyMap);

        atm.give(6650);

        var expected = Map.of(
                BANKNOTE_50, 5,
                BANKNOTE_100, 4,
                BANKNOTE_500, 3,
                BANKNOTE_1000, 1,
                BANKNOTE_5000, 0
        );
        assertThat(atm.getBalanceAsMap()).isEqualTo(expected);
    }

    @Test
    void balanceWithEmptyATM() {
        assertThat(atm.getBalance()).isEqualTo(0);
    }

    @Test
    void balanceAsString() {
        var moneyMap = Map.of(
                BANKNOTE_50, 1,
                BANKNOTE_100, 1,
                BANKNOTE_500, 1,
                BANKNOTE_1000, 1,
                BANKNOTE_5000, 1
        );
        atm.receive(moneyMap);
        assertThat(atm.getBalance()).isEqualTo(6650);;
    }
}