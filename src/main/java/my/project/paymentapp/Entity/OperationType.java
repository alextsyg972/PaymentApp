package my.project.paymentapp.Entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import my.project.paymentapp.exception.WrongOperationTypeException;

public enum OperationType {

    DEPOSIT("DEPOSIT"),
    WITHDRAW("WITHDRAW");


    @JsonIgnore
    private final String text;

    @JsonIgnore
    OperationType(final String text) {
        this.text = text;
    }

    @JsonValue
    public String getText() {
        return text;
    }

    @JsonCreator
    public static OperationType byText(final String text) {
        for (final OperationType value : OperationType.values()) {
            if (value.getText().equals(text)) return value;
        }
        throw new WrongOperationTypeException("Неправильный тип операции: " + text);
    }
}
