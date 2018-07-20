package frsf.isi.died.app.vista.material;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import frsf.isi.died.app.controller.LibroController;
import frsf.isi.died.app.controller.VideoController;
import frsf.isi.died.tp.modelo.productos.Libro;
import frsf.isi.died.tp.modelo.productos.Video;

public class VideoPanel extends VPanel{
	private JScrollPane scrollPane;
	private JTable tabla;
	private JLabel lblTitulo;
	private JLabel lblCosto;
	
	private JLabel lblDuracion;
	private JTextField txtTitulo;
	private JTextField txtCosto;

	private JTextField txtDuracion;
	private JButton btnAgregar;
	private JButton btnCancelar;

	private VideoTableModel tableModel;

	private VideoController controller;
	
	public VideoPanel() {
		this.setLayout(new GridBagLayout());
		tableModel = new VideoTableModel();
	}
	
	public void construir() {
		GridBagConstraints gridConst= new GridBagConstraints();
		lblTitulo = new JLabel("Titulo: ");
		gridConst.gridx=0;
		gridConst.gridy=0;
		this.add(lblTitulo, gridConst);
		
		txtTitulo = new JTextField();
		txtTitulo.setColumns(40);
		gridConst.gridx=1;
		gridConst.gridwidth=5;
		this.add(txtTitulo, gridConst);
		

		btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener( e ->{
			try {
				Double costo = Double.valueOf(txtCosto.getText());
				Integer duracion = Integer.valueOf(txtDuracion.getText());
				controller.agregarVideo(txtTitulo.getText(), costo, duracion);
				txtTitulo.setText("");
				txtCosto.setText("");		
				txtDuracion.setText("");
			}catch(Exception ex) {
			    JOptionPane.showMessageDialog(this, ex.getMessage(), "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
			}
		});
		gridConst.gridwidth=1;
		gridConst.weightx=1.0;
		gridConst.anchor = GridBagConstraints.LINE_START;
		gridConst.gridx=6;
		this.add(btnAgregar, gridConst);
		
		
		lblCosto= new JLabel("Costo: ");		
		gridConst.gridx=0;
		gridConst.gridy=1;
		gridConst.weightx=0.0;
		this.add(lblCosto, gridConst);
		
		txtCosto = new JTextField();
		txtCosto.setColumns(5);
		gridConst.gridx=1;
		this.add(txtCosto, gridConst);
		
		lblDuracion= new JLabel("Duracion: ");
		gridConst.gridx=2;
		this.add(lblDuracion, gridConst);
		
		txtDuracion = new JTextField();
		txtDuracion.setColumns(5);
		gridConst.gridx=3;
		this.add(txtDuracion, gridConst);
		
		
		btnCancelar= new JButton("Cancelar");
		gridConst.gridx=6;
		gridConst.weightx=1.0;
		gridConst.anchor = GridBagConstraints.LINE_START;
		this.add(btnCancelar, gridConst);
		
		tabla = new JTable(this.tableModel);
		tabla.setFillsViewportHeight(true);
		scrollPane= new JScrollPane(tabla);
		
		gridConst.gridx=0;
		gridConst.gridwidth=7;	
		gridConst.gridy=2;
		gridConst.weighty=1.0;
		gridConst.weightx=1.0;
		gridConst.fill=GridBagConstraints.BOTH;
		gridConst.anchor=GridBagConstraints.PAGE_START;		
		this.add(scrollPane, gridConst);
	}

	public VideoController getController() {
		return controller;
	}

	public void setController(VideoController controller) {
		this.controller = controller;
	}
	
	public void setListaVideos(List<Video> VideosLista,boolean actualizar) {
		this.tableModel.setVideos(VideosLista);
		if(actualizar) this.tableModel.fireTableDataChanged();
	}
}
