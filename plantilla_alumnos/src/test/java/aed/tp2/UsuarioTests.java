package aed.tp2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTests {
    
    @Test
    void crearUsuario() {
        Usuario us = new Usuario(1325);
        assertEquals(1325, us.ID());
        assertEquals(0, us.totales());
    }

    @Test
    void crearUsuarioConReferido() {
        Usuario us = new Usuario(1328, true);
        assertEquals(1328, us.ID());
        assertEquals(500, us.totales());        
        assertEquals(500, us.disponibles());
        assertEquals(0, us.canjeadas());
    }


    @Test
    void agregarMillas() {
        Usuario us = new Usuario(1325);
        assertEquals(1325, us.ID());
        assertEquals(0, us.totales());
        assertEquals(0, us.disponibles());
        
        us.acumularMillas(150);        
        assertEquals(150, us.totales());
        assertEquals(150, us.disponibles());

        us.canjearMillas(100);        
        assertEquals(150, us.totales());
        assertEquals(50, us.disponibles());
    }

    /*testeo varias transferencias */
    @Test
    void transferenciasVarias() {
        Usuario us1 = new Usuario(1328);
        Usuario us2 = new Usuario(1234);
        assertEquals(1328, us1.ID());
        /*chequero que los atributos esten bien */
        assertFalse(us1.esParExclusivo());        
        assertEquals(null, us1.ultimoTransferidor());

        us1.acumularMillas(1000);
        us2.acumularMillas(1000);

        assertEquals(1000, us1.totales());
        assertEquals(1000, us2.totales());

        us2.recibirTransferencia(us1,150);
        /*Transferencia solo de un lado deberia generar PartransferidorExclusivo para ambos usuarios*/
        assertEquals(1000, us1.totales());
        assertEquals(850, us1.disponibles());
        assertEquals(150, us1.canjeadas());
        assertEquals(true, us1.esParExclusivo());
        assertEquals(us2, us1.ultimoTransferidor());

        assertEquals(1000, us2.totales());
        assertEquals(1150, us2.disponibles());
        assertEquals(0, us2.canjeadas());
        assertEquals(true, us2.esParExclusivo());
        assertEquals(us1, us2.ultimoTransferidor());
    }

    /*testeo pares trasnferidores exlusivos */
    @Test
    void transferidoresExclusivos() {
        Usuario us1 = new Usuario(1328);
        Usuario us2 = new Usuario(1234);

        us1.acumularMillas(1000);
        us2.acumularMillas(1000);

        us2.recibirTransferencia(us1,150);

        assertTrue(us1.esParExclusivo());
        assertTrue(us2.esParExclusivo());
        
        us1.recibirTransferencia(us2,500);

        /*interaccion basica dos a dos */

        assertTrue(us1.esParExclusivo());
        assertTrue(us2.esParExclusivo());

        assertEquals(us2, us1.ultimoTransferidor());
        assertEquals(us1, us2.ultimoTransferidor());

        /*Trasnferencia de terceros deberia romper ParExclusivo para todos los usuarios involucrados*/

        Usuario us3 = new Usuario(4567,true);

        us1.recibirTransferencia(us3, 250);        

        assertFalse(us1.esParExclusivo());
        assertFalse(us2.esParExclusivo());
        assertFalse(us3.esParExclusivo());        
    }

}

