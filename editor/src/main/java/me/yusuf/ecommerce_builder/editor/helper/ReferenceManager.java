package me.yusuf.ecommerce_builder.editor.helper;

import me.yusuf.ecommerce_builder.shared.types.plugin.EntitySource;
import me.yusuf.ecommerce_builder.shared.types.tuple.Tuple2;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ReferenceManager {
    private final Class<?>[] defaultEntityClasses;
    private final Map<String, Tuple2<String,Patterns>> patternsMap;
    private final int editorId;
    private final int newVersion;
    public ReferenceManager(Class<?>[] defaultEntityClasses, int editorId, int oldVersion) {
        this.defaultEntityClasses = defaultEntityClasses;
        this.editorId = editorId;
        this.newVersion = oldVersion + 1;
        final String packageNameRegex = (EntitySource.DYNAMIC_PACKAGE_PREFIX + editorId).replace(".","\\.");
        this.patternsMap = Arrays.stream(defaultEntityClasses).filter(c->c.getDeclaringClass()==null).collect(Collectors.toMap(c->EntitySource.getClassName(c.getSimpleName(),newVersion,editorId), c -> {
            String oldVersionedName = oldVersion != 0 ? c.getSimpleName() + "_v" + oldVersion : c.getSimpleName();
            return new Tuple2<>( c.getName(),new Patterns(Pattern.compile("^\\bimport\\s+" + packageNameRegex + "\\." + oldVersionedName + "\\s*;", Pattern.MULTILINE | Pattern.DOTALL),
                    Pattern.compile("\\b" + packageNameRegex + "\\." + oldVersionedName + "\\b"),
                    Pattern.compile("\\b" + oldVersionedName + "\\b")
            )
        );
        }));
    }
    public String update(String source, String currentClassName){
        for (var clasAndPats: patternsMap.entrySet()) {
            var defaultClass = clasAndPats.getValue()._1();
            var pats = clasAndPats.getValue()._2();
            var className = defaultClass.equals(currentClassName)?
                    currentClassName.replaceAll("(\\w+\\.)+","") + "_v" + newVersion //only simple name for the current class.
                    : clasAndPats.getKey();
            source = pats.importPat.matcher(source).replaceFirst("import " + className + ";\n");
            source = pats.fqrPat.matcher(source).replaceAll(className);
            source = pats.entityPat.matcher(source).replaceAll(className );
        }
        return source;
    }
    private record Patterns(
            Pattern importPat,
            Pattern fqrPat,
            Pattern entityPat
    ){}
}
