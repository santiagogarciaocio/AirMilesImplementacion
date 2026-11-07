package aed.tp2;

import java.util.ArrayList;

public class AirMiles implements SistemaAirMiles {
    private Trie<Promocion> promociones;
    private ABB<Usuario> usuarios;
    private Heap<Usuario> ranking;
    private Usuario masAcumulador;
    private Usuario masCanjeador;

    public AirMiles() {
        promociones = new Trie<>();
        Promocion noPromo = new Promocion("NoPromo",1);
        promociones.agregar("NoPromo", noPromo);
        usuarios = new ABB<>();
        ranking = new Heap<>();
    }    

    public void registrarUsuario(int id) {
        Usuario nuevoUsuario = new Usuario(id);
        if (usuarios.cardinal() == 0) {
            masAcumulador = nuevoUsuario;
            masCanjeador = nuevoUsuario;
        }
        Handle<Usuario> handleNuevoUsuario = ranking.agregar(nuevoUsuario.ID(), nuevoUsuario);
        usuarios.insertar(id, handleNuevoUsuario);
    }

    public void registrarUsuarioConReferidor(int id, int ref) {
        Usuario referido = new Usuario(id, true);
        Handle<Usuario> handleReferido = ranking.agregar(referido.ID(), referido);
        usuarios.insertar(id, handleReferido);

        Handle<Usuario> handleReferidor = usuarios.buscar(ref);
        handleReferidor.valor().acumularMillas(500);
        ranking.siftUp(handleReferidor.valor().ID());

        masAcumuladorCheck(handleReferidor.valor());        
        /*No hace falta chequear el UsuarioReferido pues a lo sumo tendran la misma cantidad que el referidor. 
        EN cuyo caso MasAcumulador, por consigna, es arbitrario cualquiera de los dos */
    }

    public void acumularMillas(int id, double distancia, String nombrePromo) {        
        Usuario us = usuarios.buscar(id).valor();
        double factor;
        if (promociones.pertenece(nombrePromo)) {
        Promocion promocion = promociones.promocion(nombrePromo);
            factor = promocion.factor();
        }
        else {
            factor = 1;
        }
        double categoria = us.categoria();
        double millas = distancia * categoria * factor;
        us.acumularMillas(millas);
        ranking.siftUp(us.ID());        
        if (promociones.pertenece(nombrePromo)) { /*Justificacion en el PDF.*/            
            Promocion promocion = promociones.promocion(nombrePromo);
            if(promocion.tieneTope()){
                promocion.restarTope(millas);
                if(promocion.tope() == 0){
                    promociones.eliminar(nombrePromo);
                }
        }}
        masAcumuladorCheck(us);
    }

    public void canjearMillas(int id, double cantidad) {
        Usuario us = usuarios.buscar(id).valor();
        us.canjearMillas(cantidad);
        ranking.siftDown(us.ID());   
        masCanjeadorCheck(us);    
    }

    public void transferirMillas(int origen, int destino, double cantidad) {
        Usuario transferidor = usuarios.buscar(origen).valor();
        Usuario receptor = usuarios.buscar(destino).valor();
        receptor.recibirTransferencia(transferidor, cantidad);
        if(transferidor.canjeadas() > masCanjeador.canjeadas()){
            masCanjeador = transferidor;
        }
        ranking.siftDown(transferidor.ID());
        ranking.siftUp(receptor.ID());                   
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
            ranking.agregar(almacenamientoTemporal[puntero].ID(), almacenamientoTemporal[puntero]);
            puntero ++;
        }
        return res;
    }

    public void iniciarPromocion(String nombre, double factor) {
        Promocion nuevaPromo = new Promocion(nombre, factor);
        promociones.agregar(nombre, nuevaPromo);
    }

    public void iniciarPromocionConTope(String nombre, double factor, double tope) {
        Promocion nuevaPromo = new Promocion(nombre, factor, tope);
        promociones.agregar(nombre, nuevaPromo);
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
        ArrayList<int[]> constructor = new ArrayList<int[]>();
        ABB<Usuario>.ABB_Iterador iterador = usuarios.new ABB_Iterador();
        while(iterador.haySiguiente()){
            Usuario us = iterador.siguiente();
            if(us.esParExclusivo() && us.ultimoTransferidor().ID() > us.ID()){
                int[] par = {us.ID(), us.ultimoTransferidor().ID()};
                constructor.add(par);
                
            }
        }
        int[][] recasteo = new int[constructor.size()][2];
        int[][] res = (int[][]) constructor.toArray(recasteo);
        return res;       
    }

    private void masCanjeadorCheck(Usuario usuario){
        if(usuario.canjeadas() > masCanjeador.canjeadas()){
            masCanjeador = usuario;
        }       
    }

    private void masAcumuladorCheck(Usuario usuario){
        if(usuario.totales() > masAcumulador.totales()){
            masAcumulador = usuario;
        }       
    }
}
