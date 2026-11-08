package aed.tp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ABBTests {

    @Test
    void nuevo_conjunto_vacio() {
        ABB<Integer> conjunto = new ABB<Integer>();

        assertFalse(conjunto.pertenece(42));
        assertEquals(0, conjunto.cardinal());
    }

    @Test
    void insertar_un_elemento() {
        ABB<Usuario> conjunto = new ABB<Usuario>();

        Heap<Usuario> hp = new Heap<Usuario>();
        Handle<Usuario> us = hp.agregar(1,new Usuario(1));

        conjunto.insertar(1,us);

        assertEquals(1, conjunto.cardinal());
        assertTrue(conjunto.pertenece(1));
    }

    @Test
    void insertar_izquierda() {
        ABB<Usuario> conjunto = new ABB<Usuario>();

        Heap<Usuario> hp = new Heap<Usuario>();
        Handle<Usuario> us1 = hp.agregar(1,new Usuario(1));
        Handle<Usuario> us2 = hp.agregar(2,new Usuario(2));
        Handle<Usuario> us3 = hp.agregar(3,new Usuario(3));

        conjunto.insertar(1,us1);
        conjunto.insertar(2,us2);
        conjunto.insertar(3,us3);

        assertTrue(conjunto.pertenece(1));
        assertTrue(conjunto.pertenece(2));
        assertTrue(conjunto.pertenece(3));
        assertEquals(3, conjunto.cardinal());
    }

    @Test
    void insertar_derecha() {
        ABB<Usuario> conjunto = new ABB<Usuario>();

        Heap<Usuario> hp = new Heap<Usuario>();
        Handle<Usuario> us1 = hp.agregar(1,new Usuario(1));
        Handle<Usuario> us2 = hp.agregar(2,new Usuario(2));
        Handle<Usuario> us3 = hp.agregar(3,new Usuario(3));
        
        conjunto.insertar(3,us3);
        conjunto.insertar(2,us2);
        conjunto.insertar(1,us1);               

        assertTrue(conjunto.pertenece(1));
        assertTrue(conjunto.pertenece(2));
        assertTrue(conjunto.pertenece(3));
        assertEquals(3, conjunto.cardinal());
    }


    @Test
    void siguiente_inorder() {
        ABB<Usuario> conjunto = new ABB<Usuario>();

        Heap<Usuario> hp = new Heap<Usuario>();
        Handle<Usuario> us5 = hp.agregar(5,new Usuario(5));
        Handle<Usuario> us4 = hp.agregar(4,new Usuario(4));
        Handle<Usuario> us20 = hp.agregar(20,new Usuario(20));
        Handle<Usuario> us15 = hp.agregar(15,new Usuario(15));
        Handle<Usuario> us12 = hp.agregar(12,new Usuario(12));
        Handle<Usuario> us16 = hp.agregar(16,new Usuario(16));
        Handle<Usuario> us24 = hp.agregar(24,new Usuario(24));
        Handle<Usuario> us22 = hp.agregar(22,new Usuario(22));
        Handle<Usuario> us25 = hp.agregar(25,new Usuario(25));        


        conjunto.insertar(5,us5);
        conjunto.insertar(4,us4);
        conjunto.insertar(20,us20);
        conjunto.insertar(15,us15);
        conjunto.insertar(12,us12);
        conjunto.insertar(16,us16);
        conjunto.insertar(24,us24);
        conjunto.insertar(22,us22);
        conjunto.insertar(25,us25);

        ABB<Usuario>.ABB_Iterador iterador = conjunto.new ABB_Iterador();
        
        assertEquals(us4.valor(), iterador.siguiente());
        assertEquals(us5.valor(), iterador.siguiente());
        assertEquals(us12.valor(), iterador.siguiente());
        assertEquals(us15.valor(), iterador.siguiente());
        assertEquals(us16.valor(), iterador.siguiente());
        assertEquals(us20.valor(), iterador.siguiente());
        assertEquals(us22.valor(), iterador.siguiente());
        assertEquals(us24.valor(), iterador.siguiente());
        assertEquals(us25.valor(), iterador.siguiente());
    }

    @Test
    void interaccionEntreUsuarios() {
        ABB<Usuario> conjunto = new ABB<Usuario>();

        Heap<Usuario> hp = new Heap<Usuario>();
        Handle<Usuario> us1 = hp.agregar(1,new Usuario(1));
        Handle<Usuario> us2 = hp.agregar(2,new Usuario(2));
        Handle<Usuario> us3 = hp.agregar(3,new Usuario(3));
        
        conjunto.insertar(3,us3);
        conjunto.insertar(2,us2);
        conjunto.insertar(1,us1);

        conjunto.buscar(3).valor().acumularMillas(500);

        assertEquals(500, us3.valor().totales());

        conjunto.buscar(2).valor().recibirTransferencia(us3.valor(),250);

        assertEquals(250, us3.valor().disponibles());
        assertEquals(250, us2.valor().disponibles());
        assertTrue(us3.valor().esParExclusivo());
        assertEquals(us2.valor(), us3.valor().ultimoTransferidor());

    }
}