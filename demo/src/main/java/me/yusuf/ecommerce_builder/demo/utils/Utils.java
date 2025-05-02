package me.yusuf.ecommerce_builder.demo.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Utils {
    private static final Map<String, Class<?>[]> cacheOfClasses = new HashMap<>();
    public static Class<?> extractEntityClassFromRepository(Class<?> r){
        var ptype = Arrays.stream(r.getGenericInterfaces())
                .filter(c->c.getTypeName().contains("Repository") && c instanceof ParameterizedType && ((ParameterizedType) c).getActualTypeArguments().length==2)
                .findAny().map(ParameterizedType.class::cast).orElseThrow(()->new RuntimeException("Repository doesn't implement javax.persistance repository classes."));
        return (Class<?>) ptype.getActualTypeArguments()[0];
    }


    public static String firstLetterToUpperCase(String in){
        var ar = in.toCharArray();

        ar[0] = Character.toUpperCase(ar[0]);
        return String.valueOf(ar);
    }
    public static Map<String, Map.Entry<String,Boolean>> propertyMap(Class<?> clazz){
        return Arrays.stream(clazz.getDeclaredMethods()).filter(m->m.getName().startsWith("is") || m.getName().startsWith("get")).collect(Collectors.toMap(m->m.getName().replaceAll("^(is|get)",""), (m->Map.entry(m.getReturnType().getSimpleName(),!m.isAnnotationPresent(Nullable.class)))));
    }
    public static String getCookieValue(String name, HttpServletRequest request){
        var cc = Arrays.stream(request.getCookies()).filter(c->c.getName().equals(name)).findFirst();
        return cc.map(Cookie::getValue).orElse(null);
    }
    public static String getTypeName(Class<?> tp){
        var ret = tp.getSimpleName();
        if(ParameterizedType.class.isAssignableFrom(tp.getGenericSuperclass().getClass())){
            var args = ((ParameterizedType)tp.getGenericSuperclass()).getActualTypeArguments();
            ret+='<' + Arrays.stream(args).map(Type::getTypeName).collect(Collectors.joining(",")) + '>';
        }
        return ret;
    }
    /**
     * @param cb
     * @param root
     * @param order format: 'order = propertyName_direction'
     * @return
     */
    public static <Z,X> Order parseOrder(CriteriaBuilder cb, From<Z,X> root, final String order){
        var propertyAndDirection = order.split("_");
        if(propertyAndDirection[1].equals("asc"))
            return cb.asc(root.get(propertyAndDirection[0]));
        else if(propertyAndDirection[1].equals("desc") )
            return cb.desc(root.get(propertyAndDirection[0]));
        else throw new UnsupportedOperationException("no matching ordering signature found in order signature:" + order);
    }
    public static <E> List<E> search(Class<E> clazz, EntityManager entityManager, String[] parameters){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        Root<E> root= cb.createQuery().from(clazz);
        int page = 0;
        int pageSize = 20;
        List<Order> orderings = new ArrayList<>();
        List<Predicate> predicates = new ArrayList<>();
        for (String parameter: parameters){
            if(parameter.startsWith("page")) page = Integer.parseInt(parameter.split("=")[1]);
            else if(parameter.startsWith("pageSize")) pageSize = Integer.parseInt(parameter.split("=")[1]);
            else if(parameter.startsWith("order")) orderings.add(Utils.parseOrder(cb,root, parameter.split("\\|")[1]));
            else if(parameter.startsWith("predicate")) predicates.add(Utils.parsePredicate(cb,root, parameter.split("\\|")[1]));
            else throw new RuntimeException("Operation prefix for parameter is not compatible: " + parameter + "compatible prefixes: page, pageSize, order, predicate");
        }
        var criteriaQuery = cb.createQuery(clazz);
        var typedQuery =  entityManager.createQuery(criteriaQuery.select(root).where(predicates.toArray(Predicate[]::new)).orderBy(orderings));
        typedQuery.setFirstResult(pageSize * page);
        typedQuery.setMaxResults(pageSize *(page +1 ));
        return typedQuery.getResultList();
    }
    /**
     * @param cb
     * @param root
     * @param parameter parameter = 'propertyName->parameter' | 'columnName_comparisonOperator=value::type'
     */
    public static <Z,X> Predicate parsePredicate(CriteriaBuilder cb, From<Z,X> root, final String parameter){
        if(parameter.contains("->")) {
            var propertyAndRefProperty = parameter.split("->");
            var join =  root.join(propertyAndRefProperty[0]);
            return parsePredicate(cb, join, propertyAndRefProperty[1]);
        }
        var columnWithOperator =  parameter.split("=")[0];
        var columnAndOperator = columnWithOperator.split("_");
        Object value = Utils.convertToTypeFromTypeString(parameter);
        if(columnAndOperator[1].equals("lt"))
            return cb.lessThan(root.get(columnAndOperator[0]), (Comparable) value);
        else if(columnAndOperator[1].equals("gt"))
            return cb.greaterThan(root.get(columnAndOperator[0]), (Comparable)value);
        else if(columnAndOperator[1].equals("eq"))
            return cb.equal(root.get(columnAndOperator[0]), value);
        else if(columnAndOperator[1].equals("like"))
            return cb.like(root.get(columnAndOperator[0]), "%"+value.toString()+"%");
        else throw new UnsupportedOperationException("no matching predicate signature found in parameter signature:" + parameter);
    }
    public static Object convertToTypeFromTypeString(String parameter){
        var parameterAndValueType = parameter.split("=");
        var valueAndType = parameterAndValueType[1].split("::");
        return Utils.convertType(valueAndType[1], valueAndType[0]);
    }
    public static Object convertType(String className, String value){
        try {
            Class<?> claz = Class.forName(className);
            var ar = value.split(",");
            if(claz.isArray()){
                if(Number.class.isAssignableFrom( claz.getComponentType()))
                    return Arrays.stream(ar).map(n-> {
                        try {
                            return claz.getMethod("parse" + firstLetterToUpperCase(claz.getSimpleName()));
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                    });
                else if(String.class.isAssignableFrom(claz.getComponentType()))
                    return ar;
                else if(byte.class.isAssignableFrom(claz.getComponentType()))
                    return Arrays.stream(ar).map(Byte::parseByte);
                else if (char.class.isAssignableFrom(claz.getComponentType()))
                    return Arrays.stream(ar).map(a->a.charAt(0));
                else if(boolean.class.isAssignableFrom(claz.getComponentType()))
                    return Arrays.stream(ar).map(Boolean::getBoolean);
                else throw new UnsupportedOperationException("no matching type is found");
            }
            else{
                if (Number.class.isAssignableFrom(claz))
                    return claz.getMethod("parse" + firstLetterToUpperCase(claz.getSimpleName())).invoke(null, value);
                else if (String.class.isAssignableFrom(claz))
                    return value;
                else if (byte.class.isAssignableFrom(claz))
                    return Byte.parseByte(value);
                else if (char.class.isAssignableFrom(claz))
                    return value.charAt(0);
                else if(boolean.class.isAssignableFrom(claz))
                    return Boolean.getBoolean(value);
                else throw new UnsupportedOperationException("no matching type is found");
            }
        } catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException | InvocationTargetException e ){
            throw new RuntimeException(e);
        }
    }
}
