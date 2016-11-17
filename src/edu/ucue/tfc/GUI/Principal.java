/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ucue.tfc.GUI;

import edu.ucue.tfc.Service.VideoService;
import javax.swing.JOptionPane;
import org.opencv.core.Core;

/**
 *
 * @author Paul
 */
public class Principal extends javax.swing.JFrame {
    
    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
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
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuitemNuevo = new javax.swing.JMenuItem();
        menuitemEliminar = new javax.swing.JMenuItem();
        menuitemListar = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuitemDiagrama = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CVTrafficCounter");
        setLocationByPlatform(true);

        jLabel1.setFont(new java.awt.Font("Lucida Sans", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("CVTrafficCounter");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(39, 39, 39))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(jLabel1)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/ucue/tfc/GUI/icons/video-player.png"))); // NOI18N
        jMenu1.setText("Video");

        menuitemNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/ucue/tfc/GUI/icons/clapperboard.png"))); // NOI18N
        menuitemNuevo.setText("Nuevo Registro");
        menuitemNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemNuevoActionPerformed(evt);
            }
        });
        jMenu1.add(menuitemNuevo);

        menuitemEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/ucue/tfc/GUI/icons/unchecked-1.png"))); // NOI18N
        menuitemEliminar.setText("Eliminar Registro");
        menuitemEliminar.setActionCommand("Eliminar Registro");
        menuitemEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemEliminarActionPerformed(evt);
            }
        });
        jMenu1.add(menuitemEliminar);

        menuitemListar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/ucue/tfc/GUI/icons/file-4.png"))); // NOI18N
        menuitemListar.setText("Listar Registros");
        menuitemListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemListarActionPerformed(evt);
            }
        });
        jMenu1.add(menuitemListar);

        jMenuBar1.add(jMenu1);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/ucue/tfc/GUI/icons/folder-5.png"))); // NOI18N
        jMenu2.setText("Datos");

        menuitemDiagrama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/ucue/tfc/GUI/icons/statistics.png"))); // NOI18N
        menuitemDiagrama.setText("Diagrama de barras");
        menuitemDiagrama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemDiagramaActionPerformed(evt);
            }
        });
        jMenu2.add(menuitemDiagrama);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuitemNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemNuevoActionPerformed
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        RegistroVideo video = new RegistroVideo();
        video.show();
    }//GEN-LAST:event_menuitemNuevoActionPerformed

    private void menuitemEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemEliminarActionPerformed
        // TODO add your handling code here:
        if (!VideoService.getMapRegistros().isEmpty()) {
            BuscarRegistros eliminar = new BuscarRegistros();
            eliminar.show();
        } else {
            JOptionPane.showMessageDialog(rootPane, "No hay registros guardados!");
            return;
        }
        
    }//GEN-LAST:event_menuitemEliminarActionPerformed

    private void menuitemListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemListarActionPerformed
        // TODO add your handling code here:
        if (!VideoService.getMapRegistros().isEmpty()) {
            ListaRegistros listaRegistros = new ListaRegistros();
            listaRegistros.setLista(VideoService.getRegistros());
            listaRegistros.show();
        } else {
            JOptionPane.showMessageDialog(rootPane, "No hay registros guardados!");
            return;
        }
    }//GEN-LAST:event_menuitemListarActionPerformed

    private void menuitemDiagramaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemDiagramaActionPerformed
        // TODO add your handling code here:
        if (!VideoService.getMapRegistros().isEmpty()) {
            GraficarRegistro grafico = new GraficarRegistro();
            grafico.show();
        } else {
            JOptionPane.showMessageDialog(rootPane, "No hay registros guardados!");
            return;
        }
    }//GEN-LAST:event_menuitemDiagramaActionPerformed

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
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenuItem menuitemDiagrama;
    private javax.swing.JMenuItem menuitemEliminar;
    private javax.swing.JMenuItem menuitemListar;
    private javax.swing.JMenuItem menuitemNuevo;
    // End of variables declaration//GEN-END:variables
}
