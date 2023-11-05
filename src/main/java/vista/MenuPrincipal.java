package vista;

import controlador.ControladoraPersistencia;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.Estacionamiento;
import modelo.Persona;
import modelo.Vehiculo;
import servicios.Validaciones;

/**
 *
 * @author lafer
 */
public class MenuPrincipal extends javax.swing.JFrame {

    ControladoraPersistencia controlador = new ControladoraPersistencia();
    Boolean esNuevaPersona = true;
    Boolean esNuevoVehiculo = true;

    /**
     * Creates new form MenuPrincipall
     */
    public MenuPrincipal() {
        initComponents();

    }

    private void limpiarPersonasCampos() {
        this.nombreTXT.setText("");
        this.apellidoTXT.setText("");
        this.nitTXT.setText("");
        this.cuiTXT.setText("");
        this.telefonoTXT.setText("");
        this.domicilioTXT.setText("");
        this.diaTXT.setText("");
        this.mesTXT.setText("");
        this.anioTXT.setText("");
    }

    private void limpiarVehiculosCampos() {
        this.placaTXT.setText("");
        this.marcaTXT.setText("");
        this.lineaTXT.setText("");
        this.modeloTXT.setText("");
        this.colorTXT.setText("");
        this.PropietarioSelect.setSelectedItem(null);
    }

    private void consolePersonalog(String texto, Color color) {
        this.logPersona.setText(texto);
        this.logPersona.setForeground(color);
    }

    private void consoleVehiculoLog(String texto, Color color) {
        this.logVehiculo.setText(texto);
        this.logVehiculo.setForeground(color);
    }

    private void consoleEstaionamientoLog(String texto, Color color) {
        this.logVehiculo.setText(texto);
        this.logVehiculo.setForeground(color);
    }

    private void actualizarTablaPersona() {
        List<Persona> personas = controlador.obtenerPersonas();
        DefaultTableModel dtm = (DefaultTableModel) this.PersonaTable.getModel();
        dtm.setRowCount(0);
        for (Persona persona : personas) {
            Object[] obj = {Long.toString(persona.getCui()), persona.getNit(), persona.getNombre(), persona.getApellido(), Integer.toString(persona.getTelefono())};
            dtm.addRow(obj);
        }
    }

    private void actualizarTablaVehiculo() {
        List<Vehiculo> vehiculos = controlador.obtenerVehiculos();
        DefaultTableModel dtm = (DefaultTableModel) this.VehiculoTable.getModel();
        dtm.setRowCount(0);
        for (Vehiculo vehiculo : vehiculos) {
            Object[] obj = {vehiculo.getPlaca(), vehiculo.getMarca(), vehiculo.getLinea(), vehiculo.getModelo(), vehiculo.getColor()};
            dtm.addRow(obj);
        }
    }

    private void actualizarTablaEstacionamientos() {
        DefaultTableModel dtm = (DefaultTableModel) this.estacionamientoTable.getModel();
        dtm.setRowCount(0);
        for (int i = 1; i <= 30; i++) {
            Estacionamiento estacionamiento = this.controlador.encontrarEstacionamieto(i);
            if (estacionamiento == null || estacionamiento.getVehiculo() == null) {
                Object[] obj = {Integer.toString(i), "Disponible", "", ""};
                dtm.addRow(obj);
            } else {
                Object[] obj = {
                    Integer.toString(i),
                    estacionamiento.isDisponible() ? "Disponible" : "Ocupado",
                    estacionamiento.getVehiculo().getPlaca(),
                    estacionamiento.getVehiculo().getPropietario().getNombre() + " " + estacionamiento.getVehiculo().getPropietario().getApellido(),};
                dtm.addRow(obj);
            }
        }
    }

    private void onPersonaEdit(Persona persona) {
        this.esNuevaPersona = false;

        this.guardarPersonaBTN.setText("Actualizar");
        this.limpiarPersonaBTN.setText("Eliminar");

        this.nitTXT.setEditable(false);
        this.cuiTXT.setEditable(false);

        this.nombreTXT.setText(persona.getNombre());
        this.apellidoTXT.setText(persona.getApellido());
        this.nitTXT.setText(persona.getNit());
        this.cuiTXT.setText(Long.toString(persona.getCui()));
        this.telefonoTXT.setText(Integer.toString(persona.getTelefono()));
        this.domicilioTXT.setText(persona.getDomicilio());
        this.diaTXT.setText(Integer.toString(persona.getFechaNacimiento().getDay()));
        this.mesTXT.setText(Integer.toString(persona.getFechaNacimiento().getMonth()));
        this.anioTXT.setText(Integer.toString(persona.getFechaNacimiento().getYear()));
    }

    private void onVehiculoEdit(Vehiculo vehiculo) {
        this.esNuevoVehiculo = false;

        this.guardarVehiculoBTN.setText("Actualizar");
        this.limpiarVehiculoBTN.setText("Eliminar");
        this.placaTXT.setEditable(false);

        this.placaTXT.setText(vehiculo.getPlaca());
        this.marcaTXT.setText(vehiculo.getMarca());
        this.lineaTXT.setText(vehiculo.getLinea());
        this.modeloTXT.setText(vehiculo.getModelo());
        this.colorTXT.setText(vehiculo.getColor());

        List<Vehiculo> vehiculos = controlador.obtenerVehiculos();
        int index = -1;
        for (Vehiculo vehiculoi : vehiculos) {
            if (vehiculoi.getPlaca().equals(vehiculo.getPlaca())) {
                break;
            }
            index++;
        }

        this.PropietarioSelect.setSelectedIndex(index + 1);
    }

    private void onEstacionamientoEdit(Estacionamiento estacionamiento) {

        this.vehiculoSelect.setEditable(true);
        this.numEstacionamientoLabel.setText(Integer.toString(estacionamiento.getNumeroParqueo()));

        if (estacionamiento.isDisponible()) {
            this.isDisponibleLabel.setText("Disponble");
            this.isDisponibleLabel.setForeground(Color.GREEN);
            this.ingresarVehiculoBTN.setEnabled(true);
            this.egresarVehiculoBTN.setEnabled(false);

        } else {
            this.isDisponibleLabel.setText("Ocupado");
            this.isDisponibleLabel.setForeground(Color.RED);
            this.ingresarVehiculoBTN.setEnabled(false);
            this.egresarVehiculoBTN.setEnabled(true);
        }

        if (estacionamiento.getVehiculo() != null) {
            this.estacionamieto_propietarioTXT.setText(estacionamiento.getVehiculo().getPropietario().getNombre() + " " + estacionamiento.getVehiculo().getPropietario().getApellido());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");

            this.FechaRegistroLabel.setText(sdf.format(estacionamiento.getFechaRegistro()));
            List<Vehiculo> vehiculos = controlador.obtenerVehiculos();
            int index = 0;
            for (Vehiculo vehiculoi : vehiculos) {
                if (vehiculoi.getPlaca().equals(estacionamiento.getVehiculo().getPlaca())) {
                    break;
                }
                index++;
            }

            this.vehiculoSelect.setSelectedIndex(index);
        } else {
            this.estacionamieto_propietarioTXT.setText("");
            this.FechaRegistroLabel.setText("");
            this.vehiculoSelect.setSelectedItem(null);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PrincipalPanel = new javax.swing.JPanel();
        navegacionPanel = new javax.swing.JTabbedPane();
        personasPanel = new javax.swing.JPanel();
        cuiTXT = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        PersonaTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        nitTXT = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        domicilioTXT = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        diaTXT = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        mesTXT = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        anioTXT = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        nombreTXT = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        apellidoTXT = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        telefonoTXT = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        guardarPersonaBTN = new javax.swing.JButton();
        limpiarPersonaBTN = new javax.swing.JButton();
        logPersona = new javax.swing.JLabel();
        nuevaPersonaBTN = new javax.swing.JButton();
        BuscarPersonaTXT = new javax.swing.JTextField();
        BuscarPersonaBTN = new javax.swing.JButton();
        vehiculosPanel = new javax.swing.JPanel();
        placaTXT = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        VehiculoTable = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        marcaTXT = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        lineaTXT = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        modeloTXT = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        colorTXT = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        guardarVehiculoBTN = new javax.swing.JButton();
        limpiarVehiculoBTN = new javax.swing.JButton();
        logVehiculo = new javax.swing.JLabel();
        nuevoVehiculoBTN = new javax.swing.JButton();
        BuscarVehiculoTXT = new javax.swing.JTextField();
        BuscarVehiculoBTN = new javax.swing.JButton();
        PropietarioSelect = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        estacionamientoPanel = new javax.swing.JPanel();
        numEstacionamientoLabel = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        estacionamientoTable = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        egresarVehiculoBTN = new javax.swing.JButton();
        ingresarVehiculoBTN = new javax.swing.JButton();
        logEstacionamiento = new javax.swing.JLabel();
        vehiculoSelect = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        estacionamieto_propietarioTXT = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        isDisponibleLabel = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        FechaRegistroLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        personasPanel.setBackground(new java.awt.Color(204, 255, 255));
        personasPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                personasPanelComponentShown(evt);
            }
        });

        cuiTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cuiTXTActionPerformed(evt);
            }
        });

        PersonaTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CUI", " NIT", "Nombres", "Apellidos", "Telefono"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(PersonaTable);
        if (PersonaTable.getColumnModel().getColumnCount() > 0) {
            PersonaTable.getColumnModel().getColumn(0).setResizable(false);
            PersonaTable.getColumnModel().getColumn(1).setResizable(false);
            PersonaTable.getColumnModel().getColumn(2).setResizable(false);
            PersonaTable.getColumnModel().getColumn(3).setResizable(false);
            PersonaTable.getColumnModel().getColumn(4).setResizable(false);
        }

        jLabel1.setText("CUI");

        nitTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nitTXTActionPerformed(evt);
            }
        });

        jLabel2.setText("NIT");

        domicilioTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                domicilioTXTActionPerformed(evt);
            }
        });

        jLabel3.setText("Domicilio");

        diaTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diaTXTActionPerformed(evt);
            }
        });

        jLabel4.setText("Fecha nacimiento");

        jLabel5.setText("dia");

        mesTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mesTXTActionPerformed(evt);
            }
        });

        jLabel6.setText("mes");

        anioTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anioTXTActionPerformed(evt);
            }
        });

        jLabel7.setText("anio");

        nombreTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreTXTActionPerformed(evt);
            }
        });

        jLabel8.setText("Nombre");

        apellidoTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apellidoTXTActionPerformed(evt);
            }
        });

        jLabel9.setText("Apellido");

        telefonoTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefonoTXTActionPerformed(evt);
            }
        });

        jLabel10.setText("Telefono");

        guardarPersonaBTN.setText("Crear");
        guardarPersonaBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarPersonaBTNActionPerformed(evt);
            }
        });

        limpiarPersonaBTN.setText("Limpiar");
        limpiarPersonaBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarPersonaBTNActionPerformed(evt);
            }
        });

        nuevaPersonaBTN.setText("Nueva Persona");
        nuevaPersonaBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevaPersonaBTNActionPerformed(evt);
            }
        });

        BuscarPersonaTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarPersonaTXTActionPerformed(evt);
            }
        });

        BuscarPersonaBTN.setText("Buscar");
        BuscarPersonaBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarPersonaBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout personasPanelLayout = new javax.swing.GroupLayout(personasPanel);
        personasPanel.setLayout(personasPanelLayout);
        personasPanelLayout.setHorizontalGroup(
            personasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, personasPanelLayout.createSequentialGroup()
                .addGroup(personasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(personasPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(personasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cuiTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(nitTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(domicilioTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addGroup(personasPanelLayout.createSequentialGroup()
                                .addGroup(personasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(diaTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(personasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(mesTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(personasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(anioTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)))
                            .addComponent(nombreTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(apellidoTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(telefonoTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, personasPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(limpiarPersonaBTN)
                        .addGap(18, 18, 18)
                        .addComponent(guardarPersonaBTN)
                        .addGap(18, 18, 18)))
                .addGroup(personasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(personasPanelLayout.createSequentialGroup()
                        .addComponent(BuscarPersonaBTN)
                        .addGap(59, 59, 59)
                        .addComponent(BuscarPersonaTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nuevaPersonaBTN)
                        .addGap(19, 19, 19))
                    .addGroup(personasPanelLayout.createSequentialGroup()
                        .addGroup(personasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(logPersona)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 817, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );
        personasPanelLayout.setVerticalGroup(
            personasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, personasPanelLayout.createSequentialGroup()
                .addGap(0, 16, Short.MAX_VALUE)
                .addGroup(personasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nuevaPersonaBTN)
                    .addComponent(BuscarPersonaTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BuscarPersonaBTN))
                .addGap(18, 18, 18)
                .addGroup(personasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(personasPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cuiTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nitTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(domicilioTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(personasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(personasPanelLayout.createSequentialGroup()
                                .addGroup(personasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, personasPanelLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(anioTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(personasPanelLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(personasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(personasPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(diaTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(personasPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(mesTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nombreTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(apellidoTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(telefonoTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, personasPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(personasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(guardarPersonaBTN)
                                    .addComponent(limpiarPersonaBTN)))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(logPersona)
                .addGap(26, 26, 26))
        );

        navegacionPanel.addTab("Personas", personasPanel);

        vehiculosPanel.setBackground(new java.awt.Color(204, 255, 255));
        vehiculosPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                vehiculosPanelComponentShown(evt);
            }
        });

        placaTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                placaTXTActionPerformed(evt);
            }
        });

        VehiculoTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Placa", "Marca", " Linea", "Modelo", "Color"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(VehiculoTable);
        if (VehiculoTable.getColumnModel().getColumnCount() > 0) {
            VehiculoTable.getColumnModel().getColumn(0).setResizable(false);
            VehiculoTable.getColumnModel().getColumn(1).setResizable(false);
            VehiculoTable.getColumnModel().getColumn(2).setResizable(false);
            VehiculoTable.getColumnModel().getColumn(3).setResizable(false);
            VehiculoTable.getColumnModel().getColumn(4).setResizable(false);
            VehiculoTable.getColumnModel().getColumn(4).setHeaderValue("Color");
        }

        jLabel11.setText("Placa");

        marcaTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                marcaTXTActionPerformed(evt);
            }
        });

        jLabel12.setText("Marca");

        lineaTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lineaTXTActionPerformed(evt);
            }
        });

        jLabel18.setText("Linea");

        modeloTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modeloTXTActionPerformed(evt);
            }
        });

        jLabel19.setText("Modelo");

        colorTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorTXTActionPerformed(evt);
            }
        });

        jLabel20.setText("Color");

        guardarVehiculoBTN.setText("Crear");
        guardarVehiculoBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarVehiculoBTNActionPerformed(evt);
            }
        });

        limpiarVehiculoBTN.setText("Limpiar");
        limpiarVehiculoBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarVehiculoBTNActionPerformed(evt);
            }
        });

        nuevoVehiculoBTN.setText("Nuevo Vehiculo");
        nuevoVehiculoBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoVehiculoBTNActionPerformed(evt);
            }
        });

        BuscarVehiculoTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarVehiculoTXTActionPerformed(evt);
            }
        });

        BuscarVehiculoBTN.setText("Buscar");
        BuscarVehiculoBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarVehiculoBTNActionPerformed(evt);
            }
        });

        PropietarioSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PropietarioSelectActionPerformed(evt);
            }
        });

        jLabel13.setText("Propietario");

        javax.swing.GroupLayout vehiculosPanelLayout = new javax.swing.GroupLayout(vehiculosPanel);
        vehiculosPanel.setLayout(vehiculosPanelLayout);
        vehiculosPanelLayout.setHorizontalGroup(
            vehiculosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vehiculosPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(vehiculosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vehiculosPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(limpiarVehiculoBTN)
                        .addGap(18, 18, 18)
                        .addComponent(guardarVehiculoBTN))
                    .addGroup(vehiculosPanelLayout.createSequentialGroup()
                        .addGroup(vehiculosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(placaTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(marcaTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(lineaTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(modeloTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(colorTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(PropietarioSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(0, 118, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(vehiculosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(vehiculosPanelLayout.createSequentialGroup()
                        .addComponent(BuscarVehiculoBTN)
                        .addGap(59, 59, 59)
                        .addComponent(BuscarVehiculoTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nuevoVehiculoBTN)
                        .addGap(19, 19, 19))
                    .addGroup(vehiculosPanelLayout.createSequentialGroup()
                        .addGroup(vehiculosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(logVehiculo)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 817, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );
        vehiculosPanelLayout.setVerticalGroup(
            vehiculosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vehiculosPanelLayout.createSequentialGroup()
                .addGap(0, 16, Short.MAX_VALUE)
                .addGroup(vehiculosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nuevoVehiculoBTN)
                    .addComponent(BuscarVehiculoTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BuscarVehiculoBTN))
                .addGap(18, 18, 18)
                .addGroup(vehiculosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(vehiculosPanelLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(placaTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(marcaTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lineaTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(modeloTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(colorTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PropietarioSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(vehiculosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(guardarVehiculoBTN)
                            .addComponent(limpiarVehiculoBTN)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(logVehiculo)
                .addGap(26, 26, 26))
        );

        navegacionPanel.addTab("Vehiculos", vehiculosPanel);

        estacionamientoPanel.setBackground(new java.awt.Color(204, 255, 255));
        estacionamientoPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                estacionamientoPanelComponentShown(evt);
            }
        });

        numEstacionamientoLabel.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        numEstacionamientoLabel.setEnabled(false);
        numEstacionamientoLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numEstacionamientoLabelActionPerformed(evt);
            }
        });

        estacionamientoTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "# estacionamiento", "estado", "Vehiculo", "Propietario "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(estacionamientoTable);
        if (estacionamientoTable.getColumnModel().getColumnCount() > 0) {
            estacionamientoTable.getColumnModel().getColumn(0).setResizable(false);
            estacionamientoTable.getColumnModel().getColumn(1).setResizable(false);
            estacionamientoTable.getColumnModel().getColumn(2).setResizable(false);
            estacionamientoTable.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel14.setText("Numero de estacionamiento");

        egresarVehiculoBTN.setText("Egresar");
        egresarVehiculoBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                egresarVehiculoBTNActionPerformed(evt);
            }
        });

        ingresarVehiculoBTN.setText("Ingresar");
        ingresarVehiculoBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ingresarVehiculoBTNActionPerformed(evt);
            }
        });

        vehiculoSelect.setEnabled(false);
        vehiculoSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vehiculoSelectActionPerformed(evt);
            }
        });

        jLabel16.setText("Vehiculo");

        estacionamieto_propietarioTXT.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        estacionamieto_propietarioTXT.setEnabled(false);
        estacionamieto_propietarioTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estacionamieto_propietarioTXTActionPerformed(evt);
            }
        });

        jLabel17.setText("Propietario");

        isDisponibleLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel15.setText("Fecha de Registro");

        FechaRegistroLabel.setFont(new java.awt.Font("Sitka Text", 1, 12)); // NOI18N

        javax.swing.GroupLayout estacionamientoPanelLayout = new javax.swing.GroupLayout(estacionamientoPanel);
        estacionamientoPanel.setLayout(estacionamientoPanelLayout);
        estacionamientoPanelLayout.setHorizontalGroup(
            estacionamientoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, estacionamientoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(estacionamientoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, estacionamientoPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(ingresarVehiculoBTN)
                        .addGap(18, 18, 18)
                        .addComponent(egresarVehiculoBTN))
                    .addGroup(estacionamientoPanelLayout.createSequentialGroup()
                        .addGroup(estacionamientoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(vehiculoSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(estacionamieto_propietarioTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addGroup(estacionamientoPanelLayout.createSequentialGroup()
                                .addComponent(numEstacionamientoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55)
                                .addComponent(isDisponibleLabel))
                            .addComponent(jLabel15)
                            .addComponent(FechaRegistroLabel))
                        .addGap(0, 97, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(estacionamientoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logEstacionamiento)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 817, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        estacionamientoPanelLayout.setVerticalGroup(
            estacionamientoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, estacionamientoPanelLayout.createSequentialGroup()
                .addGap(0, 57, Short.MAX_VALUE)
                .addGroup(estacionamientoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(estacionamientoPanelLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(estacionamientoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(numEstacionamientoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(isDisponibleLabel))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(vehiculoSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(estacionamieto_propietarioTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FechaRegistroLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(estacionamientoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(egresarVehiculoBTN)
                            .addComponent(ingresarVehiculoBTN)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(logEstacionamiento)
                .addGap(26, 26, 26))
        );

        navegacionPanel.addTab("Estacionamientos", estacionamientoPanel);

        javax.swing.GroupLayout PrincipalPanelLayout = new javax.swing.GroupLayout(PrincipalPanel);
        PrincipalPanel.setLayout(PrincipalPanelLayout);
        PrincipalPanelLayout.setHorizontalGroup(
            PrincipalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(navegacionPanel)
        );
        PrincipalPanelLayout.setVerticalGroup(
            PrincipalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(navegacionPanel)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PrincipalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PrincipalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cuiTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cuiTXTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cuiTXTActionPerformed

    private void nitTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nitTXTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nitTXTActionPerformed

    private void nombreTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreTXTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreTXTActionPerformed

    private void apellidoTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apellidoTXTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidoTXTActionPerformed

    private void telefonoTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telefonoTXTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonoTXTActionPerformed

    private void guardarPersonaBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarPersonaBTNActionPerformed
        this.logPersona.setText("");

        Persona persona = new Persona();

        persona.setNombre(this.nombreTXT.getText());
        if ("".equals(persona.getNombre())) {
            this.consolePersonalog("Ingresa Nombre", Color.RED);
            return;
        }

        persona.setApellido(this.apellidoTXT.getText());
        if ("".equals(persona.getApellido())) {
            this.logPersona.setText("Ingresa Apellido");
            return;
        }

        persona.setDomicilio(this.domicilioTXT.getText());
        if ("".equals(persona.getDomicilio())) {
            this.consolePersonalog("Ingresa un domicilio", Color.RED);
            return;
        }

        try {
            persona.setCui(Long.parseLong(this.cuiTXT.getText()));
            if (!Validaciones.validarCUI(persona.getCui())) {
                this.consolePersonalog("Cui no valido 1", Color.RED);
                return;
            }
        } catch (NumberFormatException e) {
            this.consolePersonalog("Cui no valido 2", Color.RED);
            return;
        }

        persona.setNit(this.nitTXT.getText());
        if (!Validaciones.validarNIT(persona.getNit())) {
            this.consolePersonalog("NIT no valido", Color.RED);
            return;
        }

        try {
            persona.setTelefono(Integer.parseInt(this.telefonoTXT.getText()));
        } catch (NumberFormatException e) {
            this.consolePersonalog("Numero telefonico no valido", Color.RED);
            return;
        }

        try {
            Date fechaNac = new Date(
                    Integer.parseInt(this.anioTXT.getText()),
                    Integer.parseInt(this.mesTXT.getText()),
                    Integer.parseInt(this.diaTXT.getText())
            );
            if (!Validaciones.validarFecha(
                    Integer.parseInt(this.diaTXT.getText()),
                    Integer.parseInt(this.mesTXT.getText()),
                    Integer.parseInt(this.anioTXT.getText())
            )) {
                this.consolePersonalog("Fecha de Nacimiento no valida", Color.RED);

                return;
            }

            persona.setFechaNacimiento(fechaNac);
        } catch (Exception e) {
            consolePersonalog("Fecha de Nacimiento no valida", Color.RED);
            return;
        }

        if (this.esNuevaPersona) {
            if (this.controlador.yaExistePersona(persona.getCui())) {
                this.consolePersonalog("Este cui ya esta en uso", Color.RED);
                return;
            }
            this.controlador.crearPersona(persona);
            this.consolePersonalog("Nueva persona Registrada", Color.GREEN);
            this.limpiarPersonasCampos();

        } else {
            this.controlador.actualizarPersona(persona);
            this.consolePersonalog(" Registro actualizado ", Color.GREEN);
        }

        this.actualizarTablaPersona();

    }//GEN-LAST:event_guardarPersonaBTNActionPerformed

    private void limpiarPersonaBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiarPersonaBTNActionPerformed

        if (!esNuevaPersona) {
            this.controlador.eliminarPersona(Long.parseLong(this.cuiTXT.getText()));
            this.nitTXT.setEditable(true);
            this.cuiTXT.setEditable(true);
            this.guardarPersonaBTN.setText("Registrar");
            this.limpiarPersonaBTN.setText("Limpiar");

            this.consolePersonalog("Persona elminada", Color.GREEN);
        }

        this.limpiarPersonasCampos();
        this.actualizarTablaPersona();
    }//GEN-LAST:event_limpiarPersonaBTNActionPerformed

    private void nuevaPersonaBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevaPersonaBTNActionPerformed
        this.limpiarPersonasCampos();
        this.consolePersonalog("", Color.BLACK);
        this.PersonaTable.getSelectionModel().clearSelection();
        this.esNuevaPersona = true;

        this.guardarPersonaBTN.setText("Registrar");
        this.limpiarPersonaBTN.setText("Limpiar");

        this.nitTXT.setEditable(true);
        this.cuiTXT.setEditable(true);
    }//GEN-LAST:event_nuevaPersonaBTNActionPerformed

    private void BuscarPersonaBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarPersonaBTNActionPerformed
        this.consolePersonalog("", Color.BLACK);

        try {
            Persona persona = controlador.encontrarPersona(Long.parseLong(this.BuscarPersonaTXT.getText()));
            if (persona == null) {
                consolePersonalog("Persona no encontrada", Color.RED);
            } else {
                onPersonaEdit(persona);
                consolePersonalog("Persona encontrada", Color.GREEN);
            }
        } catch (NumberFormatException e) {

            consolePersonalog("Cui no valido", Color.RED);
        }


    }//GEN-LAST:event_BuscarPersonaBTNActionPerformed

    private void BuscarPersonaTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarPersonaTXTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarPersonaTXTActionPerformed

    private void placaTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_placaTXTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_placaTXTActionPerformed

    private void marcaTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_marcaTXTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_marcaTXTActionPerformed

    private void lineaTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lineaTXTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lineaTXTActionPerformed

    private void modeloTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modeloTXTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_modeloTXTActionPerformed

    private void colorTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorTXTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_colorTXTActionPerformed

    private void guardarVehiculoBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarVehiculoBTNActionPerformed
        Vehiculo vehiculo = new Vehiculo();

        vehiculo.setPlaca(this.placaTXT.getText());
        if (!Validaciones.validarPlacas(vehiculo.getPlaca())) {
            this.consoleVehiculoLog("Placa no valida", Color.RED);
            return;
        }
        vehiculo.setMarca(this.marcaTXT.getText());
        if ("".equals(vehiculo.getMarca())) {
            this.consoleVehiculoLog("Ingresa Marca", Color.RED);
            return;
        }

        vehiculo.setLinea(this.lineaTXT.getText());
        if ("".equals(vehiculo.getLinea())) {
            this.consoleVehiculoLog("Ingresa Linea", Color.RED);
            return;
        }
        vehiculo.setModelo(this.modeloTXT.getText());
        if ("".equals(vehiculo.getModelo())) {
            this.consoleVehiculoLog("Ingresa modelo", Color.RED);
            return;
        }

        vehiculo.setColor(this.colorTXT.getText());
        if ("".equals(vehiculo.getColor())) {
            this.consoleVehiculoLog("Ingresa Color", Color.RED);
            return;
        }

        vehiculo.setPropietario((Persona) this.PropietarioSelect.getSelectedItem());

        if (vehiculo.getPropietario() == null) {
            this.consoleVehiculoLog("Selecciona un propietario", Color.RED);
            return;
        }

        if (this.esNuevoVehiculo) {
            if (controlador.yaExisteVehiculo(vehiculo.getPlaca())) {
                this.consoleVehiculoLog("Ya existe este vehiculo", Color.RED);
            }
            this.controlador.crearVehiculo(vehiculo);
            this.consoleVehiculoLog("Nuevc Vehiculo  Registrado", Color.GREEN);
            this.limpiarVehiculosCampos();

        } else {
            this.controlador.actualizarVehiculo(vehiculo);
            this.consoleVehiculoLog(" Vehiculo actualizado ", Color.GREEN);
        }

        this.actualizarTablaVehiculo();
    }//GEN-LAST:event_guardarVehiculoBTNActionPerformed

    private void limpiarVehiculoBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiarVehiculoBTNActionPerformed
        if (!esNuevoVehiculo) {
            this.controlador.eliminarVehiculo(this.placaTXT.getText());
            this.placaTXT.setEditable(true);

            this.guardarVehiculoBTN.setText("Registrar");
            this.limpiarVehiculoBTN.setText("Limpiar");

            this.consoleVehiculoLog("Vehiculo elminada", Color.GREEN);
            this.actualizarTablaVehiculo();
        }

        this.limpiarVehiculosCampos();
    }//GEN-LAST:event_limpiarVehiculoBTNActionPerformed

    private void nuevoVehiculoBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoVehiculoBTNActionPerformed
        this.limpiarVehiculosCampos();
        this.consoleVehiculoLog("", Color.BLACK);
        this.VehiculoTable.getSelectionModel().clearSelection();
        this.esNuevoVehiculo = true;

        this.guardarVehiculoBTN.setText("Registrar");
        this.limpiarVehiculoBTN.setText("Limpiar");

        this.placaTXT.setEditable(true);

    }//GEN-LAST:event_nuevoVehiculoBTNActionPerformed

    private void BuscarVehiculoTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarVehiculoTXTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarVehiculoTXTActionPerformed

    private void BuscarVehiculoBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarVehiculoBTNActionPerformed
        try {
            Vehiculo vehiculo = controlador.encontrarVehiculo(this.BuscarVehiculoTXT.getText());

            if (vehiculo == null) {
                this.consoleVehiculoLog("Vehiculo no encontrado", Color.RED);
            } else {
                this.onVehiculoEdit(vehiculo);
                this.consoleVehiculoLog("Vehiculo encontrado", Color.GREEN);
            }
        } catch (Exception e) {
            System.out.println(e);
            consoleVehiculoLog("placa no valida", Color.RED);
        }
    }//GEN-LAST:event_BuscarVehiculoBTNActionPerformed

    private void domicilioTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_domicilioTXTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_domicilioTXTActionPerformed

    private void anioTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anioTXTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_anioTXTActionPerformed

    private void mesTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mesTXTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mesTXTActionPerformed

    private void diaTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diaTXTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_diaTXTActionPerformed

    private void PropietarioSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PropietarioSelectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PropietarioSelectActionPerformed

    private void vehiculosPanelComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_vehiculosPanelComponentShown

        this.PropietarioSelect.removeAllItems();

        List<Persona> personas = controlador.obtenerPersonas();

        for (Persona persona : personas) {
            this.PropietarioSelect.addItem(persona);
        }
        this.PropietarioSelect.setSelectedItem(null);

        this.VehiculoTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                consoleVehiculoLog("", Color.BLACK);

                Vehiculo vehiculo = controlador.encontrarVehiculo(
                        VehiculoTable.getValueAt(VehiculoTable.rowAtPoint(evt.getPoint()), 0).toString()
                );
                onVehiculoEdit(vehiculo);
            }
        });
        this.actualizarTablaVehiculo();
    }//GEN-LAST:event_vehiculosPanelComponentShown

    private void personasPanelComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_personasPanelComponentShown

        this.PersonaTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                consolePersonalog("", Color.BLACK);
                Persona persona = controlador.encontrarPersona(
                        Long.parseLong(
                                PersonaTable.getValueAt(PersonaTable.rowAtPoint(evt.getPoint()), 0).toString()
                        )
                );
                onPersonaEdit(persona);
            }
        });

        this.actualizarTablaPersona();

    }//GEN-LAST:event_personasPanelComponentShown

    private void egresarVehiculoBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_egresarVehiculoBTNActionPerformed
        int numEstacionamiento = Integer.parseInt(this.numEstacionamientoLabel.getText());
        this.PropietarioSelect.setSelectedItem(null);

        Estacionamiento estacionamiento = controlador.vaciarEstacionamiento(numEstacionamiento);
        this.onEstacionamientoEdit(estacionamiento);

        this.actualizarTablaEstacionamientos();
    }//GEN-LAST:event_egresarVehiculoBTNActionPerformed

    private void ingresarVehiculoBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ingresarVehiculoBTNActionPerformed
        int numEstacionamiento = Integer.parseInt(this.numEstacionamientoLabel.getText());
        Vehiculo vehiculo = (Vehiculo) this.vehiculoSelect.getSelectedItem();

        Estacionamiento estacionamiento = this.controlador.llenarEstacionamiento(numEstacionamiento, vehiculo);
        this.onEstacionamientoEdit(estacionamiento);
        this.actualizarTablaEstacionamientos();

    }//GEN-LAST:event_ingresarVehiculoBTNActionPerformed

    private void vehiculoSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vehiculoSelectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vehiculoSelectActionPerformed

    private void estacionamientoPanelComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_estacionamientoPanelComponentShown

        this.vehiculoSelect.removeAllItems();

        List<Vehiculo> vehiculos = this.controlador.obtenerVehiculos();

        for (Vehiculo vehiculo : vehiculos) {
            this.vehiculoSelect.addItem(vehiculo);
        }
        this.vehiculoSelect.setSelectedItem(null);

        this.estacionamientoTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                consoleEstaionamientoLog("", Color.BLACK);
                vehiculoSelect.setEnabled(true);
                Estacionamiento estacionamiento = controlador.encontrarEstacionamieto(
                        Integer.parseInt(
                                estacionamientoTable.getValueAt(estacionamientoTable.rowAtPoint(evt.getPoint()), 0).toString()
                        )
                );
                onEstacionamientoEdit(estacionamiento);
            }
        });
        this.actualizarTablaEstacionamientos();

    }//GEN-LAST:event_estacionamientoPanelComponentShown

    private void estacionamieto_propietarioTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estacionamieto_propietarioTXTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_estacionamieto_propietarioTXTActionPerformed

    private void numEstacionamientoLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numEstacionamientoLabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numEstacionamientoLabelActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BuscarPersonaBTN;
    private javax.swing.JTextField BuscarPersonaTXT;
    private javax.swing.JButton BuscarVehiculoBTN;
    private javax.swing.JTextField BuscarVehiculoTXT;
    private javax.swing.JLabel FechaRegistroLabel;
    private javax.swing.JTable PersonaTable;
    private javax.swing.JPanel PrincipalPanel;
    private javax.swing.JComboBox<Persona> PropietarioSelect;
    private javax.swing.JTable VehiculoTable;
    private javax.swing.JTextField anioTXT;
    private javax.swing.JTextField apellidoTXT;
    private javax.swing.JTextField colorTXT;
    private javax.swing.JTextField cuiTXT;
    private javax.swing.JTextField diaTXT;
    private javax.swing.JTextField domicilioTXT;
    private javax.swing.JButton egresarVehiculoBTN;
    private javax.swing.JPanel estacionamientoPanel;
    private javax.swing.JTable estacionamientoTable;
    private javax.swing.JTextField estacionamieto_propietarioTXT;
    private javax.swing.JButton guardarPersonaBTN;
    private javax.swing.JButton guardarVehiculoBTN;
    private javax.swing.JButton ingresarVehiculoBTN;
    private javax.swing.JLabel isDisponibleLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton limpiarPersonaBTN;
    private javax.swing.JButton limpiarVehiculoBTN;
    private javax.swing.JTextField lineaTXT;
    private javax.swing.JLabel logEstacionamiento;
    private javax.swing.JLabel logPersona;
    private javax.swing.JLabel logVehiculo;
    private javax.swing.JTextField marcaTXT;
    private javax.swing.JTextField mesTXT;
    private javax.swing.JTextField modeloTXT;
    private javax.swing.JTabbedPane navegacionPanel;
    private javax.swing.JTextField nitTXT;
    private javax.swing.JTextField nombreTXT;
    private javax.swing.JButton nuevaPersonaBTN;
    private javax.swing.JButton nuevoVehiculoBTN;
    private javax.swing.JTextField numEstacionamientoLabel;
    private javax.swing.JPanel personasPanel;
    private javax.swing.JTextField placaTXT;
    private javax.swing.JTextField telefonoTXT;
    private javax.swing.JComboBox<Vehiculo> vehiculoSelect;
    private javax.swing.JPanel vehiculosPanel;
    // End of variables declaration//GEN-END:variables
}
