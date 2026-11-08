package aed.tp2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class VectorTests {


    @Test
    void agregarElementos() {
        Vector vec = new Vector();

        vec.agregar(15, 9);

        assertEquals(9, vec.obtener(15)); 
        vec.modificarPosicion(15, 99);
        assertEquals(99, vec.obtener(15)); 
        
    }

}
