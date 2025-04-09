package my.project.paymentapp.service.impl;

import my.project.paymentapp.Entity.OperationType;
import my.project.paymentapp.Entity.WalletEntity;
import my.project.paymentapp.exception.NotEnoughWalletAmountException;
import my.project.paymentapp.exception.OperationTypeNotFoundException;
import my.project.paymentapp.exception.WalletNotFoundException;
import my.project.paymentapp.repository.WalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {

    @InjectMocks
    private WalletServiceImpl walletService;

    @Mock
    private WalletRepository walletRepository;

    @Test
    void getWalletValid() {
        UUID uuid = UUID.randomUUID();
        WalletEntity wallet = new WalletEntity();
        wallet.setWalletId(uuid);
        wallet.setAmount(BigDecimal.ONE);

        when(walletRepository.findById(uuid)).thenReturn(Optional.of(wallet));
        WalletEntity walletEntity = walletService.getWallet(uuid);
        Assertions.assertEquals(walletEntity, wallet);
        verify(walletRepository, times(1)).findById(uuid);
    }

    @Test
    void getWalletNotValidUUID() {
        UUID uuid = UUID.randomUUID();
        WalletEntity wallet = new WalletEntity();
        wallet.setWalletId(uuid);
        wallet.setAmount(BigDecimal.ONE);

        when(walletRepository.findById(uuid)).thenThrow(new WalletNotFoundException("Кошелек с id: "+ uuid +" не найден"));
        assertThrows(WalletNotFoundException.class, () -> walletService.getWallet(uuid));
        verify(walletRepository, times(1)).findById(uuid);
    }

    @Test
    void changeBalanceDepositValid() {
        UUID uuid = UUID.randomUUID();
        WalletEntity wallet = new WalletEntity();
        wallet.setWalletId(uuid);
        wallet.setAmount(BigDecimal.ONE);
        wallet.setOperationType(OperationType.DEPOSIT);

        when(walletRepository.findById(uuid)).thenReturn(Optional.of(wallet));
        walletService.changeBalance(wallet);

        verify(walletRepository, times(1)).findById(uuid);
        verify(walletRepository, times(1)).save(wallet);

    }


    @Test
    void changeBalanceWithdrawValid() {
        UUID uuid = UUID.randomUUID();
        WalletEntity wallet = new WalletEntity();
        wallet.setWalletId(uuid);
        wallet.setAmount(BigDecimal.ONE);
        wallet.setOperationType(OperationType.WITHDRAW);

        when(walletRepository.findById(uuid)).thenReturn(Optional.of(wallet));
        walletService.changeBalance(wallet);

        verify(walletRepository, times(1)).findById(uuid);
        verify(walletRepository, times(1)).save(wallet);

    }

    @Test
    void changeBalanceOperationTypeNotValid() {
        UUID uuid = UUID.randomUUID();
        WalletEntity wallet = new WalletEntity();
        wallet.setWalletId(uuid);
        wallet.setAmount(BigDecimal.ONE);

        assertThrows(OperationTypeNotFoundException.class, () -> walletService.changeBalance(wallet));
        verify(walletRepository, times(0)).findById(uuid);
        verify(walletRepository, times(0)).save(wallet);

    }

    @Test
    void depositValid() {
        UUID uuid = UUID.randomUUID();
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setWalletId(uuid);
        walletEntity.setAmount(BigDecimal.ONE);

        UUID uuid2 = UUID.randomUUID();
        WalletEntity wallet = new WalletEntity();
        wallet.setWalletId(uuid2);
        wallet.setAmount(BigDecimal.ONE);

        when(walletRepository.findById(uuid)).thenReturn(Optional.of(wallet));
        walletService.deposit(walletEntity);

        assertEquals(BigDecimal.valueOf(2), wallet.getAmount());
        verify(walletRepository, times(1)).save(wallet);

    }


    @Test
    void withdrawValid() {
        UUID uuid = UUID.randomUUID();
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setWalletId(uuid);
        walletEntity.setAmount(BigDecimal.ONE);

        UUID uuid2 = UUID.randomUUID();
        WalletEntity wallet = new WalletEntity();
        wallet.setWalletId(uuid2);
        wallet.setAmount(BigDecimal.ONE);

        when(walletRepository.findById(uuid)).thenReturn(Optional.of(wallet));
        walletService.withdraw(walletEntity);

        assertEquals(BigDecimal.valueOf(0), wallet.getAmount());
        verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    void withdrawNotValidAmount() {
        UUID uuid = UUID.randomUUID();
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setWalletId(uuid);
        walletEntity.setAmount(BigDecimal.TEN);

        UUID uuid2 = UUID.randomUUID();
        WalletEntity wallet = new WalletEntity();
        wallet.setWalletId(uuid2);
        wallet.setAmount(BigDecimal.ONE);

        when(walletRepository.findById(uuid)).thenReturn(Optional.of(wallet));
        assertThrows(NotEnoughWalletAmountException.class,() -> walletService.withdraw(walletEntity));
        verify(walletRepository, times(0)).save(wallet);
    }
}