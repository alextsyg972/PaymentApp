package my.project.paymentapp.controller;

import jakarta.validation.Valid;
import my.project.paymentapp.Entity.WalletEntity;
import my.project.paymentapp.dto.WalletDto;
import my.project.paymentapp.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class WalletController {


    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping(value = "/wallets/{wallet_uuid}", produces = "application/json")
    public ResponseEntity<WalletDto> getBalance(@PathVariable UUID wallet_uuid) {
        WalletEntity wallet = walletService.getWallet(wallet_uuid);
        WalletDto walletDto = WalletDto
                .builder()
                .walletId(wallet.getWalletId())
                .amount(wallet.getAmount())
                .build();
        return new ResponseEntity<>(walletDto, HttpStatus.OK);
    }

    @PostMapping(value = "/wallet", produces = "application/json")
    public ResponseEntity<WalletDto> changeBalance(@RequestBody @Valid WalletEntity wallet) {
        wallet = walletService.changeBalance(wallet);
        WalletDto walletDto = WalletDto
                .builder()
                .walletId(wallet.getWalletId())
                .amount(wallet.getAmount())
                .build();
        return new ResponseEntity<>(walletDto, HttpStatus.OK);
    }

}
