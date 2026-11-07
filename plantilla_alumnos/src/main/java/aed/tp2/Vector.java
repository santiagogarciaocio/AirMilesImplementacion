package aed.tp2;

public class Vector {
    private  int[] data;

    public  Vector(){
        data = new int[10];
    }   

    public void agregar(int indice, int elem){
        while (data.length < indice + 1) {
            redimensionar();
        }
        data[indice] = elem;
    }

    public void redimensionar(){
        int[] nuevoVector = new int[data.length * 10];
        for(int i=0; i < data.length; i++){
            nuevoVector[i] = data[i];
        }
        data = nuevoVector;
    }

    public int obtener(int i){
        return data[i];
    }

    public void modificarPosicion(int pos, int nuevo){
        data[pos] = nuevo;
    }
}
