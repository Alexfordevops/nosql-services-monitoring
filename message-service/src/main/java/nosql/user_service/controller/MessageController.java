package nosql.user_service.controller;

import nosql.user_service.dto.MessageResponseDto;
import nosql.user_service.dto.UserRequestDto;
import nosql.user_service.dto.UserResponseDto;
import nosql.user_service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    //Conecta com o UserService
    @Autowired
    private MessageService userService;

    @PostMapping("/setMessage")
    public ResponseEntity<UserResponseDto> setMessage(UserRequestDto data){
        System.out.println(data.message);
        UserResponseDto response = userService.setMessage(data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getMessageByUser/{name}")
    public ResponseEntity<List<MessageResponseDto>> getMessageByUser(
            @PathVariable String name
    ){
        List<MessageResponseDto> response = userService.getMessagesByUser(name);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/getUsersAndMessages")
    public ResponseEntity<List<UserResponseDto>> getUsersAndMessages(){
        List<UserResponseDto> response = userService.getUsersAndMessages();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
