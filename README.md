# Compilateur SASS -> CSS
## Par Zack Pedretti

### Sommaire:

- Notice d'utilisation
- Règles de base
- Variables
- Expressions arithmétiques
- Mixins (@mixin) et Include (@include)
- Boucles For (@for)
- Boucles Each (@each)
- Conditions If (@if), Else If (@else if) Else (@else)
- Travail supplémentaire
- Commentaire sur le TP

### Notice d'utilisation
Pour utiliser le compilateur, il faut utiliser le fichier "GenerateCss.java".
Celui-ci permet d'écrire le résultat du compilateur dans un fichier css.
Pour sélectionner le fichier à compiler, il faut modifier la variable "inputPath" et y inscrire le chemin du fichier voulu.
Pour sélectionner le nom et le chemin du fichier généré, il faut modifier la variable "outputPath".

Pour tester la compilation dans la console, il est possible d'utiliser "TestMain.java".

### Règles de base
- Les identifieurs peuvent être imbriqués à n'importe quel niveau.

### Variables
Les variables fonctionnent correctement. Elles sont initialisées dans le .root du fichier CSS.

Les listes sont également implémentées. Lorsqu'une liste est utilisée comme attribut, ses éléments sont affichés, chacun séparé d'une virgule. 

Il est également possible d'utiliser la fonction nth(), comme expliqué dans la section <ins>**Travail supplémentaire**</ins>.

### Expressions arithmétiques
Les expressions arithmétiques sont implémentées. Contrairement à la version antérieure, le compilateur n'utilise pas les fonctions calc(), mais calcule réellement les valeurs.
Il est impossible de mélanger les pixels, les pourcentages et les valeurs hexadécimales.
Le calcul donne un nombre réel si un réel fait partie de l'expression. Sinon il donne un entier.

### Mixins (@mixin) et Include (@include)
Les mixins sont implémentées entièrement.
Les fonctionnalités implémentées sont :
- Mixins sans arguments (et leurs utilisations)
- Mixins avec arguments (et leurs utilisations)
- Mixins avec valeurs par défaut (et leurs utilisations sans attributs)
- Mixins avec une liste en attribut (et leurs utilisations avec une liste)

### Boucles For (@for)
Les boucles For sont implémentées entièrement.
Les fonctionnalités implémentées sont :
- Boucles @for avec from... through
- Boucles @for avec from... to 
- Boucles dynamiques avec liste

### Boucles Each (@each)
Les boucles Each sont entièrement implémentées.
Les fonctionnalités implémentées sont :
- Boucles @each avec plusieurs valeurs littérales et une variable d'itération
- Boucles @each passant dans une liste
- Boucles @each passant dans une map

### Conditions If (@if) Else (@else)
Les conditions sont entièrement implémentées.
Les fonctionnalités implémentées sont :
- Condition simple (avec un booléen)
- Conditions avec expression arithmétique
- Conditions if, if else et else
- Conditions avec opérateurs logiques (or ou and)
- Conditions avec négation (not)

### Travail supplémentaire

- Le sélecteur parent (&:) est implémenté. Le ruleset de ce sélecteur est désemboîté et écrit en dehors de le scope du parent.
- Les listes sont implémentées. Elles peuvent être séparées de virgules ou d'espaces. En compilant, les éléments de la liste seront utilisé à la place de ses appels et ses éléments seront séparés par des virgules.
- Les maps sont implémentés.

#### Fonctions
- La fonction length() prenant en paramètre une liste et renvoyant sa longueur est implémentée
- La fonction nth() prenant en paramètre une liste et un entier i renvoyant la valeur i de la liste est implémentée.
- Les fonctions darken() et lighten() prenant en paramètre une variable de couleur et un pourcentage i renvoyant la couleur i% plus sombre / plus clair sont implémentées (Utilisent color-mix, fonction présente dans les versions plus récentes de CSS)
- La fonction linear-gradient() est implémentée
