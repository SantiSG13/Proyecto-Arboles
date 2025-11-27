package arboles;

public class Principal {
    public static void main(String[] args) {
        Arbol arbol = new Arbol();
        String cadena = "DFANBGCE";
        arbol.ConstruirArbol(cadena);


        arbol.SoloHijos(arbol.getRaiz());
    }
}
