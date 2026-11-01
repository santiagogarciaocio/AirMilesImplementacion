package aed.tp2;

import java.util.ArrayList;

class Heap<T extends Comparable<T>> {
    private ArrayList<T> _data;
    private int _tamaño;

    public Heap() {
        _data = new ArrayList<T>();
        _tamaño = 0;
    }

    public int tamaño() {
        return _tamaño;
    }

    public T máximo () {
        if(_tamaño == 0) {throw new UnsupportedOperationException("Vacío");}
        return _data.get(0);
    }

    public Handle<T> agregar(T u) {
        _tamaño += 1;
        _data.add(u);
        int posActual = _data.size() - 1;
        while (_data.get(posActual).compareTo(_data.get((posActual - 1)/ 2)) > 0) { // Mientras tenga más millas disponibles que su padre
            _data.set(posActual, _data.get((posActual - 1)/ 2));
            _data.set((posActual - 1)/2, u);
            posActual = (posActual - 1)/2;
        }
        return new HandleHeap(u);
    }

    public T sacarMáximo() {
        if(_tamaño == 0) {throw new UnsupportedOperationException("Vacío");}
        _tamaño -= 1;
        T res = _data.get(0);
        T último = _data.get(_data.size() - 1);
        _data.set(0, último);
        _data.remove(_data.size() - 1);
        int posActual = 0;
        boolean hayUnHijoMayorQueElPadre = true;
        while (_data.size() > 2*posActual + 2 && hayUnHijoMayorQueElPadre) { // Mientras tenga 2 hijos y no sean ambos menores que él
            int posHijoIzq = 2*posActual + 1;
            int posHijoDer = 2*posActual + 2;
            int posHijoMayor = posHijoDer;
            if (_data.get(posHijoIzq).compareTo(_data.get(posHijoDer)) > 0) {
                posHijoMayor = posHijoIzq;
            }
            if (_data.get(posHijoMayor).compareTo(último) > 0) {
                _data.set(posActual, _data.get(posHijoMayor));
                _data.set(posHijoMayor, último);
                posActual = posHijoMayor;
            }
            else {hayUnHijoMayorQueElPadre = false;}
        }
        if (_data.size() > 2*posActual + 1) {
            int posHijo = 2*posActual +1;
            if (_data.get(posHijo).compareTo(_data.get(posActual)) > 0) {
                _data.set(posActual, _data.get(posHijo));
                _data.set(posHijo, último);
                posActual = posHijo;
            }
        }
        return res;
    }

    public Handle<T> agregarAlFinal(T u) {
        _tamaño += 1;
        _data.add(u);
        return new HandleHeap(u);
    }

    public T getData(int i) {
        return _data.get(i);
    }

    public void setearData(int i, T elem) {
        _data.set(i, elem);
    }

    public class HandleHeap implements Handle<T>{
        private T usuario;

        private HandleHeap(T elem){
            this.usuario = elem;
        }

        public T valor(){
            return this.usuario;
        }

        public void eliminar(){            
            ABB.this.eliminarNodo(this.usuario);
        }

        public int compareTo(Handle<T> otroHandle){
            return this.valor().compareTo(otroHandle.valor());
        }

        public String toString(){            
            return ABB.this.toString();
        }

        public void modificar (T v) {
            this.usuario = v;
        }

        public boolean equals (Handle<T> otroHandle) {
            boolean res = this.valor().compareTo(otroHandle.valor()) == 0;
            return res;
        }

        public int posicionEnHeap() {
            return indiceHeap;
        }

        public void cambiarIndice(int i) {
            indiceHeap = i;
        }
    }
}
