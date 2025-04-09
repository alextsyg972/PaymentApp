package my.project.paymentapp.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "wallet")
@Getter
@Setter
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "wallet_id")
    @NotNull
    private UUID walletId;

    @Transient
    private OperationType operationType;

    @Column(name = "amount")
    @Digits(integer = 12,fraction = 2, message = "Ошибка в значении amount, integer <= 12, fraction <= 2")
    @NotNull
    @PositiveOrZero
    private BigDecimal amount;

    public WalletEntity(UUID walletId, OperationType operationType, BigDecimal amount) {
        this.walletId = walletId;
        this.operationType = operationType;
        this.amount = amount;
    }

    public WalletEntity() {
    }

    @Override
    public String toString() {
        return "WalletEntity{" +
                "walletId=" + walletId +
                ", operationType=" + operationType +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WalletEntity that = (WalletEntity) o;
        return Objects.equals(walletId, that.walletId) && operationType == that.operationType && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(walletId, operationType, amount);
    }
}
