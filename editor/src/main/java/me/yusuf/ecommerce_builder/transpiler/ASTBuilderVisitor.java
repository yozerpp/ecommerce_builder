package me.yusuf.ecommerce_builder.transpiler;

import me.yusuf.ecommerce_builder.transpiler.ast.*;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.*;
import me.yusuf.ecommerce_builder.transpiler.generated.TurkishPseudoCodeBaseVisitor;
import me.yusuf.ecommerce_builder.transpiler.generated.TurkishPseudoCodeParser;

import java.util.ArrayList;
import java.util.List;

public class ASTBuilderVisitor extends TurkishPseudoCodeBaseVisitor<ASTNode> {

    @Override
    public PluginDef visitIşlevTanımı(TurkishPseudoCodeParser.IşlevTanımıContext ctx) {
        PluginDef ret = new PluginDef();
        // If the işlevİsmi() is not present, try to use hataİfadesi() or sonraİfadesi()
        if (ctx.işlevİsmi() != null) {
            ret.hookedMethod = ctx.işlevİsmi().getText();
        } else if (ctx.hataİfadesi() != null) {
            ret.hookedMethod = ctx.hataİfadesi().hataİsmi().getText();
        } else if (ctx.sonraİfadesi() != null) {
            ret.hookedMethod = ctx.sonraİfadesi().getText();
        } else {
            ret.hookedMethod = "";
        }
        ret.block = (Block) visitGövde(ctx.gövde());
        ret.name = ctx.eylemİsmi().getText();
        return ret;
    }

    @Override
    public ASTNode visitVarlıkİsmi(TurkishPseudoCodeParser.VarlıkİsmiContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitEylemİsmi(TurkishPseudoCodeParser.EylemİsmiContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitIşlevİsmi(TurkishPseudoCodeParser.IşlevİsmiContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitHataİfadesi(TurkishPseudoCodeParser.HataİfadesiContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitSonraİfadesi(TurkishPseudoCodeParser.SonraİfadesiContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitHataDenklemi(TurkishPseudoCodeParser.HataDenklemiContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitHataİsmi(TurkishPseudoCodeParser.HataİsmiContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitIfade(TurkishPseudoCodeParser.IfadeContext ctx) {
        if (ctx.döngüİfadesi() != null) {
            return visitDöngüİfadesi(ctx.döngüİfadesi());
        } else if (ctx.eğerİfadesi() != null) {
            return visitEğerİfadesi(ctx.eğerİfadesi());
        } else if (ctx.herBiriİfadesi() != null) {
            return visitHerBiriİfadesi(ctx.herBiriİfadesi());
        } else if (ctx.değişkenTanımı() != null) {
            return visitDeğişkenTanımı(ctx.değişkenTanımı());
        } else if (ctx.denklemİfadesi() != null) {
            return visitDenklemİfadesi(ctx.denklemİfadesi());
        } else if (ctx.gövde() != null) {
            return visitGövde(ctx.gövde());
        } else if (ctx.dönmeİfadesi() != null) {
            return visitDönmeİfadesi(ctx.dönmeİfadesi());
        }
        throw new IllegalArgumentException("Unknown ifade type: " + ctx.getText());
    }

    @Override
    public FunctionCallExpr visitFonksiyonÇağrımı(TurkishPseudoCodeParser.FonksiyonÇağrımıContext ctx) {
        FunctionCallExpr ret = new FunctionCallExpr();
        ret.functionName = ctx.değişken().getText();
        List<Expression> argsList = new ArrayList<>();
        if (ctx.denklem() != null) {
            for (TurkishPseudoCodeParser.DenklemContext denklemCtx : ctx.denklem()) {
                argsList.add((Expression) visitDenklem(denklemCtx));
            }
        }
        ret.args = argsList.toArray(new Expression[0]);
        return ret;
    }

    @Override
    public Statement visitDenklemİfadesi(TurkishPseudoCodeParser.DenklemİfadesiContext ctx) {
        if (ctx.fonksiyonÇağrımı() != null) {
            return visitFonksiyonÇağrımı(ctx.fonksiyonÇağrımı());
        } else if (ctx.atama() != null) {
            return visitAtama(ctx.atama());
        }
        throw new RuntimeException("Unknown denklem ifadesi: " + ctx.getText());
    }

    @Override
    public ForeachStatement visitHerBiriİfadesi(TurkishPseudoCodeParser.HerBiriİfadesiContext ctx) {
        ForeachStatement ret = new ForeachStatement();
        ret.collectionName = ctx.koleksiyonİsmi().getText();
        ret.elementName = ctx.elementİsmi().getText();
        ret.block = (Block) visitGövde(ctx.gövde());
        return ret;
    }

    @Override
    public ASTNode visitKoleksiyonİsmi(TurkishPseudoCodeParser.KoleksiyonİsmiContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitElementİsmi(TurkishPseudoCodeParser.ElementİsmiContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public LoopStatement visitDöngüİfadesi(TurkishPseudoCodeParser.DöngüİfadesiContext ctx) {
        LoopStatement ret = new LoopStatement();
        ret.condition = (Expression) visitKoşul(ctx.koşul());
        ret.block = (Block) visitGövde(ctx.gövde());
        return ret;
    }

    @Override
    public IfStatement visitEğerİfadesi(TurkishPseudoCodeParser.EğerİfadesiContext ctx) {
        IfStatement ret = new IfStatement();
        ret.condition = (Expression) visitKoşul(ctx.koşul());
        ret.happyPath = (Block) visitGövde(ctx.gövde(0));
        if (ctx.gövde().size() > 1) {
            ret.sadPath = (Block) visitGövde(ctx.gövde(1));
        }
        return ret;
    }

    @Override
    public Expr visitKoşul(TurkishPseudoCodeParser.KoşulContext ctx) {
        return visitDenklem(ctx.denklem());
    }

    @Override
    public Block visitGövde(TurkishPseudoCodeParser.GövdeContext ctx) {
        Block block = new Block();
        for (var ifadeCtx : ctx.ifade()) {
            block.statements.add((Statement) visitIfade(ifadeCtx));
        }
        return block;
    }

    @Override
    public VarDeclarationStatement visitDeğişkenTanımı(TurkishPseudoCodeParser.DeğişkenTanımıContext ctx) {
        VarDeclarationStatement ret = new VarDeclarationStatement();
        ret.varName = ctx.ilkelDeğişken().getText();
        ret.value = visitDenklem(ctx.denklem());
        return ret;
    }

    @Override
    public AssignmentExpr visitAtama(TurkishPseudoCodeParser.AtamaContext ctx) {
        AssignmentExpr ret = new AssignmentExpr();
        ret.left = ctx.değişken().getText();
        ret.right = visitDenklem(ctx.denklem());
        return ret;
    }

    @Override
    public Primary.Identifier visitIlkelDeğişken(TurkishPseudoCodeParser.IlkelDeğişkenContext ctx) {
        Primary.Identifier ret = new Primary.Identifier();
        ret.identifier = ctx.getText();
        return ret;
    }

    @Override
    public ReturnStatement visitDönmeİfadesi(TurkishPseudoCodeParser.DönmeİfadesiContext ctx) {
        ReturnStatement ret = new ReturnStatement();
        ret.value = visitDenklem(ctx.denklem());
        return ret;
    }

    @Override
    public Expr visitDenklem(TurkishPseudoCodeParser.DenklemContext ctx) {
        LogicalAndExpr first = visitMantıksalVeDenklemi(ctx.mantıksalVeDenklemi(0));
        List<LogicalAndExpr> rest = new ArrayList<>();
        for (int i = 1; i < ctx.mantıksalVeDenklemi().size(); i++) {
            rest.add( visitMantıksalVeDenklemi(ctx.mantıksalVeDenklemi(i)));
        }
        return new Expr(first, rest);
    }

    @Override
    public LogicalAndExpr visitMantıksalVeDenklemi(TurkishPseudoCodeParser.MantıksalVeDenklemiContext ctx) {
        EqualityExpr first = (EqualityExpr) visitEşitlikDenklemi(ctx.eşitlikDenklemi(0));
        List<EqualityExpr> rest = new ArrayList<>();
        for (int i = 1; i < ctx.eşitlikDenklemi().size(); i++) {
            rest.add((EqualityExpr) visitEşitlikDenklemi(ctx.eşitlikDenklemi(i)));
        }
        return new LogicalAndExpr(first, rest);
    }

    @Override
    public EqualityExpr visitEşitlikDenklemi(TurkishPseudoCodeParser.EşitlikDenklemiContext ctx) {
        ComparisonExpr first = (ComparisonExpr) visitKarşılaştırmaDenklemi(ctx.karşılaştırmaDenklemi(0));
        List<EqualityExpr.Op> ops = new ArrayList<>();
        for (int i = 1; i < ctx.karşılaştırmaDenklemi().size(); i++) {
            String operator = ctx.eşitlikİşareti(i - 1).getText();
            ComparisonExpr expr = (ComparisonExpr) visitKarşılaştırmaDenklemi(ctx.karşılaştırmaDenklemi(i));
            ops.add(new EqualityExpr.Op(operator, expr));
        }
        return new EqualityExpr(first, ops);
    }

    @Override
    public ASTNode visitEşitlikİşareti(TurkishPseudoCodeParser.EşitlikİşaretiContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ComparisonExpr visitKarşılaştırmaDenklemi(TurkishPseudoCodeParser.KarşılaştırmaDenklemiContext ctx) {
        AdditiveExpr first = (AdditiveExpr) visitToplamaDenklemi(ctx.toplamaDenklemi(0));
        List<ComparisonExpr.Op> ops = new ArrayList<>();
        for (int i = 1; i < ctx.toplamaDenklemi().size(); i++) {
            String operator = ctx.karşılaştrımaİşareti(i - 1).getText();
            AdditiveExpr expr = (AdditiveExpr) visitToplamaDenklemi(ctx.toplamaDenklemi(i));
            ops.add(new ComparisonExpr.Op(operator, expr));
        }
        return new ComparisonExpr(first, ops);
    }

    @Override
    public ASTNode visitKarşılaştrımaİşareti(TurkishPseudoCodeParser.KarşılaştrımaİşaretiContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public AdditiveExpr visitToplamaDenklemi(TurkishPseudoCodeParser.ToplamaDenklemiContext ctx) {
        MultiplicativeExpr first = (MultiplicativeExpr) visitÇarpmaDenklemi(ctx.çarpmaDenklemi(0));
        List<AdditiveExpr.Op> ops = new ArrayList<>();
        for (int i = 1; i < ctx.çarpmaDenklemi().size(); i++) {
            String operator = ctx.toplamaÇıkarmaİşareti(i - 1).getText();
            MultiplicativeExpr expr = (MultiplicativeExpr) visitÇarpmaDenklemi(ctx.çarpmaDenklemi(i));
            ops.add(new AdditiveExpr.Op(operator, expr));
        }
        return new AdditiveExpr(first, ops);
    }

    @Override
    public ASTNode visitToplamaÇıkarmaİşareti(TurkishPseudoCodeParser.ToplamaÇıkarmaİşaretiContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public MultiplicativeExpr visitÇarpmaDenklemi(TurkishPseudoCodeParser.ÇarpmaDenklemiContext ctx) {
        UnaryExpr first = (UnaryExpr) visitTekliDenklem(ctx.tekliDenklem(0));
        List<MultiplicativeExpr.Op> ops = new ArrayList<>();
        for (int i = 1; i < ctx.tekliDenklem().size(); i++) {
            String operator = ctx.çarpmaBölmeİşareti(i - 1).getText();
            UnaryExpr expr = (UnaryExpr) visitTekliDenklem(ctx.tekliDenklem(i));
            ops.add(new MultiplicativeExpr.Op(operator, expr));
        }
        return new MultiplicativeExpr(first, ops);
    }

    @Override
    public ASTNode visitÇarpmaBölmeİşareti(TurkishPseudoCodeParser.ÇarpmaBölmeİşaretiContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public UnaryExpr visitTekliDenklem(TurkishPseudoCodeParser.TekliDenklemContext ctx) {
        if (ctx.eksiİşareti() != null) {
            String operator = ctx.eksiİşareti().getText();
            Expression operand = visitTekliDenklem(ctx.tekliDenklem());
            return new UnaryExpr(operator, operand);
        } else {
            Expression expr = (Expression) visitDeğilİfadesi(ctx.değilİfadesi());
            if (expr instanceof UnaryExpr) {
                return (UnaryExpr) expr;
            } else {
                return new UnaryExpr(null, expr);
            }
        }
    }

    @Override
    public ASTNode visitEksiİşareti(TurkishPseudoCodeParser.EksiİşaretiContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Expression visitDeğilİfadesi(TurkishPseudoCodeParser.DeğilİfadesiContext ctx) {
        Expression expr = (Expression) visitDeğer(ctx.değer());
        if (ctx.DEĞİL() != null) {
            return new PostfixExpr(expr, true);
        }
        return expr;
    }

    @Override
    public ASTNode visitDeğişken(TurkishPseudoCodeParser.DeğişkenContext ctx) {
        Primary.Identifier ret = new Primary.Identifier();
        ret.identifier = ctx.getText();
        return ret;
    }

    @Override
    public ASTNode visitSabitDeğer(TurkishPseudoCodeParser.SabitDeğerContext ctx) {
        if (ctx.SAYI() != null) {
            Primary.Number ret = new Primary.Number();
            String text = ctx.SAYI().getText();
            if (isFloat(text))
                ret.number = Float.parseFloat(text);
            else
                ret.number = Integer.parseInt(text);
            return ret;
        } else if (ctx.YAZI() != null) {
            Primary.Str ret = new Primary.Str();
            ret.string = ctx.YAZI().getText();
            return ret;
        }
        throw new RuntimeException("Unknown sabitDeğer: " + ctx.getText());
    }

    @Override
    public ASTNode visitDeğer(TurkishPseudoCodeParser.DeğerContext ctx) {
        if (ctx.sabitDeğer() != null) {
            return visitSabitDeğer(ctx.sabitDeğer());
        } else if (ctx.değişken() != null) {
            return visitDeğişken(ctx.değişken());
        } else if (ctx.denklem() != null) {
            return visitDenklem(ctx.denklem());
        } else if (ctx.denklemİfadesi() != null) {
            return visitDenklemİfadesi(ctx.denklemİfadesi());
        }
        throw new RuntimeException("Unknown değer type: " + ctx.getText());
    }

    public static boolean isFloat(String str) {
        return str.matches("[-+]?[0-9]*\\.[0-9]+([eE][-+]?[0-9]+)?[fF]?");
    }
}
