package aed.tp2;

import aed.tp2.TrieMalo.Nodo;

class ABBUsuarios {

    private Nodo _raíz;
    private int _cardinal;
    private int _altura;
    /*Altura no lo usamos para nada, lo podemos eliminar? */

    private class Nodo {
        Usuario valor;
        Nodo izq;
        Nodo der;
        Nodo padre;

        Nodo (Usuario v) {
            valor = v;
            izq = null;
            der = null;
            padre = null;
        }
    }
    
    private class HandleABB implements Handle<Usuario>{
        private Nodo nodo;

        private HandleABB(Nodo n){
            this.nodo = n;
        }

        public Usuario valor(){
            return this.nodo.valor;
        }

        public void eliminar(){            
            ABBUsuarios.this.eliminarNodo(this.nodo.valor);
        }

        public int compareTo(Handle<Usuario> otroHandle){
            return this.valor().compareTo(otroHandle.valor());
        }

        public String toString(){            
            return ABBUsuarios.this.toString();
        }

        public void modificar (Usuario v) {
            this.nodo.valor = v;
        }

        public boolean equals (Handle<Usuario> otroHandle) {
            boolean res = this.valor().compareTo(otroHandle.valor()) == 0;
            return res;
        }
    }

    public ABBUsuarios() {
        _raíz = null;
        _cardinal = 0;
        _altura = 0;
    }

    public int cardinal() {
        return _cardinal;
    }

    public Usuario minimo(){
        Nodo actual = _raíz;
        while(! (actual.izq == null)){
            actual = actual.izq;
        }
        return actual.valor;
    }

    public Usuario maximo(){
        Nodo actual = _raíz;
        while(! (actual.der == null)){
            actual = actual.der;
        }
        return actual.valor;        
    }

    public boolean pertenece(Usuario elem){
        return perteneceAux(_raíz, elem);
    }

    private boolean perteneceAux (Nodo nodo, Usuario elem) {
        if (nodo == null) {return false;}
        if (elem.compareTo(nodo.valor) == 0) {return true;}
        if (elem.compareTo(nodo.valor) > 0) {return perteneceAux (nodo.der, elem);}
        else {return perteneceAux (nodo.izq, elem);}
    }

    public boolean pertenece(int id){
        if (_cardinal == 0) {
            return false;
        }
        Nodo actual = _raíz;        
        while (actual != null) {         
            if (id < actual.valor.ID()) {
                actual = actual.izq;
            } else if (id < actual.valor.ID()) {
                actual = actual.der;
            } else {
                return true;
            }}        
        return false;
    }

    public Usuario usuario(int id){
        if(! pertenece(id)){
            throw new UnsupportedOperationException("El usuario no existe");
        }
        else{
            Nodo actual = _raíz;
            while(id != actual.valor.ID()){
                if(id < actual.valor.ID()){
                    actual = actual.izq;
                }
                else{
                    actual = actual.der;
                }
            }
            return actual.valor;
        }        
    }

    public Handle<Usuario> insertar(Usuario elem){
        if(pertenece(elem)){
            return null;
        }
        Nodo nuevo = new Nodo(elem);        
        if(_raíz == null){
            _raíz = nuevo; 
            _cardinal ++; 
        }
        else{
            Nodo actual = _raíz;        
            while(elem.compareTo(actual.valor) != 0){
                if(elem.compareTo(actual.valor) < 0){
                    if(actual.izq != null){
                        actual = actual.izq;
                    }else{
                        actual.izq = nuevo;
                        nuevo.padre = actual;
                        _cardinal ++;
                    }
                }
                else{
                    if(actual.der != null){
                        actual = actual.der;
                    }else{
                        actual.der = nuevo;
                        nuevo.padre = actual;
                        _cardinal ++;
                    }
                }}}
        return new HandleABB (nuevo);
    }
/* En ningun momento usamos eliminar. No parece ser una opcion de AirMilles eliminar un usuario */
    public void eliminarNodo (Usuario elem) {
        if(!pertenece(elem)){ //si no pertenece no hago nada
            return;
        }
        Nodo actual = _raíz;
        while(actual.valor.compareTo(elem) != 0){ //Busco elemento a eliminar
            if(elem.compareTo(actual.valor) < 0){
                actual = actual.izq;            
            }else if(elem.compareTo(actual.valor) > 0){
                actual = actual.der;
            }
        }
        if((actual.der != null) && (actual.izq !=null)){ //caso dos arboles hijos
            Nodo sucesorInmediato = actual.der; // encuentro el sucesor inmediato            
            while (sucesorInmediato.izq != null){
                sucesorInmediato = sucesorInmediato.izq;
            }
            actual.valor = sucesorInmediato.valor;

            if(sucesorInmediato == actual.der){
                actual.der = sucesorInmediato.der;
                if(sucesorInmediato.der != null){
                    sucesorInmediato.der.padre = actual;
                }
            }else{                
                sucesorInmediato.padre.izq = sucesorInmediato.der;
                if(sucesorInmediato.der != null){
                    sucesorInmediato.der.padre = sucesorInmediato.padre;
                }
            }}
        else if((actual.der != null) || (actual.izq !=null)){ //caso un arbol
            if(actual == _raíz){
                if(actual.der != null){
                    _raíz = actual.der;
                    actual.der.padre = null;
                }else{
                    _raíz = actual.izq;
                    actual.izq.padre = null;
                }
            }
            else if(actual ==actual.padre.izq){
                if(actual.der != null){
                    actual.der.padre = actual.padre;
                    actual.padre.izq = actual.der;
                }else{
                    actual.izq.padre = actual.padre;
                    actual.padre.izq = actual.izq;
                }
            }else{
                if(actual.der != null){
                    actual.der.padre = actual.padre;
                    actual.padre.der = actual.der;
                }else{
                    actual.izq.padre = actual.padre;
                    actual.padre.der = actual.izq;
                }}}
        else{ //caso eliminar hoja
            if(actual == _raíz){
                _raíz = null;
            }else{
                if(actual == actual.padre.izq){
                    actual.padre.izq = null;
                }else{actual.padre.der = null;}

            }}
        _cardinal --;
    }

    public String toString(){
        if(_cardinal == 0){return "{}";}
        else{
            String res = "{";
            ABBUsuarios.ABB_Iterador iterador = new ABB_Iterador();
            while(iterador.haySiguiente()){
                res = res+iterador.siguiente().toString();
                if(iterador.haySiguiente()){res = res+",";}
            }
            if(_cardinal == 1) {
                res = res+maximo();
            }else{res = res+","+maximo();}
            return res+"}";
        }
    }

    public class ABB_Iterador {
        private Nodo _actual;

        public ABB_Iterador(){
            _actual = _raíz;
            while(_actual.izq != null){
                _actual = _actual.izq; 
            }
        }

        public boolean haySiguiente() {            
            return _actual.valor.compareTo(maximo()) != 0;
        }
    
        public Usuario siguiente() {            
            Usuario devolver = _actual.valor;
            if(haySiguiente()){
                //caso si tiene derecha
                if(_actual.der != null){
                    Nodo nuevoActual = _actual.der;
                    while(nuevoActual.izq != null){
                        nuevoActual = nuevoActual.izq;                    
                    }
                    _actual = nuevoActual;
                }else{
                    Nodo nuevoActual = _actual.padre;
                    Nodo valorAnterior = _actual;
                    while(nuevoActual.izq.valor.compareTo(valorAnterior.valor) != 0){
                        nuevoActual = nuevoActual.padre;
                        valorAnterior = valorAnterior.padre;
                    }
                    _actual = nuevoActual;
                }}           
            return devolver;
        }
    }

    public ABB_Iterador iterador() {
        return new ABB_Iterador();
    }
}

    


    
