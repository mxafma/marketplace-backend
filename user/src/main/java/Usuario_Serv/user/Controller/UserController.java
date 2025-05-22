package Usuario_Serv.user.Controller;

import Usuario_Serv.user.Model.User;
import Usuario_Serv.user.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> crearUsuario(@RequestBody User user) {
        return ResponseEntity.ok(userService.crearUsuario(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> obtenerTodos() {
        return ResponseEntity.ok(userService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> obtenerPorId(@PathVariable Long id) {
        return userService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        userService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
public ResponseEntity<User> actualizarUsuario(@PathVariable Long id, @RequestBody User user) {
    try {
        User actualizado = userService.actualizarUsuario(id, user);
        return ResponseEntity.ok(actualizado);
    } catch (RuntimeException e) {
        return ResponseEntity.notFound().build();
    }
}


}
