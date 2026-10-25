package aed.tp2;

public class Trie {

    private Nodo raiz;

    private class Nodo {
        private Nodo[] letras;
        private Promocion promo;

        public Nodo() {
            letras = new Nodo[26];
            promo = null;
        }

        public Nodo(Promocion promocion) {
            letras = new Nodo[26];
            promo = promocion;
        }

        public int indice(char c) {
            return c - 'a';
        }

        public boolean haySiguiente(char c) {
            return letras[indice(c)] != null;
        }

        public Nodo Siguiente(char c) {
            return letras[indice(c)];
        }

        public Nodo agregarNodo(char c) {
            letras[indice(c)] = new Nodo();
            return letras[indice(c)];
        }
    }

    public Trie() {
        raiz = new Nodo();
    }

    public boolean pertenece(String promocion) {
        int i = 0;
        Nodo nodo_actual = raiz;
        while (i < promocion.length()) {
            if (!nodo_actual.haySiguiente(promocion.charAt(i))) {
                return false;
            }
            else {
                nodo_actual = nodo_actual.Siguiente(promocion.charAt(i));
                i++;
            }
        }
        return nodo_actual.promo != null;
    }

    public void agregar(String promocion, double factor) {
        int i = 0;
        Nodo nodo_actual = raiz;
        while (i < promocion.length()) {
            if (nodo_actual.haySiguiente(promocion.charAt(i))) {
                nodo_actual = nodo_actual.Siguiente(promocion.charAt(i));
                i++;
            }
            else {
                nodo_actual = nodo_actual.agregarNodo(promocion.charAt(i));
                i++;
            }
        }
        nodo_actual.promo = new Promocion(promocion, factor);
        return;
    }

    public void eliminar(String promocion) {
        int i = 0;
        Nodo nodo_actual = raiz;
        while (i < promocion.length()) {
            nodo_actual = nodo_actual.Siguiente(promocion.charAt(i));
            i++;
        }
        nodo_actual.promo = null;
    }

}
