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
    void pertenece() {
        Trie tr = new Trie();

        tr.agregar("nolimits", 3.15, 100000);

        assertTrue(tr.pertenece("nolimits"));
        assertFalse(tr.pertenece("nolimit"));
    }

    @Test
    void eliminarPromo() {
        Trie tr = new Trie();
        tr.agregar("nopromo",1);
        tr.agregar("nolimits", 3.15, 100000);

        assertTrue(tr.pertenece("nolimits"));

        tr.eliminar("nolimits");

        assertFalse(tr.pertenece("nolimits"));
    }

    @Test
    void eliminarSinCompartidas() {
        Trie tr = new Trie();
        tr.agregar("nopromo",1);
        tr.agregar("sipromo", 3.15, 100000);

        assertTrue(tr.pertenece("nopromo"));
        assertTrue(tr.pertenece("sipromo"));        

        tr.eliminar("sipromo");

        assertTrue(tr.pertenece("nopromo"));

    }

    @Test
    void eliminarPrefijo() {
        Trie tr = new Trie();
        
        tr.agregar("nopromo",1);
        tr.agregar("nopromociones",2);

        tr.eliminar("nopromo");

        assertFalse(tr.pertenece("nopromo"));
        assertTrue(tr.pertenece("nopromociones"));
    }

    @Test
    void eliminarSufijo() {
        Trie tr = new Trie();
        
        tr.agregar("nopromo",1);
        tr.agregar("nopromociones",2);

        tr.eliminar("nopromociones");

        assertTrue(tr.pertenece("nopromo"));
        assertFalse(tr.pertenece("nopromociones"));
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
