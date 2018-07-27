package frsf.isi.died.app.vista.material;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import frsf.isi.died.app.controller.BuscarController;
import frsf.isi.died.app.controller.LibroController;
import frsf.isi.died.app.controller.TiposDeOrden;
import frsf.isi.died.app.controller.VideoController;
import frsf.isi.died.app.excepciones.DataOutOfBoundException;
import frsf.isi.died.tp.modelo.productos.Relevancia;
import frsf.isi.died.tp.modelo.productos.Libro;
import frsf.isi.died.tp.modelo.productos.Video;
import frsf.isi.died.tp.util.OrdenarMaterialPorCalificacion;
import frsf.isi.died.tp.util.OrdenarMaterialPorFecha;
import frsf.isi.died.tp.util.OrdenarMaterialPorPrecio;
import frsf.isi.died.tp.util.OrdenarMaterialPorRelevancia;

public class BuscarMaterialPanel extends JPanel {

	private JScrollPane scrollPane;
	private JTable tabla;
	private JLabel lblTitulo;
	private JLabel lblCalificacion;
	private JLabel lblTema;
	private JLabel lblRangosFecha;
	private JLabel lblTipoOrden;
	private JTextField txtTitulo;
	private JTextField txtCalificacion;
	private JTextField txtFechaI;
	private JTextField txtFechaF;
	private JButton btnBuscar;
	private JButton btnCancelar;
	private JComboBox comboTema;
	private JComboBox comboTipoOrden;

	private VideoTableModel tableModelVideo;
	private LibroTableModel tableModelLibro;

	BuscarController buscarController;
	private VideoController controllerVideo;
	private LibroController controllerLibro;
	
	
	public BuscarMaterialPanel() {
		this.setLayout(new GridBagLayout());
		// Asignar el TableModel segun el comboTema seleccionado.
		tableModelLibro = new LibroTableModel();
		tableModelVideo = new VideoTableModel();
	}
	
	public void construir() {
		GridBagConstraints gridConst= new GridBagConstraints();
		lblTitulo = new JLabel("Titulo: ");
		gridConst.gridx=0;
		gridConst.gridy=0;
		this.add(lblTitulo, gridConst);
		
		txtTitulo = new JTextField();
		txtTitulo.setColumns(10);
		gridConst.gridx=1;
		gridConst.gridwidth=1;
		this.add(txtTitulo, gridConst);
		
		
		lblCalificacion = new JLabel("Calificacion: ");
		gridConst.gridx=2;
		this.add(lblCalificacion, gridConst);
		
		txtCalificacion = new JTextField();
		txtCalificacion.setColumns(3);
		gridConst.gridx=3;
		this.add(txtCalificacion, gridConst);
		
		
		lblTema = new JLabel("Tema: ");
		gridConst.gridx=4;
		this.add(lblTema, gridConst);
		
		comboTema = new JComboBox();
		comboTema.addItem("LIBRO");
		comboTema.addItem("VIDEO");
		comboTema.setBackground(getBackground().brighter());
		gridConst.gridx=5;
		this.add(comboTema, gridConst);
		
		
		lblRangosFecha = new JLabel("Rango de fechas: ");
		gridConst.gridx=6;
		this.add(lblRangosFecha, gridConst);
		
		txtFechaI = new JTextField();
		txtFechaI.setToolTipText("dd-MM-yyyy");
		txtFechaI.setColumns(6);
		gridConst.gridx=7;
		this.add(txtFechaI, gridConst);
		
		txtFechaF = new JTextField();
		txtFechaF.setToolTipText("dd-MM-yyyy");
		txtFechaF.setColumns(6);
		gridConst.gridx=8;
		this.add(txtFechaF, gridConst);
		
		
		lblTipoOrden = new JLabel("Tipo de ordenamiento: ");
		gridConst.gridx=0;
		gridConst.gridy=1;
		gridConst.gridwidth=2;
		this.add(lblTipoOrden, gridConst);
		
		comboTipoOrden = new JComboBox();
		for(TiposDeOrden t : TiposDeOrden.values()) {
			comboTipoOrden.addItem(t.getNombre());
		}
		//comboTipoOrden.setModel(new DefaultComboBoxModel(TiposDeOrden.values()));	
		comboTipoOrden.setBackground(getBackground().brighter());
		gridConst.gridx=2;
		gridConst.gridwidth=2;
		this.add(comboTipoOrden, gridConst);
		
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener( e ->{
			try {
				
				if( (comboTema.getSelectedItem().toString()).equals("LIBRO") ) {
					// LIBRO
					tabla.setModel(tableModelLibro);
					String titulo = txtTitulo.getText();
				//	controllerLibro.verificarTitulo(titulo);
					Integer calificacion;
					if(txtCalificacion.getText().isEmpty()) {
						calificacion = -1;
					}else {
						calificacion = Integer.valueOf(txtCalificacion.getText());
						//controllerLibro.verificarCalificacion(calificacion);
					}
					String fechaI = txtFechaI.getText();
					String fechaF = txtFechaF.getText();
					List<Libro> listaLibrosMostrar = buscarController.buscarLibros(titulo, calificacion, fechaI, fechaF);
		
					TiposDeOrden ordenSeleccionado = TiposDeOrden.getEnum(comboTipoOrden.getSelectedItem().toString());
					Set<Libro> listaOrdenada = new TreeSet<Libro>();
					listaOrdenada = buscarController.ordenarLibros(listaLibrosMostrar, ordenSeleccionado);
					buscarController.cargarTablaLibros(listaOrdenada);
					
					//Limpiar campos
					txtTitulo.setText("");
					txtCalificacion.setText("");
					txtFechaI.setText("");
					txtFechaF.setText("");
					comboTema.setSelectedIndex(0);
					comboTipoOrden.setSelectedIndex(0);
				}else {
					// VIDEO
					tabla.setModel(tableModelVideo);
					controllerVideo.verificarTitulo(txtTitulo.getText());
				}
				
			}
			catch(DataOutOfBoundException d){
				 JOptionPane.showMessageDialog(this, d.getMessage(), "Dato fuera de rango", JOptionPane.ERROR_MESSAGE);
			}
			catch(Exception ex) {
			    JOptionPane.showMessageDialog(this, ex.getMessage(), "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
			}
		});
		gridConst.gridx=9;
		gridConst.gridy=0;
		gridConst.weightx=1.0;
		gridConst.anchor = GridBagConstraints.LINE_START;
		gridConst.gridwidth=1;
		this.add(btnBuscar, gridConst);
		
		
		btnCancelar= new JButton("Cancelar");
		gridConst.gridx=9;
		gridConst.gridy=1;
		gridConst.weightx=1.0;
		gridConst.anchor = GridBagConstraints.LINE_START;
		this.add(btnCancelar, gridConst);
		
		tabla = new JTable();
		tabla.setFillsViewportHeight(true);
		scrollPane= new JScrollPane(tabla);
		
		gridConst.gridx=0;
		gridConst.gridwidth=10;	
		gridConst.gridy=2;
		gridConst.weighty=1.0;
		gridConst.weightx=1.0;
		gridConst.fill=GridBagConstraints.BOTH;
		gridConst.anchor=GridBagConstraints.PAGE_START;		
		this.add(scrollPane, gridConst);
	}
	
	public void setController(BuscarController buscarController) {
		this.buscarController = buscarController;
	}
	
	public void setControllers(LibroController controllerLibro, VideoController controllerVideo) {
		this.controllerLibro = controllerLibro;
		this.controllerVideo = controllerVideo;
	}
	
	public void setListaLibros(Set<Libro> librosLista,boolean actualizar) {
		List<Libro> l = new ArrayList<Libro>();
		l.addAll(librosLista);
		this.tableModelLibro.setLibros(l);
		if(actualizar) this.tableModelLibro.fireTableDataChanged();
	}
	
}
