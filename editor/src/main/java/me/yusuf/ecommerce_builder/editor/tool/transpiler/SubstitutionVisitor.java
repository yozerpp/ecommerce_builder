package me.yusuf.ecommerce_builder.editor.tool.transpiler;

import lombok.Getter;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.*;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.expression.Primary;
import me.yusuf.ecommerce_builder.shared.types.tuple.Tuple2;
import me.yusuf.utils.StringUtils;

import java.util.*;

/**
 * @implNote Not thread safe
 */
@Getter
public class SubstitutionVisitor implements ASTModifierVisitor{
    public static class Factory{
        private final Map<Tuple2<String,SymbolType>, String> defines;
        public Factory(Map<Tuple2<String,SymbolType>, String> defines){
            this.defines = defines;
        }
        public SubstitutionVisitor create(){
            return new SubstitutionVisitor(defines,new HashSet<>());
        }
    }
    private final Map<Tuple2<String, SymbolType>, String> subs;
    private final Set<String> definedSymbols;
    private final Set<String> entityTypes = new HashSet<>();
    public enum SymbolType{
        Function,
        Variable
    }
    SubstitutionVisitor(Map<Tuple2<String,SymbolType>, String> subs, Set<String> definedSymbols){
        this.subs = subs;
        this.definedSymbols = definedSymbols;
    }

    @Override
    public Object visitAssignmentExpr(AssignmentExpr asn) {
        visitIdentifier(asn.getLeft(), true);
        visitExpression(asn.getRight());
        return null;
    }

    @Override
    public Object visitFunctionCallExpr(FunctionCallExpr fce) {
        var key = new Tuple2<>(fce.getFunctionName(),SymbolType.Function);
        if (subs.containsKey(key))
            fce.setFunctionName(subs.get(key));
        return ASTModifierVisitor.super.visitFunctionCallExpr(fce);
    }
    boolean inVarDecl;
    @Override
    public Object visitVarDeclarationStatement(VarDeclarationStatement vds) {
        if (subs.containsKey(new Tuple2<>(vds.getVarName(), SymbolType.Variable)) ||
                subs.containsKey(new Tuple2<>(vds.getVarName(), SymbolType.Function))||
                definedSymbols.contains(vds.getVarName()))
            throw new SymbolError(SymbolError.Cause.already_defined,vds.getVarName());
        definedSymbols.add(vds.getVarName());
        return ASTModifierVisitor.super.visitVarDeclarationStatement(vds);
    }
    @Getter
    public static class SymbolError extends RuntimeException{
        public enum Cause{
            already_defined,
            undefined
        }
        private final Cause causeEnum;
        private final String identifier;
        public SymbolError(Cause causeEnum, String identifier){
            super("Symbol " + identifier + " is " + causeEnum.name().replace("_"," "));
            this.causeEnum = causeEnum;
            this.identifier = identifier;
        }
    }

    @Override
    public Object visitPrimary(Primary p) {
        if (p instanceof Primary.Identifier id)
            visitIdentifier(id, true);
        return null;
    }

    public Object visitIdentifier(Primary.Identifier id,boolean root) {
        String sub;
        if ((sub = subs.get(new Tuple2<>(id.identifier, SymbolType.Variable))) != null) {
            id.identifier = StringUtils.firstLetterToLowerCase(sub);
            definedSymbols.add(id.identifier);
            entityTypes.add(sub);
        } else if ((sub = subs.get(new Tuple2<>(id.identifier, SymbolType.Function))) != null) {
            id.identifier = sub;
        } else if (!definedSymbols.contains(id.identifier)&&root) throw new SymbolError(SymbolError.Cause.undefined, id.identifier);
        if (id.memberAccess!=null)
            visitIdentifier(id.memberAccess, false);
        return null;
    }
}
