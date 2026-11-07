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

    public int minimo(){
        Nodo actual = _raíz;
        while(! (actual.izq == null)){
            actual = actual.izq;
        }
        return actual.ID;
    }

    public int maximo(){
        Nodo actual = _raíz;
        while(! (actual.der == null)){
            actual = actual.der;
        }
        return actual.ID;        
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

    public class ABB_Iterador {
        private Nodo _actual;

        public ABB_Iterador(){
            if (_raíz == null){
                return; 
            }
            _actual = _raíz;
            while(_actual.izq != null){
                _actual = _actual.izq; 
            }
        }

        public boolean haySiguiente() {
            if (_raíz == null){
                return false;
            }   
            return _actual.ID != maximo();
        }
    
        public T siguiente() {            
            T devolver = _actual.handle.valor();
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

    


    
