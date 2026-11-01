package aed.tp2;

class ABB<T> {

    private Nodo _raíz;
    private int _cardinal;

    private class Nodo {
        int ID;
        Handle<T> handle;
        Nodo izq;
        Nodo der;
        Nodo padre;

        Nodo (int id, Handle<T> handle) {
            this.ID = id;
            this.handle = handle;
            izq = null;
            der = null;
            padre = null;
        }
    }

    public ABB() {
        _raíz = null;
        _cardinal = 0;
    }

    public int cardinal() {
        return _cardinal;
    }

    public Handle<T> minimo(){
        Nodo actual = _raíz;
        while(! (actual.izq == null)){
            actual = actual.izq;
        }
        return actual.handle;
    }

    public Handle<T> maximo(){
        Nodo actual = _raíz;
        while(! (actual.der == null)){
            actual = actual.der;
        }
        return actual.handle;        
    }

    public boolean pertenece(T elem){ // esta creo que no se puede hacer en O(log n) ya que hay que recorrer todos los nodos
        return perteneceAux(_raíz, elem);
    }

    private boolean perteneceAux (Nodo nodo, T elem) {
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
            if (id < actual.ID) {
                actual = actual.izq;
            } else if (id > actual.ID) {
                actual = actual.der;
            } else {
                return true;
            }}        
        return false;
    }

    public Handle<T> buscar(int id){
        if(! pertenece(id)){
            throw new UnsupportedOperationException("El usuario no existe");
        }
        else{
            Nodo actual = _raíz;
            while(id != actual.ID){
                if(id < actual.ID){
                    actual = actual.izq;
                }
                else{
                    actual = actual.der;
                }
            }
            return actual.handle;
        }        
    }

    public void insertar(int id, Handle<T> elem){ //asumimos que no te pueden pasar un usuario repetido por el requiere
        Nodo nuevo = new Nodo(id, elem);        
        if(_raíz == null){
            _raíz = nuevo; 
            _cardinal ++;
            return; 
        }
        else{
            Nodo actual = _raíz;        
            while(true){
                if(id < actual.ID){
                    if(actual.izq != null){
                        actual = actual.izq;
                    }else{
                        actual.izq = nuevo;
                        nuevo.padre = actual;
                        _cardinal ++;
                        return;
                    }
                }
                else{
                    if(actual.der != null){
                        actual = actual.der;
                    }else{
                        actual.der = nuevo;
                        nuevo.padre = actual;
                        _cardinal ++;
                        return;
                    }
                }}}
    }
/* En ningun momento usamos eliminar. No parece ser una opcion de AirMilles eliminar un usuario */
    public void eliminarNodo (T elem) {
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
            ABB<T>.ABB_Iterador iterador = new ABB_Iterador();
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
            return _actual.ID != maximo();
        }
    
        public int siguiente() {            
            int devolver = _actual.ID;
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
                    while(nuevoActual.izq.ID != valorAnterior.ID){
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

    


    
