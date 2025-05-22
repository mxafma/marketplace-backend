package Usuario_Serv.user.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import Usuario_Serv.user.Model.User;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNombreUsuario(String nombreUsuario);
}
