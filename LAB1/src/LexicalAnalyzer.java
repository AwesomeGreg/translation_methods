import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

public class LexicalAnalyzer {
    private InputStream is;
    private int curChar;
    private int curPos;
    private Token curToken;
    private String curName;

    public LexicalAnalyzer(InputStream is) throws ParseException {
        this.is = is;
        curPos = 0;
        nextChar();
    }

    private boolean isBlank(int c) {
        return c == ' ' || c == '\r' || c == '\n' || c == '\t';
    }

    private void nextChar() throws ParseException {
        curPos++;
        try {
            curChar = is.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), curPos);
        }
    }

    public void nextToken() throws ParseException {
        while (isBlank(curChar)) {
            nextChar();
        }
        switch (curChar) {
            case '(':
                nextChar();
                curToken = Token.LPAREN;
                break;
            case ')':
                nextChar();
                curToken = Token.RPAREN;
                break;
            case ',':
                nextChar();
                curToken = Token.COMMA;
                break;
            case ':':
                nextChar();
                curToken = Token.COLON;
                break;
            case ';':
                nextChar();
                curToken = Token.SEMI;
                break;
            case -1:
                curToken = Token.END;
                break;
            default:
                String key = "";

                if (Character.isLetter(curChar) || curChar == '_') {
                    while (!isBlank(curChar)) {
                        if (Character.isLetter(curChar) || Character.isDigit(curChar) || curChar == '_') {
                            key += (char) curChar;
                            nextChar();
                        } else {
                            break;
                        }
                    }
                    if (key.equals("function"))
                        curToken = Token.FUNC;
                    else if (key.equals("procedure"))
                        curToken = Token.PROC;
                    else {
                        curToken = Token.NAME;
                        this.curName = key;
                    }
                } else {
                    throw new ParseException("Illigal character " + (char) curChar, curPos);
                }
                break;
        }
    }

    public String getLastName() {
        return curName;
    }

    public Token curToken() {
        return curToken;
    }

    public int curPos() {
        return curPos;
    }
}
