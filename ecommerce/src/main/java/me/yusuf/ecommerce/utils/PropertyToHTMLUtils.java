package me.yusuf.ecommerce.utils;

import me.yusuf.ecommerce.misc.Element;
import me.yusuf.ecommerce.misc.ElementImpl;

import java.util.ArrayList;
import java.util.List;

public interface PropertyToHTMLUtils {
    static void ToElementRepresentation(Object object){
        var cls = object.getClass();
        List<ElementImpl> elements= new ArrayList<>();
        for (var field : cls.getDeclaredFields()) {
            var element = field.getAnnotation(me.yusuf.ecommerce.misc.Element.class);
            if(element == null) continue;
             elements.add(new ElementImpl(element, field));
        }
    }
}
