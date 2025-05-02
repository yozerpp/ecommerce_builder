package me.yusuf.ecommerce_builder.editor.network;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.yusuf.ecommerce_builder.editor.service.EditorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static me.yusuf.ecommerce_builder.editor.network.ControllerBase.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/editor")
public class LoginController extends ControllerBase{
    private final EditorService editorService;
    public LoginController(EditorService editorService) {
        this.editorService = editorService;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletResponse response,@RequestBody LoginForm loginForm) {
        var ret=editorService.login(loginForm.email, loginForm.password);
        if (ret == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect email or password.");
        var c = new Cookie("editorId", ret.getId().toString());
        c.setPath("/");
        c.setMaxAge(60 * 60 * 24 * 30); // 30 days
        response.addCookie(c);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest req, HttpServletResponse response) {
        var c = new Cookie("editorId", null);
        c.setPath("/");
        c.setMaxAge(0);
        response.addCookie(c);
        var s = req.getSession();
        s.removeAttribute("editorId");
        s.removeAttribute("editor");
        return ResponseEntity.ok().build();
    }
    @PostMapping
    public ResponseEntity<?> register(@RequestBody EditorForm editorForm ) {
        if (editorService.register(editorForm.firstName, editorForm.lastName, editorForm.email, editorForm.password)
        !=null) return ResponseEntity.ok().build();
        else return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists with this email.");
    }
    private record LoginForm(
            String email,
            String password
    ) {}
    private record EditorForm(
            String firstName,
            String lastName,
            String email,
            String password
    ) {}
}
