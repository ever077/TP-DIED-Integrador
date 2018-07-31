package frsf.isi.died.app.vista.material;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import frsf.isi.died.app.controller.WishListController;
import frsf.isi.died.app.excepciones.DataOutOfBoundException;
import frsf.isi.died.app.excepciones.MaterialNotFoundException;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;

public class WishListPanel extends JPanel{

	private JScrollPane scrollPane;
	private JTable tabla;
	private JButton btnCancelar;

	private WishTableModel wishTableModel;

	private WishListController controller;
	// Id del libro que selecciona el usuario
	private Integer idLibroSeleccionado = 0;
	
	
	public WishListPanel() {
		this.setLayout(new GridBagLayout());
		wishTableModel = new WishTableModel();
	}
	
	public void construir() {
		GridBagConstraints gridConst= new GridBagConstraints();
		
		

		btnCancelar= new JButton("Cancelar");
		gridConst.gridx=2;
		gridConst.gridy=2;
		gridConst.weightx=1.0;
		gridConst.anchor = GridBagConstraints.LINE_START;
		this.add(btnCancelar, gridConst);
		
		tabla = new JTable(this.wishTableModel);
		tabla.setFillsViewportHeight(true);
		scrollPane= new JScrollPane(tabla);
		
		
		gridConst.gridx=0;
		gridConst.gridwidth=5;	
		gridConst.gridy=0;
		gridConst.weighty=1.0;
		gridConst.weightx=1.0;
		gridConst.fill=GridBagConstraints.BOTH;
		gridConst.anchor=GridBagConstraints.PAGE_START;		
		this.add(scrollPane, gridConst);
	}

	public WishListController getController() {
		return controller;
	}

	public void setController(WishListController controller) {
		this.controller = controller;
	}
	
	public void setListaMateriales(PriorityQueue<MaterialCapacitacion> colaPrioridad,boolean actualizar) {
		this.wishTableModel.setMateriales(colaPrioridad);
		if(actualizar) this.wishTableModel.fireTableDataChanged();
	}
	

	
}
