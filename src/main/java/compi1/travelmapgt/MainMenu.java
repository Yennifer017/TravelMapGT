package compi1.travelmapgt;

import compi1.travelmapgt.util.Clock;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author yennifer
 */
public class MainMenu extends javax.swing.JFrame {

    private Clock clock;
    private Thread threadClock;
    private Backend backend;
   
    protected final static int FROM_NODE = 0, TO_NODE = 1, 
            TYPE_TRANS = 2, FILTER = 3, BEST_SPECIFICATION = 4;
    
    protected final static int VEHICLE_TYPE = 0;
    
    private JComboBox[] specifications;

    /**
     * Creates new form MainMenu
     */
    public MainMenu() {
        initComponents();
        this.setLocationRelativeTo(null);
        initClock();
        backend = new Backend();
        initComboBox();
    }

    private void initClock() {
        clock = new Clock(hourDisplay);
        threadClock = new Thread(clock);
        threadClock.start();
    }
    
    private void initComboBox(){
        specifications = new JComboBox[5];
        specifications[FROM_NODE] = fromNodeCB;
        specifications[TO_NODE] = toNodeCB;
        specifications[TYPE_TRANS] = transpCB;
        specifications[FILTER] = filterCB;
        specifications[BEST_SPECIFICATION] = typeRuteCB;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        hourDisplay = new javax.swing.JLabel();
        fromNodeCB = new javax.swing.JComboBox<>();
        toNodeCB = new javax.swing.JComboBox<>();
        transpCB = new javax.swing.JComboBox<>();
        filterCB = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        bStart = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        typeRuteCB = new javax.swing.JComboBox<>();
        displayGraph = new javax.swing.JPanel();
        grafoDisplay = new javax.swing.JLabel();
        menu = new javax.swing.JMenuBar();
        dataMenu = new javax.swing.JMenu();
        LocationDataOp = new javax.swing.JMenuItem();
        TraficDataOp = new javax.swing.JMenuItem();
        exampleDataOp = new javax.swing.JMenuItem();
        restartIde = new javax.swing.JMenuItem();
        HourMenu = new javax.swing.JMenu();
        setHourOp = new javax.swing.JMenuItem();
        resetHourOp = new javax.swing.JMenuItem();
        pauseClockOp = new javax.swing.JMenuItem();
        continueClock = new javax.swing.JMenuItem();
        Information = new javax.swing.JMenu();
        helpOp = new javax.swing.JMenuItem();
        creditsOp = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 47, 79));

        jLabel1.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(228, 228, 228));
        jLabel1.setText("Hora:");

        hourDisplay.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        hourDisplay.setForeground(new java.awt.Color(228, 228, 228));
        hourDisplay.setText("00:00");

        transpCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vehiculo", "Caminando" }));

        filterCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gasolina / Desgaste Fisico", "Distancia", "Distancia y Gasolina/Desgaste", "Dist, tiempo y trafico (vehiculo)" }));

        jLabel3.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(228, 228, 228));
        jLabel3.setText("Inicio:");

        jLabel4.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(228, 228, 228));
        jLabel4.setText("Fin:");

        jLabel5.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(228, 228, 228));
        jLabel5.setText("Trans.");

        jLabel6.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(228, 228, 228));
        jLabel6.setText("Filtros:");

        bStart.setText("Iniciar");
        bStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bStartActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(228, 228, 228));
        jLabel7.setText("Ruta:");

        typeRuteCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "La mejor", "La peor" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hourDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(toNodeCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(transpCB, 0, 240, Short.MAX_VALUE)
                    .addComponent(filterCB, 0, 1, Short.MAX_VALUE)
                    .addComponent(fromNodeCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(typeRuteCB, 0, 1, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
            .addComponent(jSeparator1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(hourDisplay))
                .addGap(26, 26, 26)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fromNodeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(toNodeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(transpCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filterCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeRuteCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(73, 73, 73)
                .addComponent(bStart)
                .addContainerGap(283, Short.MAX_VALUE))
        );

        displayGraph.setBackground(new java.awt.Color(2, 41, 58));
        displayGraph.setForeground(new java.awt.Color(0, 63, 72));

        javax.swing.GroupLayout displayGraphLayout = new javax.swing.GroupLayout(displayGraph);
        displayGraph.setLayout(displayGraphLayout);
        displayGraphLayout.setHorizontalGroup(
            displayGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displayGraphLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(grafoDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
                .addContainerGap())
        );
        displayGraphLayout.setVerticalGroup(
            displayGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displayGraphLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(grafoDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        dataMenu.setText("Datos");

        LocationDataOp.setText("Ingresar datos de ubicacion");
        LocationDataOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LocationDataOpActionPerformed(evt);
            }
        });
        dataMenu.add(LocationDataOp);

        TraficDataOp.setText("Ingresar datos de trafico");
        TraficDataOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TraficDataOpActionPerformed(evt);
            }
        });
        dataMenu.add(TraficDataOp);

        exampleDataOp.setText("Datos de ejemplo");
        exampleDataOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exampleDataOpActionPerformed(evt);
            }
        });
        dataMenu.add(exampleDataOp);

        restartIde.setText("Eliminar datos");
        restartIde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restartIdeActionPerformed(evt);
            }
        });
        dataMenu.add(restartIde);

        menu.add(dataMenu);

        HourMenu.setText("Horario");

        setHourOp.setText("Ajustar reloj");
        setHourOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setHourOpActionPerformed(evt);
            }
        });
        HourMenu.add(setHourOp);

        resetHourOp.setText("Autoajustar reloj");
        resetHourOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetHourOpActionPerformed(evt);
            }
        });
        HourMenu.add(resetHourOp);

        pauseClockOp.setText("Pausar reloj");
        pauseClockOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseClockOpActionPerformed(evt);
            }
        });
        HourMenu.add(pauseClockOp);

        continueClock.setText("Reiniciar reloj");
        continueClock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                continueClockActionPerformed(evt);
            }
        });
        HourMenu.add(continueClock);

        menu.add(HourMenu);

        Information.setText("Informacion");

        helpOp.setText("Ayuda");
        helpOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpOpActionPerformed(evt);
            }
        });
        Information.add(helpOp);

        creditsOp.setText("Creditos");
        creditsOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creditsOpActionPerformed(evt);
            }
        });
        Information.add(creditsOp);

        menu.add(Information);

        setJMenuBar(menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(displayGraph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(displayGraph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LocationDataOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LocationDataOpActionPerformed
        backend.readLocationInfo(grafoDisplay, fromNodeCB, toNodeCB);
    }//GEN-LAST:event_LocationDataOpActionPerformed

    private void TraficDataOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TraficDataOpActionPerformed
        backend.readTraficInfo();
    }//GEN-LAST:event_TraficDataOpActionPerformed

    private void exampleDataOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exampleDataOpActionPerformed
        JOptionPane.showMessageDialog(null, """
                                            Veanse ejemplos de archivos de entrada en 
                                            el repositorio de GitHub del proyecto ;)""");
    }//GEN-LAST:event_exampleDataOpActionPerformed

    private void setHourOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setHourOpActionPerformed
        backend.setHour(clock);
    }//GEN-LAST:event_setHourOpActionPerformed

    private void resetHourOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetHourOpActionPerformed
        clock.restartHour();
    }//GEN-LAST:event_resetHourOpActionPerformed

    private void pauseClockOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseClockOpActionPerformed
        clock.stop();
    }//GEN-LAST:event_pauseClockOpActionPerformed

    private void helpOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpOpActionPerformed
        JOptionPane.showMessageDialog(null,
                "Puedes consultar el manual de usuario en el repositorio de github",
                "Ayuda", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_helpOpActionPerformed

    private void creditsOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creditsOpActionPerformed
        JOptionPane.showMessageDialog(null, """
                Proyecto creado con muuuy poco tiempo, no esperes mucho de el :c tenle piedad al programa
                Si encuentras un bug es culpa del poco tiempo de hacer el proyecto y no dormir bien -iG""",
                "Ayuda", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_creditsOpActionPerformed

    private void bStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bStartActionPerformed
        try {
            backend.findPaths(specifications);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "No hay datos para buscar el recorrido");
        }
    }//GEN-LAST:event_bStartActionPerformed

    private void continueClockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_continueClockActionPerformed
        clock.restart();
        threadClock.interrupt();
    }//GEN-LAST:event_continueClockActionPerformed

    private void restartIdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restartIdeActionPerformed
        backend.restartIde(fromNodeCB, toNodeCB, grafoDisplay);
    }//GEN-LAST:event_restartIdeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu HourMenu;
    private javax.swing.JMenu Information;
    private javax.swing.JMenuItem LocationDataOp;
    private javax.swing.JMenuItem TraficDataOp;
    private javax.swing.JButton bStart;
    private javax.swing.JMenuItem continueClock;
    private javax.swing.JMenuItem creditsOp;
    private javax.swing.JMenu dataMenu;
    private javax.swing.JPanel displayGraph;
    private javax.swing.JMenuItem exampleDataOp;
    private javax.swing.JComboBox<String> filterCB;
    private javax.swing.JComboBox<String> fromNodeCB;
    private javax.swing.JLabel grafoDisplay;
    private javax.swing.JMenuItem helpOp;
    private javax.swing.JLabel hourDisplay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JMenuBar menu;
    private javax.swing.JMenuItem pauseClockOp;
    private javax.swing.JMenuItem resetHourOp;
    private javax.swing.JMenuItem restartIde;
    private javax.swing.JMenuItem setHourOp;
    private javax.swing.JComboBox<String> toNodeCB;
    private javax.swing.JComboBox<String> transpCB;
    private javax.swing.JComboBox<String> typeRuteCB;
    // End of variables declaration//GEN-END:variables
}
