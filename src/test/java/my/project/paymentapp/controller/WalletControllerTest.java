package my.project.paymentapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.project.paymentapp.Entity.OperationType;
import my.project.paymentapp.Entity.WalletEntity;
import my.project.paymentapp.exception.WalletNotFoundException;
import my.project.paymentapp.service.WalletService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = WalletController.class)
class WalletControllerTest {

    @MockitoBean
    WalletService walletService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void getBalance() throws Exception {
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setWalletId(UUID.fromString("388b377f-58f5-45d2-951b-19da115c2afd"));
        walletEntity.setAmount(BigDecimal.valueOf(32298.00));
        when(walletService.getWallet(UUID.fromString("388b377f-58f5-45d2-951b-19da115c2afd"))).thenReturn(walletEntity);

         mockMvc.perform(get("/api/v1//wallets/{wallet_uuid}", "388b377f-58f5-45d2-951b-19da115c2afd"))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.walletId").value("388b377f-58f5-45d2-951b-19da115c2afd"))
                 .andExpect(jsonPath("$.amount").value(32298.00));
         verify(walletService, times(1)).getWallet(UUID.fromString("388b377f-58f5-45d2-951b-19da115c2afd"));

    }

    @Test
    void getBalanceWrongUUID() throws Exception {
        UUID uuid = UUID.fromString("388b377f-58f5-45d2-951b-19da115c2afa");
        when(walletService.getWallet(uuid))
                .thenThrow(new WalletNotFoundException("Кошелек с id: " + uuid + " не найден"));

        mockMvc.perform(get("/api/v1//wallets/{wallet_uuid}", "388b377f-58f5-45d2-951b-19da115c2afa"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Кошелек с id: 388b377f-58f5-45d2-951b-19da115c2afa не найден"));
        verify(walletService, times(1)).getWallet(UUID.fromString("388b377f-58f5-45d2-951b-19da115c2afa"));
    }


    @Test
    void changeBalance() throws Exception {
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setWalletId(UUID.fromString("388b377f-58f5-45d2-951b-19da115c2afd"));
        walletEntity.setAmount(BigDecimal.valueOf(32298.00));
        walletEntity.setOperationType(OperationType.DEPOSIT);
        when(walletService.changeBalance(walletEntity)).thenReturn(walletEntity);

        String walletJson = objectMapper.writeValueAsString(walletEntity);

        mockMvc.perform(post("/api/v1/wallet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(walletJson))
                .andExpect(status().isOk());
        verify(walletService, times(1)).changeBalance(walletEntity);
    }
}