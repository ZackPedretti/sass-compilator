import java.util.Arrays;

/**
 * Classe modélisant un mixin ayant une liste... en attribut
 */
public class MixinArgList extends Mixin{
    public MixinArgList(String[] rules, String[] args) {
        super(rules, args);
    }

    /**
     * Méthode récupérant le code compilé du ruleset du mixin.
     * @param callArgs arguments
     * @return chaîne compilée
     */
    @Override
    public String convertToCss(String[] callArgs) {
        pendingCss = Arrays.stream(rules).filter(s -> s.contains("&:")).toArray(String[]::new);

        String listArg = Arrays.stream(callArgs).reduce("", (acc, arg) -> acc + ", " + arg).substring(2);

        pendingCss = Arrays.stream(pendingCss)
                .map(s -> s.replace(args[0], listArg))
                .toArray(String[]::new);

        return Arrays.stream(rules)
                .map(s -> s.replace(args[0], listArg))
                .reduce("", (a, s) -> a + "\t" + s);
    }
}
