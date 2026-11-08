package aed.tp2;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class HeapTests {

    @Test
    void crearHeap() {
        Heap<Usuario> hp = new Heap<Usuario>();
        assertEquals(0, hp.tamaño());
    }

    @Test
    void agregarElemento() {
        Heap<Usuario> hp = new Heap<Usuario>();
        Usuario u1 = new Usuario(1);

        hp.agregar(1, u1);
        assertEquals(1, hp.tamaño());

        hp.desencolar();
        assertEquals(0, hp.tamaño());
    }

    @Test
    void agregarVarios() {
        /*          (U7, 1000)
            (U5, 400)       (U1, 500)
        (U2, 10)(U4, 250)(U3, 300)(U6, 500)
        
        */
        Heap<Usuario> hp = new Heap<Usuario>();
        Usuario u1 = new Usuario(1);
        Usuario u2 = new Usuario(2);
        Usuario u3 = new Usuario(3);
        Usuario u4 = new Usuario(4);
        Usuario u5 = new Usuario(5);
        Usuario u6 = new Usuario(6);
        Usuario u7 = new Usuario(7);

        u1.acumularMillas(500);
        u2.acumularMillas(10);
        u3.acumularMillas(300);
        u4.acumularMillas(250);
        u5.acumularMillas(400);
        u6.acumularMillas(500);
        u7.acumularMillas(1000);

 
        hp.agregar(1, u1);
        hp.agregar(2, u2);
        hp.agregar(3, u3);
        hp.agregar(4, u4);
        hp.agregar(5, u5);
        hp.agregar(6, u6);
        hp.agregar(7, u7);

        assertEquals(7, hp.tamaño());

        assertEquals(u7, hp.desencolar());
        assertEquals(6, hp.tamaño());

        assertEquals(u6, hp.desencolar());
        assertEquals(5, hp.tamaño());

        assertEquals(u1, hp.desencolar());
        assertEquals(4, hp.tamaño());

        assertEquals(u5, hp.desencolar());
        assertEquals(3, hp.tamaño());

        assertEquals(u3, hp.desencolar());
        assertEquals(2, hp.tamaño());

        assertEquals(u4, hp.desencolar());
        assertEquals(1, hp.tamaño());
        
        assertEquals(u2, hp.desencolar());
        assertEquals(0, hp.tamaño());

    }


}
