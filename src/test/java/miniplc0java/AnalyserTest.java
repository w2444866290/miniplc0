package miniplc0java;

import miniplc0java.analyser.Analyser;
import miniplc0java.error.CompileError;
import miniplc0java.instruction.Instruction;
import miniplc0java.instruction.Operation;
import miniplc0java.tokenizer.StringIter;
import miniplc0java.tokenizer.Tokenizer;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class AnalyserTest {
    public boolean RunAnalyser(String filepath) throws CompileError {
        Scanner scanner;
        try {
            FileInputStream input = new FileInputStream(filepath);
            scanner = new Scanner(input);
            var iter = new StringIter(scanner);
            var tokenizer = new Tokenizer(iter);
            var analyser = new Analyser(tokenizer);

            analyser.analyse();

            return true;
        }
        catch (Exception e) {
            System.err.println(e);
            System.exit(0);
            return false;
        }
    }

    @Test
    public void Test1() throws CompileError {
        boolean isdone = RunAnalyser("analysertest.txt");
        assertEquals(true, isdone);
    }
}
