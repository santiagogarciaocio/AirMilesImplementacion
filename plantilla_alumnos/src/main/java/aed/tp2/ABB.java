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
        // (...)
        return new HandleABB (new Nodo(elem));
    }

    public void eliminarNodo (T elem) {}
}

    


    
