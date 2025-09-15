package nosql.user_service.repository;

import nosql.user_service.model.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<MessageModel, Long> {

    //Busca usuario por username
    List<MessageModel> findByNameIgnoreCase(String name);
}
