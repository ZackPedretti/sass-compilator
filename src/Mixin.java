import java.util.Arrays;
import java.util.HashMap;

/**
 * Classe modélisant un mixin.
 * Elle permet de stocker un ruleset, les arguments du Mixin (s'il en a) ainsi que des valeurs par défaut pour ces attributs.
 */
public class Mixin {

    String[] rules;
    String[] args;
    HashMap<String, String> defaultValues;
    String[] pendingCss;

    /**
     * Constructeur sans valeurs par défaut.
     * @param rules ruleset
     * @param args attributs du mixin
     */
    public Mixin(String[] rules, String[] args){
        this.rules = rules;
        this.args = args;
        this.defaultValues = new HashMap<>();
    }

    /**
     * Constructeurs avec valeurs par défaut.
     * @param rules ruleset
     * @param args attributs du mixin
     * @param defaultValues valeurs par défaut
     */
    public Mixin(String[] rules, String[] args, HashMap<String, String> defaultValues){
        this.rules = rules;
        this.args = args;
        this.defaultValues = defaultValues;
    }

    /**
     * Méthode récupérant le code compilé du ruleset du mixin.
     * @param callArgs arguments
     * @return chaîne compilée
     */
    public String convertToCss(String[] callArgs){

        if(callArgs.length < args.length - defaultValues.size()) throw new RuntimeException("Il n'y a pas assez d'arguments pour la liste d'arguments");
        String[] callRules = Arrays.stream(rules).filter(s -> !s.contains("&:")).toArray(String[]::new);
        pendingCss = Arrays.stream(rules).filter(s -> s.contains("&:")).toArray(String[]::new);
        for(int i = 0; i < args.length; i++){
            String argName = args[i];
            String value;
            if(i < callArgs.length) value = callArgs[i];
            else if(defaultValues.containsKey(argName)) value = defaultValues.get(argName);
            else throw new RuntimeException("Aucun argument pour " + argName);

            callRules = Arrays.stream(callRules)
                    .map(s -> s.replace(argName, value))
                    .toArray(String[]::new);

            pendingCss = Arrays.stream(pendingCss)
                    .map(s -> s.replace(argName, value))
                    .toArray(String[]::new);
        }
        return Arrays.stream(callRules).reduce("", (a, s) -> a + "\t" + s);
    }

    /**
     * Méthode récupérant le code compilé du ruleset du mixin sans argument.
     * @return chaîne compilée
     */
    public String convertToCss(){
        if(defaultValues != null && args.length != defaultValues.size()) throw new RuntimeException("Il n'y a pas assez de valeurs par défaut pour les attributs");
        return convertToCss(new String[] {});
    }

    /**
     * Méthode permettant de récupérer le code du mixin qui doit être écrit en dehors de la close.
     * @return chaîne compilée
     */
    public String[] getPendingCss(){
        return pendingCss;
    }
}
