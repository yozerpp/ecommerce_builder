package me.yusuf.ecommerce_builder.shared.types.conversion;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import me.yusuf.ecommerce_builder.shared.types.plugin.ClassFileObject;
import me.yusuf.ecommerce_builder.shared.types.annotation.MethodInfo;
import me.yusuf.utils.ReflectionUtils;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Collectors;

public class ClassMapper extends ObjectMapper {
    protected static class MethodInfoDeserializer extends JsonDeserializer<MethodInfo>{

        @Override
        public MethodInfo deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            var mapper = (ObjectMapper)p.getCodec();
            var tree= mapper.readTree(p);
            var uFN = tree.get("userFriendlyName");
            var oE=tree.get("operatedEntites");
            return new MethodInfo.Impl(uFN!=null? uFN.asToken().asString():null,
                    oE!=null?mapper.treeToValue(oE,Class[].class):null);
        }
    }
    protected static class TypeSerializer extends JsonSerializer<Type> {
        @Override
        public void serialize(Type type, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(type.getTypeName());
        }
    }
    protected static class TypeDeserializer extends JsonDeserializer<Type> {
        @Override
        public Type deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            try {
                return ReflectionUtils.typeForName(jsonParser.getText());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    protected static class ParameterizedTypeSerializer extends JsonSerializer<ParameterizedType>{
        @Override
        public void serialize(ParameterizedType value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(ReflectionUtils.typeToString(value));
        }
    }
    protected static class ParameterizedTypeDeserializer extends JsonDeserializer<ParameterizedType>{
        @Override
        public ParameterizedType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            try {
                return (ParameterizedType) ReflectionUtils.typeForName(p.getText());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    protected static class MethodSerializer extends JsonSerializer<Method> {

        @Override
        public void serialize(Method method, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(method.getDeclaringClass().getName() + '.' + method.getName() + '(' + Arrays.stream(method.getParameterTypes()).map(Class::getName).collect(Collectors.joining(",")) + ')' );
        }
    }
    protected static class MethodDeserializer extends JsonDeserializer<Method>{
        @Override public Method deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            var fqNameAndParams=jsonParser.getText().split("\\(");
            var clssAndMethodName = fqNameAndParams[0].split("\\.");
            var className = Arrays.stream(clssAndMethodName).limit(clssAndMethodName.length - 1).collect(Collectors.joining("."));
            var paramTypes  = Arrays.stream(fqNameAndParams[1].replace(")","").split(","))
                    .map(s->{
                        try {
                            return Class.forName(s);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }).toArray(Class[]::new);
            try {
                return Class.forName(className).getMethod(clssAndMethodName[clssAndMethodName.length -1 ], paramTypes);
            } catch (NoSuchMethodException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    protected static class InMemoryClassFileObjectSerializer extends JsonSerializer<ClassFileObject>{
        @Override
        public void serialize(ClassFileObject classFileObject, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("name", classFileObject.getName());
            jsonGenerator.writeStringField("bytes", Base64.getEncoder().encodeToString(classFileObject.getClassBytes()));
            jsonGenerator.writeEndObject();
        }
    }
    protected static class InMemoryClassFileObjectDeserializer extends JsonDeserializer<ClassFileObject>{
        @Override
        public ClassFileObject deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var node = jsonParser.getCodec().readTree(jsonParser);
            String name = node.get("name").asToken().asString();
            byte[] bytes = Base64.getDecoder().decode(node.get("bytes").asToken().asString());
            return new ClassFileObject(name, bytes);
        }
    }
    public ClassMapper(){
        super();
        registerModule();
    }
    public ClassMapper(ObjectMapper mapper){
        super(mapper);
        registerModule();
    }
    public ClassMapper(ObjectMapper mapper, JsonFactory factory){
        super(mapper, factory);
        registerModule();
    }
    private SimpleModule getModule(){
        SimpleModule module = new SimpleModule();
        module.addSerializer(Type.class, new TypeSerializer());
        module.addDeserializer(Type.class, new TypeDeserializer());
        module.addSerializer(Method.class, new MethodSerializer());
        module.addDeserializer(Method.class, new MethodDeserializer());
        module.addSerializer(ClassFileObject.class, new InMemoryClassFileObjectSerializer());
        module.addDeserializer(ClassFileObject.class, new InMemoryClassFileObjectDeserializer());
        module.addDeserializer(MethodInfo.class, new MethodInfoDeserializer());
        module.addSerializer(ParameterizedType.class, new ParameterizedTypeSerializer());
        module.addDeserializer(ParameterizedType.class, new ParameterizedTypeDeserializer());
        return module;
    }
    private void registerModule(){
        registerModule(getModule());
    }

    @Override
    public ObjectMapper copy() {
        return new ClassMapper(this);
    }

    @Override
    public ObjectMapper copyWith(JsonFactory factory) {
        return new ClassMapper(this, factory);
    }
}
