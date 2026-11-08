package aed.tp2;

import java.util.ArrayList;

class Heap<T extends Comparable<T>> {
    private ArrayList<Nodo> _data; //el heap
    private Vector _posiciones; //posiciones en el heap indexadas por ID
    private int _tamaño;

    public Heap() {
        _data = new ArrayList<Nodo>();
        _posiciones = new Vector();
        _tamaño = 0;
    }

    private class Nodo {
        int ID;
        T elem;

        Nodo (int id, T elem) {
            this.ID = id;
            this.elem = elem;
        }
    }

    public int tamaño() {
        return _tamaño;
    }

    public Handle<T> agregar(int clave, T u) {
        Nodo nuevoNodo = new Nodo(clave, u);
        _data.add(nuevoNodo);
        _posiciones.agregar(clave, _tamaño);
        siftUp(nuevoNodo.ID);
        _tamaño += 1;
        return new HandleHeap(u);
    }

    public T desencolar(){ //requiere que haya al menos 1 elemento
        T maximo = _data.get(0).elem;
        if (_tamaño > 1) { //si hay un solo elemento y lo sacamos, el siftDown se rompe
            intercambiar(_data.get(0).ID, _data.get(_tamaño - 1).ID); 
            _data.remove(_tamaño - 1);
            _tamaño -= 1;
            siftDown(_data.get(0).ID);
        }
        else {
            _data.remove(_tamaño - 1);
            _tamaño -= 1;
        }
        return maximo;
    }

    public void siftUp(int id) { //le pasamos el ID
        int i = _posiciones.obtener(id); //del ID pasamos a la posición en el heap
        while (i > 0) {
            int padre = (i - 1)/ 2;
            if (_data.get(i).elem.compareTo(_data.get(padre).elem) > 0) { //comparamos los usuarios
                intercambiar(_data.get(i).ID, _data.get(padre).ID); //volvemos a pasar el ID
                i = padre;
            }
            else return; 
        }
    }

    public void siftDown(int id) {
        int i = _posiciones.obtener(id);
        while ((i * 2) + 1 < tamaño()) { //mientras tenga por lo menos algún hijo
            int izq = (i * 2) + 1;
            int der = (i * 2) + 2;
            int hijoMayor;
            if (der < tamaño() && _data.get(der).elem.compareTo(_data.get(izq).elem) > 0) { //si der es igual a tamaño entonces no existe 
                hijoMayor = der;
            }
            else {
                hijoMayor = izq;
            } //solo hace falta comparar con el hijo mayor, ya que si soy más grande que el hijo mayor entonces soy más grande que ambos, y si soy más chico tengo que swappear con el más grande
            if (_data.get(i).elem.compareTo(_data.get(hijoMayor).elem) < 0) {  
                intercambiar(_data.get(i).ID, _data.get(hijoMayor).ID);
                i = hijoMayor;
            }
            else return; 
        }
    }

    private void intercambiar(int id_1, int id_2) {
        int posicionEnHeap_id_1 = _posiciones.obtener(id_1); //a partir de sus IDs averiguo
        int posicionEnHeap_id_2 = _posiciones.obtener(id_2); //sus posiciones en el heap

        Nodo nodo_1 = _data.get(posicionEnHeap_id_1); //me guardo los nodos de esas posiciones
        Nodo nodo_2 = _data.get(posicionEnHeap_id_2); // en 2 variables 

        _data.set(posicionEnHeap_id_1, nodo_2); //los cambio de lugar en el heap
        _data.set(posicionEnHeap_id_2, nodo_1);

        _posiciones.modificarPosicion(id_1, posicionEnHeap_id_2); //actualizo _posiciones con sus 
        _posiciones.modificarPosicion(id_2, posicionEnHeap_id_1); //nuevas posiciones
    }

    public class HandleHeap implements Handle<T>{
        private T elem;

        private HandleHeap(T elem){
            this.elem = elem;
        }

        public T valor(){
            return this.elem;
        }

        public void eliminar(){            
            this.elem = null;
        }

        public void modificar (T v) {
            this.elem = v;
        }

        public boolean equals (Handle<T> otroHandle) {
            boolean res = this.valor().compareTo(otroHandle.valor()) == 0;
            return res;
        }
    }
}
