package arboles;

public class Nodo {
    private char dato;
    private Nodo LigaI, LigaD;

    public Nodo (char dato) {
        this.dato = dato;
        this.LigaI = null;
        this.LigaD = null;
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
}
