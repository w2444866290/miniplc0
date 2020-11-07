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
    public List<Instruction> RunAnalyser(String filepath) throws CompileError {
        Scanner scanner;
        try {
            FileInputStream input = new FileInputStream(filepath);
            scanner = new Scanner(input);
        }
        catch (Exception e) {
            System.err.println(e);
            System.exit(0);
            return null;
        }
        var iter = new StringIter(scanner);
        var tokenizer = new Tokenizer(iter);
        var analyser = new Analyser(tokenizer);

        var outArray = analyser.analyse();

        return outArray;
    }

    @Test
    public void Test1() throws CompileError {
        var instructions = new ArrayList<Instruction>();
        instructions.add(new Instruction(Operation.LIT, 1));
        instructions.add(new Instruction(Operation.LOD, 0));
        instructions.add(new Instruction(Operation.WRT));

        var outArray = RunAnalyser("test1.txt");

        assertEquals(instructions, outArray);
    }

    @Test
    public void Test2() throws CompileError {
        var instructions = new ArrayList<Instruction>();
        instructions.add(new Instruction(Operation.LIT, 1));
        instructions.add(new Instruction(Operation.WRT));

        var outArray = RunAnalyser("test1.txt");

        assertEquals(instructions, outArray);
    }

    @Test
    public void Test3() throws CompileError {
        var instructions = new ArrayList<Instruction>();
        instructions.add(new Instruction(Operation.LIT, 1));
        instructions.add(new Instruction(Operation.WRT));

        var outArray = RunAnalyser("test1.txt");

        assertEquals(instructions, outArray);
    }
}
