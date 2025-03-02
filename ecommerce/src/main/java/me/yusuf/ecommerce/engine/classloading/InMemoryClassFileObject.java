package me.yusuf.ecommerce.engine.classloading;
import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

public class InMemoryClassFileObject extends SimpleJavaFileObject {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    public InMemoryClassFileObject(String className, Kind kind) {
        super(URI.create("string:///" + className.replace('.', '/') + kind.extension), kind);
    }

    @Override
    public OutputStream openOutputStream() {
        return outputStream;
    }

    public byte[] getClassBytes() {
        return outputStream.toByteArray();
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

