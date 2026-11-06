package aed.tp2;


public class Trie<T> {

    private Nodo<T> raiz;

    private class Nodo<T> {
        private Nodo<T>[] letras;
        private T elem;
        private int cantHijos;

        public Nodo() {
            letras = (Nodo<T>[]) new Nodo[256];
            cantHijos = 0;
        }

        public int indice(char c) {
            return (int) c;
        }

        public boolean haySiguiente(char c) {
            return letras[indice(c)] != null;
        }

        public Nodo<T> Siguiente(char c) {
            return letras[indice(c)];
        }

        public Nodo<T> agregarNodo(char c) {
            letras[indice(c)] = new Nodo<>();
            return letras[indice(c)];
        }
    }

    public Trie() {
        raiz = new Nodo<>();
    }

    public boolean pertenece(String nombreElemento) {
        int i = 0;
        Nodo<T> nodo_actual = raiz;
        while (i < nombreElemento.length()) {
            if (!nodo_actual.haySiguiente(nombreElemento.charAt(i))) {
                return false;
            }
            else {
                nodo_actual = nodo_actual.Siguiente(nombreElemento.charAt(i));
                i++;
            }
        }
        if(nodo_actual.elem == null){
            return false;
        }else{
            return true;
        }
    }

    public void agregar(String nombreElemento, T elemento) {
        int i = 0;
        Nodo<T> nodo_actual = raiz;
        while (i < nombreElemento.length()) {
            if (nodo_actual.haySiguiente(nombreElemento.charAt(i))) {
                nodo_actual.cantHijos ++;
                nodo_actual = nodo_actual.Siguiente(nombreElemento.charAt(i));
                i++;
            }
            else {
                nodo_actual.cantHijos ++;
                nodo_actual = nodo_actual.agregarNodo(nombreElemento.charAt(i));
                i++;
            }
        }
        nodo_actual.cantHijos ++;
        nodo_actual.elem = elemento;
        return;
    }


    public void eliminar(String nombreElemento) {
        int i = 0;
        Nodo<T> nodo_actual = raiz;
        while (i < nombreElemento.length()) {
            nodo_actual.cantHijos --;            
            nodo_actual = nodo_actual.Siguiente(nombreElemento.charAt(i));
            if(nodo_actual.cantHijos == 1){
                break;}
            i++;            
            }
        if(i == nombreElemento.length()){
            nodo_actual.elem = null;
        }else{
            podar(nombreElemento, i);
        }
    }

    private void podar(String nombreElemento, int podarEn){
        int i = 0;
        Nodo<T> nodo_actual = raiz;
        while (i < podarEn) {
            nodo_actual = nodo_actual.Siguiente(nombreElemento.charAt(i));            
            i++;            
            }
        nodo_actual.letras[nombreElemento.charAt(i)] = null;
    }

    public T nombreElemento(String nombreElemento) {
        int i = 0;
        Nodo<T> nodo_actual = raiz;
        while (i < nombreElemento.length()) {
            nodo_actual = nodo_actual.Siguiente(nombreElemento.charAt(i));
            i++;
            
        }
        return nodo_actual.elem;
    }

}
