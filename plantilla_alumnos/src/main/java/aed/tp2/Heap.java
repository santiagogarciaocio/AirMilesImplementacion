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
        siftUp(_tamaño - 1);
        return new HandleHeap(u);
    }

    public T desencolar(){
        _tamaño -= 1;
        T maximo = _data.get(0);
        intercambiar(0, _tamaño);
        _data.removeLast();
        siftDown(0);
        return maximo;
    }

    private void intercambiar(int i, int j) {
        T elem_1 = _data.get(i);
        T elem_2 = _data.get(j);
        _data.set(i, elem_2);
        _data.set(j, elem_1);
        if (_data.get(i) instanceof Usuario){
            ((Usuario) _data.get(i)).setIndiceHeap(i);
            ((Usuario) _data.get(j)).setIndiceHeap(j);
        }
    }

    public void siftUp(int i) {
        while (i > 0) {
            int padre = (i - 1)/ 2;
            if (_data.get(i).compareTo(_data.get(padre)) > 0) {
                intercambiar(i, padre);
                i = padre;
            }
            else return; 
        }
    }

    public void siftDown(int i) {
        while ((i * 2) + 1 < tamaño()) {
            int izq = (i * 2) + 1;
            int der = (i * 2) + 2;
            int hijoMayor;
            if (der < tamaño() && _data.get(der).compareTo(_data.get(izq)) > 0) {
                hijoMayor = der;
            }
            else {
                hijoMayor = izq;
            }
            if (_data.get(i).compareTo(_data.get(hijoMayor)) < 0) {
                intercambiar(i, hijoMayor);
                i = hijoMayor;
            }
            else {
                return; 
            }
        }}

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
    }
}
