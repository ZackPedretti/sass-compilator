import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
public class TestMain {
    public static void main(String[] args) throws IOException {
        String filePath = "scssFiles/mixin.scss";

        ScssLexer lexer = new ScssLexer(CharStreams.fromFileName(filePath));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ScssParser parser = new ScssParser(tokens);

        System.out.println(new ScssToCssGenerator().visit(parser.stylesheet()));
    }
}
