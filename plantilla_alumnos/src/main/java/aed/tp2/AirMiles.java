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
        int posHeap = ranking.tamaño();
        nuevoUsuario.setIndiceHeap(posHeap);
        if (usuarios.cardinal() == 0) {
            masAcumulador = nuevoUsuario;
            masCanjeador = nuevoUsuario;
        }
        Handle<Usuario> handleNuevoUsuario = ranking.agregarAlFinal(nuevoUsuario);
        siftUp(posHeap);
        usuarios.insertar(id, handleNuevoUsuario);
    }

    public void registrarUsuarioConReferidor(int id, int ref) {
        Usuario referido = new Usuario(id, true);
        int posHeap = ranking.tamaño();
        referido.setIndiceHeap(posHeap);
        Handle<Usuario> handleReferido = ranking.agregarAlFinal(referido);
        siftUp(handleReferido.valor().indiceHeap());
        usuarios.insertar(id, handleReferido);

        Handle<Usuario> handleReferidor = usuarios.buscar(ref);
        handleReferidor.valor().acumularMillas(500);
        int posReferidor = handleReferidor.valor().indiceHeap();
        siftUp(posReferidor);
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
        siftUp(us.valor().indiceHeap());
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
        siftDown(us.valor().indiceHeap());       
        if(us.valor().canjeadas() > masCanjeador.canjeadas()){
            masCanjeador = us.valor();
        }
    }

    public void transferirMillas(int origen, int destino, double cantidad) {
        Handle<Usuario> transferidor = usuarios.buscar(origen);
        Handle<Usuario> receptor = usuarios.buscar(destino);
        receptor.valor().transferencia(transferidor.valor(), cantidad);
        transferidor.valor().transferencia(receptor.valor(), 0);
        transferidor.valor().canjearMillas(cantidad);
        if(transferidor.valor().canjeadas() > masCanjeador.canjeadas()){
            masCanjeador = transferidor.valor();
        }
        siftDown(transferidor.valor().indiceHeap());
        siftUp(receptor.valor().indiceHeap());        
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
            if (ranking.tamaño() > 0){
            ranking.getData(0).setIndiceHeap(0);
            siftDown(0);
            }
            res[puntero] = us.ID();
            almacenamientoTemporal[puntero] = us;
            puntero ++;
        }
        puntero = 0;
        while(puntero < res.length){
            ranking.agregarAlFinal(almacenamientoTemporal[puntero]);
            almacenamientoTemporal[puntero].setIndiceHeap(ranking.tamaño() - 1);
            siftUp(ranking.tamaño() - 1);
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

    private void intercambiar(int i, int j) {
        Usuario g = ranking.getData(j);
        Usuario h = ranking.getData(i);
        g.setIndiceHeap(i);
        h.setIndiceHeap(j);
        ranking.setearData(i, g);
        ranking.setearData(j, h);
    }

    public void siftUp(int i) {
        while (i > 0) {
            int padre = (i - 1)/ 2;
            if (ranking.getData(i).compareTo(ranking.getData(padre)) > 0) {
                intercambiar(i, padre);
                i = padre;
            }
            else return; 
        }
    }

    public void siftDown(int i) {
        while ((i * 2) + 1 < ranking.tamaño()) {
            int izq = (i * 2) + 1;
            int der = (i * 2) + 2;
            int hijoMayor;
            if (der < ranking.tamaño() && ranking.getData(der).compareTo(ranking.getData(izq)) > 0) {
                hijoMayor = der;
            }
            else {
                hijoMayor = izq;
            }
            if (ranking.getData(i).compareTo(ranking.getData(hijoMayor)) < 0) {
                intercambiar(i, hijoMayor);
                i = hijoMayor;
            }
            else {
                return; 
            }
        }}
}
