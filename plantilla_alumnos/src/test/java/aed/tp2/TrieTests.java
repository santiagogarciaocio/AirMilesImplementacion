package aed.tp2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TrieTests {


    @Test
    void crearTrie() {
        Trie<Promocion> tr = new Trie<Promocion>();

        
        tr.agregar("nopromo",new Promocion("nopromo",1));

        assertTrue(tr.pertenece("nopromo"));
    }

    @Test
    void crearPromoConTope() {
        Trie<Promocion> tr = new Trie<Promocion>();
        tr.agregar("nopromo",new Promocion("nopromo",1));

        tr.agregar("nolimits", new Promocion("nolimits",3.15, 100000));

        assertTrue(tr.pertenece("nolimits"));
        assertFalse(tr.pertenece("No limitss"));
    }

    @Test
    void pertenece() {
        Trie<Promocion> tr = new Trie<Promocion>();
        tr.agregar("nopromo",new Promocion("nopromo",1));

        tr.agregar("nolimits",new Promocion("nolimits", 3.15, 100000));

        assertTrue(tr.pertenece("nolimits"));
        assertFalse(tr.pertenece("nolimit"));
    }

    @Test
    void eliminarPromo() {
        Trie<Promocion> tr = new Trie<Promocion>();
        tr.agregar("nopromo",new Promocion("nopromo",1));
        
        tr.agregar("nolimits",new Promocion("nolimits", 3.15, 100000));

        assertTrue(tr.pertenece("nolimits"));

        tr.eliminar("nolimits");

        assertFalse(tr.pertenece("nolimits"));
    }

    @Test
    void poda() {
        Trie<Promocion> tr = new Trie<Promocion>();
        tr.agregar("nopromo",new Promocion("nopromo",1));
        
        tr.agregar("nolimits",new Promocion("nolimits", 3.15, 100000));

        assertTrue(tr.pertenece("nolimits"));

        tr.eliminar("nolimits");

        assertFalse(tr.pertenece("nolimits"));
    }

    @Test
    void eliminarSinCompartidas() {
        Trie<Promocion> tr = new Trie<Promocion>();
        tr.agregar("nopromo",new Promocion("nopromo",1));
        tr.agregar("sipromo",new Promocion("sipromo", 3.15, 100000));

        assertTrue(tr.pertenece("nopromo"));
        assertTrue(tr.pertenece("sipromo"));        

        tr.eliminar("sipromo");

        assertTrue(tr.pertenece("nopromo"));

    }

    @Test
    void eliminarPrefijo() {
        Trie<Promocion> tr = new Trie<Promocion>();

        tr.agregar("nopromo",new Promocion("nopromo",1));
        tr.agregar("nopromociones",new Promocion("nopromociones",2));

        tr.eliminar("nopromo");

        assertFalse(tr.pertenece("nopromo"));
        assertTrue(tr.pertenece("nopromociones"));
    }

    @Test
    void eliminarSufijo() {
        Trie<Promocion> tr = new Trie<Promocion>();
        
        tr.agregar("nopromo",new Promocion("nopromo",1));
        tr.agregar("nopromociones",new Promocion("nopromociones",2));

        tr.eliminar("nopromociones");

        assertTrue(tr.pertenece("nopromo"));
        assertFalse(tr.pertenece("nopromociones"));
    }

    @Test
    void modificarPromo() {
        Trie<Promocion> tr = new Trie<Promocion>();
        tr.agregar("nopromo",new Promocion("nopromo",1));

        tr.agregar("nolimits",new Promocion("nolimits", 3.15, 100000));
        
        Promocion prm = tr.buscarElemento("nolimits");
        prm.restarTope(50000);
        assertEquals(50000,tr.buscarElemento("nolimits").tope());

    }
}
