package my.project.paymentapp.service;

import my.project.paymentapp.Entity.WalletEntity;

import java.util.UUID;

public interface WalletService {

    WalletEntity getWallet(UUID wallet_uuid);

    WalletEntity changeBalance(WalletEntity walletEntity);

    WalletEntity deposit(WalletEntity walletEntity);

    WalletEntity withdraw(WalletEntity walletEntity);

}
