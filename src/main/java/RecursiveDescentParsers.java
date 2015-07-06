import java.util.Iterator;

/**
 * Created by jryoo on 7/5/15.
 */
public class RecursiveDescentParsers {
    public static class ParserOne {
        // S --> + S S | - S S | a
        public static final String TOKEN_EXPRESSION = "S";
        public static final String TOKEN_PLUS = "+";
        public static final String TOKEN_MINUS = "-";
        public static final String TOKEN_A = "a";

        String lookahead;
        Iterator<String> tokens;
        String statement;

        public ParserOne(final String statement) {
            this.statement = statement;
            tokens = new Iterator<String>() {
                String[] tokens = statement.split(" ");
                int nextIndex = 0;
                @Override
                public boolean hasNext() {
                    return nextIndex < tokens.length;
                }

                @Override
                public String next() {
                    if (hasNext()) {
                        return tokens[nextIndex++];
                    } else {
                        return null;
                    }
                }
            };
            lookahead = tokens.next();
        }

        public void stmt() {
            System.out.println("lookahead = " + lookahead);
            switch(lookahead) {
                case TOKEN_PLUS:
                    match(TOKEN_PLUS);
                    stmt();
                    stmt();
                    break;
                case TOKEN_MINUS:
                    match(TOKEN_MINUS);
                    stmt();
                    stmt();
                    break;
                case TOKEN_A:
                    match(TOKEN_A);
                    break;
                default:
                    throw new RuntimeException("syntax error token: " + lookahead);
            }
        }

        public void match(String t) {
            if (lookahead.equals(t)) {
                lookahead = tokens.next();
                if (lookahead == null) {
                    System.out.println("Apparently Finished");
                    System.exit(0);
                }
            } else {
                throw new RuntimeException("syntax error token: " + t);
            }
        }

    }

    public static class ParserTwo {
        // S --> S ( S ) S | e
    }

    public static class ParserThree {
        // S --> 0 S 1 | 0 1
    }

    public static void main(String[] args) {
        ParserOne parserOne = new ParserOne("a + + a a a");
        parserOne.stmt();
    }
}
