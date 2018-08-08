package frsf.isi.died.app.vista.pageRank;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import frsf.isi.died.app.controller.BuscarController;
import frsf.isi.died.app.controller.PageRankController;
import frsf.isi.died.app.vista.material.LibroTableModel;
import frsf.isi.died.app.vista.material.VideoTableModel;
import frsf.isi.died.app.vista.wishlist.WishTableModel;
import frsf.isi.died.tp.modelo.productos.Temas;

public class PageRankPanel extends JPanel {

	private JLabel lblTema;
	private JComboBox comboTema;
	private JButton btnBuscar;
	private JScrollPane scrollPane;
	private JTable tabla;
	
	private PageRankController pageRankController;
	private PageRankTableModel pageRankTableModel;
	
	
	// Table model de PageRank
	
	public PageRankPanel() {
		this.setLayout(new GridBagLayout());
		pageRankTableModel = new PageRankTableModel();
	}
	
	public void construir() {
		GridBagConstraints gridConst = new GridBagConstraints();
		lblTema = new JLabel("Tema: ");
		gridConst.gridx=0;
		gridConst.gridy=0;
		this.add(lblTema, gridConst);
		
		comboTema = new JComboBox();
		for(Temas t : Temas.values()) {
			comboTema.addItem(t.getNombre());
		}
		// El combo box muestra la posicion inicial
		comboTema.setSelectedIndex(0);
		comboTema.setBackground(getBackground().brighter());
		gridConst.gridx = 1;
		gridConst.gridwidth = 2;
		this.add(comboTema, gridConst);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener( e ->{
			
		});
		gridConst.gridx = 4;
		gridConst.gridwidth = 1;
		gridConst.weightx=1.0;
		gridConst.anchor = GridBagConstraints.LINE_START;
		this.add(btnBuscar, gridConst);
		
		tabla = new JTable();
		tabla.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(tabla);
		
		gridConst.gridx=0;
		gridConst.gridy=1;
		gridConst.gridwidth=5;
		gridConst.weighty=1.0;
		gridConst.weightx=1.0;
		gridConst.fill = GridBagConstraints.BOTH;
		gridConst.anchor = GridBagConstraints.PAGE_START;		
		this.add(scrollPane, gridConst);
		
	}
	
	public void setController(PageRankController pageRankController) {
		this.pageRankController = pageRankController;
	}
	
}
