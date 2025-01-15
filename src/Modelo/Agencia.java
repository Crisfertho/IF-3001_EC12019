package Modelo;

import java.util.Arrays;

public class Agencia {

  // Enumeración para las ubicaciones permitidas
  public enum Ubicacion {
    IZQUIERDA("Izquierda"),
    DERECHA("Derecha"),
    ESTE("Este"),
    OESTE("Oeste"),
    NORTE("Norte"),
    SUR("Sur");

    private final String NameLocation;

    // Constructor para asignar un nombre personalizado
    Ubicacion(String NameLocation) {
        this.NameLocation = NameLocation;
    }

    @Override
    public String toString() {
        return NameLocation; // Retorna el nombre personalizado
    }

    // Método para obtener una Ubicacion desde un String
    public static Ubicacion fromString(String ubicacion) throws IllegalArgumentException {
        for (Ubicacion u : Ubicacion.values()) {
            if (u.toString().equalsIgnoreCase(ubicacion)) {
                return u;
            }
        }
        throw new IllegalArgumentException("Ubicación no válida: " + ubicacion);
    }
}

    private Auto[] autos;
    private String nombre;
    private int capacidad;
    private Ubicacion ubicacion;

    public Agencia() {}

    public Agencia(String nombre, int capacidad, Ubicacion ubicacion) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.ubicacion = ubicacion;
        this.autos = new Auto[capacidad];
    }

    public void setAutos(Auto[] autos) {
        this.autos = autos;
    }

    public Auto[] getAutos() {
        return autos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
        this.autos = new Auto[capacidad]; // Ajustar el arreglo si cambia la capacidad.
    }

    public String getUbicacion() {
        return ubicacion.toString(); // Convertir a String
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = Ubicacion.fromString(ubicacion); // Convertir de String a Ubicacion
    }


    @Override
    public String toString() {
        return "Agencia{" +
                "nombre='" + nombre + '\'' +
                ", capacidad=" + capacidad +
                ", ubicacion='" + ubicacion + '\'' +
                ", autos=" + Arrays.toString(autos) +
                '}';
    }
}