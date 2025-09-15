package nosql.user_service.service;

import jakarta.transaction.Transactional;
import nosql.user_service.dto.MessageResponseDto;
import nosql.user_service.dto.UserRequestDto;
import nosql.user_service.dto.UserResponseDto;
import nosql.user_service.model.MessageModel;
import nosql.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private UserRepository userRepository; // Repositório JPA para usuários

    //Cria uma mensagem
    @Transactional
    public UserResponseDto setMessage(UserRequestDto data){

        //Cria o objeto user
        MessageModel user = new MessageModel();
        user.setName(data.name);
        user.setMessage(data.message);

        //Salva no banco de dados
        MessageModel savedUser = userRepository.save(user);

        //Cria o DTO de resposta
        return new UserResponseDto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getMessage()
        );
    }

    //Busca mensagens por usuario utilizando o nome
    public List<MessageResponseDto> getMessagesByUser(String name){

        //Busca o usuário pelo nome
        List<MessageModel> users = userRepository.findByNameIgnoreCase(name);

        //Converter Lista de usuarios para lista de DTO
        return users.stream()
                .map(user -> new MessageResponseDto(
                        user.getMessage()
                ))
                .collect(Collectors.toList());
    }

    //Busca todos os usuarios
    public List<UserResponseDto> getUsersAndMessages(){

        //Busca os usuarios e suas mensagens no repositório
        List<MessageModel> users = userRepository.findAll();

        //Converter Lista de usuarios para lista de DTO
        return users.stream()
                .map(user -> new UserResponseDto(
                        user.getId(),
                        user.getName(),
                        user.getMessage()
                ))
                .collect(Collectors.toList());
    }
}
