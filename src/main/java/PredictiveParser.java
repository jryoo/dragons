import java.util.ArrayList;

/**
 * Created by jryoo on 7/5/15.
 */
public class PredictiveParser {

    public static final String TOKEN_EXPR = "expr";
    public static final String TOKEN_IF = "if";
    public static final String TOKEN_FOR = "for";
    public static final String TOKEN_OTHER = "other";

    String lookahead;
    int currentIndex = 0;
    String[] tokens;
    String statement;

    public PredictiveParser(String statement) {
        this.statement = statement;
        this.tokens = statement.split(" ");
        this.lookahead = tokens[currentIndex];
    }

    public void stmt() {
        switch (lookahead) {
            case TOKEN_EXPR:
                match(TOKEN_EXPR);
                match(";");
                break;
            case TOKEN_IF:
                match(TOKEN_IF);
                match("(");
                match(TOKEN_EXPR);
                match(")");
                stmt();
                break;
            case TOKEN_FOR:
                match(TOKEN_FOR);
                match("(");
                optexpr();
                match(";");
                optexpr();
                match(";");
                optexpr();
                match(")");
                stmt();
                break;
            case TOKEN_OTHER:
                match(TOKEN_OTHER);
                break;
            default:
                throw new RuntimeException("syntax error");
        }
    }

    public void optexpr() {
        if(lookahead.equals(TOKEN_EXPR))
            match(TOKEN_EXPR);
    }

    public void match(String t) {
        if (lookahead.equals(t)) {
            currentIndex++;
            if (currentIndex < tokens.length) {
                lookahead = tokens[currentIndex];
            } else {
                System.out.println("DONE");

            }
        } else {
            throw new RuntimeException("syntax error token: " + t);
        }
    }

    public static void main(String[] args) {
        PredictiveParser parser = new PredictiveParser("for ( ; expr ; expr ) other");
        parser.stmt();
    }
}
