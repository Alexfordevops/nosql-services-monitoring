package nosql.user_service.dto;

public class MessageResponseDto {
    private String message;

    public MessageResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
