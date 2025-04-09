package my.project.paymentapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@Builder
public class WalletDto {

    private UUID walletId;
    private BigDecimal amount;


}
