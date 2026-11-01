package aed.tp2;

import aed.tp2.ABB.ABB_Iterador;

public class AirMiles implements SistemaAirMiles {
    /*COMPLETAR*/
    private Trie promociones;
    private ABB<Usuario> usuarios;
    private Heap<Usuario> ranking;
    private Usuario masAcumulador;
    private Usuario masCanjeador;

    public AirMiles() {
        promociones = new Trie();
        promociones.agregar("NoPromo",1);
        usuarios = new ABB<>();
        ranking = new Heap<>();
    }

    public void registrarUsuario(int id) {
        Usuario nuevoUsuario = new Usuario(id);
        int posHeap = ranking.tamaño(); //primero lo meto al final
        if (usuarios.cardinal() == 0) {
            masAcumulador = nuevoUsuario;
            masCanjeador = nuevoUsuario;
        }
        Handle<Usuario> handleNuevoUsuario = usuarios.insertar(id, nuevoUsuario, posHeap);
        ranking.agregarAlFinal(handleNuevoUsuario);
        ranking.siftUp(posHeap); //acá lo acomodo
    }

    public void registrarUsuarioConReferidor(int id, int ref) {
        int posHeap = ranking.tamaño();
        Handle<Usuario> nuevoUsuario = usuarios.insertar(id, new Usuario(id,true), posHeap);
        ranking.agregarAlFinal(nuevoUsuario);
        ranking.siftUp(posHeap);
        
        ABB<Usuario>.HandleABB referidor = (ABB<Usuario>.HandleABB) usuarios.buscar(ref); //casteo para que sea HandleABB y pueda usar métodos que difinimos como posicionEnHeap
        referidor.valor().acumularMillas(500);
        ranking.siftUp(referidor.posicionEnHeap()); //después de sumarle las millas acomodo
        if(referidor.valor().totales() > masAcumulador.totales()){
            masAcumulador = referidor.valor();
        }
    }

    public void acumularMillas(int id, double distancia, String nombrePromo) {        
        ABB<Usuario>.HandleABB us = (ABB<Usuario>.HandleABB) usuarios.buscar(id); //de nuevo, casteo a HandleABB porque esa es la clase que me permite saber su posición en el heap
        Promocion promocion = promociones.promocion(nombrePromo);
        double categoria = us.valor().categoria();
        double factor = promocion.factor();
        double millas = distancia * categoria * factor;
        us.valor().acumularMillas(millas);
        ranking.siftUp(us.posicionEnHeap());
        if(promocion.tieneTope()){
            promocion.restarTope(distancia);
            if(promocion.tope() == 0){
                promociones.eliminar(nombrePromo);
            }
        }
        if(us.valor().totales() > masAcumulador.totales()){
            masAcumulador = us.valor();
        }
    }

    public void canjearMillas(int id, double cantidad) {
        ABB<Usuario>.HandleABB us = (ABB<Usuario>.HandleABB) usuarios.buscar(id);
        us.canjearMillas(cantidad);
        ranking.siftDown(us.posicionEnHeap);        
        if(us.valor().canjeadas() > masCanjeador.canjeadas()){
            masCanjeador = us.valor();
        }
    }

    public void transferirMillas(int origen, int destino, double cantidad) {
        Usuario transferidor = usuarios.usuario(origen);
        Usuario receptor = usuarios.usuario(destino);
        transferidor.transferencia(receptor, cantidad);
        /*Aca hay que chequear que si el transferidor era el masACumulador, y por haber canjeado dejo de serlo. El masAcumulador tiene que ser actualizarse */
        if(transferidor.ID() == masCanjeador.ID()){
        }else{}
        if(transferidor.canjeadas() > masCanjeador.canjeadas()){
            masCanjeador = transferidor;
        }
        if(receptor.totales() > masAcumulador.totales()){
            masAcumulador = receptor;
        }        
    }

    public double consultarSaldo(int id) {
        ABB<Usuario>.HandleABB us = (ABB<Usuario>.HandleABB) usuarios.buscar(id);
        return us.valor().disponibles();
    }

    public String consultarCategoria(int id) {
        ABB<Usuario>.HandleABB us = (ABB<Usuario>.HandleABB) usuarios.buscar(id);
        return us.valor().categoriaStrg();
    }

    public int[] rankingMillas(int n) {
        int [] res;
        if(n > ranking.tamaño()){
            res = new int[ranking.tamaño()];
        }else{
            res = new int[n];
        }
        int puntero = 0;
        while(puntero < n){
            Handle<Usuario> us = ranking.sacarMáximo();
            res[puntero] = us.valor().ID();
        }
        /*aca falta volver a agregar los usuarios al heap */
        return res;
        
    }

    public void iniciarPromocion(String nombre, double factor) {
        promociones.agregar(nombre, factor);
    }

    public void iniciarPromocionConTope(String nombre, double factor, double tope) {
        promociones.agregar(nombre, factor, tope);
    }

    public void finalizarPromocion(String nombre) {
        promociones.eliminar(nombre);
    }

    public int elMasAcumulador() {
        return masAcumulador.ID();
    }

    public int elMasCanjeador() {
        return masCanjeador.ID();
    }

    public int[][] paresTransferidoresExclusivos() {
        int[][] res = new int[0][2];
        
        ABBUsuarios.ABB_Iterador iterador = usuarios.new ABB_Iterador();
        while(iterador.haySiguiente()){
            Usuario us = iterador.siguiente();
            if(us.estaEnParejaTransferidoresExclusivos()){
                if(us.ID() < us.parejaTransferidoraExclusiva()){
                    res = new int[res.length+1][2];
                    res[res.length][0] = us.ID();
                    res[res.length][0] = us.parejaTransferidoraExclusiva();
                }
            }
        }             
        return res;        
    }
}
