/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frsf.isi.died.app.vista.grafo;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import frsf.isi.died.app.controller.GrafoController;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;

/**
 *
 * @author mdominguez
 */
public class ControlPanel extends JPanel {
    
    private JComboBox<MaterialCapacitacion> cmbVertice1; 
    private JComboBox<MaterialCapacitacion> cmbVertice2; 
    private JTextField txtLongitudCamino; 
    private JButton btnBuscarCamino; 
    private GrafoController controller;
    private List<MaterialCapacitacion> listaVertices;
  
    
        
    public void armarPanel( List<MaterialCapacitacion> listaVertices){
    	this.listaVertices = listaVertices;
//    	this.cmbVertice1 = new JComboBox(listaVertices.toArray()); 
//      this.cmbVertice2 = new JComboBox(listaVertices.toArray()); 
    	this.cmbVertice1 = new JComboBox();
    	this.cmbVertice1.setModel(new DefaultComboBoxModel(listaVertices.toArray()));
    	
    	this.cmbVertice2 = new JComboBox();
    	this.cmbVertice2.setModel(new DefaultComboBoxModel(listaVertices.toArray()));
    	
        this.txtLongitudCamino = new JTextField(5); 
        this.btnBuscarCamino = new JButton("Buscar Camino");
        this.btnBuscarCamino.addActionListener(
                e -> { 
                	controller.rePintarGrafo();
                	if(txtLongitudCamino.getText().isEmpty()) {
                		Integer idOrigen = this.listaVertices.get(cmbVertice1.getSelectedIndex()).getId();
	                    Integer idDestino = this.listaVertices.get(cmbVertice2.getSelectedIndex()).getId();
	                    List<List<MaterialCapacitacion>> caminos = controller.buscarCaminos(idOrigen,idDestino); 
	                    controller.pintarPrimero(caminos);
                	}
                	
                	
                	else{
                		Integer n = Integer.parseInt(txtLongitudCamino.getText());
	                    Integer idOrigen = this.listaVertices.get(cmbVertice1.getSelectedIndex()).getId();
	                    Integer idDestino = this.listaVertices.get(cmbVertice2.getSelectedIndex()).getId();
	                    List<List<MaterialCapacitacion>> caminos = controller.buscarCaminosHastaNSaltos(idOrigen,idDestino,n); 
	                    controller.pintarPrimero(caminos);
                	}
                }
                    
        );
        this.add(new JLabel("Vertice Origen"));        
    	this.add(cmbVertice1);
    	this.add(new JLabel("Vertice Destino"));
    	this.add(cmbVertice2);
    	this.add(new JLabel("Cantidad de saltos"));        
    	this.add(txtLongitudCamino);        
    	this.add(btnBuscarCamino);        
    }

    public GrafoController getController() {
        return controller;
    }

    public void setController(GrafoController controller) {
        this.controller = controller;
    }

    public void cargarCombosBoxs(List<MaterialCapacitacion> listaVertices) {
    	this.listaVertices = listaVertices;
    	this.cmbVertice1.removeAllItems();
    	this.cmbVertice2.removeAllItems();
//    	this.cmbVertice1. = new JComboBox(listaVertices.toArray()); 
//        this.cmbVertice2 = new JComboBox(listaVertices.toArray()); 
        this.cmbVertice1.setModel(new DefaultComboBoxModel(listaVertices.toArray()));
        this.cmbVertice2.setModel(new DefaultComboBoxModel(listaVertices.toArray()));
        this.cmbVertice1.repaint();
        this.cmbVertice2.repaint();
    }
    
}
