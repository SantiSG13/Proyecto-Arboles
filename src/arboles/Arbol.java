package arboles;

import javax.swing.*;
import java.awt.*;

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

    // --- Lógica AVL ---

    // Obtener altura de un nodo
    private int obtenerAltura(Nodo n) {
        if (n == null) {
            return 0;
        }
        return n.getAltura();
    }

    // Obtener factor de equilibrio
    private int obtenerFactorEquilibrio(Nodo n) {
        if (n == null) {
            return 0;
        }
        return obtenerAltura(n.getLigaI()) - obtenerAltura(n.getLigaD());
    }

    // Actualizar altura de un nodo
    private void actualizarAltura(Nodo n) {
        if (n != null) {
            n.setAltura(Math.max(obtenerAltura(n.getLigaI()), obtenerAltura(n.getLigaD())) + 1);
        }
    }

    // Rotación Simple Derecha
    private Nodo rotacionDerecha(Nodo P) {
        Nodo Q = P.getLigaI();
        Nodo aux = Q.getLigaD();

        // Rotación
        Q.setLigaD(P);
        P.setLigaI(aux);

        // Actualizar alturas
        actualizarAltura(P);
        actualizarAltura(Q);

        return Q; // Nueva raíz
    }

    // Rotación Simple Izquierda
    private Nodo rotacionIzquierda(Nodo P) {
        Nodo Q = P.getLigaD();
        Nodo aux = Q.getLigaI();

        // Rotación
        Q.setLigaI(P);
        P.setLigaD(aux);

        // Actualizar alturas
        actualizarAltura(P);
        actualizarAltura(Q);

        return Q; // Nueva raíz
    }

    // Balancear nodo
    private Nodo balancear(Nodo n) {
        actualizarAltura(n);
        int balance = obtenerFactorEquilibrio(n);
        n.setFe(balance); // Actualizar FE para visualización/debug

        // Caso Izquierda-Izquierda (Rotación Derecha)
        if (balance > 1 && obtenerFactorEquilibrio(n.getLigaI()) >= 0) {
            return rotacionDerecha(n);
        }

        // Caso Derecha-Derecha (Rotación Izquierda)
        if (balance < -1 && obtenerFactorEquilibrio(n.getLigaD()) <= 0) {
            return rotacionIzquierda(n);
        }

        // Caso Izquierda-Derecha (Rotación Doble Izquierda-Derecha)
        if (balance > 1 && obtenerFactorEquilibrio(n.getLigaI()) < 0) {
            n.setLigaI(rotacionIzquierda(n.getLigaI()));
            return rotacionDerecha(n);
        }

        // Caso Derecha-Izquierda (Rotación Doble Derecha-Izquierda)
        if (balance < -1 && obtenerFactorEquilibrio(n.getLigaD()) > 0) {
            n.setLigaD(rotacionDerecha(n.getLigaD()));
            return rotacionIzquierda(n);
        }

        return n;
    }

    // Construir árbol (Modificado para usar inserción AVL)
    public void ConstruirArbol(String cadenaOriginal) {
        Raiz = null; // Reiniciar árbol
        char[] vectorCaracteres = cadenaOriginal.toCharArray();
        for (int i = 0; i < vectorCaracteres.length; i++) {
            insertarDato(vectorCaracteres[i]);
        }
    }

    // Insertar dato (AVL)
    public boolean insertarDato(char dato) {
        if (buscarNodo(Raiz, dato) != null) {
            return false; // Ya existe
        }
        Raiz = insertarAvlAux(Raiz, dato);
        return true;
    }

    private Nodo insertarAvlAux(Nodo nodo, char dato) {
        if (nodo == null) {
            return new Nodo(dato);
        }

        if (dato < nodo.getDato()) {
            nodo.setLigaI(insertarAvlAux(nodo.getLigaI(), dato));
        } else if (dato > nodo.getDato()) {
            nodo.setLigaD(insertarAvlAux(nodo.getLigaD(), dato));
        } else {
            return nodo; // Duplicados no permitidos
        }

        return balancear(nodo);
    }

    // Helper method to search for a node (needed for insertarDato and eliminarDato)
    private Nodo buscarNodo(Nodo nodo, char dato) {
        if (nodo == null || nodo.getDato() == dato) {
            return nodo;
        }
        if (dato < nodo.getDato()) {
            return buscarNodo(nodo.getLigaI(), dato);
        } else {
            return buscarNodo(nodo.getLigaD(), dato);
        }
    }

    // Eliminar dato (AVL)
    public boolean eliminarDato(char dato) {
        if (buscarNodo(Raiz, dato) == null) {
            return false;
        }
        Raiz = eliminarAvlAux(Raiz, dato);
        return true;
    }

    private Nodo eliminarAvlAux(Nodo nodo, char dato) {
        if (nodo == null) {
            return null; // No encontrado
        }

        if (dato < nodo.getDato()) {
            nodo.setLigaI(eliminarAvlAux(nodo.getLigaI(), dato));
        } else if (dato > nodo.getDato()) {
            nodo.setLigaD(eliminarAvlAux(nodo.getLigaD(), dato));
        } else {
            // Nodo encontrado
            if (nodo.getLigaI() == null || nodo.getLigaD() == null) {
                Nodo temp;
                if (nodo.getLigaI() != null) {
                    temp = nodo.getLigaI();
                } else {
                    temp = nodo.getLigaD();
                }

                if (temp == null) {
                    nodo = null; // Caso hoja
                } else {
                    nodo = temp; // Caso un hijo
                }
            } else {
                // Caso dos hijos
                char sucesor = encontrarMinimo(nodo.getLigaD());
                nodo.setDato(sucesor);
                nodo.setLigaD(eliminarAvlAux(nodo.getLigaD(), sucesor));
            }
        }

        if (nodo == null) {
            return null;
        }

        return balancear(nodo);
    }

    private char encontrarMinimo(Nodo nodo) {
        char minVal = nodo.getDato();
        while (nodo.getLigaI() != null) {
            minVal = nodo.getLigaI().getDato();
            nodo = nodo.getLigaI();
        }
        return minVal;
    }

    // --- Métodos de Recorrido y Consulta ---

    public void MostrarInOrden() {
        if (Raiz == null) {
            JOptionPane.showMessageDialog(null, "El árbol está vacío", "Recorrido InOrden",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder resultado = new StringBuilder();
            MostrarInOrdenAux(Raiz, resultado);
            JOptionPane.showMessageDialog(null, resultado.toString(), "Recorrido InOrden",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void MostrarInOrdenAux(Nodo Raiz, StringBuilder resultado) {
        if (Raiz != null) {
            MostrarInOrdenAux(Raiz.getLigaI(), resultado);
            resultado.append(Raiz.getDato()).append("   ");
            MostrarInOrdenAux(Raiz.getLigaD(), resultado);
        }
    }

    public void MostrarPreOrden() {
        if (Raiz == null) {
            JOptionPane.showMessageDialog(null, "El árbol está vacío", "Recorrido PreOrden",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder resultado = new StringBuilder();
            MostrarPreOrdenAux(Raiz, resultado);
            JOptionPane.showMessageDialog(null, resultado.toString(), "Recorrido PreOrden",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void MostrarPreOrdenAux(Nodo Raiz, StringBuilder resultado) {
        if (Raiz != null) {
            resultado.append(Raiz.getDato()).append("   ");
            MostrarPreOrdenAux(Raiz.getLigaI(), resultado);
            MostrarPreOrdenAux(Raiz.getLigaD(), resultado);
        }
    }

    public void MostrarPostOrden() {
        if (Raiz == null) {
            JOptionPane.showMessageDialog(null, "El árbol está vacío", "Recorrido PosOrden",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder resultado = new StringBuilder();
            MostrarPostOrdenAux(Raiz, resultado);
            JOptionPane.showMessageDialog(null, resultado.toString(), "Recorrido PosOrden",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void MostrarPostOrdenAux(Nodo Raiz, StringBuilder resultado) {
        if (Raiz != null) {
            MostrarPostOrdenAux(Raiz.getLigaI(), resultado);
            MostrarPostOrdenAux(Raiz.getLigaD(), resultado);
            resultado.append(Raiz.getDato()).append("   ");
        }
    }

    public void MostrarHojas() {
        StringBuilder resultado = new StringBuilder();
        MostrarHojasAux(Raiz, resultado);
        if (resultado.length() == 0) {
            JOptionPane.showMessageDialog(null, "No hay hojas (árbol vacío?)", "Hojas",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, resultado.toString(), "Hojas del Árbol",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void MostrarHojasAux(Nodo nodo, StringBuilder resultado) {
        if (nodo != null) {
            if (nodo.getLigaI() == null && nodo.getLigaD() == null) {
                resultado.append(nodo.getDato()).append("   ");
            }
            MostrarHojasAux(nodo.getLigaI(), resultado);
            MostrarHojasAux(nodo.getLigaD(), resultado);
        }
    }

    public void MostrarPadres() {
        StringBuilder resultado = new StringBuilder();
        MostrarPadresAux(Raiz, resultado);
        if (resultado.length() == 0) {
            JOptionPane.showMessageDialog(null, "No hay padres (árbol vacío o solo raíz?)", "Padres",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, resultado.toString(), "Padres del Árbol",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void MostrarPadresAux(Nodo nodo, StringBuilder resultado) {
        if (nodo != null) {
            if (nodo.getLigaI() != null || nodo.getLigaD() != null) {
                resultado.append(nodo.getDato()).append("   ");
            }
            MostrarPadresAux(nodo.getLigaI(), resultado);
            MostrarPadresAux(nodo.getLigaD(), resultado);
        }
    }

    public int nivel(char dato) {
        return nivelAux(Raiz, dato, 1);
    }

    private int nivelAux(Nodo nodo, char dato, int nivelActual) {
        if (nodo == null) {
            return -1;
        }
        if (nodo.getDato() == dato) {
            return nivelActual;
        }
        int nivelIzq = nivelAux(nodo.getLigaI(), dato, nivelActual + 1);
        if (nivelIzq != -1) {
            return nivelIzq;
        }
        return nivelAux(nodo.getLigaD(), dato, nivelActual + 1);
    }

    public int altura(char dato) {
        Nodo nodoEncontrado = buscarNodo(Raiz, dato);
        if (nodoEncontrado == null) {
            return -1;
        }
        return alturaAux(nodoEncontrado);
    }

    private int alturaAux(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        if (nodo.getLigaI() == null && nodo.getLigaD() == null) {
            return 1;
        }
        int altIzq = alturaAux(nodo.getLigaI());
        int altDer = alturaAux(nodo.getLigaD());
        return Math.max(altIzq, altDer) + 1;
    }

    public String ancestros(char dato) {
        StringBuilder resultado = new StringBuilder();
        if (buscarNodo(Raiz, dato) == null) {
            return ""; // Dato no existe
        }
        if (Raiz.getDato() == dato) {
            return ""; // Raíz no tiene ancestros
        }
        ancestrosAux(Raiz, dato, resultado);
        return resultado.toString();
    }

    private boolean ancestrosAux(Nodo nodo, char dato, StringBuilder resultado) {
        if (nodo == null) {
            return false;
        }
        if (nodo.getDato() == dato) {
            return true;
        }
        if (ancestrosAux(nodo.getLigaI(), dato, resultado) || ancestrosAux(nodo.getLigaD(), dato, resultado)) {
            resultado.append(nodo.getDato()).append(" ");
            return true;
        }
        return false;
    }

    public String hermanos(char dato) {
        if (Raiz == null || Raiz.getDato() == dato) {
            return ""; // Árbol vacío o es la raíz
        }
        Nodo padre = encontrarPadre(Raiz, dato);
        if (padre == null) {
            return ""; // No se encontró
        }

        StringBuilder sb = new StringBuilder();
        if (padre.getLigaI() != null && padre.getLigaI().getDato() == dato) {
            if (padre.getLigaD() != null) {
                sb.append(padre.getLigaD().getDato());
            }
        } else if (padre.getLigaD() != null && padre.getLigaD().getDato() == dato) {
            if (padre.getLigaI() != null) {
                sb.append(padre.getLigaI().getDato());
            }
        }
        return sb.toString();
    }

    private Nodo encontrarPadre(Nodo nodo, char dato) {
        if (nodo == null) {
            return null;
        }
        if ((nodo.getLigaI() != null && nodo.getLigaI().getDato() == dato)
                || (nodo.getLigaD() != null && nodo.getLigaD().getDato() == dato)) {
            return nodo;
        }

        Nodo izq = encontrarPadre(nodo.getLigaI(), dato);
        if (izq != null)
            return izq;
        return encontrarPadre(nodo.getLigaD(), dato);
    }

    public String primosHermanos(char dato) {
        if (Raiz == null) {
            return "";
        }

        Nodo padre = encontrarPadre(Raiz, dato);
        if (padre == null) {
            return ""; // Es la raíz o no existe
        }

        Nodo abuelo = encontrarPadre(Raiz, padre.getDato());
        if (abuelo == null) {
            return ""; // El padre es la raíz, no hay tíos
        }

        Nodo tio = null;
        if (abuelo.getLigaI() == padre) {
            tio = abuelo.getLigaD();
        } else {
            tio = abuelo.getLigaI();
        }

        if (tio == null) {
            return ""; // No tiene tío
        }

        StringBuilder sb = new StringBuilder();
        if (tio.getLigaI() != null) {
            sb.append(tio.getLigaI().getDato()).append(" ");
        }
        if (tio.getLigaD() != null) {
            sb.append(tio.getLigaD().getDato()).append(" ");
        }

        return sb.toString();
    }
}
