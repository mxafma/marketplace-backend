package Usuario_Serv.user.Service;

import Usuario_Serv.user.Model.User;
import Usuario_Serv.user.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User crearUsuario(User user) {
        return userRepository.save(user);
    }

    public List<User> obtenerTodos() {
        return userRepository.findAll();
    }

    public Optional<User> obtenerPorId(Long id) {
        return userRepository.findById(id);
    }

    public void eliminarUsuario(Long id) {
        userRepository.deleteById(id);
    }

    public User actualizarUsuario(Long id, User userActualizado) {
    return userRepository.findById(id).map(usuarioExistente -> {
        usuarioExistente.setNombreUsuario(userActualizado.getNombreUsuario());
        usuarioExistente.setCorreo(userActualizado.getCorreo());
        usuarioExistente.setContrasena(userActualizado.getContrasena());
        return userRepository.save(usuarioExistente);
    }).orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }



}
