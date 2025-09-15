package nosql.user_service.model;

import jakarta.persistence.*;

@Entity
public class MessageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Nome do usuário
    @Column
    private String name;

    //Mensagem que o usuário atribui na imagem
    @Column
    private String message;

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String messsage) {
        this.message = messsage;
    }
}
