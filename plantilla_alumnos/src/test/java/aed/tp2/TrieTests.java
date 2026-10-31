package aed.tp2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class TrieTests {


    @Test
    void crearTrie() {
        Trie tr = new Trie();

        
        tr.agregar("nopromo",1);

        assertTrue(tr.pertenece("nopromo"));
    }

    @Test
    void crearPromoConTope() {
        Trie tr = new Trie();

        tr.agregar("nolimits", 3.15, 100000);

        assertTrue(tr.pertenece("nolimits"));
        assertFalse(tr.pertenece("No limitss"));
    }

    @Test
    void eliminarPromo() {
        Trie tr = new Trie();
        tr.agregar("nolimits", 3.15, 100000);

        assertTrue(tr.pertenece("nolimits"));

        tr.eliminar("nolimits");

        assertFalse(tr.pertenece("nolimits"));

    }

    @Test
    void modificarPromo() {
        Trie tr = new Trie();

        tr.agregar("nolimits", 3.15, 100000);
        
        Promocion prm = tr.promocion("nolimits");
        prm.restarTope(50000);
        assertEquals(50000,tr.promocion("nolimits").tope());

    }
}
