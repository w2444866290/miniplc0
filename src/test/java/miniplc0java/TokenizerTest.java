package miniplc0java;

import miniplc0java.tokenizer.StringIter;
import miniplc0java.tokenizer.Token;
import miniplc0java.tokenizer.Tokenizer;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

public class TokenizerTest {
    public String RunTokenizer(StringIter it) {
        var tokenizer = new Tokenizer(it);
        var outString = new String();

        outString = tokenizer.Run();

        return outString;
    }

    @Test
    public void TestlexUInt() {
        Scanner scanner;
        String input = new String("1 0 -1 1-1 0010");
        scanner = new Scanner(input);
        var iter = new StringIter(scanner);

        var outString = RunTokenizer(iter);

        assertEquals("UnsignedInteger1/UnsignedInteger0/MinusSign/UnsignedInteger1/UnsignedInteger1/MinusSign/UnsignedInteger1/UnsignedInteger10//", outString);
    }

    @Test
    public void TestlexIdentOrKeyword() {
        Scanner scanner;
        String input = new String("\t\nbegin end var const print abc 123abc ident");
        scanner = new Scanner(input);
        var iter = new StringIter(scanner);

        var outString = RunTokenizer(iter);

        assertEquals("Begin/End/Var/Const/Print/Identifierabc/UnsignedInteger123/Identifierabc/Identifierident//", outString);
    }

    @Test
    public void TestlexOperatorOrUnknown() {
        Scanner scanner;
        String input = new String("+-*/=();");
        scanner = new Scanner(input);
        var iter = new StringIter(scanner);

        var outString = RunTokenizer(iter);

        assertEquals("PlusSign/MinusSign/MultiplicationSign/DivisionSign/EqualSign/LeftBracket/RightBracket/Semicolon//", outString);
    }
}
