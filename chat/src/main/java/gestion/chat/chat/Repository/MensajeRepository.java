package gestion.chat.chat.Repository;



import gestion.chat.chat.Model.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    
    // Conversación entre dos usuarios (ambos sentidos)
    List<Mensaje> findByIdEmisorAndIdReceptorOrIdReceptorAndIdEmisor(
        Long emisor1, Long receptor1, Long emisor2, Long receptor2
    );
    @Query("SELECT m FROM Mensaje m WHERE (m.idEmisor = :user1 AND m.idReceptor = :user2) OR (m.idEmisor = :user2 AND m.idReceptor = :user1) ORDER BY m.fecha ASC")
List<Mensaje> obtenerConversacionEntreUsuarios(@Param("user1") Long user1, @Param("user2") Long user2);

}
