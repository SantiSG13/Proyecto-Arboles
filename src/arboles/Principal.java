package arboles;

import javax.swing.*;

public class Principal {
    public static void main(String[] args) {
        int opcionPrincipal = 0;
        int opcionSecundaria = 0;

        String cadena = JOptionPane.showInputDialog("Ingrese la Cadena del Árbol: ");
        Arbol arbol = new Arbol();
        arbol.ConstruirArbol(cadena);

        do {
            opcionPrincipal = MenuPrincipal();
            switch (opcionPrincipal) {
                case 1:
                    // 1. Los 3 Recorridos del árbol
                    do {
                        opcionSecundaria = MenuSecundario();
                        switch (opcionSecundaria) {
                            case 1:
                                // Mostrar recorrido Posorden
                                arbol.MostrarPostOrden();
                                break;
                            case 2:
                                // Mostrar recorrido Inorden
                                arbol.MostrarInOrden();
                                break;
                            case 3:
                                // Mostrar recorrido Preorden
                                arbol.MostrarPreOrden();
                                break;
                            case 0:
                                JOptionPane.showMessageDialog(null, "Saliendo del Menu de Recorridos");
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Opción Inválida");
                        }
                    } while (opcionSecundaria != 0);
                    break;
                case 2:
                    // Mostrar Hojas
                    arbol.MostrarHojas();
                    break;
                case 3:
                    // Mostrar Padres
                    arbol.MostrarPadres();
                    break;
                case 4:
                    // Nivel de un dato ingresado
                    String dato = JOptionPane.showInputDialog(null, "Ingrese el dato a consultar (un caracter):",
                            "Nivel de Dato", JOptionPane.QUESTION_MESSAGE);
                    int nivel = arbol.nivel(dato.charAt(0));
                    if (nivel == -1) {
                        JOptionPane.showMessageDialog(null, "El dato '" + dato + "' no se encuentra en el árbol.",
                                "Nivel", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "El dato '" + dato + "' está en el nivel: " + nivel,
                                "Nivel", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                case 5:
                    // Altura de un dato ingresado
                    String datoAlt = JOptionPane.showInputDialog(null,
                            "Ingrese el dato para consultar su altura (un caracter):", "Altura",
                            JOptionPane.QUESTION_MESSAGE);
                    int altura = arbol.altura(datoAlt.charAt(0));
                    if (altura == -1) {
                        JOptionPane.showMessageDialog(null, "El dato '" + datoAlt + "' no se encuentra en el árbol.",
                                "Altura", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "La altura del dato '" + datoAlt + "' es: " + altura,
                                "Altura", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                case 6:
                    // Ancestros de un dato ingresado
                    String datoI = JOptionPane.showInputDialog(null,
                            "Ingrese el dato para mostrar sus ancestros (un caracter):", "Ancestros",
                            JOptionPane.QUESTION_MESSAGE);
                    String anc = arbol.ancestros(datoI.charAt(0));
                    if (anc.isEmpty()) {
                        JOptionPane.showMessageDialog(null,
                                "No se encontraron ancestros (dato no existe o es la raíz).", "Ancestros",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Ancestros del dato '" + datoI + "':\n" + anc, "Ancestros",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                case 7:
                    // Insertar un dato
                    String cadenaNueva = JOptionPane.showInputDialog(null,
                            "Ingrese el dato a insertar en el arbol (un caracter):", "Insertar Dato",
                            JOptionPane.QUESTION_MESSAGE);
                    boolean insertado = arbol.insertarDato(cadenaNueva.charAt(0));
                    char datoNuevo = cadenaNueva.charAt(0);
                    if (insertado) {
                        JOptionPane.showMessageDialog(null, "El dato '" + datoNuevo + "' fue insertado en el árbol.",
                                "Insertar", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "El dato '" + datoNuevo + "' ya existe en el árbol (no se inserta duplicado).",
                                "Insertar", JOptionPane.INFORMATION_MESSAGE);
                    }

                    break;
                case 8:
                    // Eliminar un dato
                    String datoEliminar = JOptionPane.showInputDialog(null,
                            "Ingrese el dato a eliminar del arbol (un caracter):", "Eliminar Dato",
                            JOptionPane.QUESTION_MESSAGE);
                    boolean eliminado = arbol.eliminarDato(datoEliminar.charAt(0));
                    if (eliminado) {
                        JOptionPane.showMessageDialog(null,
                                "El dato '" + datoEliminar + "' fue eliminado correctamente.", "Eliminar",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "No se encontró el dato '" + datoEliminar + "' en el árbol.", "Eliminar",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                case 9:
                    // Mostrar árbol
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            JFrame frame = new ArbolGrafico(arbol);
                            frame.setSize(800, 600);
                            frame.setVisible(true);
                        }
                    });
                    break;
                case 10:
                    // Mostrar Hermanos
                    String datoH = JOptionPane.showInputDialog(null,
                            "Ingrese el dato para mostrar sus hermanos (un caracter):", "Hermanos",
                            JOptionPane.QUESTION_MESSAGE);
                    String hermanos = arbol.hermanos(datoH.charAt(0));
                    if (hermanos.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No se encontraron hermanos para el dato '" + datoH + "'.",
                                "Hermanos", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Hermanos del dato '" + datoH + "':\n" + hermanos,
                                "Hermanos", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                case 11:
                    // Mostrar Primos Hermanos
                    String datoPH = JOptionPane.showInputDialog(null,
                            "Ingrese el dato para mostrar sus primos hermanos (un caracter):", "Primos Hermanos",
                            JOptionPane.QUESTION_MESSAGE);
                    String primos = arbol.primosHermanos(datoPH.charAt(0));
                    if (primos.isEmpty()) {
                        JOptionPane.showMessageDialog(null,
                                "No se encontraron primos hermanos para el dato '" + datoPH + "'.",
                                "Primos Hermanos", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Primos hermanos del dato '" + datoPH + "':\n" + primos,
                                "Primos Hermanos", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "Hasta pronto, :)");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida");
            }

        } while (opcionPrincipal != 0);

    }

    public static int MenuPrincipal() {
        int opcionPr = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la opción que desea: \n"
                + "1. Los 3 Recorridos del árbol \n"
                + "2. Mostrar Hojas \n"
                + "3. Mostrar Padres (Raices) \n"
                + "4. Nivel de un dato ingresado \n"
                + "5. Altura de dato ingresado \n"
                + "6. Ancestros de un dato ingresado \n"
                + "7. Insertar un dato \n"
                + "8. Eliminar un dato \n"
                + "9. Mostrar Árbol \n"
                + "10. Mostrar Hermanos \n"
                + "11. Mostrar Primos Hermanos \n"
                + "0. Salir \n"));
        return opcionPr;
    }

    public static int MenuSecundario() {
        int opcionSc = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la opción que desea: \n"
                + "1. Mostrar Recorrido PostOrden \n"
                + "2. Mostrar Recorrido InOrden \n"
                + "3. Mostrar Recorrido PreOrden \n"
                + "0. Salir\n"));
        return opcionSc;
    }

}
