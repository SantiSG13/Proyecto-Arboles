package arboles;

public class Nodo {
    private char dato;
    private Nodo LigaI, LigaD;
    private int altura;
    private int fe; // Factor de Equilibrio

    public Nodo(char dato) {
        this.dato = dato;
        this.LigaI = null;
        this.LigaD = null;
        this.altura = 1; // Altura inicial de una hoja es 1
        this.fe = 0;
    }

    public char getDato() {
        return dato;
    }

    public void setDato(char dato) {
        this.dato = dato;
    }

    public Nodo getLigaI() {
        return LigaI;
    }

    public void setLigaI(Nodo ligaI) {
        this.LigaI = ligaI;
    }

    public Nodo getLigaD() {
        return LigaD;
    }

    public void setLigaD(Nodo ligaD) {
        this.LigaD = ligaD;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getFe() {
        return fe;
    }

    public void setFe(int fe) {
        this.fe = fe;
    }
}
