package aed.tp2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class TrieTests {


    @Test
    void crearTrie() {
        Trie tr = new Trie();

        assertTrue(tr.pertenece("No promo"));
        assertFalse(tr.pertenece("Super Promo"));
    }


    @Test
    void crearPromoConTope() {
        Trie tr = new Trie();

        tr.agregar("No limits", 3.15, 100000);

        assertTrue(tr.pertenece("No limits"));
        assertFalse(tr.pertenece("No limitss"));
    }

}
