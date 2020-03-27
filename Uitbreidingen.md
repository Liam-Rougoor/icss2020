#Geïmplementeerde uitbreidingen
## Boolean expressies
Boolean expressies zijn geïmplementeerd. Hieronder een voorbeeld syntax:
- 5 > 2
- Var1 := 3 == 5;
- Var2 := !Var1;

Voor het implementeren van boolean expressies heb ik een Strategy pattern gebruikt en een Comparison class gemaakt. Elke strategy bevat
de daadwerkelijke vergelijking >, <, ==.

## If else constructie
Het is mogelijk om na een if {} een else {} aan te plakken. Binnen de else kan weer een if gemaakt worden.

## Refactorings/Clean code
Ik heb zo clean mogelijk geprobeerd te werken door zo min mogelijk instanceof te gebruiken en de check, transform en generate logica
zoveel mogelijk in de node zelf te laten.
