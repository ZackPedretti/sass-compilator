import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.FileWriter;
import java.io.IOException;

public class GenerateCss {
    public static void main(String[] args) throws IOException {
        String inputPath = "scssFiles/mixin.scss";
        String outputPath = "cssFiles/mixin.css";

        ScssLexer lexer = new ScssLexer(CharStreams.fromFileName(inputPath));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ScssParser parser = new ScssParser(tokens);

        String content = new ScssToCssGenerator().visit(parser.stylesheet());

        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(content);
        } catch (IOException e) {
            System.out.println("Une erreur est survenue lors de l'écriture dans le fichier : " + e.getMessage());
        }
    }
}
