package Controlador;

import Modelo.Agencia;
import Modelo.Auto;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Menu {

    private Lista lista;
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    private Agencia nuevaAgencia;
    private List<Auto> autosAgencia;

    public Menu() {
        lista = new Lista();
        autosAgencia = new ArrayList<>();
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Gestión de Agencias");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Para gestionar manualmente el cierre.
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel menuPanel = createMenuPanel();
        mainPanel.add(menuPanel, "Menu");

        JPanel agenciaPanel = createAgenciaPanel();
        mainPanel.add(agenciaPanel, "Agencia");

        JPanel autoPanel = createAutoPanel();
        mainPanel.add(autoPanel, "Auto");

        JPanel busquedaPanel = createBusquedaPanel();
        mainPanel.add(busquedaPanel, "Busqueda");

        JPanel eliminarPanel = createEliminarPanel();
        mainPanel.add(eliminarPanel, "Eliminar");

        JPanel unidadesPorAgenciaPanel = createUnidadesPorAgenciaPanel();
        mainPanel.add(unidadesPorAgenciaPanel, "UnidadesAgencia");

        JPanel mostrarValorPanel = createMostrarValorPanel();
        mainPanel.add(mostrarValorPanel, "MostrarValor");

        frame.add(mainPanel);
        frame.setVisible(true);

        // Confirmación al cerrar ventana.
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                confirmExit();
            }
        });
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE); // Fondo blanco para un estilo moderno
    
        // Crear un estilo común para los botones
        Dimension buttonSize = new Dimension(140, 30); // Tamaño pequeño de los botones
        Font buttonFont = new Font("SansSerif", Font.PLAIN, 12); // Fuente minimalista
    
        // Crear los botones
        JButton btnAgregarAgencia = createStyledButton("Agregar Agencia", buttonSize, buttonFont);
        JButton btnMostrarLista = createStyledButton("Mostrar Lista", buttonSize, buttonFont);
        JButton btnBuscar = createStyledButton("Buscar Unidad", buttonSize, buttonFont);
        JButton btnEliminar = createStyledButton("Eliminar Código", buttonSize, buttonFont);
        JButton btnValorExpo = createStyledButton("Valor Expo Móvil", buttonSize, buttonFont);
    
        // Agregar acciones a los botones
        btnAgregarAgencia.addActionListener(e -> cardLayout.show(mainPanel, "Agencia"));
        btnMostrarLista.addActionListener(e -> mostrarListaEnCuadroDeTexto());
        btnBuscar.addActionListener(e -> cardLayout.show(mainPanel, "Busqueda"));
        btnEliminar.addActionListener(e -> cardLayout.show(mainPanel, "Eliminar"));
        btnValorExpo.addActionListener(e -> cardLayout.show(mainPanel, "MostrarValor"));
    
        // Agregar los botones al panel con un pequeño espacio entre ellos
        panel.add(btnAgregarAgencia);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnMostrarLista);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnBuscar);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnEliminar);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnValorExpo);
    
        return panel;
    }
    
    private JButton createStyledButton(String text, Dimension size, Font font) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setFont(font);
        button.setFocusPainted(false);
        button.setBackground(new Color(240, 240, 240)); // Color neutro
        button.setForeground(Color.BLACK); // Texto negro para contraste
        button.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1)); // Borde sutil
        return button;
    }

    private JPanel createAgenciaPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));

        JTextField txtNombre = new JTextField();
        JTextField txtCapacidad = new JTextField();
        JComboBox<Agencia.Ubicacion> comboUbicacion = new JComboBox<>(Agencia.Ubicacion.values());
        JButton btnGuardar = new JButton("Guardar");
        JButton btnVolver = new JButton("Volver");

        panel.add(new JLabel("Nombre de Agencia:"));
        panel.add(txtNombre);

        panel.add(new JLabel("Capacidad:"));
        panel.add(txtCapacidad);

        panel.add(new JLabel("Ubicación:"));
        panel.add(comboUbicacion);

        panel.add(btnGuardar);
        panel.add(btnVolver);

        btnGuardar.addActionListener((ActionEvent e) -> {
            String nombre = txtNombre.getText();
            int capacidad;
            try {
                capacidad = Integer.parseInt(txtCapacidad.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "La capacidad debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Agencia.Ubicacion ubicacion = (Agencia.Ubicacion) comboUbicacion.getSelectedItem();
            nuevaAgencia = new Agencia(nombre, capacidad, ubicacion);
            autosAgencia.clear();

            JOptionPane.showMessageDialog(frame, "Agencia guardada exitosamente. Ahora ingresa los autos.");
            cardLayout.show(mainPanel, "Auto");
        });

        btnVolver.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        return panel;
    }

    private JPanel createAutoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 10, 10));

        JTextField txtCodigo = new JTextField();
        JTextField txtPaisOrigen = new JTextField();
        JTextField txtMarca = new JTextField();
        JTextField txtModelo = new JTextField();
        JTextField txtEstilo = new JTextField();
        JTextField txtCosto = new JTextField();

        //Validación código
        txtCodigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (txtCodigo.getText().length() >= 3 || (!Character.isLetterOrDigit(c))) {
                    e.consume();
                }
            }
        });

        //Validación Costo
        txtCosto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });

        //Validación Modelo
        txtModelo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || txtModelo.getText().length() >= 4) {
                    e.consume();
                }
            }
        });

        //Validación de solo letras
        txtPaisOrigen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isLetter(e.getKeyChar())) {
                    e.consume();
                }
            }
        });

        //Validación de solo letras
        txtMarca.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isLetter(e.getKeyChar())) {
                    e.consume();
                }
            }
        });

        //Validación de solo letras
        txtEstilo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isLetter(e.getKeyChar())) {
                    e.consume();
                }
            }
        });

        JButton btnAgregar = new JButton("Agregar Auto");
        JButton btnFinalizar = new JButton("Finalizar Agencia");

        panel.add(new JLabel("Código del Auto:"));
        panel.add(txtCodigo);

        panel.add(new JLabel("País de Origen:"));
        panel.add(txtPaisOrigen);

        panel.add(new JLabel("Marca:"));
        panel.add(txtMarca);

        panel.add(new JLabel("Modelo:"));
        panel.add(txtModelo);

        panel.add(new JLabel("Estilo:"));
        panel.add(txtEstilo);

        panel.add(new JLabel("Costo:"));
        panel.add(txtCosto);

        panel.add(btnAgregar);
        panel.add(btnFinalizar);

        btnAgregar.addActionListener((ActionEvent e) -> {
            String codigo = txtCodigo.getText();
            String paisOrigen = txtPaisOrigen.getText();
            String marca = txtMarca.getText();
            String modelo = txtModelo.getText();
            String estilo = txtEstilo.getText();
            double costo;
            try {
                costo = Double.parseDouble(txtCosto.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "El costo debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!codigo.matches("\\d[A-Za-z]{2}")) {
                JOptionPane.showMessageDialog(frame, "El código debe ser un número seguido de dos letras.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!modelo.matches("\\d{4}")) {
                JOptionPane.showMessageDialog(frame, "El modelo debe ser un año de 4 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Auto auto = new Auto(codigo, paisOrigen, marca, modelo, estilo, costo);
            autosAgencia.add(auto);

            JOptionPane.showMessageDialog(frame, "Auto agregado exitosamente.");
            txtCodigo.setText("");
            txtPaisOrigen.setText("");
            txtMarca.setText("");
            txtModelo.setText("");
            txtEstilo.setText("");
            txtCosto.setText("");
        });

        btnFinalizar.addActionListener((ActionEvent e) -> {
            if (autosAgencia.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Debes agregar al menos un auto antes de finalizar.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Auto[] autosArray = autosAgencia.toArray(new Auto[0]);
            nuevaAgencia.setAutos(autosArray);
            lista.ingresar(nuevaAgencia);

            JOptionPane.showMessageDialog(frame, "Agencia y autos guardados exitosamente.");
            cardLayout.show(mainPanel, "Menu");
        });

        return panel;
    }

    private JPanel createBusquedaPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margen interno
    
        // Etiqueta para el campo
        JLabel lblCodigo = new JLabel("Código del Auto:");
        lblCodigo.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Campo de texto centrado
        JTextField txtCodigoBusqueda = new JTextField();
        txtCodigoBusqueda.setFont(new Font("Arial", Font.PLAIN, 14));
        txtCodigoBusqueda.setForeground(Color.GRAY);
        txtCodigoBusqueda.setText("Ingrese el código del auto...");
        txtCodigoBusqueda.setCaretColor(Color.BLACK);
        txtCodigoBusqueda.setMaximumSize(new Dimension(200, 30));
        txtCodigoBusqueda.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Botón "Buscar"
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(56, 142, 60));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 14));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscar.setMaximumSize(new Dimension(120, 40));
        btnBuscar.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Botón "Volver"
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(211, 47, 47));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.setMaximumSize(new Dimension(120, 40));
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Añadiendo los componentes al panel
        panel.add(lblCodigo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(txtCodigoBusqueda);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnBuscar);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnVolver);
    
        // Acción del botón "Buscar"
        btnBuscar.addActionListener((ActionEvent e) -> {
            String codigo = txtCodigoBusqueda.getText().trim();
            if (codigo.isEmpty() || codigo.equals("Ingrese el código del auto...")) {
                JOptionPane.showMessageDialog(frame, "Por favor, ingrese un código válido.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
    
            Auto autoEncontrado = buscarAutoPorCodigo(codigo);
            if (autoEncontrado != null) {
                JOptionPane.showMessageDialog(frame, autoEncontrado.toString(), "Auto encontrado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Auto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        // Acción del botón "Volver"
        btnVolver.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
    
        return panel;
    }
    
    

    private Auto buscarAutoPorCodigo(String codigo) {
        for (Auto auto : autosAgencia) {
            if (auto.getCodigo().equals(codigo)) {
                return auto;
            }
        }
        return null;
    }

    private JPanel createEliminarPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margen interno
    
        // Etiqueta para el campo
        JLabel lblCodigo = new JLabel("Código del Auto:");
        lblCodigo.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Campo de texto centrado
        JTextField txtCodigoEliminar = new JTextField();
        txtCodigoEliminar.setFont(new Font("Arial", Font.PLAIN, 14));
        txtCodigoEliminar.setForeground(Color.GRAY);
        txtCodigoEliminar.setText("Ingrese el código del auto...");
        txtCodigoEliminar.setCaretColor(Color.BLACK);
        txtCodigoEliminar.setMaximumSize(new Dimension(200, 30));
        txtCodigoEliminar.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Botón "Eliminar"
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(56, 142, 60));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEliminar.setFocusPainted(false);
        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEliminar.setMaximumSize(new Dimension(120, 40));
        btnEliminar.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Botón "Volver"
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(211, 47, 47));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.setMaximumSize(new Dimension(120, 40));
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Añadiendo los componentes al panel
        panel.add(lblCodigo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(txtCodigoEliminar);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnEliminar);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnVolver);
    
        // Acción del botón "Eliminar"
        btnEliminar.addActionListener((ActionEvent e) -> {
            String codigo = txtCodigoEliminar.getText().trim();
            if (codigo.isEmpty() || codigo.equals("Ingrese el código del auto...")) {
                JOptionPane.showMessageDialog(frame, "Por favor, ingrese un código válido.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
    
            Auto auto = buscarAutoPorCodigo(codigo); // Buscar el auto
            if (auto != null) {
                int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Auto encontrado:\n" + auto + "\n¿Desea eliminar este auto?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    eliminarAutoPorCodigo(codigo);
                    JOptionPane.showMessageDialog(frame, "Auto eliminado exitosamente.", "Eliminación", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Auto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        // Acción del botón "Volver"
        btnVolver.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
    
        return panel;
    }
          

    private boolean eliminarAutoPorCodigo(String codigo) {
        for (Auto auto : autosAgencia) {
            if (auto.getCodigo().equals(codigo)) {
                autosAgencia.remove(auto);
                return true;
            }
        }
        return false;
    }

    private JPanel createUnidadesPorAgenciaPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));

        JComboBox<Agencia> comboAgencia = new JComboBox<>(lista.getAgencias().toArray(new Agencia[0]));
        JButton btnMostrarUnidades = new JButton("Mostrar Unidades");

        panel.add(comboAgencia);
        panel.add(btnMostrarUnidades);

        btnMostrarUnidades.addActionListener((ActionEvent e) -> {
            Agencia agenciaSeleccionada = (Agencia) comboAgencia.getSelectedItem();
            mostrarUnidadesPorAgencia(agenciaSeleccionada);
        });

        return panel;
    }

    private void mostrarUnidadesPorAgencia(Agencia agencia) {
        StringBuilder sb = new StringBuilder();
        for (Auto auto : agencia.getAutos()) {
            sb.append(auto.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(frame, sb.toString(), "Unidades de " + agencia.getNombre(), JOptionPane.INFORMATION_MESSAGE);
    }

    private JPanel createMostrarValorPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE); // Fondo blanco para un diseño limpio
    
        // Crear un estilo común para los botones
        Dimension buttonSize = new Dimension(200, 30); // Tamaño pequeño
        Font buttonFont = new Font("SansSerif", Font.PLAIN, 12); // Fuente minimalista
    
        // Crear los botones estilizados
        JButton btnValorMonetario = createStyledButton("Mostrar Valor Inventario por Agencia", buttonSize, buttonFont);
        JButton btnValorTotal = createStyledButton("Valor Total Expo Móvil", buttonSize, buttonFont);
        JButton btnVolver = createStyledButton("Volver", buttonSize, buttonFont);
    
        // Agregar acciones a los botones
        btnValorMonetario.addActionListener(e -> mostrarValorMonetario());
        btnValorTotal.addActionListener(e -> mostrarValorTotal());
        btnVolver.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
    
        // Agregar los botones al panel con espaciado entre ellos
        panel.add(btnValorMonetario);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnValorTotal);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnVolver);
    
        return panel;
    }
    

    private void mostrarValorMonetario() {
        for (Agencia agencia : lista.getAgencias()) {
            double valorInventario = calcularValorInventario(agencia);
            double seguro = calcularSeguroPorAgencia(agencia);
            double participacion = calcularMontoPorParticipacion(agencia);
            JOptionPane.showMessageDialog(frame, "Agencia: " + agencia.getNombre() + "\n" +
                            "Valor Inventario: $" + valorInventario + "\n" +
                            "Seguro: $" + seguro + "\n" +
                            "Participación: $" + participacion);
        }
    }

    private void mostrarValorTotal() {
        double total = calcularMontoTotalExpo();
        JOptionPane.showMessageDialog(frame, "Valor total recaudado en la Expo Móvil: $" + total);
    }

    private double calcularValorInventario(Agencia agencia) {
        double total = 0;
        for (Auto auto : agencia.getAutos()) {
            total += auto.getCosto();
        }
        return total;
    }

    private double calcularSeguroPorAgencia(Agencia agencia) {
        double totalSeguro = 0;
        for (Auto auto : agencia.getAutos()) {
            totalSeguro += auto.getCosto() * 0.05;
        }
        return totalSeguro;
    }

    private double calcularMontoPorParticipacion(Agencia agencia) {
        return agencia.getAutos().length * 5; // $5 por cada auto
    }

    private double calcularMontoTotalExpo() {
        double total = 0;
        for (Agencia agencia : lista.getAgencias()) {
            total += calcularMontoPorParticipacion(agencia) + calcularSeguroPorAgencia(agencia);
        }
        return total;
    }

    // Clase que muestra la lista de autos en un cuadro de diálogo.
    private void mostrarListaEnCuadroDeTexto() {
        // Generar la lista de autos como texto.
        String listaAgencias = generarListaAgencias();

        // Mostrar el texto generado en un cuadro de diálogo.
        JOptionPane.showMessageDialog(
            frame,
            listaAgencias,
            "Lista de Agencia & Autos",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    // Método auxiliar para generar la lista de agencias y autos como texto.
private String generarListaAgencias() {
    StringBuilder listaAgencias = new StringBuilder();

    // Recorrer la lista de agencias.
    for (Agencia agencia : lista.getAgencias()) {
        listaAgencias.append("Agencia: ").append(agencia.getNombre()).append("\n");
        
        // Obtener la lista de autos de la agencia.
        String listaAutos = generarListaAutos(agencia);
        
        // Agregar la lista de autos debajo de la agencia.
        listaAgencias.append(listaAutos).append("\n");
    }

    // Verificar si la lista está vacía.
    if (listaAgencias.length() == 0) {
        listaAgencias.append("No hay agencias en la lista.");
    }

    return listaAgencias.toString();
}

    // Método auxiliar para generar la lista de autos como texto.
    private String generarListaAutos(Agencia agencia) {
    StringBuilder listaAutos = new StringBuilder();

    // Construir la lista de autos.
    for (Auto auto : agencia.getAutos()) {
        listaAutos.append(auto.toString()).append("\n");
    }

    // Verificar si la lista está vacía.
    if (listaAutos.length() == 0) {
        listaAutos.append("No hay autos en la lista.");
    }

    return listaAutos.toString();
}
        
    private void confirmExit() {
        int confirm = JOptionPane.showOptionDialog(frame, "¿Estás seguro de salir?", "Confirmar salida",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

}
