package compi1.travelmapgt;

import compi1.travelmapgt.exceptions.NoDataFoundException;
import compi1.travelmapgt.exceptions.NoPathException;
import compi1.travelmapgt.models.KeyMove;
import compi1.travelmapgt.models.Recorrido;
import compi1.travelmapgt.structures.btree.BTree;
import compi1.travelmapgt.util.Clock;
import java.io.IOException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author yennifer
 */
public class GuidedTravel extends javax.swing.JFrame {

    private Backend backend;

    private JComboBox[] specifications;
    private BTree<Recorrido> recorridos;
    private Recorrido currentRecorrido;
    private int currentNode;

    /**
     * Creates new form MainMenu
     *
     * @param backend
     * @param recorridos
     * @param specifications
     * @throws compi1.travelmapgt.exceptions.NoPathException
     * @throws java.io.IOException
     */
    public GuidedTravel(Backend backend, JComboBox[] specifications, BTree<Recorrido> recorridos)
            throws NoPathException, IOException {
        initComponents();
        this.setLocationRelativeTo(null);
        initDetails(specifications);
        this.backend = backend;
        this.recorridos = recorridos;
        this.specifications = specifications;
        currentRecorrido = backend.initDefinePath(recorridos, grafoDisplay, specifications);
        displayRoute.setText(currentRecorrido.toString());
        displayWeight.setText(String.valueOf(currentRecorrido.getWeight()));
        currentNode = 0;
        backend.initOptionsRecorrido(moveToCB, recorridos, currentNode, -1);
    }

    private void initDetails(JComboBox[] specifications) {
        destinityDisp.setText(specifications[MainMenu.TO_NODE].getSelectedItem().toString());
        typeTransDisp.setText(specifications[MainMenu.TYPE_TRANS].getSelectedItem().toString());

        String[] filter = specifications[MainMenu.FILTER].getSelectedItem().toString().split("/");
        String filterName;
        if (filter.length == 2) {
            filterName = specifications[MainMenu.TYPE_TRANS].getSelectedIndex() == MainMenu.VEHICLE_TYPE
                    ? filter[0] : filter[1];
        } else {
            filterName = filter[0];
        }
        filterDisp.setText(filterName);
        typeRouteDisp.setText(specifications[MainMenu.BEST_SPECIFICATION].getSelectedItem().toString());
        currentNodeDisp.setText(specifications[MainMenu.FROM_NODE].getSelectedItem().toString());
    }

    protected void initClock(Clock clock) {
        clock.setDisplayTime(hourDisplay);
    }

    private void continuePath() {
        int numberNodeTo = ((KeyMove) moveToCB.getSelectedItem()).getKeyNumber();
        if (numberNodeTo == currentRecorrido.getRecorrido().get(currentNode + 1)) { //si va por el camino adecuado
            continueRoute();
        } else { //si hay que recalcular la ruta
            recalculateRoute();
        }
    }

    private void recalculateRoute() {
        if (((KeyMove) moveToCB.getSelectedItem()).getKeyString()
                .equals(specifications[MainMenu.TO_NODE].getSelectedItem().toString())) {
            finishRoute();
        } else {
            currentRecorrido.hiddeInGraph(true);
            try {
                currentNode = 0;
                this.recorridos = backend.generateRecorridos(
                        specifications,
                        ((KeyMove) moveToCB.getSelectedItem()).getKeyString()
                );
                currentNodeDisp.setText(((KeyMove) moveToCB.getSelectedItem()).getKeyString());
                currentRecorrido = backend.initDefinePath(recorridos, grafoDisplay, specifications);
                displayRoute.setText(currentRecorrido.toString());
                displayWeight.setText(String.valueOf(currentRecorrido.getWeight()));
                backend.initOptionsRecorrido(moveToCB, recorridos, currentNode, -1);
            } catch (NoDataFoundException ex) {
                //no deberia pasar
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error inesperado, no se pudo cargar la imagen correctamente.");
            } catch (NoPathException ex) {
                JOptionPane.showMessageDialog(null, "No se pudo encontrar un camino hacia el nodo especificado.");
            }
        }
    }

    private void continueRoute() {
        currentNode++; //nos movemos
        if (currentNode == currentRecorrido.getRecorrido().size() - 1) { //llego al final
            finishRoute();
        } else { //aun no es el final
            try {
                currentRecorrido.getGraph().getNode(currentRecorrido.getRecorrido().get(currentNode - 1))
                        .setActive(false);
                currentNodeDisp.setText(currentRecorrido.getGraph().getNode(currentRecorrido.getRecorrido().get(currentNode)).getKeyLocation());

                backend.initOptionsRecorrido(moveToCB, recorridos, currentNode, currentRecorrido.getRecorrido().get(currentNode));

                backend.updateRecorrido(grafoDisplay, specifications);
            } catch (NoDataFoundException ex) {
                System.out.println("No deberia ocurrir nunca, desde guidedTravel");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado no se pudo graficar el grafo");
            }
        }
    }

    private void finishRoute() {
        JOptionPane.showMessageDialog(null, "Has llegado a tu destino :D");
        this.dispose();
        backend.showFronted();
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        continueRoute = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        moveToCB = new javax.swing.JComboBox<>();
        stop = new javax.swing.JButton();
        destinityDisp = new javax.swing.JLabel();
        typeTransDisp = new javax.swing.JLabel();
        filterDisp = new javax.swing.JLabel();
        typeRouteDisp = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        currentNodeDisp = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        scroll = new javax.swing.JScrollPane();
        displayRoute = new javax.swing.JTextPane();
        jLabel14 = new javax.swing.JLabel();
        displayWeight = new javax.swing.JLabel();
        displayGraph = new javax.swing.JPanel();
        grafoDisplay = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 47, 79));

        jLabel1.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(228, 228, 228));
        jLabel1.setText("Hora:");

        hourDisplay.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        hourDisplay.setForeground(new java.awt.Color(228, 228, 228));
        hourDisplay.setText("00:00");

        jLabel3.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(228, 228, 228));
        jLabel3.setText("Destino:");

        jLabel4.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(228, 228, 228));
        jLabel4.setText("Trans.");

        jLabel5.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(228, 228, 228));
        jLabel5.setText("Filtro:");

        jLabel6.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(228, 228, 228));
        jLabel6.setText("Ruta:");

        continueRoute.setBackground(new java.awt.Color(0, 153, 0));
        continueRoute.setForeground(new java.awt.Color(255, 255, 255));
        continueRoute.setText("Continuar Recorrido");
        continueRoute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                continueRouteActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(228, 228, 228));
        jLabel7.setText("Moverse:");

        moveToCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "La mejor", "La peor" }));

        stop.setBackground(new java.awt.Color(204, 0, 0));
        stop.setForeground(new java.awt.Color(255, 255, 255));
        stop.setText("Parar recorrido");
        stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopActionPerformed(evt);
            }
        });

        destinityDisp.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        destinityDisp.setForeground(new java.awt.Color(228, 228, 228));
        destinityDisp.setText("Destinity");

        typeTransDisp.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        typeTransDisp.setForeground(new java.awt.Color(228, 228, 228));
        typeTransDisp.setText("TypeTrans");

        filterDisp.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        filterDisp.setForeground(new java.awt.Color(228, 228, 228));
        filterDisp.setText("NombreFiltro");

        typeRouteDisp.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        typeRouteDisp.setForeground(new java.awt.Color(228, 228, 228));
        typeRouteDisp.setText("Mejor");

        jLabel12.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(228, 228, 228));
        jLabel12.setText("Ubicacion:");

        currentNodeDisp.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        currentNodeDisp.setForeground(new java.awt.Color(228, 228, 228));
        currentNodeDisp.setText("CurrentNode");

        jLabel2.setFont(new java.awt.Font("Cantarell", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(228, 228, 228));
        jLabel2.setText("SIGUIENDO UN RECORRIDO");

        jLabel13.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(228, 228, 228));
        jLabel13.setText("Ruta:");

        displayRoute.setEditable(false);
        displayRoute.setBackground(new java.awt.Color(0, 59, 98));
        displayRoute.setBorder(null);
        displayRoute.setFont(new java.awt.Font("Cantarell", 0, 20)); // NOI18N
        displayRoute.setForeground(new java.awt.Color(255, 255, 255));
        displayRoute.setText("Display the best route");
        scroll.setViewportView(displayRoute);

        jLabel14.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(228, 228, 228));
        jLabel14.setText("Peso Ruta:");

        displayWeight.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        displayWeight.setForeground(new java.awt.Color(228, 228, 228));
        displayWeight.setText("-------------");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(moveToCB, 0, 240, Short.MAX_VALUE)
                                    .addComponent(currentNodeDisp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(displayWeight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(22, 22, 22))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(continueRoute, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                                            .addComponent(stop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(destinityDisp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(typeTransDisp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(filterDisp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(typeRouteDisp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(hourDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(23, 23, 23))))
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1)))
                .addContainerGap())
            .addComponent(jSeparator2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(hourDisplay))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(destinityDisp))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(typeTransDisp))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(filterDisp))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(typeRouteDisp))
                .addGap(29, 29, 29)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(currentNodeDisp))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(displayWeight))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moveToCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(52, 52, 52)
                .addComponent(continueRoute)
                .addGap(18, 18, 18)
                .addComponent(stop)
                .addContainerGap(148, Short.MAX_VALUE))
        );

        displayGraph.setBackground(new java.awt.Color(2, 41, 58));
        displayGraph.setForeground(new java.awt.Color(0, 63, 72));

        javax.swing.GroupLayout displayGraphLayout = new javax.swing.GroupLayout(displayGraph);
        displayGraph.setLayout(displayGraphLayout);
        displayGraphLayout.setHorizontalGroup(
            displayGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displayGraphLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(grafoDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, 812, Short.MAX_VALUE))
        );
        displayGraphLayout.setVerticalGroup(
            displayGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displayGraphLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(grafoDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

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

    private void continueRouteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_continueRouteActionPerformed
        continuePath();
    }//GEN-LAST:event_continueRouteActionPerformed

    private void stopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopActionPerformed
        backend.endPath(currentRecorrido, this);
    }//GEN-LAST:event_stopActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        backend.showFronted();
        this.dispose();
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton continueRoute;
    private javax.swing.JLabel currentNodeDisp;
    private javax.swing.JLabel destinityDisp;
    private javax.swing.JPanel displayGraph;
    private javax.swing.JTextPane displayRoute;
    private javax.swing.JLabel displayWeight;
    private javax.swing.JLabel filterDisp;
    private javax.swing.JLabel grafoDisplay;
    private javax.swing.JLabel hourDisplay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JComboBox<String> moveToCB;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JButton stop;
    private javax.swing.JLabel typeRouteDisp;
    private javax.swing.JLabel typeTransDisp;
    // End of variables declaration//GEN-END:variables
}
