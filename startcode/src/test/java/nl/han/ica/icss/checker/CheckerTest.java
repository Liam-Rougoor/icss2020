package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.AST;
import nl.han.ica.icss.parser.Fixtures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckerTest {

    @Test
    void check() {
        AST uncheckedLevel = Fixtures.uncheckedLevel1();
        new Checker().check(uncheckedLevel);
    }
}