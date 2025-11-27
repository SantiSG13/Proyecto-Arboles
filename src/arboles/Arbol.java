package arboles;

import javax.swing.*;

public class Arbol {
    private Nodo Raiz;

    public Arbol() {
        this.Raiz = null;
    }

    public Nodo getRaiz() {
        return Raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.Raiz = raiz;
    }

    public void ConstruirArbol(String cadenaOriginal) {
        char[] vectorCaracteres = cadenaOriginal.toCharArray();

        for (int i = 0; i < vectorCaracteres.length; i++) {
            if (Raiz == null) {
                Raiz = new Nodo(vectorCaracteres[i]);
            } else {
                Nodo actual = Raiz;
                boolean bandera = false; // reiniciar por cada carácter
                while (bandera == false) {
                    if (actual.getDato() < vectorCaracteres[i]) {
                        if (actual.getLigaD() == null) {
                            Nodo nuevoNodo = new Nodo(vectorCaracteres[i]);
                            actual.setLigaD(nuevoNodo);
                            bandera = true;
                        } else {
                            actual = actual.getLigaD();
                        }
                    } else if (actual.getDato() > vectorCaracteres[i]) {
                        if (actual.getLigaI() == null) {
                            Nodo nuevoNodo = new Nodo(vectorCaracteres[i]);
                            actual.setLigaI(nuevoNodo);
                            bandera = true;
                        } else {
                            actual = actual.getLigaI();
                        }
                    } else {
                        // igual: no insertar
                        bandera = true;
                    }
                }
            }
        }
    }

    public void MostrarInOrden(Nodo Raiz) {
        StringBuilder resultado = new StringBuilder();
        MostrarInOrdenAux(Raiz, resultado);
        JOptionPane.showMessageDialog(null, resultado.toString(), "Recorrido InOrden", JOptionPane.INFORMATION_MESSAGE);
    }

    private void MostrarInOrdenAux(Nodo Raiz, StringBuilder resultado) {
        if (Raiz != null) {
            MostrarInOrdenAux(Raiz.getLigaI(), resultado);
            resultado.append(Raiz.getDato()).append("   ");
            MostrarInOrdenAux(Raiz.getLigaD(), resultado);
        }
    }

    public void MostrarPreOrden(Nodo Raiz) {
        StringBuilder resultado = new StringBuilder();
        MostrarPreOrdenAux(Raiz, resultado);
        JOptionPane.showMessageDialog(null, resultado.toString(), "Recorrido PreOrden", JOptionPane.INFORMATION_MESSAGE);
    }

    private void MostrarPreOrdenAux(Nodo Raiz, StringBuilder resultado) {
        if (Raiz != null) {
            resultado.append(Raiz.getDato()).append("   ");
            MostrarPreOrdenAux(Raiz.getLigaI(), resultado);
            MostrarPreOrdenAux(Raiz.getLigaD(), resultado);
        }
    }

    public void MostrarPostOrden(Nodo Raiz) {
        StringBuilder resultado = new StringBuilder();
        MostrarPostOrdenAux(Raiz, resultado);
        JOptionPane.showMessageDialog(null, resultado.toString(), "Recorrido PosOrden", JOptionPane.INFORMATION_MESSAGE);
    }

    private void MostrarPostOrdenAux(Nodo Raiz, StringBuilder resultado) {
        if (Raiz != null) {
            MostrarPostOrdenAux(Raiz.getLigaI(), resultado);
            MostrarPostOrdenAux(Raiz.getLigaD(), resultado);
            resultado.append(Raiz.getDato()).append("   ");
        }
    }

    public void SoloHijos(Nodo Raiz) {
        int total = ContarSoloHijos(Raiz);
        JOptionPane.showMessageDialog(null, "Nodos con solo un hijo: " + total, "Conteo de Nodos", JOptionPane.INFORMATION_MESSAGE);
    }

    private int ContarSoloHijos(Nodo Raiz) {

        if (Raiz == null) {
            return 0;
        }

        int cont = 0;

        // Verificar si el nodo actual tiene solo un hijo
        if ((Raiz.getLigaI() != null && Raiz.getLigaD() == null) || (Raiz.getLigaD() != null && Raiz.getLigaI() == null)) {
            cont = 1;
        }

        // Sumar los conteos de los subárboles
        cont += ContarSoloHijos(Raiz.getLigaI());
        cont += ContarSoloHijos(Raiz.getLigaD());

        return cont;
    }
}


