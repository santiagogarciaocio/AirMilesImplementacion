package aed.tp2;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class HeapTests {

    @Test
    void crearHeap() {
        Heap<Handle<Usuario>> hp = new Heap();
        assertEquals(0, hp.tamaño());
    }

    @Test
    void agregarElemento() {
        Heap<Handle<Usuario>> hp = new Heap();
        Usuario u1 = new Usuario(1);

        hp.agregar(u1);
        assertEquals(u1, hp.máximo());
        assertEquals(1, hp.tamaño());

        hp.sacarMáximo();
        assertEquals(0, hp.tamaño());
    }

    @Test
    void agregarVarios() {
        /*          (U7, 1000)
            (U5, 400)       (U1, 500)
        (U2, 10)(U4, 250)(U3, 300)(U6, 500)
        
        */
        Heap<Handle<Usuario>> hp = new Heap();
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

 
        hp.agregar(u1);
        hp.agregar(u2);
        hp.agregar(u3);
        hp.agregar(u4);
        hp.agregar(u5);
        hp.agregar(u6);
        hp.agregar(u7);

        assertEquals(7, hp.tamaño());

        assertEquals(u7, hp.sacarMáximo());
        assertEquals(6, hp.tamaño());

        assertEquals(u6, hp.sacarMáximo());
        assertEquals(5, hp.tamaño());

        assertEquals(u1, hp.sacarMáximo());
        assertEquals(4, hp.tamaño());

        assertEquals(u5, hp.sacarMáximo());
        assertEquals(3, hp.tamaño());

        assertEquals(u3, hp.sacarMáximo());
        assertEquals(2, hp.tamaño());

        assertEquals(u4, hp.sacarMáximo());
        assertEquals(1, hp.tamaño());
        
        assertEquals(u2, hp.sacarMáximo());
        assertEquals(0, hp.tamaño());

    }


}
