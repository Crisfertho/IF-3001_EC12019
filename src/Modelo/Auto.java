package Modelo;

public class Auto {

    private String codigo;
    private String paisOrigen;
    private String marca;
    private String modelo;
    private String estilo;
    private double costo;

    public Auto() {}

    public Auto(String codigo, String paisOrigen, String marca, String modelo, String estilo, double costo) {
        setCodigo(codigo);  // Usamos los setters para validación
        setPaisOrigen(paisOrigen);
        setMarca(marca);
        setModelo(modelo);
        setEstilo(estilo);
        setCosto(costo);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        // Verifica que el código sea una letra seguida de dos dígitos numéricos
        if (codigo == null || !codigo.matches("^\\d[A-Za-z]{2}$")) {
            throw new IllegalArgumentException("El código debe ser un número seguido de dos letras.");
        }
        this.codigo = codigo;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        // Validación para evitar cadenas vacías o nulas
        if (paisOrigen == null || paisOrigen.trim().isEmpty()) {
            throw new IllegalArgumentException("El país de origen no puede estar vacío.");
        }
        this.paisOrigen = paisOrigen;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        // Validación para evitar cadenas vacías o nulas
        if (marca == null || marca.trim().isEmpty()) {
            throw new IllegalArgumentException("La marca no puede estar vacía.");
        }
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        // Verifica que el modelo sea un año de 4 dígitos
        if (modelo == null || !modelo.matches("\\d{4}")) {
            throw new IllegalArgumentException("El modelo debe ser un año de 4 dígitos.");
        }
        this.modelo = modelo;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        // Validación para evitar cadenas vacías o nulas
        if (estilo == null || estilo.trim().isEmpty()) {
            throw new IllegalArgumentException("El estilo no puede estar vacío.");
        }
        this.estilo = estilo;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        if (costo <= 0) {
            throw new IllegalArgumentException("El costo debe ser un valor positivo.");
        }
        this.costo = costo;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "codigo='" + codigo + '\'' +
                ", paisOrigen='" + paisOrigen + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", estilo='" + estilo + '\'' +
                ", costo=" + costo +
                '}';
    }
}