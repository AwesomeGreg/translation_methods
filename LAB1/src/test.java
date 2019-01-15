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
    private static String testString;

    private static void test() {
        try (InputStream is = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8))) {
            Tree curTree = parser.parse(is);
            resTrees.add(curTree);
            System.out.println("[" + testString + "]\nTest PASSED");
            new Visualizer(new Tree(""), "").run(resTrees.get(resTrees.size() - 1), "Test no." +
                    Integer.toString(resTrees.size() - 1));
        } catch (IOException e) {
            System.out.println("IOException happened at test: \"" + testString + "\"\n Error: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("ParseException happened at test: \"" + testString + "\"\n Error: " + e.getMessage());
        } catch (AssertionError e) {
            System.out.println("Assertion error at test: \"" + testString + "\"\n Error: " + e.getMessage());
        }
    }

    public static void main(String arg[]) {
        Tree tree;
        //func with empty arglist
        // A -> e
        testString = "function a():integer;";
        test();

        //proc with empty arglist
        // A -> e
        testString = "procedure a();";
        test();

        //function with two args of the same type
        // both N1 and N2 -> e
        testString = "function a( a , b : asd):ads;";
        test();

        //procedure with two args of the same type
        // both N1 and N2 -> e
        testString = "procedure a(a , a : asd);";
        test();

        //function full set of args
        // N2 -> ; A1
        testString = "function a (a , a : asd; asd, asd : asd)   : sad;";
        test();

        //procedure full set of args
        // N2 -> ; A1
        testString = "procedure a (a , a : asd; asd, asd : asd)  ;";
        test();

        //random test no.1
        testString = "function _as123dlk_as123dmlk      (   as, as  :   asd; as12:  as;     as, s:  sd)     :    as;";
        test();

        //random test no.2
        testString = "procedure          _as1_a123s123dmlk        (   as, as  :   asd;" +
                " as12:  as;   " +
                "  as, s:  sd)          " + "\n" +
                ";";
        test();

        //simple D + var test
        testString = "function a(a,b:int; var s:int):int;";
        test();

        //big test for new D rule + var
        testString = "function    \t _asd12_sd \n (  asd, asd   : sad; \n var aweq: asd; asd: asd;  \t var asd, ad,  " +
                "das:asdasd  ) : asdsad \t\n\t ;";
        test();

        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Assertion Tests ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

        //lex test
        testString = "_";
        test();

        //parse first word test
        testString = "lol";
        test();

        //parse NAME test
        testString = "function 1";
        test();

        //parser ( test
        testString = "function asd)";
        test();

        //parser var name test
        testString = "function asd(:)";
        test();

        //parser var type name test
        testString = "function asd(ads:;)";
        test();

        //parser SEMI test
        testString = "function asd(asd:asd asd:asd)";
        test();

        //parser VAR + semi colon test
        testString = "function asd(asd:asd; var asd:asd;)";
        test();
    }
}
