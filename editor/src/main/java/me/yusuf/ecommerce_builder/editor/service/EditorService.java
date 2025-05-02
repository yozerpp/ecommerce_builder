package me.yusuf.ecommerce_builder.editor.service;

import jakarta.annotation.Nullable;
import me.yusuf.ecommerce_builder.editor.domain.entity.Editor;
import me.yusuf.ecommerce_builder.editor.domain.repository.EditorRepository;
import org.springframework.stereotype.Service;

@Service
public class EditorService {
    private final EditorRepository editorRepository;
    public EditorService(EditorRepository editorRepository) {
        this.editorRepository = editorRepository;
    }
    public Editor register( @Nullable String fName, String lName, @Nullable String email, @Nullable String password){
        if (fName == null ||lName == null || email == null || password == null) {
            throw new IllegalArgumentException("All fields must be provided");
        }
        Editor editor = new Editor();
        editor.setFirstName(fName);
        editor.setLastName(lName);
        editor.setPassword(password);
        editor.setUsername(email);
        try {
            return editorRepository.save(editor);
        } catch (Exception e){ e.printStackTrace(); return null;}
        }
    public Editor login(String email, String password){
        try {
            return editorRepository.findByUsernameAndPassword(email, password);
        } catch (Exception e){
            return null;
        }
    }
}
