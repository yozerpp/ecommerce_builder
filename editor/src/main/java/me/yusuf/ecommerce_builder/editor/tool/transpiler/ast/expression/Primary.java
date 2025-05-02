package me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.expression;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.yusuf.utils.StringUtils;

import java.util.List;
import java.util.Objects;

public interface Primary extends Expression{
    static Expr wrap(Primary primary) {
        var post = new PostfixExpr(primary,false);
        var un = new UnaryExpr(null, post);
        var mult = new MultiplicativeExpr(un, List.of());
        var add = new AdditiveExpr(mult,List.of());
        var comp = new ComparisonExpr(add, List.of());
        var eq = new EqualityExpr(comp, List.of());
        var land= new LogicalAndExpr(eq, List.of());
        return new Expr(land,List.of());
    }
    @NoArgsConstructor
    class Number implements Primary{
        public java.lang.Number number;
        public Number(java.lang.Number number) {
            this.number = number;
        }
        @Override
        public boolean equals(Object object) {
            if (object == null || getClass() != object.getClass()) return false;

            Number number1 = (Number) object;
            return number.equals(number1.number);
        }

        @Override
        public int hashCode() {
            return number.hashCode();
        }

        @Override
        public String toString() {
            return number.toString();
        }
    }
    @NoArgsConstructor
    @AllArgsConstructor
    class StringLiteral implements Primary{
        public String string;

        @Override
        public boolean equals(Object object) {
            if (object == null || getClass() != object.getClass()) return false;

            StringLiteral stringLiteral = (StringLiteral) object;
            return string.equals(stringLiteral.string);
        }

        @Override
        public int hashCode() {
            return string.hashCode();
        }

        @Override
        public String toString() {
            return string;
        }
    }
    @NoArgsConstructor
    @AllArgsConstructor
    class Identifier implements Primary{
        public String identifier;
        public Identifier memberAccess;
        @Override
        public boolean equals(Object object) {
            if (object == null || getClass() != object.getClass()) return false;

            Identifier that = (Identifier) object;
            return identifier.equals(that.identifier);
        }

        @Override
        public int hashCode() {
            return identifier.hashCode()*31 + Objects.hashCode(memberAccess);
        }

        @Override
        public String toString() {
            return identifier +(memberAccess != null ? ".get" + StringUtils.firstLetterToUpperCase(memberAccess.toString()) + "()": "");
        }
    }
}