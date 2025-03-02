package me.yusuf.ecommerce.engine.classloading;
import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

public class InMemoryClassFileObject extends SimpleJavaFileObject {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final String className; // Store the actual class name

    public InMemoryClassFileObject(String className, Kind kind) {
        super(URI.create("string:///" + className.replace('.', '/') + kind.extension), kind);
        this.className = className; // Store the class name
    }

    @Override
    public OutputStream openOutputStream() {
        return outputStream;
    }

    public byte[] getClassBytes() {
        return outputStream.toByteArray();
    }
    
    public String getClassName() {
        return className; // Return the actual class name
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof InMemoryClassFileObject classFileObject) {
            return classFileObject.getName().equals(getName());
        }
        return false;
    }
    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
