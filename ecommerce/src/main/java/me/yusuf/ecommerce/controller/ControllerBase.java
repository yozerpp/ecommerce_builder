package me.yusuf.ecommerce.controller;

import jakarta.persistence.Embeddable;
import me.yusuf.utils.ReflectionUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;

public abstract class ControllerBase {
protected static String locationUrl(Object id, String path){
    if(id.getClass().isAnnotationPresent(Embeddable.class)){
        var ids = ReflectionUtils.getFields(id);
        var strids =Arrays.stream(ids).map(Object::toString).toArray(String[]::new);
        id = String.join("/", strids);
    }
    return ServletUriComponentsBuilder.fromCurrentContextPath().toUriString() + '/' + path + '/' +id.toString();
}
protected static String createdMessage(Object id, String path){
    var s =path.split("/");
    return s[s.length-1] + " created. You can view it by clicking <a href=\"" + locationUrl(id, path) + "\">here</a>";
}

}