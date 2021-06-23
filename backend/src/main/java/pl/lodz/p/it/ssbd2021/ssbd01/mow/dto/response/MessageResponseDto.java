package pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Dto dla prostych wiadomości.
 */
public class MessageResponseDto {
    private String message;

    /**
     * Tworzy nową instancję klasy Message response dto.
     *
     * @param message message
     */
    public MessageResponseDto(String message) {
        this.message = message;
    }

    /**
     * Pobiera pole message.
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Ustawia pole message.
     *
     * @param message message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("message", message)
                .toString();
    }
}

