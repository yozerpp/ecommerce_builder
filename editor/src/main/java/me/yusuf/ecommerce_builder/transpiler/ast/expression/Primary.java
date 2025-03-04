package me.yusuf.ecommerce_builder.transpiler.ast.expression;

public interface Primary extends Expression{
    public static Expr wrap(Primary primary) {
        var un = new UnaryExpr(null, primary);
        var mult = new MultiplicativeExpr(un,null);
        var add = new AdditiveExpr(mult,null);
        var comp = new ComparisonExpr(add, null);
        var eq = new EqualityExpr(comp, null);
        var land= new LogicalAndExpr(eq, null);
        return new Expr(land,null);
    }
    class Number implements Primary{
        public java.lang.Number number;
        @Override
        public String toString() {
            return number.toString();
        }
    }
    class Str implements Primary{
        public String string;

        @Override
        public String toString() {
            return string;
        }
    }
    class Identifier implements Primary{
        public String identifier;

        @Override
        public String toString() {
            return identifier;
        }
    }
}
