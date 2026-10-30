package aed.tp2;

public class TriePromociones {
    private Nodo[] raiz;

    private class Nodo {
        private Promocion promo;
        private Nodo[] hijo;

        public Nodo(){
            promo = null;
            hijo = null;
        }
        
    }

    public TriePromociones(){
        raiz = new Nodo[26];
        completarNivel(raiz);
        agregarPromocion("NoPromo", 1);
    }

    private void completarNivel(Nodo[] nivel){
        for(int i=0;i<26;i++){
            nivel[i] = new Nodo();
        }
    }

    private int indiceDeChar(char c){
        return Character.valueOf(c)-97;
    }

    public boolean pertenece(String promocion){
        promocion = promocion.toLowerCase();
        int i = 0;
        Nodo[] nivel = raiz;
        while ((i < promocion.length()) && (nivel[indiceDeChar(promocion.charAt(i))].hijo != null)){
            nivel = nivel[indiceDeChar(promocion.charAt(i))].hijo;
            i ++;
        }
        if((i != promocion.length()) || (nivel[indiceDeChar(promocion.charAt(i-1))].promo == null)){
            return false;
        }
        else{
            return nivel[indiceDeChar(promocion.charAt(i-1))].promo.nombre().compareTo(promocion) == 0;
        }
    }

    public void agregarPromocion(String promocion, double factor){
        promocion = promocion.toLowerCase();
        int i = 0;
        Nodo[] nivel = raiz;
        while (i < promocion.length()){
            if(nivel[indiceDeChar(promocion.charAt(i))].hijo != null){
                nivel = nivel[indiceDeChar(promocion.charAt(i))].hijo;
                i ++;
            }
            else{
                nivel[indiceDeChar(promocion.charAt(i))].hijo = new Nodo[26];
                completarNivel(nivel[indiceDeChar(promocion.charAt(i))].hijo);
                nivel = nivel[indiceDeChar(promocion.charAt(i))].hijo;
                i ++;
            }
        }
        nivel[indiceDeChar(promocion.charAt(i-1))].promo = new Promocion(promocion, factor);
    }

    public void agregarPromocion(String promocion, double factor, int tope){
        promocion = promocion.toLowerCase();
        int i = 0;
        Nodo[] nivel = raiz;
        while (i < promocion.length()){
            if(nivel[indiceDeChar(promocion.charAt(i))].hijo != null){
                nivel = nivel[indiceDeChar(promocion.charAt(i))].hijo;
                i ++;
            }
            else{
                nivel[indiceDeChar(promocion.charAt(i))].hijo = new Nodo[26];
                completarNivel(nivel[indiceDeChar(promocion.charAt(i))].hijo);
                nivel = nivel[indiceDeChar(promocion.charAt(i))].hijo;
                i ++;
            }
        }
        nivel[indiceDeChar(promocion.charAt(i-1))].promo = new Promocion(promocion, factor, tope);
    }

    public void eliminar(String promocion){
        promocion = promocion.toLowerCase();
        int i = 0;
        Nodo[] nivel = raiz;
        while ((i < promocion.length()) && (nivel[indiceDeChar(promocion.charAt(i))].hijo != null)){
            nivel = nivel[indiceDeChar(promocion.charAt(i))].hijo;
            i ++;
        }
        nivel[indiceDeChar(promocion.charAt(i-1))].promo = null;
    }

    public Promocion promocion(String promocion){
        promocion = promocion.toLowerCase();
        int i = 0;
        Nodo[] nivel = raiz;
        while ((i < promocion.length()) && (nivel[indiceDeChar(promocion.charAt(i))].hijo != null)){
            nivel = nivel[indiceDeChar(promocion.charAt(i))].hijo;
            i ++;
        }        
        return nivel[indiceDeChar(promocion.charAt(i-1))].promo;
        
    }

}
