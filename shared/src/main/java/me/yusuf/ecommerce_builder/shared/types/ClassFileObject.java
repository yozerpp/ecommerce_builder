package me.yusuf.ecommerce_builder.shared.types;
import lombok.Getter;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URI;

@Getter
public class ClassFileObject extends SimpleJavaFileObject implements Serializable{
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final String className;


    public byte[] getClassBytes() {
        return outputStream.toByteArray();
    }
    public ClassFileObject(String className, Kind kind) {
        super(URI.create("string:///" + className.replace('.', '/') + kind.extension), kind);
        this.className = className;
    }
    public ClassFileObject(String className, byte[] bytes){
        this(className, Kind.CLASS);
        this.outputStream.writeBytes(bytes);
    }
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
    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
