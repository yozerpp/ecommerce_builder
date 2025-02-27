package me.yusuf.ecommerce.misc;

import jakarta.validation.constraints.NotNull;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public record ElementImpl(String label, Object value, String tag, String id, String[] classNames, String[] style, Map.Entry<String,String>[][] attributeEntries, int[] containerIds, String[] containerClassNames, PropertyType type, boolean hideNull, boolean required) implements Element {
    public ElementImpl(@NotNull Element element, Object value){
        this(element.label(),value,element.tag(),element.id(), element.classNames(),element.style(), Arrays.stream(element.attributes()).map(s-> Arrays.stream(s.split(";")).map(e->Map.entry(e.split("=")[0],e.split("=")[1])).toArray(Map.Entry[]::new)).toArray(Map.Entry[][]::new),element.containerIds(), element.containerClassNames(), element.type(),element.hideNull(),element.required());
    }
    @Override
    public Class<? extends Annotation> annotationType() {
        return Element.class;
    }
    @Override
    public String[] attributes() {
        return Arrays.stream(attributeEntries).map(e-> Arrays.stream(e).map(ee->ee.getKey() + "=" + ee.getValue()).collect(Collectors.joining(";"))).toArray(String[]::new);
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ElementImpl element)) return false;
        return hideNull() == element.hideNull() && required() == element.required() && Objects.equals(id(), element.id()) && Objects.equals(tag(), element.tag()) && Objects.equals(label(), element.label()) && Objects.equals(value(), element.value()) && Objects.deepEquals(this.style(), element.style()) && type() == element.type() && Objects.deepEquals(classNames(), element.classNames()) && Objects.deepEquals(attributeEntries(), element.attributeEntries());
    }
    @Override
    public int hashCode() {
        return Objects.hash(label(), value(), tag(), id(), Arrays.hashCode(classNames()), Arrays.hashCode(this.style()), Arrays.hashCode(attributeEntries()), type(), hideNull(), required());
    }
    @Override
    public String toString() {
        return "ElementImpl{" +
                "label='" + label + '\'' +
                ", value=" + value +
                ", tag='" + tag + '\'' +
                ", id='" + id + '\'' +
                ", classNames=" + Arrays.toString(classNames) +
                ", style=" + Arrays.toString(style) +
                ", attributeEntries=" + Arrays.toString(attributeEntries) +
                ", containerIds=" + Arrays.toString(containerIds) +
                ", containerClassNames=" + Arrays.toString(containerClassNames) +
                ", type=" + type +
                ", hideNull=" + hideNull +
                ", required=" + required +
                '}';
    }
}
