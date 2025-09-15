package nosql.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequestDto {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 4, max = 20, message = "O nome deve ter entre 4 e 20 caracteres")
    public String name;

    @NotBlank
    public String message;
}
