package aed.tp2;

import aed.tp2.ABBUsuarios.ABB_Iterador;

public class AirMiles implements SistemaAirMiles {
    /*COMPLETAR*/
    private Trie promociones;
    private ABBUsuarios usuarios;
    private Heap<Handle<Usuario>> ranking;
    private Usuario masAcumulador;
    private Usuario masCanjeador;

    public AirMiles() {
        promociones = new Trie();
        usuarios = new ABBUsuarios();
        ranking = new Heap<Handle<Usuario>>();
        masAcumulador = null; /*aca los test piden Usuario ficticion con ID -1 */
        masCanjeador = null; /*aca los test piden Usuario ficticion con ID -1 */
    }

    public void registrarUsuario(int id) {
        Handle<Usuario> nuevoUsuario = usuarios.insertar(new Usuario(id));
        ranking.agregar(nuevoUsuario);
    }

    public void registrarUsuarioConReferidor(int id, int ref) {
        Handle<Usuario> nuevoUsuario = usuarios.insertar(new Usuario(id,true));
        ranking.agregar(nuevoUsuario);
        Usuario referidor = usuarios.usuario(ref);
        referidor.acumularMillas(500);
        /*Hay qe chequear que el heap siga cumpliendo su abstraccion */
        if(referidor.totales() > masAcumulador.totales()){
            masAcumulador = referidor;
        }
    }

    public void acumularMillas(int id, double distancia, String nombrePromo) {        
        Usuario us = usuarios.usuario(id);
        double categoria = us.categoria();
        double factor = 1 /*No tiene funcion buscar el trie */;
        double millas = distancia * categoria * factor;
        us.acumularMillas(millas);
        /*Hay qe chequear que el heap siga cumpliendo su abstraccion */
        if(us.totales() > masAcumulador.totales()){
            masAcumulador = us;
        }
    }

    public void canjearMillas(int id, double cantidad) {
        Usuario us = usuarios.usuario(id);
        us.canjearMillas(cantidad);
        /*Hay qe chequear que el heap siga cumpliendo su abstraccion */        
        if(us.canjeadas() > masCanjeador.canjeadas()){
            masCanjeador = us;
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
        Usuario us = usuarios.usuario(id);
        return us.disponibles();
    }

    public String consultarCategoria(int id) {
        Usuario us = usuarios.usuario(id);
        return us.categoriaStrg();
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
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void iniciarPromocionConTope(String nombre, double factor, double tope) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void finalizarPromocion(String nombre) {
        throw new UnsupportedOperationException("Sin implementar");
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
