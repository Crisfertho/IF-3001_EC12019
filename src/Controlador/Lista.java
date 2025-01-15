package Controlador;

import Modelo.Agencia;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Lista implements Serializable {

    private static final long serialVersionUID = 1L;
    private Nodo inicio;
    private Nodo Final;
    private int tamano;

    private JTextArea jtxt_area;
    private JScrollPane scroll;
    public static FileNameExtensionFilter filtro;
    private Font fuente;

    public Lista() {
        inicio = null;
        Final = null;
        tamano = 0;

        jtxt_area = new JTextArea();
        jtxt_area.setBackground(Color.WHITE);
        jtxt_area.setFont(fuente);
        jtxt_area.setForeground(Color.black);
        jtxt_area.setEditable(false);

        scroll = new JScrollPane(jtxt_area);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(300, 350));

        filtro = new FileNameExtensionFilter("Archivos de texto (.txt)", "txt", "TXT");
    }

    public boolean vacio() {
        return inicio == null;
    }

    public void ingresar(Agencia dato) {
        Nodo nuevo = new Nodo(dato);

        if (vacio()) {
            inicio = nuevo;
            Final = nuevo;
        } else {
            Final.siguiente = nuevo;
            Final = nuevo;
        }

        tamano++;
    }

    public void mostrar() {
        if (vacio()) {
            JOptionPane.showMessageDialog(null, "La lista se encuentra vacía.");
            return;
        }

        StringBuilder str = new StringBuilder();
        Nodo actual = inicio;

        while (actual != null) {
            str.append(actual.dato.toString()).append("\n");
            actual = actual.siguiente;
        }

        JOptionPane.showMessageDialog(null, "Lista:\n" + str.toString());
    }

    // Método para obtener todas las agencias en una lista
    public List<Agencia> getAgencias() {
        List<Agencia> agencias = new ArrayList<>();
        Nodo actual = inicio;

        while (actual != null) {
            agencias.add(actual.dato);
            actual = actual.siguiente;
        }
        return agencias;
    }

    // Método para buscar una agencia por nombre
    public Agencia buscarAgenciaPorNombre(String nombre) {
        Nodo actual = inicio;
        while (actual != null) {
            if (actual.dato.getNombre().equalsIgnoreCase(nombre)) {
                return actual.dato;
            }
            actual = actual.siguiente;
        }
        return null; // Si no se encuentra la agencia
    }

    // Método para eliminar una agencia por nombre
    public boolean eliminarAgencia(String nombre) {
        if (vacio()) {
            return false;
        }

        Nodo actual = inicio;
        Nodo anterior = null;

        while (actual != null) {
            if (actual.dato.getNombre().equalsIgnoreCase(nombre)) {
                if (anterior == null) { // Si la agencia a eliminar está al principio
                    inicio = actual.siguiente;
                } else {
                    anterior.siguiente = actual.siguiente;
                }
                if (actual == Final) { // Si la agencia a eliminar es la última
                    Final = anterior;
                }
                tamano--;
                return true;
            }
            anterior = actual;
            actual = actual.siguiente;
        }

        return false; // Si no se encuentra la agencia
    }

    // Nuevo método getInicio
    public Nodo getInicio() {
        return inicio;
    }

    // Clase Nodo que almacena las agencias
    class Nodo {
        Agencia dato;
        Nodo siguiente;

        public Nodo(Agencia dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }
}
