// Generated from C:/Users/Zack/Documents/Cours/Compilation/projet-ZackPedretti/src/ScssParser.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ScssParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ScssParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ScssParser#stylesheet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStylesheet(ScssParser.StylesheetContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(ScssParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#importDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportDeclaration(ScssParser.ImportDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#importPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportPath(ScssParser.ImportPathContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#asClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsClause(ScssParser.AsClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#withClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWithClause(ScssParser.WithClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(ScssParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#variableValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableValue(ScssParser.VariableValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#variableName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableName(ScssParser.VariableNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#namespace_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamespace_(ScssParser.Namespace_Context ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#propertyDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPropertyDeclaration(ScssParser.PropertyDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#prio}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrio(ScssParser.PrioContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#propertyValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPropertyValue(ScssParser.PropertyValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#mediaDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMediaDeclaration(ScssParser.MediaDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#mediaQueryList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMediaQueryList(ScssParser.MediaQueryListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#mediaQuery}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMediaQuery(ScssParser.MediaQueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#mediaExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMediaExpression(ScssParser.MediaExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#mixinDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMixinDeclaration(ScssParser.MixinDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#contentDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContentDeclaration(ScssParser.ContentDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#fontFaceDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFontFaceDeclaration(ScssParser.FontFaceDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#keyframesDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyframesDeclaration(ScssParser.KeyframesDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#keyframesBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyframesBlock(ScssParser.KeyframesBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#percentageStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPercentageStatement(ScssParser.PercentageStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#includeDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIncludeDeclaration(ScssParser.IncludeDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#interpolationDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterpolationDeclaration(ScssParser.InterpolationDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#extendDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExtendDeclaration(ScssParser.ExtendDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#warndingDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWarndingDeclaration(ScssParser.WarndingDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#errorDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitErrorDeclaration(ScssParser.ErrorDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#atStatementDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtStatementDeclaration(ScssParser.AtStatementDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#ruleset}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuleset(ScssParser.RulesetContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(ScssParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#selectorGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectorGroup(ScssParser.SelectorGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#selector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelector(ScssParser.SelectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#combinator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCombinator(ScssParser.CombinatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#selectorSequence}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectorSequence(ScssParser.SelectorSequenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#id}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(ScssParser.IdContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#typeSelector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeSelector(ScssParser.TypeSelectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#typeNamespacePrefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeNamespacePrefix(ScssParser.TypeNamespacePrefixContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#universal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUniversal(ScssParser.UniversalContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#className}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassName(ScssParser.ClassNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#interpolation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterpolation(ScssParser.InterpolationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#parentRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParentRef(ScssParser.ParentRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#attrib}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttrib(ScssParser.AttribContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#pseudo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPseudo(ScssParser.PseudoContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#functionalPseudo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionalPseudo(ScssParser.FunctionalPseudoContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#pseudoParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPseudoParameter(ScssParser.PseudoParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#negation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegation(ScssParser.NegationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#negationArg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegationArg(ScssParser.NegationArgContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#operator_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperator_(ScssParser.Operator_Context ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(ScssParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(ScssParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#parameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameters(ScssParser.ParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(ScssParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#functionBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionBody(ScssParser.FunctionBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#functionReturn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionReturn(ScssParser.FunctionReturnContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#functionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionStatement(ScssParser.FunctionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(ScssParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(ScssParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#expressionPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionPart(ScssParser.ExpressionPartContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#ifExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfExpression(ScssParser.IfExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#listDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListDeclaration(ScssParser.ListDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#listCommaSeparated}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListCommaSeparated(ScssParser.ListCommaSeparatedContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#listSpaceSeparated}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListSpaceSeparated(ScssParser.ListSpaceSeparatedContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#listBracketed}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListBracketed(ScssParser.ListBracketedContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#listElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListElement(ScssParser.ListElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#mapDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapDeclaration(ScssParser.MapDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#mapEntries}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapEntries(ScssParser.MapEntriesContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#mapEntry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapEntry(ScssParser.MapEntryContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#mapKey}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapKey(ScssParser.MapKeyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#mapValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapValue(ScssParser.MapValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#ifDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfDeclaration(ScssParser.IfDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#elseIfStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseIfStatement(ScssParser.ElseIfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#elseStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseStatement(ScssParser.ElseStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#forDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForDeclaration(ScssParser.ForDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#through}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThrough(ScssParser.ThroughContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#whileDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileDeclaration(ScssParser.WhileDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#eachDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEachDeclaration(ScssParser.EachDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#eachValueList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEachValueList(ScssParser.EachValueListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#var_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_(ScssParser.Var_Context ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#calc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCalc(ScssParser.CalcContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#rotate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRotate(ScssParser.RotateContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#rgba}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRgba(ScssParser.RgbaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#repeat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRepeat(ScssParser.RepeatContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#unit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnit(ScssParser.UnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#length}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLength(ScssParser.LengthContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#dimension}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDimension(ScssParser.DimensionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#percentage}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPercentage(ScssParser.PercentageContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#degree}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDegree(ScssParser.DegreeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#measurment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMeasurment(ScssParser.MeasurmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#uri}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUri(ScssParser.UriContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#arglist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArglist(ScssParser.ArglistContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#plusMinus}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlusMinus(ScssParser.PlusMinusContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#hexcolor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHexcolor(ScssParser.HexcolorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#color}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColor(ScssParser.ColorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#boolean}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolean(ScssParser.BooleanContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(ScssParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScssParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(ScssParser.IdentifierContext ctx);
}