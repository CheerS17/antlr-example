package cwru.compilers;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

public class Calculator {

    public static class CalculatorVisitor
      extends AbstractParseTreeVisitor<Node>
      implements CALCVisitor<Node> {

        @Override
        public Node visitExpr(CALCParser.ExprContext context) {
            Node term = visit(context.term());
            if (context.expr() != null) {
                return (new Node<String>(context.op.getText()))
                          .addkid(visit(context.expr()))
                          .addkid(term);
            }
            return term;
        }

        @Override
        public Node visitTerm(CALCParser.TermContext context) {
            Node factor = visit(context.factor());
            if (context.term() != null) {
                return (new Node<String>(context.op.getText()))
                          .addkid(visit(context.term()))
                          .addkid(factor);
            }
            return factor;
        }

        @Override
        public Node visitFactor(CALCParser.FactorContext context) {
            if (context.expr() != null) {
                return visit(context.expr());
            } else {
                return new Node<Integer>(Integer.valueOf(context.NUMBER().getText()));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Expects exactly one expression");
            System.exit(1);
        }

        ANTLRInputStream input = new ANTLRInputStream(args[0]);
        CALCLexer lexer = new CALCLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CALCParser parser = new CALCParser(tokens);
        ParseTree tree = parser.expr();
        CalculatorVisitor visitor = new CalculatorVisitor();

        Node ast = visitor.visit(tree);
        try {
            System.out.println(Eval.evaluate(ast));
        } catch (Eval.EvalError e) {
            System.err.println(e);
            System.exit(2);
        }
    }
}
