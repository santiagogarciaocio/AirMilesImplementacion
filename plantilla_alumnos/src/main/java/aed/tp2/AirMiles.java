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
        nuevoUsuario.setOrdenDeLlegada(ranking.orden());
        if (usuarios.cardinal() == 0) {
            masAcumulador = nuevoUsuario;
            masCanjeador = nuevoUsuario;
        }
        Handle<Usuario> handleNuevoUsuario = ranking.agregar(nuevoUsuario);
        usuarios.insertar(id, handleNuevoUsuario);
    }

    public void registrarUsuarioConReferidor(int id, int ref) {
        Usuario referido = new Usuario(id, true);
        referido.setOrdenDeLlegada(ranking.orden());
        Handle<Usuario> handleReferido = ranking.agregar(referido);
        usuarios.insertar(id, handleReferido);

        Handle<Usuario> handleReferidor = usuarios.buscar(ref);
        handleReferidor.valor().acumularMillas(500);
        int posReferidor = handleReferidor.valor().ordenDeLlegada();
        ranking.siftUp(posReferidor);
        if(handleReferidor.valor().totales() > masAcumulador.totales()){
            masAcumulador = handleReferidor.valor();
        }
    }

    public void acumularMillas(int id, double distancia, String nombrePromo) {        
        Handle<Usuario> us = usuarios.buscar(id);
        double factor;
        if (promociones.pertenece(nombrePromo)) {
        Promocion promocion = promociones.promocion(nombrePromo);
            factor = promocion.factor();
        }
        else {
            factor = 1;
        }
        double categoria = us.valor().categoria();
        double millas = distancia * categoria * factor;
        us.valor().acumularMillas(millas);
        ranking.siftUp(us.valor().ordenDeLlegada());
        if (promociones.pertenece(nombrePromo)) {
        Promocion promocion = promociones.promocion(nombrePromo);
        if(promocion.tieneTope()){
            promocion.restarTope(millas);
            if(promocion.tope() == 0){
                promociones.eliminar(nombrePromo);
            }
        }}
        if(us.valor().totales() > masAcumulador.totales()){
            masAcumulador = us.valor();
        }
    }

    public void canjearMillas(int id, double cantidad) {
        Handle<Usuario> us = usuarios.buscar(id);
        us.valor().canjearMillas(cantidad);
        ranking.siftDown(us.valor().ordenDeLlegada());       
        if(us.valor().canjeadas() > masCanjeador.canjeadas()){
            masCanjeador = us.valor();
        }
    }

    public void transferirMillas(int origen, int destino, double cantidad) {
        Handle<Usuario> transferidor = usuarios.buscar(origen);
        Handle<Usuario> receptor = usuarios.buscar(destino);
        receptor.valor().recibirTransferencia(transferidor.valor(), cantidad);
        if(transferidor.valor().canjeadas() > masCanjeador.canjeadas()){
            masCanjeador = transferidor.valor();
        }
        ranking.siftDown(transferidor.valor().ordenDeLlegada());
        ranking.siftUp(receptor.valor().ordenDeLlegada());           
    }

    public double consultarSaldo(int id) {
        Handle<Usuario> us = usuarios.buscar(id);
        return us.valor().disponibles();
    }

    public String consultarCategoria(int id) {
        Handle<Usuario> us = usuarios.buscar(id);
        return us.valor().categoriaStrg();
    }

    public int[] rankingMillas(int n) {
        Usuario[] almacenamientoTemporal;
        int[] res;
        if(n > ranking.tamaño()){
            res = new int[ranking.tamaño()];
            almacenamientoTemporal = new Usuario[ranking.tamaño()];
        }else{
            res = new int[n];
            almacenamientoTemporal = new Usuario[n];
        }
        int puntero = 0;
        while(puntero < res.length){
            Usuario us = ranking.desencolar();
            res[puntero] = us.ID();
            almacenamientoTemporal[puntero] = us;
            puntero ++;
        }
        puntero = 0;
        while(puntero < res.length){
            almacenamientoTemporal[puntero].setOrdenDeLlegada(ranking.orden());
            ranking.agregar(almacenamientoTemporal[puntero]);
            puntero ++;
        }
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
        if (usuarios.cardinal() == 0){
            int[][] vacio = {};
            return vacio;
        }
        int[][] res_aux = new int[usuarios.cardinal()][2];
        int tamaño_res = 0;
        ABB<Usuario>.ABB_Iterador iterador = usuarios.new ABB_Iterador();
        while(iterador.haySiguiente()){
            Usuario us = iterador.siguiente();
            if(us.esParExclusivo() && us.ultimoTransferidor().ID() > us.ID()){
                int[] par = {us.ID(), us.ultimoTransferidor().ID()};
                res_aux[tamaño_res] = par;
                tamaño_res ++;
            }
        }
        int[][] res = new int[tamaño_res][2];
        int contador = 0;
        while (contador < tamaño_res){
            res[contador] = res_aux[contador];
            contador ++;
        }             
        return res;        
    }
}
