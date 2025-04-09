package my.project.paymentapp.service.impl;

import my.project.paymentapp.Entity.OperationType;
import my.project.paymentapp.Entity.WalletEntity;
import my.project.paymentapp.exception.NotEnoughWalletAmountException;
import my.project.paymentapp.exception.OperationTypeNotFoundException;
import my.project.paymentapp.exception.WalletNotFoundException;
import my.project.paymentapp.repository.WalletRepository;
import my.project.paymentapp.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public WalletEntity getWallet(UUID wallet_uuid) {
        Optional<WalletEntity> wallet = walletRepository.findById((wallet_uuid));
        return wallet.orElseThrow(() -> new WalletNotFoundException("Кошелек с id: "+ wallet_uuid +" не найден"));
    }

    @Override
    @Transactional
    public WalletEntity changeBalance(WalletEntity walletEntity) {
        validOperationType(walletEntity);
        WalletEntity wallet = new WalletEntity();
        if (walletEntity.getOperationType().equals(OperationType.DEPOSIT)) {
             wallet = deposit(walletEntity);
        } else if (walletEntity.getOperationType().equals(OperationType.WITHDRAW)) {
            wallet = withdraw(walletEntity);
        }
        return wallet;
    }

    @Override
    public WalletEntity deposit(WalletEntity walletEntity) {
        WalletEntity wallet = getWallet(walletEntity.getWalletId());
        wallet.setAmount(wallet.getAmount().add(walletEntity.getAmount()));
        wallet = walletRepository.save(wallet);
        return wallet;
    }

    @Override
    public WalletEntity withdraw(WalletEntity walletEntity) {
        WalletEntity wallet = getWallet(walletEntity.getWalletId());
        validAmount(wallet.getAmount(), walletEntity.getAmount());
        wallet.setAmount(wallet.getAmount().subtract(walletEntity.getAmount()));
        wallet = walletRepository.save(wallet);
        return wallet;
    }

    private void validAmount(BigDecimal currentAmount,BigDecimal withdrawAmount) {
        if (currentAmount.subtract(withdrawAmount).signum() < 0) {
            throw new NotEnoughWalletAmountException("Недостаточно средств на кошельке");
        }
    }

    private void validOperationType(WalletEntity walletEntity) {
        if (walletEntity.getOperationType() == null) {
            throw new OperationTypeNotFoundException("Тип операции: null");
        }
    }
}
