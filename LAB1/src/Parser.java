import java.io.InputStream;
import java.text.ParseException;
import java.util.Arrays;

public class Parser {
    LexicalAnalyzer lex;

    private Tree S() throws ParseException {
        switch (lex.curToken()) {
            case FUNC:
                lex.nextToken();
                if (lex.curToken() != Token.NAME) {
                    throw new ParseException("function name expected at position ", lex.curPos());
                }
                String fName = lex.getLastName();
                lex.nextToken();
                if (lex.curToken() != Token.LPAREN) {
                    throw new ParseException("( expected at position ", lex.curPos());
                }
                lex.nextToken();
                Tree arglist = A();
                if (arglist.children.size() == 0) {
                    arglist.children = Arrays.asList(new Tree("\u03B5"));
                }
                if (lex.curToken() != Token.RPAREN) {
                    throw new ParseException(") expected at position ", lex.curPos());
                }
                lex.nextToken();
                if (lex.curToken() != Token.COLON) {
                    throw new ParseException(": expected at position ", lex.curPos());
                }
                lex.nextToken();
                if (lex.curToken() != Token.NAME) {
                    throw new ParseException("typename expected at position ", lex.curPos());
                }
                String tName = lex.getLastName();
                lex.nextToken();
                if (lex.curToken() != Token.SEMI) {
                    throw new ParseException("; expected at position ", lex.curPos());
                }
                return new Tree("S", new Tree("function"), new Tree(fName), new Tree("("), arglist,
                        new Tree(")"), new Tree(":"), new Tree(tName), new Tree(";"));
            case PROC:
                lex.nextToken();
                if (lex.curToken() != Token.NAME) {
                    throw new ParseException("procedure name expected at position ", lex.curPos());
                }
                String pName = lex.getLastName();
                lex.nextToken();
                if (lex.curToken() != Token.LPAREN) {
                    throw new ParseException("( expected at position ", lex.curPos());
                }
                lex.nextToken();
                arglist = A();
                if (arglist.children.size() == 0) {
                    arglist.children = Arrays.asList(new Tree("\u03B5"));
                }
                if (lex.curToken() != Token.RPAREN) {
                    throw new ParseException(") expected at position ", lex.curPos());
                }
                lex.nextToken();
                if (lex.curToken() != Token.SEMI) {
                    throw new ParseException("; expected at position ", lex.curPos());
                }
                return new Tree("S", new Tree("procedure"), new Tree(pName), new Tree("("), arglist,
                        new Tree(")"), new Tree(";"));
            default:
                throw new AssertionError("unexpected symbol");
        }
    }

    private Tree A() throws ParseException {
        switch (lex.curToken()) {
            case RPAREN:
                return new Tree("A");
            case NAME:
                Tree A1 = A1();
                return new Tree("A", A1);
            default:
                throw new AssertionError("unexpected symbol");
        }
    }

    private Tree A1() throws ParseException {
        switch (lex.curToken()) {
            case NAME:
                String var = lex.getLastName();
                lex.nextToken();
                Tree N1 = N1();
                return new Tree("A1", new Tree(var), N1);
            default:
                throw new AssertionError("unexpected symbol");
        }
    }

    private Tree N1() throws ParseException {
        switch (lex.curToken()) {
            case COMMA:
                lex.nextToken();
                if (lex.curToken() != Token.NAME) {
                    throw new ParseException("variable name expected at position ", lex.curPos());
                }
                String var = lex.getLastName();
                lex.nextToken();
                Tree N1 = N1();
                return new Tree("N1", new Tree(","), new Tree(var), N1);
            case COLON:
                lex.nextToken();
                if (lex.curToken() != Token.NAME) {
                    throw new ParseException("variable name expected at position ", lex.curPos());
                }
                var = lex.getLastName();
                lex.nextToken();
                Tree N2 = N2();
                if (N2.children.size() == 0) {
                    N2.children = Arrays.asList(new Tree("\u03B5"));
                }
                return new Tree("N1", new Tree(":"), new Tree(var), N2);
            default:
                throw new AssertionError("unexpected symbol");
        }
    }

    private Tree N2() throws ParseException {
        switch (lex.curToken()) {
            case SEMI:
                lex.nextToken();
                Tree A1 = A1();
                return new Tree("N2", new Tree(";"), A1);
            case RPAREN:
                return new Tree("N2");
            default:
                throw new AssertionError("unexpected symbol");
        }
    }

    Tree parse(InputStream is) throws ParseException {
        lex = new LexicalAnalyzer(is);
        lex.nextToken();
        return S();
    }
}
