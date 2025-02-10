import org.antlr.v4.runtime.RuleContext;

import java.util.*;
import java.util.stream.Collectors;

public class ScssToCssGenerator extends ScssParserBaseVisitor<String> {

    //region Règles de base

    private static final String ENTIER = "entier";
    private static final String REEL = "réel";
    private static final String PIXEL = "pixel";
    private static final String HEX = "hexadécimal";
    private static final String POURCENTAGE = "pourcentage";
    private static final String BOOLEAN = "boolean";
    // HashMap qui contiendra les variables et leurs valeurs pour les inscrire dans le root
    private HashMap<String, Variable> variableMap;
    // Liste qui stocke les variables qui ne sont pas globales (dans un scope)
    private HashMap<String, Mixin> mixinMap;
    // ArrayList qui contiendra le CSS en attente d'écriture (jusqu'à ce que la pile soit vide)
    private ArrayList<String> pendingCss;
    // Pile de déclaration
    private Stack<String> declarationStack;
    private boolean writingMixin;
    private boolean inScope;

    /**
     * Méthode permettant de savoir si une valeur est booléenne.
     *
     * @param value valeur
     * @return true si c'est une valeur booléenne, false sinon.
     */
    private static boolean isBoolean(String value) {
        return Objects.equals(value, "true") || Objects.equals(value, "false");
    }

    /**
     * Méthode qui vérifie si une opération est valide :
     * - On ne peut pas faire d'opération entre des booléens et des valeurs arithmétiques
     * - On ne peut additionner, soustraire, multiplier, diviser et comparer la grandeur des valeurs booléennes
     * - On ne peut pas utiliser de connecteurs logiques sur des valeurs arithmétiques
     *
     * @param val1     première valeur de l'opération
     * @param val2     deuxième valeur de l'opération
     * @param operator opérateur de l'opération
     */
    private static void checkOperation(String val1, String val2, String operator) {

        if ((isBoolean(val1) && !isBoolean(val2)) || (!isBoolean(val1) && isBoolean(val2)))
            throw new RuntimeException("Des valeurs booléennes et des valeurs arithmétiques ne peuvent pas être mélangées");

        switch (operator) {
            case "+", "-", "*", "/", "<", ">", "=<", ">=" -> {
                if (isBoolean(val1))
                    throw new RuntimeException("L'opérateur " + operator + " ne peut pas être utiliser avec des booléens");
            }
            case "and", "or", "not" -> {
                if (!isBoolean(val1))
                    throw new RuntimeException("L'opérateur " + operator + " ne peut pas être utiliser avec des valeurs arithmétiques");
            }
        }
    }

    /**
     * Méthode permettant de calculer une expression arithmétique.
     *
     * @param expressionValues Valeurs de l'expression
     * @param operators        opérateurs
     * @return String du résultat
     */
    private static String calculateExpression(List<String> expressionValues, List<String> operators) {
        String unite = getUnit(expressionValues, operators); // Avoir l'unité de l'opération

        String res = convertValue(calculateWholeExpression(expressionValues, operators));
        if (isBoolean(res)) return res;
        switch (unite) {
            case PIXEL -> {
                return res + "px";
            }
            case HEX -> {
                return "#" + Integer.toHexString(Integer.parseInt(res));
            }
            case POURCENTAGE -> {
                return res + "%";
            }
            default -> {
                return res;
            }
        }
    }

    /**
     * Méthode faisant une seule opération.
     *
     * @param val1     Première valeur de l'opération
     * @param val2     Deuxième valeur de l'opération
     * @param operator Opérateur de l'opération
     * @return Résultat de l'opération.
     */
    private static String calculateExpressionPart(String val1, String val2, String operator) {
        checkOperation(val1, val2, operator);
        val1 = convertValue(val1);
        val2 = convertValue(val2);

        switch (operator) {
            case "*" -> {
                return (Float.parseFloat(val1) * Float.parseFloat(val2)) + "";
            }
            case "/" -> {
                return (Float.parseFloat(val1) / Float.parseFloat(val2)) + "";
            }
            case "+" -> {
                return (Float.parseFloat(val1) + Float.parseFloat(val2)) + "";
            }
            case "-" -> {
                return (Float.parseFloat(val1) - Float.parseFloat(val2)) + "";
            }
            case "not" -> {
                return val1.equals("true") ? "false" : "true";
            }
            case "and" -> {
                return val1.equals("true") && val2.equals("true") ? "true" : "false";
            }
            case "or" -> {
                return val1.equals("true") || val2.equals("true") ? "true" : "false";
            }
            case "==" -> {
                return val1.equals(val2) + "";
            }
            case "=<" -> {
                return (Float.parseFloat(val1) <= Float.parseFloat(val2)) + "";
            }
            case "=>" -> {
                return (Float.parseFloat(val1) >= Float.parseFloat(val2)) + "";
            }
            case "<" -> {
                return (Float.parseFloat(val1) < Float.parseFloat(val2)) + "";
            }
            case ">" -> {
                return (Float.parseFloat(val1) > Float.parseFloat(val2)) + "";
            }
            default -> throw new RuntimeException("L'opérateur " + operator + " n'est pas permis");
        }
    }

    /**
     * Permet de calculer les valeurs avec les opérateurs
     *
     * @param values    valeurs
     * @param operators opérateurs
     * @return résultat
     */
    private static String calculateWholeExpression(List<String> values, List<String> operators) {
        if (values == null || values.isEmpty() || operators == null || !(operators.size() == values.size() - 1 || operators.size() == values.size())) {
            throw new IllegalArgumentException("Les listes de valeurs et d'opérateurs sont invalides.");
        }

        while (operators.contains("(")) {
            int insideParenthesis = 0;
            int closingIndex = -1;
            int i = 0;
            while (closingIndex == -1 && i < operators.size()) {
                if (operators.get(i).equals("(")) insideParenthesis++;
                if (operators.get(i).equals(")")) {
                    if (insideParenthesis == 0) closingIndex = i;
                    else insideParenthesis--;
                }
                i++;
            }

            // S'il n'y a pas de parenthèses fermantes, l'opération est mauvaise
            if (closingIndex == -1) throw new RuntimeException("Parenthèses incorrectes");

            // Indice de la première parenthèse ouvrante
            int openingIndex = operators.indexOf("(");

            // Valeurs utilisées pour l'appel récursif
            List<String> recursiveValues = values.subList(openingIndex, closingIndex);
            List<String> recursiveOperators = operators.subList(openingIndex + 1, closingIndex);

            // Nouvelles valeurs
            List<String> newValues = values.subList(0, openingIndex);
            List<String> newOperators = operators.subList(0, openingIndex);

            // On remplace l'expression entre parenthèses par son résultat calculé par un appel récursif
            newValues.add(convertValue(calculateWholeExpression(recursiveValues, recursiveOperators)));

            newValues.addAll(values.subList(closingIndex + 1, values.size()));
            newOperators.addAll(operators.subList(closingIndex + 1, operators.size()));

            values = newValues;
            operators = newOperators;
        }

        for (String o : new String[]{"*", "/", "+", "-", "and", "or", "==", "<=", ">=", "<", ">"}) {
            while (operators.contains(o)) {
                int i = operators.indexOf(o);
                operators.remove(i);
                String val1 = values.get(i).replace("%", "");
                String val2 = values.get(i + 1).replace("%", "");
                String res = calculateExpressionPart(val1, val2, o);
                values.remove(i);
                values.remove(i);
                values.add(i, res);
            }
        }

        if (!operators.isEmpty())
            throw new RuntimeException("Les opérateurs " + operators + " ne sont pas autorisés en Sass.");

        return values.get(0); // La dernière valeur contient le résultat
    }


    /**
     * Méthode qui permet de déterminer l'unité de l'opération à partir de la liste d'expressions.
     *
     * @param expressionParts Liste des éléments de l'expression.
     * @return L'unité détectée :
     * - ENTIER
     * - REEL
     * - PIXEL
     * - HEX
     * - POURCENTAGE
     */
    private static String getUnit(List<String> expressionParts, List<String> operators) {
        if (expressionParts == null || expressionParts.isEmpty()) {
            throw new IllegalArgumentException("La liste d'expressionParts ne peut pas être vide.");
        }

        String detectedUnit = null;

        for (String o : operators) {
            switch (o) {
                case "and", "not", "or", "==", "<", "<=", ">", ">=" -> detectedUnit = BOOLEAN;
            }
        }

        for (String part : expressionParts) {
            String lowerPart = part.toLowerCase();

            if (lowerPart.equals("true") || lowerPart.equals("false")) detectedUnit = BOOLEAN;

            if (lowerPart.matches("-?\\d+px") || lowerPart.matches("-?\\d+\\.\\d+px")) {
                detectedUnit = PIXEL; // Si l'unité est "px", elle devient l'unité dominante.
            } else if (lowerPart.matches("#[0-9a-fA-F]{3,6}")) {
                detectedUnit = HEX; // Si l'unité est une couleur hexadécimale, elle est prioritaire.
            } else if (lowerPart.matches("-?\\d+%") || lowerPart.matches("-?\\d+\\.\\d+%")) {
                detectedUnit = POURCENTAGE; // Si l'unité est "%", elle est prioritaire.
            } else if (lowerPart.matches("-?\\d+\\.\\d+")) {
                if (detectedUnit == null || detectedUnit.equals(ENTIER)) {
                    detectedUnit = REEL; // Si aucune autre unité n'a été détectée, on définit "réel".
                }
            } else if (lowerPart.matches("-?\\d+")) {
                if (detectedUnit == null) {
                    detectedUnit = ENTIER; // Si aucune autre unité n'a été détectée, on définit "entier".
                }
            }
        }

        if (detectedUnit == null) {
            throw new RuntimeException("Aucune unité valide trouvée dans l'expression.");
        }

        return detectedUnit;
    }


    /**
     * Méthode qui permet de convertir des valeurs SASS en valeurs pures (sans px, % etc...)
     *
     * @param value valeur SASS
     * @return String d'une valeur Java valide
     */
    private static String convertValue(String value) {
        // Conversion en fonction du type demandé
        if (value.matches("-?\\d+\\.\\d+px")) {
            return Float.parseFloat(value.replace("px", "")) + "";
        } else if (value.matches("-?\\d+px")) {
            return Integer.parseInt(value.replace("px", "")) + "";
        } else if (value.matches("-?\\d+")) {
            return Integer.parseInt(value) + "";
        } else if (value.matches("-?\\d+\\.\\d+")) {
            return Float.parseFloat(value) + "";
        } else if (value.matches("-?\\d+%")) {
            return Integer.parseInt(value.replace("%", "")) + "";
        } else {
            return value;
        }
    }


    /**
     * Méthode permettant d'ajouter au code final l'initialisation de variables CSS
     * SCSS peut créer des variables n'importe où dans le code avec ${nom}
     * <p>
     * En CSS, il faut initialiser les variables dans une clause root, préférablement en début de fichier :
     * <p>
     * :root{
     * --variable1: 20px;
     * --variable2: #3498DB;
     * }
     *
     * @param cssCode StringBuilder correspondant au code CSS final sans le root
     * @return le code CSS final avec le root et converti en String
     */
    public String appendRootVariables(StringBuilder cssCode) {
        if (variableMap.isEmpty()) return cssCode.toString(); // Aucune variable

        String variables = variableMap.entrySet().stream()
                .filter(entry -> !entry.getValue().isTemporary() && entry.getValue().getType().equals("var"))
                .map(entry -> "--" + entry.getKey() + ": " + entry.getValue().getValue() + ";")
                .reduce((entry1, entry2) -> entry1 + "\n        " + entry2)
                .orElse("");

        if(variables.isEmpty()) return cssCode.toString();

        return ":root{\n\t" +
                variables
                + "\n}\n\n" + cssCode.toString();
    }


    /**
     * Méthode qui teste si l'on peut écrire le code CSS en attente
     * Si elle peut, elle l'écrit
     * <p>
     * La méthode peut écrire le code si et seulement si le nesting est à 0,
     * C'est-à-dire en dehors de toute déclaration. (pile vide)
     *
     * @return Une chaîne de code CSS si elle peut les écrire, une chaîne vide sinon
     */
    public String writePendingCss() {
        // Si nesting n'est pas égal à 0 la condition n'est pas bonne
        if (pendingCss.isEmpty() || !declarationStack.empty()) return "";

        String code = pendingCss.stream().reduce("", (acc, loop) -> acc + "\n" + loop);

        pendingCss.clear();

        return code;
    }

    @Override
    public String visitStylesheet(ScssParser.StylesheetContext ctx) {

        variableMap = new HashMap<>();

        mixinMap = new HashMap<>();

        pendingCss = new ArrayList<>();

        declarationStack = new Stack<>();

        StringBuilder cssCode = new StringBuilder(); // Objet StringBuilder qui va contenir le code CSS

        writingMixin = false;
        inScope = false;

        for (ScssParser.StatementContext statement : ctx.statement()) {

            String statementStr = visit(statement);
            // S'il n'y a rien, saut de ligne
            cssCode.append(Objects.requireNonNullElse(statementStr, "\n")); // IntelliJ

            // Écrire les boucles en attente
            cssCode.append(writePendingCss());
        }
        if (variableMap.isEmpty()) return cssCode.toString();
        return appendRootVariables(cssCode).trim();
    }

    @Override
    public String visitSelector(ScssParser.SelectorContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitSelectorGroup(ScssParser.SelectorGroupContext ctx) {
        StringBuilder group = new StringBuilder();
        for (ScssParser.SelectorContext selector : ctx.selector()) {
            group.append(visit(selector)).append(", ");
        }
        declarationStack.push(group.substring(0, group.length() - 2)); // Dans une déclaration
        return group.substring(0, group.length() - 2) + "{";
    }

    @Override
    public String visitPropertyDeclaration(ScssParser.PropertyDeclarationContext ctx) {
        String res = visit(ctx.propertyValue());

        // Il faut visiter la valeur pour savoir s'il s'agit de valeur littérale ou de variable
        // Si l'évaluation renvoie null, c'est une valeur littérale. Sinon, c'est une variable
        // Il faut renvoyer le résultat de la visite si c'est une variable, sinon il faut renvoyer le texte (égal à une valeur littérale)

        return "\n\t" + ctx.identifier().getText() + ": " + (res != null ? res : visit(ctx.propertyValue())) + ";";
    }

    @Override
    public String visitPropertyValue(ScssParser.PropertyValueContext ctx) {
        if (ctx.listSpaceSeparated() != null) return visit(ctx.listSpaceSeparated());
        if (ctx.listCommaSeparated() != null) return visit(ctx.listCommaSeparated());
        return visit(ctx.value());
    }

    @Override
    public String visitListCommaSeparated(ScssParser.ListCommaSeparatedContext ctx) {
        return concatenateListElements(ctx.listElement());
    }

    @Override
    public String visitListSpaceSeparated(ScssParser.ListSpaceSeparatedContext ctx) {
        return concatenateListElements(ctx.listElement());
    }

    /**
     * Méthode permettant de concaténer les éléments d'une liste. Concatène les éléments avec des espaces entre chacun.
     *
     * @param elements éléments d'une liste
     * @return String des éléments concaténés.
     */
    private String concatenateListElements(List<ScssParser.ListElementContext> elements) {
        StringBuilder res = new StringBuilder();
        for (ScssParser.ListElementContext val : elements) res.append(visit(val)).append(", ");
        return res.substring(0, res.length() - 2);
    }

    @Override
    public String visitListElement(ScssParser.ListElementContext ctx) {
        StringBuilder res = new StringBuilder();
        for (ScssParser.ValueContext value : ctx.value()) {
            res.append(visit(value)).append(" ");
        }
        return res.toString();
    }

    @Override
    public String visitRuleset(ScssParser.RulesetContext ctx) {

        String res = visit(ctx.selectorGroup()).trim() +
                visit(ctx.block()) +
                "\n}\n";

        declarationStack.pop(); // On sort d'une déclaration

        // Si la déclaration est imbriquée
        if (!declarationStack.empty()) {
            res = "\n" + (declarationStack.stream()
                    .reduce("", (accumulated, current) -> accumulated + " " + current) + " " + res).trim();
            if (!(ctx.parent.parent.parent instanceof ScssParser.ForDeclarationContext)) { // Vérifier si c'est dans un ForDeclaration, À AMÉLIORER
                pendingCss.add(res.replace("&", visit(ctx.selectorGroup()).trim()));
                return "";
            }
        }

        return res + "\n";
    }

    @Override
    public String visitBlock(ScssParser.BlockContext ctx) {

        // StringBuilder qui va contenir les règles
        StringBuilder block = new StringBuilder();

        // Il faut visiter chaque enfant
        for (ScssParser.StatementContext statement : ctx.statement()) {
            String result = visit(statement);

            // Si la visite ne renvoie pas null, on ajoute la chaîne aux règles
            if (result != null) block.append("\t").append(result);
        }
        return block.toString();
    }

    @Override
    public String visitValue(ScssParser.ValueContext ctx) {
        String valueText = ctx.getText();

        if (ctx.calc() != null) return visit(ctx.calc());

        // Si la valeur est une variable
        if (ctx.variableName() != null) return visit(ctx.variableName());

        // Vérifier si la valeur contient un calcul
        if (ctx.expression() != null) return visit(ctx.expression());

        if (ctx.functionCall() != null) return visit(ctx.functionCall());

        return valueText;
    }

    // Il faut mettre la variable dans le HashMap pour l'initialiser à la fin de la lecture du code
    @Override
    public String visitVariableDeclaration(ScssParser.VariableDeclarationContext ctx) {

        boolean isTemp = inScope || !declarationStack.isEmpty();

        String name = visit(ctx.variableName());

        Variable variable;

        if (ctx.variableValue().listDeclaration(0) != null) {
            Variable[] vals = Arrays.stream(visit(ctx.variableValue().listDeclaration(0)).split(",")).map(s -> new SimpleVariable("null", s, true)).toArray(Variable[]::new);
            for (int i = 0; i < vals.length; i++) {
                if (variableMap.containsKey(vals[i].getValue()))
                    vals[i] = new SimpleVariable("null", variableMap.get(i).getValue(), false);
            }
            variable = new ListVariable(vals, isTemp);
        } else if (ctx.variableValue().mapDeclaration(0) != null) {
            HashMap<String, Variable> vals = Arrays.stream(visit(ctx.variableValue().mapDeclaration(0)).split(","))
                    .map(s -> s.split(":"))
                    .collect(Collectors.toMap(
                            arr -> arr[0].trim(),
                            arr -> new SimpleVariable("null", arr.length > 1 ? arr[1].trim() : "", true), // Valeur : Variable avec la partie après ':' ou vide si absente
                            (v1, v2) -> v1, // En cas de doublons, on garde la première occurrence
                            HashMap::new // Collecte en HashMap
                    ));
            for (String v : vals.keySet()) {
                if (variableMap.containsKey(v)) vals.put(v, variableMap.get(v));
            }

            variable = new MapVariable(vals, isTemp);
        } else {
            String value = visit(ctx.variableValue());

            if (variableMap.containsKey(value)) value = variableMap.get(value).getValue();

            variable = new SimpleVariable(name, value, isTemp);
        }


        // Vérification : si une variable est assignée à une déclaration de variable, la variable doit prendre sa valeur.
        /*
        Exemple :
        $variable1 = 20px;
        $variable2 = $variable1;

        Sera transformé en:
        --variable1: 20px;
        --variable2: 20px;
         */

        variableMap.put(name, variable);

        return ""; // Pas null, sinon il y aura un saut de ligne
    }

    @Override
    public String visitMapDeclaration(ScssParser.MapDeclarationContext ctx) {
        return visit(ctx.mapEntries());
    }

    @Override
    public String visitMapEntries(ScssParser.MapEntriesContext ctx) {
        return ctx.mapEntry().stream().map(this::visit).collect(Collectors.joining(","));
    }

    @Override
    public String visitMapEntry(ScssParser.MapEntryContext ctx) {
        return visit(ctx.mapKey()) + ":" + visit(ctx.mapValue());
    }

    @Override
    public String visitMapKey(ScssParser.MapKeyContext ctx) {
        if (ctx.mapDeclaration() != null) return visit(ctx.mapDeclaration());
        if (ctx.listDeclaration() != null) return visit(ctx.listDeclaration());
        if (ctx.value() != null) return visit(ctx.value());
        else return ctx.getText();
    }

    @Override
    public String visitMapValue(ScssParser.MapValueContext ctx) {

        if (ctx.mapDeclaration() != null) return visit(ctx.mapDeclaration());
        if (ctx.listDeclaration() != null) return visit(ctx.listDeclaration());
        if (ctx.value() != null) return visit(ctx.value());
        else return ctx.getText();
    }

    @Override
    public String visitVariableValue(ScssParser.VariableValueContext ctx) {
        if (writingMixin) return ctx.getText();
        if (!ctx.listDeclaration().isEmpty()) return visit(ctx.listDeclaration(0));
        if (!ctx.mapDeclaration().isEmpty()) return visit(ctx.mapDeclaration(0));
        return visit(ctx.value());
    }

    @Override
    public String visitVariableName(ScssParser.VariableNameContext ctx) {
        String name = ctx.getText();

        if (writingMixin) return name;

        if (ctx.parent instanceof ScssParser.VariableDeclarationContext) return name.substring(1);

        if (ctx.parent instanceof ScssParser.ExpressionPartContext || ctx.parent.parent instanceof ScssParser.ParameterContext)
            return variableMap.get(name.substring(1)).getValue();

        if (!variableMap.containsKey(name.substring(1))) return name;

        return variableMap.get(name.substring(1)) instanceof SimpleVariable ? ((SimpleVariable) variableMap.get(name.substring(1))).getVariableCall() : variableMap.get(name.substring(1)).getValue();
    }

    @Override
    public String visitListDeclaration(ScssParser.ListDeclarationContext ctx) {
        return ctx.getText();
    }

    /**
     * Méthode statique permettant de transformer un mixin / include SCSS en règles CSS
     * <p>
     * La méthode doit :
     * - réarranger les règles
     * - remplacer les arguments par les valeurs littérales correspondantes
     *
     * @param identifier nom du mixin
     * @return une chaîne contenant les règles CSS
     */
    public String convertMixinToCSS(String identifier, String[] args) {
        if (!mixinMap.containsKey(identifier)) throw new RuntimeException("Le mixin n'est pas défini");

        return mixinMap.get(identifier).convertToCss(args);
    }

    public String convertMixinToCSS(String identifier) {

        if (!mixinMap.containsKey(identifier)) throw new RuntimeException("Le mixin n'est pas défini");

        return mixinMap.get(identifier).convertToCss();
    }


    //endregion

    //region Conditions

    // Il faut mettre le mixin dans les HashMaps pour l'utiliser dans le code CSS
    @Override
    public String visitMixinDeclaration(ScssParser.MixinDeclarationContext ctx) {
        writingMixin = true;
        String identifier = ctx.identifier().getText();

        // On récupère les règles
        String[] rules = ctx.block().statement().stream()
                .filter(s -> !s.getText().equals("{") && !s.getText().equals("}"))
                .map(this::visit)
                .toArray(String[]::new);

        writingMixin = false;

        // S'il n'y a pas de paramètres, on crée un Mixin sans paramètres ni valeurs par défaut
        if (ctx.parameters() == null || ctx.parameters().isEmpty()) {
            mixinMap.put(identifier, new Mixin(rules, new String[]{}));
            return "";
        }

        String[] args = new String[ctx.parameters().parameter().size()];
        HashMap<String, String> defaultValues = new HashMap<>();

        if (ctx.parameters().parameter().size() == 1 && ctx.parameters().parameter(0).arglist() != null) {
            mixinMap.put(ctx.identifier().getText(), new MixinArgList(rules, new String[]{ctx.parameters().parameter(0).getText().replace(".", "")}));
            return "";
        }

        for (int i = 0; i < args.length; i++) {
            ScssParser.ParameterContext p = ctx.parameters().parameter(i);

            if (p.variableDeclaration() != null) {

                // Nom de l'argument et valeur par défaut
                String name = p.variableDeclaration().variableName().getText();
                String value = visit(p.variableDeclaration().variableValue());

                args[i] = name;
                defaultValues.put(name, value);

            } else {
                // Nom de l'argument
                args[i] = p.getText();
            }
        }

        mixinMap.put(identifier, new Mixin(rules, args, defaultValues));

        return "";
    }

    @Override
    public String visitIncludeDeclaration(ScssParser.IncludeDeclarationContext ctx) {

        if (ctx.identifier() != null) return convertMixinToCSS(ctx.identifier().getText());

        String[] args = ctx.functionCall().parameters().parameter()
                .stream()
                .map(RuleContext::getText)
                .toArray(String[]::new);

        String[] pendingCssMixin = mixinMap.get(ctx.functionCall().identifier().getText()).getPendingCss();

        if (pendingCssMixin != null && pendingCssMixin.length > 0) {
            pendingCss.add(Arrays.stream(pendingCssMixin).reduce("", (acc, s) -> acc + s).replace("&", declarationStack.stream().reduce("", (acc, s) -> acc + s + " ").trim()));
        }

        return convertMixinToCSS(ctx.functionCall().identifier().getText(), args);
    }

    @Override
    public String visitForDeclaration(ScssParser.ForDeclarationContext ctx) {

        StringBuilder loop = new StringBuilder();
        inScope = true;

        int limit = Integer.parseInt(visit(ctx.through()));
        if (ctx.To() != null) limit--;

        for (int i = Integer.parseInt(ctx.Number().getText()); i <= limit; i++) {
            String varName = ctx.variableName().getText().substring(1);
            variableMap.put(varName, new SimpleVariable(varName, i + "", true));
            loop.append(visit(ctx.block()).replace("#{" + ctx.variableName().getText() + "}", i + "").trim()).append("\n");
        }

        pendingCss.add(loop.toString());

        variableMap.remove(ctx.variableName().getText().substring(1));
        inScope = false;

        return "";
    }

    @Override
    public String visitThrough(ScssParser.ThroughContext ctx) {
        if (ctx.Number() != null) return ctx.Number().getText();
        if (ctx.expression() != null) return visit(ctx.expression());
        if (ctx.functionCall() != null) return visit(ctx.functionCall());
        return ctx.getText();
    }

    @Override
    public String visitEachDeclaration(ScssParser.EachDeclarationContext ctx) {

        StringBuilder loops = new StringBuilder();

        StringBuilder loop = new StringBuilder();

        inScope = true;

        // On ajoute les déclarations imbriquées
        for (String declaration : declarationStack) {
            loop.append(declaration).append(" ");
        }

        loop.append(visit(ctx.block()));

        if (ctx.variableName(0) != null && ctx.variableName(1) != null) { // 2 variables avec une map
            String varName1 = ctx.variableName(0).getText();
            String varName2 = ctx.variableName(1).getText();
            MapVariable map = (MapVariable) variableMap.get(ctx.eachValueList().listDeclaration().listCommaSeparated().listElement(0).value(0).variableName().getText().substring(1));

            for (String key : map.getKeySet()) {
                String currentLoop = loop.toString().replace("#{" + varName1 + "}", key);
                currentLoop = currentLoop.replace(varName1, key);
                currentLoop = currentLoop.replace("#{" + varName2 + "}", map.getValue(key));
                currentLoop = currentLoop.replace(varName2, map.getValue(key));
                loops.append(currentLoop.trim()).append("\n");
            }

            pendingCss.add(loops.toString());
            return "";
        }

        String varName = ctx.variableName(0).getText();

        String[] values;

        if(variableMap.containsKey(visit(ctx.eachValueList()).substring(1)) && variableMap.get(visit(ctx.eachValueList()).substring(1)) instanceof ListVariable) values = Arrays.stream(((ListVariable) variableMap.get(visit(ctx.eachValueList()).substring(1))).getValues()).map(Variable::getValue).toArray(String[]::new);
        else values = ctx.eachValueList().getText().split(",");

        for (String value : values) {

            String currentLoop = loop.toString().replace("#{" + varName + "}", value);
            currentLoop = currentLoop.replace(varName, value);
            loops.append(currentLoop.trim()).append("\n");
        }

        pendingCss.add(loops.toString());

        inScope = false;

        return "";
    }

    @Override
    public String visitEachValueList(ScssParser.EachValueListContext ctx) {
        if (ctx.mapDeclaration() != null) return visit(ctx.mapDeclaration());
        if (ctx.listDeclaration() != null) return visit(ctx.listDeclaration());
        else return ctx.getText();
    }

    @Override
    public String visitExpression(ScssParser.ExpressionContext ctx) {

        List<String> expressionParts = new ArrayList<>();
        List<String> operators = new ArrayList<>();

        for (ScssParser.ExpressionPartContext ep : ctx.expressionPart()) {
            expressionParts.add(visit(ep));
        }

        for (ScssParser.Operator_Context operator_context : ctx.operator_()) {
            operators.add(operator_context.getText());
        }

        if (ctx.Not().isEmpty()) return calculateExpression(expressionParts, operators);

        return calculateExpression(expressionParts, operators).contains("true") ? "false" : "true";
    }


    @Override
    public String visitExpressionPart(ScssParser.ExpressionPartContext ctx) {
        if (ctx.variableName() != null) return visit(ctx.variableName());
        if (ctx.expression() != null) return visit(ctx.expression());
        if (ctx.calc() != null) {
            return visit(ctx.calc().expression());
        }
        return ctx.getText();
    }

    @Override
    public String visitCalc(ScssParser.CalcContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public String visitIfDeclaration(ScssParser.IfDeclarationContext ctx) {
        if (visit(ctx.expression()).equals("true")) return visit(ctx.block());
        if (ctx.elseIfStatement() != null) {
            for (ScssParser.ElseIfStatementContext elseif : ctx.elseIfStatement()) {
                String res = visit(elseif);
                if (!res.equals("")) return res;
            }
        }
        if (ctx.elseStatement() != null) return visit(ctx.elseStatement());
        return "";
    }

    @Override
    public String visitElseIfStatement(ScssParser.ElseIfStatementContext ctx) {
        if (visit(ctx.expression()).equals("true")) return visit(ctx.block());
        return "";
    }

    @Override
    public String visitElseStatement(ScssParser.ElseStatementContext ctx) {
        return visit(ctx.block());
    }

    @Override
    public String visitIfExpression(ScssParser.IfExpressionContext ctx) {
        return super.visitIfExpression(ctx);
    }

    //endregion

    //region Fonctions


    @Override
    public String visitFunctionCall(ScssParser.FunctionCallContext ctx) {

        switch (ctx.identifier().getText()) {
            case "length": {
                Variable list = variableMap.get(ctx.parameters().getText().substring(1));
                if (!(list instanceof ListVariable)) {
                    throw new RuntimeException("La variable " + list + " n'est pas une liste.");
                }
                return ((ListVariable) list).length() + "";
            }

            case "nth": {
                int index = Integer.parseInt(visit(ctx.parameters().parameter(1).value())) - 1;
                Variable list = variableMap.get(ctx.parameters().parameter(0).getText().substring(1));
                if (!(list instanceof ListVariable))
                    throw new RuntimeException("La variable " + ctx.parameters().getText().substring(1) + " n'est pas une liste.");
                return ((ListVariable) list).getValue(index);
            }

            case "darken": {
                return "color-mix(in srgb, black " + visit(ctx.parameters().parameter(1).value()) + ", " + visit(ctx.parameters().parameter(0).value()) + ") /* color-mix est une fonction présente dans des versions récentes de CSS */";
            }

            case "lighten": {
                return "color-mix(in srgb, white " + visit(ctx.parameters().parameter(1).value()) + ", " + visit(ctx.parameters().parameter(0).value()) + ") /* color-mix est une fonction présente dans des versions récentes de CSS */";
            }

            case "linear-gradient": {

                String args = ctx.parameters().parameter(0).getText().replace(",", ", ").replace("to", "to ");
                return "linear-gradient(" + args + ")";
            }

            default:
                return super.visitFunctionCall(ctx);
        }
    }

    //endregion
}
