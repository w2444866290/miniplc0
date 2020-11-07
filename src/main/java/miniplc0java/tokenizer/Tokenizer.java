package miniplc0java.tokenizer;

import miniplc0java.error.TokenizeError;
import miniplc0java.error.ErrorCode;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    private StringIter it;

    public Tokenizer(StringIter it) {
        this.it = it;
    }

    // 这里本来是想实现 Iterator<Token> 的，但是 Iterator 不允许抛异常，于是就这样了
    /**
     * 获取下一个 Token
     * 
     * @return
     * @throws TokenizeError 如果解析有异常则抛出
     */
    public Token nextToken() throws TokenizeError {
        it.readAll();

        // 跳过之前的所有空白字符
        skipSpaceCharacters();

        if (it.isEOF()) {
            return new Token(TokenType.EOF, "", it.currentPos(), it.currentPos());
        }

        char peek = it.peekChar();
        if (Character.isDigit(peek)) {
            return lexUInt();
        } else if (Character.isAlphabetic(peek)) {
            return lexIdentOrKeyword();
        } else {
            return lexOperatorOrUnknown();
        }
    }

    private Token lexUInt() throws TokenizeError {
        char peek = it.peekChar();
        StringBuilder buf = new StringBuilder();

        // 直到查看下一个字符不是数字为止:
        // -- 前进一个字符，并存储这个字符
        while (Character.isDigit(peek)) {
            buf.append(it.nextChar());
            peek = it.peekChar();
        }

        // 解析存储的字符串为无符号整数
        // 解析成功则返回无符号整数类型的token，否则返回编译错误
        int uint = Integer.parseInt(buf.toString());
        if (!Double.isNaN(uint)) {
            return new Token(TokenType.Uint, uint, it.previousPos(), it.currentPos());
        }
        else {
            // todo: is it right?
            throw new TokenizeError(ErrorCode.ConstantNeedValue, it.currentPos());
        }
    }

    private Token lexIdentOrKeyword() throws TokenizeError {
        char peek = it.peekChar();
        StringBuilder buf = new StringBuilder();

        // 直到查看下一个字符不是数字或字母为止:
        // -- 前进一个字符，并存储这个字符
        while (Character.isLetterOrDigit(peek)) {
            buf.append(it.nextChar());
            peek = it.peekChar();
        }

        // 尝试将存储的字符串解释为关键字
        // -- 如果是关键字，则返回关键字类型的 token
        // -- 否则，返回标识符
        String token = buf.toString();
        switch (token) {
            case "begin":
                return  new Token(TokenType.Begin, "begin", it.previousPos(), it.currentPos());

            case "end":
                return  new Token(TokenType.End, "end", it.previousPos(), it.currentPos());

            case "var":
                return  new Token(TokenType.Var, "var", it.previousPos(), it.currentPos());

            case "const":
                return  new Token(TokenType.Const, "const", it.previousPos(), it.currentPos());

            case "print":
                return  new Token(TokenType.Print, "print", it.previousPos(), it.currentPos());

            default:
                return new Token(TokenType.Ident, token, it.previousPos(), it.currentPos());
        }
    }

    private Token lexOperatorOrUnknown() throws TokenizeError {
        switch (it.nextChar()) {
            case '+':
                return new Token(TokenType.Plus, '+', it.previousPos(), it.currentPos());

            case '-':
                return new Token(TokenType.Minus, '-', it.previousPos(), it.currentPos());

            case '*':
                return new Token(TokenType.Mult, '*', it.previousPos(), it.currentPos());

            case '/':
                return new Token(TokenType.Div, '/', it.previousPos(), it.currentPos());

            case '=':
                return new Token(TokenType.Equal, '=', it.previousPos(), it.currentPos());

            case ';':
                return new Token(TokenType.Semicolon, ';', it.previousPos(), it.currentPos());

            case '(':
                return new Token(TokenType.LParen, '(', it.previousPos(), it.currentPos());

            case ')':
                return new Token(TokenType.RParen, ')', it.previousPos(), it.currentPos());

            default:
                // 不认识这个输入，摸了
                throw new TokenizeError(ErrorCode.InvalidInput, it.previousPos());
        }
    }

    private void skipSpaceCharacters() {
        while (!it.isEOF() && Character.isWhitespace(it.peekChar())) {
            it.nextChar();
        }
    }

    public String Run() {
        var tokens = new ArrayList<Token>();
        var tokenbuilder = new StringBuilder();
        try {
            while (true) {
                var token = this.nextToken();
                if (token.getTokenType().equals(TokenType.EOF)) {
                    tokenbuilder.append('/');
                    break;
                }
                tokens.add(token);
                tokenbuilder.append(token.getTokenType().toString());
                if (token.getTokenType() == TokenType.Ident || token.getTokenType() == TokenType.Uint) {
                    tokenbuilder.append(token.getValue().toString());
                }
                tokenbuilder.append('/');
            }
            return tokenbuilder.toString();
        } catch (Exception e) {
            // 遇到错误不输出，直接退出
            System.err.println(e);
            System.exit(0);
            return null;
        }
    }
}
