package aed.tp2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PromocionesTests {

    @Test
    void VariosTests() {
        Promocion prm = new Promocion("No promo",1);
        assertEquals("No promo", prm.nombre());
        assertEquals(1, prm.factor());
        assertFalse(prm.tieneTope());
        assertEquals(Double.POSITIVE_INFINITY, prm.tope());        
        prm.restarTope(10000000);
        assertEquals(Double.POSITIVE_INFINITY, prm.tope());

        Promocion prm2 = new Promocion("Test",2.5, 20000);
        assertEquals("Test", prm2.nombre());
        assertEquals(2.5, prm2.factor());
        assertTrue(prm2.tieneTope());
        assertEquals(20000, prm2.tope());

        assertTrue(prm2.sePasaDelTope(30000));
        assertFalse(prm2.sePasaDelTope(3000));
        assertTrue(prm2.igualAlTope(20000));
        prm2.restarTope(17890);
        assertTrue(prm2.sePasaDelTope(3000));
    }

}
