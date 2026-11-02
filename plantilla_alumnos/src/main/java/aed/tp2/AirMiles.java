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
        Handle<Usuario> handleNuevoUsuario = ranking.agregar(nuevoUsuario);
        siftUp(posHeap);
        usuarios.insertar(id, handleNuevoUsuario);
    }

    public void registrarUsuarioConReferidor(int id, int ref) {
        Usuario referido = new Usuario(id, true);
        int posHeap = ranking.tamaño();
        referido.setIndiceHeap(posHeap);
        Handle<Usuario> handleReferido = ranking.agregar(referido);
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
        Promocion promocion = promociones.promocion(nombrePromo);
        double categoria = us.valor().categoria();
        double factor = promocion.factor();
        double millas = distancia * categoria * factor;
        us.valor().acumularMillas(millas);
        siftUp(us.valor().indiceHeap());
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
        transferidor.valor().transferencia(receptor.valor(), cantidad);
        if(transferidor.valor().ID() == masCanjeador.ID()){
        }
        else{
        if(transferidor.valor().canjeadas() > masCanjeador.canjeadas()){
            masCanjeador = transferidor.valor();
        }}        
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
            Usuario us = ranking.sacarMáximo();
            res[puntero] = us.ID();
            almacenamientoTemporal[puntero] = us;
            puntero ++;
        }
        puntero = 0;
        while(puntero < res.length){
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
        int[][] res = new int[0][2];
        
        ABB.ABB_Iterador iterador = usuarios.new ABB_Iterador();
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

    private void intercambiar(int i, int j) {
        Usuario g = ranking.getData(j);
        Usuario h = ranking.getData(i);
        ranking.setearData(i, g);
        ranking.setearData(j, h);
        g.setIndiceHeap(i);
        h.setIndiceHeap(j); 
    }

    public void siftUp(int i) {
        while (i > 0) {
            int padre = (i - 1)/ 2;
            if (ranking.getData(i).compareTo(ranking.getData(padre)) > 0) {
                intercambiar(i, padre);
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
