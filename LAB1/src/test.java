import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class test {
    public static List<Tree> resTrees = new ArrayList<>(0);
    private static Parser parser = new Parser();

    private static void test(String testString) {
        try (InputStream is = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8))) {
            Tree curTree = parser.parse(is);
            resTrees.add(curTree);
        } catch (IOException e) {
            System.out.println("IOException happened at test: \"" + testString + "\"\n Error: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("ParseException happened at test: \"" + testString + "\"\n Error: " + e.getMessage());
        }
    }

    public static void main(String arg[]) {
        Tree tree;
        String testString;
        //func with empty arglist
        // A -> e
        testString = "function a():integer;";
        test(testString);
        new Visualizer(new Tree(""), "").run(resTrees.get(0), "Test 1");

        //proc with empty arglist
        // A -> e
        testString = "procedure a();";
        test(testString);
        new Visualizer(new Tree(""), "").run(resTrees.get(1), "Test 2");

        //function with two args of the same type
        // both N1 and N2 -> e
        testString = "function a( a , b : asd):ads;";
        test(testString);
        new Visualizer(new Tree(""), "").run(resTrees.get(2), "Test 3");

        //procedure with two args of the same type
        // both N1 and N2 -> e
        testString = "procedure a(a , a : asd);";
        test(testString);
        new Visualizer(new Tree(""), "").run(resTrees.get(3), "Test 4");

        //function full set of args
        // N2 -> ; A1
        testString = "function a (a , a : asd; asd, asd : asd)   : sad;";
        test(testString);
        new Visualizer(new Tree(""), "").run(resTrees.get(4), "Test 5");

        //procedure full set of args
        // N2 -> ; A1
        testString = "procedure a (a , a : asd; asd, asd : asd)  ;";
        test(testString);
        new Visualizer(new Tree(""), "").run(resTrees.get(5), "Test 6");

        //random test no.1
        testString = "function _as123dlk_as123dmlk      (   as, as  :   asd; as12:  as;     as, s:  sd)     :    as;";
        test(testString);
        new Visualizer(new Tree(""), "").run(resTrees.get(6), "Random test no.1");

        //random test no.2
        testString = "procedure          _as1_a123s123dmlk        (   as, as  :   asd;" +
                " as12:  as;   " +
                "  as, s:  sd)          " + "\n" +
                ";";
        test(testString);
        new Visualizer(new Tree(""), "").run(resTrees.get(7), "Random test no.2");
    }
}
