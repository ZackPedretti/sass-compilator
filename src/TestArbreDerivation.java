import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestArbreDerivation {
    public static void main(String[] args) {
        final String scssDir = "scssFiles";

        File folder = new File(scssDir);
        File[] files = folder.listFiles();

        assert files != null; // IntelliJ
        for (File file : files) {
            System.out.println("Fichier: " + file.getName());
            testScssFile(file.getPath());
        }
    }

    public static void testScssFile(String file){
        try{
            ScssLexer lexer = new ScssLexer(CharStreams.fromFileName(file));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ScssParser parser = new ScssParser(tokens);

            // Arbre de dérivation
            ParseTree tree = parser.stylesheet();

            if (parser.getNumberOfSyntaxErrors() == 0) {
                System.out.println("Aucune erreur");
            } else {
                System.out.println("Erreur dans le fichier " + file);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
