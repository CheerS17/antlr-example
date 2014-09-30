package cwru.compilers;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

public class Calculator {

    public static class CalculatorVisitor extends AbstractParseTreeVisitor<Integer> implements CALCVisitor<Integer> {
        @Override
        public Integer visitExpr(CALCParser.ExprContext context) {
            int left = visit(context.factor());
            if (context.expr() != null) {
                int right = visit(context.expr());
                switch (context.op.getText()) {
                    case "+":
                        return left + right;
                    case "-":
                        return left - right;
                    default:
                        throw new IllegalStateException("Missing operand: " + context.op.getText());
                }
            } else {
                return left;
            }
        }

        @Override
        public Integer visitFactor(CALCParser.FactorContext context) {
            int left = visit(context.term());
            if (context.factor() != null) {
                int right = visit(context.factor());
                switch (context.op.getText()) {
                    case "*":
                        return left * right;
                    case "/":
                        return left / right;
                    default:
                        throw new IllegalStateException("Missing operand: " + context.op.getText());
                }
            } else {
                return left;
            }
        }

        @Override
        public Integer visitTerm(CALCParser.TermContext context) {
            if (context.expr() != null) {
                return visit(context.expr());
            } else {
                return Integer.valueOf(context.NUMBER().getText());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Expects exactly one expression");
            return;
        }

        ANTLRInputStream input = new ANTLRInputStream(args[0]);
        CALCLexer lexer = new CALCLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CALCParser parser = new CALCParser(tokens);
        ParseTree tree = parser.expr();
        CalculatorVisitor visitor = new CalculatorVisitor();

        System.out.println(visitor.visit(tree));
    }
}
