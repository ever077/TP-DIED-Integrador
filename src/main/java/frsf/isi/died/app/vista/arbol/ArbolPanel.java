package frsf.isi.died.app.vista.arbol;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import frsf.isi.died.app.controller.ArbolController;
import frsf.isi.died.app.dao.MaterialCapacitacionDaoDefault;
import frsf.isi.died.tp.estructuras.Nodo;
import frsf.isi.died.tp.estructuras.TipoNodo;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;

public class ArbolPanel extends JPanel {

	JLabel lblTipo;
	JLabel lblValor;
	JTextField txtValor;
	JComboBox comboTipo;
	JButton btnAgregarNodo;
    JScrollPane  scrollPane;
    JTree arbol;
    DefaultTreeModel modeloArbol;
    
    DefaultMutableTreeNode raiz;
    
    ArbolController arbolController;
    
    public ArbolPanel() {
    	this.setLayout(new GridBagLayout());
    }
	
    public void setController(ArbolController arbolController) {
			this.arbolController = arbolController;
		}
    
	public DefaultMutableTreeNode getRaiz() {
		return raiz;
	}

	public void construir(MaterialCapacitacion m) {
		GridBagConstraints gridConst= new GridBagConstraints();
		
		lblTipo = new JLabel("Tipo: ");
		gridConst.gridy = 1;
		gridConst.gridx = 1;
		gridConst.gridwidth = 2;
		this.add(lblTipo, gridConst);
		
		comboTipo = new JComboBox();
		
		comboTipo.setModel(new DefaultComboBoxModel(TipoNodo.values()));
		comboTipo.removeItem(TipoNodo.TITULO);
		

		comboTipo.setBackground(getBackground().brighter());
		gridConst.gridy = 1;
		gridConst.gridx = 3;
		gridConst.gridwidth = 2;
		this.add(comboTipo, gridConst);
		
		lblValor = new JLabel("Valor: ");
		gridConst.gridy = 2;
		gridConst.gridx = 1;
		gridConst.gridwidth = 2;
		this.add(lblValor, gridConst);
		
		txtValor = new JTextField();
		txtValor.setColumns(20);
		gridConst.gridy = 2;
		gridConst.gridx = 3;
		gridConst.gridwidth = 8;
		this.add(txtValor, gridConst);
		
		btnAgregarNodo = new JButton("Agregar Nodo");
		btnAgregarNodo.addActionListener( e ->{
			
			DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) arbol.getLastSelectedPathComponent();
			arbolController.cargarArbol(nodo, TipoNodo.valueOf(comboTipo.getSelectedItem().toString()), txtValor.getText(), arbol);
			arbolController.guardarArbol(m, arbol);
			
		});
		gridConst.gridy = 3;
		gridConst.gridx = 3;
		gridConst.gridwidth = 2;
		gridConst.weightx = 1.0;
		gridConst.anchor = GridBagConstraints.LINE_START;
		this.add(btnAgregarNodo, gridConst);
		
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(m.getTitulo());
		this.raiz = raiz;
		modeloArbol = new DefaultTreeModel(this.raiz);
		arbol = new JTree(modeloArbol);
		scrollPane =  new JScrollPane(arbol);
		
		gridConst.gridy = 4;
		gridConst.gridx = 0;
		gridConst.gridwidth = 20;
		gridConst.weighty=1.0;
		gridConst.weightx=1.0;
		gridConst.fill=GridBagConstraints.BOTH;
		gridConst.anchor=GridBagConstraints.PAGE_START;	
		this.add(scrollPane, gridConst);
		
	}
	
    public void setNuevoNodo(String titulo) {
    	DefaultMutableTreeNode d = new DefaultMutableTreeNode();
    	d.setUserObject(titulo);
    	this.raiz.add(d);
    }
	
}
