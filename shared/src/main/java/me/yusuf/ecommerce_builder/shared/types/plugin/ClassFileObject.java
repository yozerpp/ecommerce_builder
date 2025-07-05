package me.yusuf.ecommerce_builder.shared.types.plugin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URI;
import java.util.Base64;

@Getter
public class ClassFileObject extends SimpleJavaFileObject implements Serializable{
    @JsonIgnore
    @Getter(AccessLevel.NONE)
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final String className;


    public byte[] getClassBytes() {
        return outputStream.toByteArray();
    }
    public void setClassBytes(byte[] bytes){
        outputStream.reset();outputStream.writeBytes(bytes);
    }
    public ClassFileObject(String className, Kind kind) {
        super(URI.create("dynamic:///" + className.replace('.', '/') + kind.extension), kind);
        this.className = className;
    }
    public ClassFileObject(String className, byte[] bytes){
        this(className, Kind.CLASS);
        this.outputStream.writeBytes(bytes);
    }
    @JsonIgnore
    @Override
    public OutputStream openOutputStream() {
        return outputStream;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClassFileObject classFileObject) {
            return classFileObject.getName().equals(getName());
        }
        return false;
    }
    @JsonIgnore
    public Dto toDto(){
        return new Dto(className, Base64.getEncoder().encodeToString(getClassBytes()));
    }
    @JsonIgnore
    public static ClassFileObject fromDto(Dto dto){
        return new ClassFileObject(dto.className,Base64.getDecoder().decode(dto.content));
    }
    @Override
    public int hashCode() {
        return getName().hashCode();
    }
    public static class Dto{
        public String className, content;
        public Dto(String className, String content){
            this.className = className;
            this.content = content;
        }
        public Dto(){}
    }
}
