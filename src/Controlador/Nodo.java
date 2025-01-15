package Controlador;

import Modelo.Agencia;

public class Nodo {

    private static final long serialVersionUID = 1L;

    Nodo siguiente;
    Agencia dato;

    public Nodo(Agencia dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public String Mostrar() {
        return "dato=" + dato + "";
    }

    // Método renombrado a getSiguiente para mayor claridad
    public Nodo getSgt() {
        return siguiente;
    }

    public void setSgt(Nodo siguiente) {
        this.siguiente = siguiente;
    }

    public Agencia getDato() {
        return dato;
    }

    public void setDato(Agencia dato) {
        this.dato = dato;
    }
}
