package arboles;

public class Principal {
    public static void main(String[] args) {
        Arbol arbol = new Arbol();
        String cadena = "DFGBICEA";
        arbol.ConstruirArbol(cadena);
        arbol.MostrarInOrden(arbol.getRaiz());
        arbol.MostrarPreOrden(arbol.getRaiz());
        arbol.MostrarPostOrden(arbol.getRaiz());
    }
}
