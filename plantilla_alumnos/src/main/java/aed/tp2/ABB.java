package aed.tp2;

import aed.tp2.Trie.Nodo;

class ABB<T extends Comparable<T>> {

    private Nodo _raíz;
    private int _cardinal;
    private int _altura;

    private class Nodo {
        T valor;
        Nodo izq;
        Nodo der;
        Nodo padre;

        Nodo (T v) {
            valor = v;
            izq = null;
            der = null;
            padre = null;
        }
    }
    
    private class HandleABB implements Handle<T>{
        private Nodo nodo;

        private HandleABB(Nodo n){
            this.nodo = n;
        }

        public T valor(){
            return this.nodo.valor;
        }

        public void eliminar(){
            ABB.this.eliminarNodo(this.nodo.valor);
        }

        public int compareTo(Handle<T> otroHandle){
            return this.valor().compareTo(otroHandle.valor());
        }

        public String toString(){
            String res = "" + this.valor();
            return res;
        }

        public void modificar (T v) {
            this.nodo.valor = v;
        }

        public boolean equals (Handle<T> otroHandle) {
            boolean res = this.valor().compareTo(otroHandle.valor()) == 0;
            return res;
        }
    }

    public ABB() {
        _raíz = null;
        _cardinal = 0;
        _altura = 0;
    }

    public int cardinal() {
        return _cardinal;
    }

    public boolean pertenece(T elem){
        return perteneceAux(_raíz, elem);
    }

    private boolean perteneceAux (Nodo nodo, T elem) {
        if (nodo == null) {return false;}
        if (elem.compareTo(nodo.valor) == 0) {return true;}
        if (elem.compareTo(nodo.valor) > 0) {return perteneceAux (nodo.der, elem);}
        else {return perteneceAux (nodo.izq, elem);}
    }

    public Handle<T> insertar(T elem){
        if(pertenece(elem)){return;}
        Nodo nuevo = new Nodo(elem);        
        if(_raiz == null){_raiz = nuevo; _cardinalidad ++; }
        else{
            Nodo actual = _raiz;        
            while(elem.compareTo(actual.valor) != 0){
                if(elem.compareTo(actual.valor) < 0){
                    if(actual.izq != null){
                        actual = actual.izq;
                    }else{
                        actual.izq = nuevo;
                        nuevo.padre = actual;
                        _cardinalidad ++;
                    }
                }
                else{
                    if(actual.der != null){
                        actual = actual.der;
                    }else{
                        actual.der = nuevo;
                        nuevo.padre = actual;
                        _cardinalidad ++;
                    }
                }}}
        return new HandleABB (new Nodo(elem));
    }

    public void eliminarNodo (T elem) {
        if(!pertenece(elem)){ //si no pertenece no hago nada
            return;
        }
        Nodo actual = _raiz;
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
            if(actual == _raiz){
                if(actual.der != null){
                    _raiz = actual.der;
                    actual.der.padre = null;
                }else{
                    _raiz = actual.izq;
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
            if(actual == _raiz){
                _raiz = null;
            }else{
                if(actual == actual.padre.izq){
                    actual.padre.izq = null;
                }else{actual.padre.der = null;}

            }}
        _cardinalidad --;
        //Aca tambien deberiamos pedir que, via handle, se elimine el usuario en el heap
    }

    public class ABB_Iterador {
        private Nodo _actual;

        public ABB_Iterador(){
            _actual = _raiz;
            while(_actual.izq != null){
                _actual = _actual.izq; 
            }
        }

        public boolean haySiguiente() {            
            return _actual.valor.compareTo(maximo()) != 0;
        }
    
        public T siguiente() {            
            T devolver = _actual.valor;
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

    


    
