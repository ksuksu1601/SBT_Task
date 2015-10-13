import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runner.RunWith;
import ru.yandex.qatools.allure.annotations.Parameter;
import ru.yandex.qatools.allure.annotations.Title;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

enum paramIndexes
{
    op1,
    op2,
    operator,
    result
}

class Constants
{
    public final static String BAD_LINE_FORMAT = "Incorrect input string";
    public final static String BAD_MEMBER_TYPE = "Wrong type of input data";
}

@Title("Arithmetic operations test suite")
@RunWith(Parameterized.class)
public class MathOperationTests {

    @Parameter
    private int op1;
    @Parameter
    private int op2;
    @Parameter
    private char operator;
    @Parameter
    private int result;

    public MathOperationTests(String testLine) {
        if (!testLine.matches("^-?\\d+;-?\\d+;[+-/*];-?\\d+$"))
            fail(Constants.BAD_LINE_FORMAT + ": " + testLine);
        String[] params = testLine.split(";");
        try {
            op1 = Integer.valueOf(params[paramIndexes.op1.ordinal()]);
            op2 = Integer.valueOf(params[paramIndexes.op2.ordinal()]);
            operator = params[paramIndexes.operator.ordinal()].charAt(0);
            result = Integer.valueOf(params[paramIndexes.result.ordinal()]);
        }
        catch (NumberFormatException e){
            fail(Constants.BAD_MEMBER_TYPE + ": " + e.getMessage());
        }
    }

    @Parameterized.Parameters
    public static List<Object[]> data() throws FileNotFoundException{
        List<Object[]> testLines = new ArrayList<Object[]>();
        Scanner scan = new Scanner(new File("mathtest.txt"));
        while(scan.hasNextLine()){
            String line = scan.nextLine();
            testLines.add(new Object[]{line});
        }
        return testLines;
    }

    @Title("Operation result check")
    @Test
    public void operationTest() {
        switch (operator){
            case '+':
                assertEquals(op1 + op2, result);
                break;
            case '-':
                assertEquals(op1 - op2, result);
                break;
            case '*':
                assertEquals(op1 * op2, result);
                break;
            case '/':
                assertEquals(op1 / op2, result);
                break;
            default:
                fail(Constants.BAD_LINE_FORMAT);
        }
    }
}