package frsf.isi.died.app.vista.material;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import frsf.isi.died.app.controller.VideoController;
import frsf.isi.died.app.excepciones.DataOutOfBoundException;
import frsf.isi.died.app.excepciones.MaterialNotFoundException;
import frsf.isi.died.tp.modelo.productos.Relevancia;
import frsf.isi.died.tp.modelo.productos.Temas;
import frsf.isi.died.tp.modelo.productos.Video;

public class VideoPanelModificacion extends VPanel {
	private JScrollPane scrollPane;
	private JTable tabla;
	private JLabel lblTitulo;
	private JLabel lblCosto;
	private JLabel lblDuracion;
	private JLabel lblCalificacion;
	private JLabel lblRelevancia;
	private JLabel lblTema;
	private JTextField txtTitulo;
	private JTextField txtCosto;
	private JTextField txtDuracion;
	private JTextField txtCalificacion;
	private JButton btnModificar;
	private JButton btnCancelar;
	private JComboBox comboRelevancia;
	private JComboBox comboTema;

	private VideoTableModel tableModel;

	private VideoController controller;
	// Id del video que selecciona el usuario
	private Integer idVideoSeleccionado = 0;
	
	private static final Integer CANTIDAD_COLUMNAS_MODIFICAR = 7;
	
	public VideoPanelModificacion() {
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
		txtTitulo.setColumns(60);
		gridConst.gridx=1;
		gridConst.gridwidth=9;
		this.add(txtTitulo, gridConst);
		

		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener( e ->{
			try {
				Double costo = Double.valueOf(txtCosto.getText());
				Integer duracion = Integer.valueOf(txtDuracion.getText());
				Integer calificacion = Integer.valueOf(txtCalificacion.getText());
				String relevancia = (String) comboRelevancia.getSelectedItem().toString();
				String tema = (Temas.getEnum( (String) comboTema.getSelectedItem().toString()) ).getNombre();
				controller.verificarTitulo(txtTitulo.getText());
				controller.verificarCalificacion(Integer.valueOf(calificacion));
				controller.modificarVideo(idVideoSeleccionado, txtTitulo.getText(), costo, duracion, calificacion, relevancia, tema);
				
				txtTitulo.setText("");
				txtCosto.setText("");
				txtDuracion.setText("");
				txtCalificacion.setText("");
				comboRelevancia.setSelectedIndex(-1);
				comboRelevancia.setEnabled(false);
				comboTema.setSelectedIndex(-1);
				comboTema.setEnabled(false);
			}
			catch(DataOutOfBoundException d) {
				JOptionPane.showMessageDialog(this, d.getMessage(), "Dato fuera de rango", JOptionPane.ERROR_MESSAGE);
			}
			catch(MaterialNotFoundException mex) {
				JOptionPane.showMessageDialog(this, mex.getMessage(), "Dato no encontrado", JOptionPane.ERROR_MESSAGE);
			}
			catch(Exception ex) {
			    JOptionPane.showMessageDialog(this, ex.getMessage(), "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
			}
		});
		gridConst.gridwidth=1;
		gridConst.weightx=1.0;
		gridConst.anchor = GridBagConstraints.LINE_START;
		gridConst.gridx=10;
		this.add(btnModificar, gridConst);
		
		
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
		
		lblCalificacion = new JLabel("Calificacion: ");
		gridConst.gridx=4;
		this.add(lblCalificacion, gridConst);
		
		txtCalificacion = new JTextField();
		txtCalificacion.setColumns(5);
		gridConst.gridx=5;
		this.add(txtCalificacion, gridConst);
		
		// ----
		lblRelevancia = new JLabel("Relevancia: ");
		gridConst.gridx=6;
		this.add(lblRelevancia, gridConst);
		
		comboRelevancia = new JComboBox();
		comboRelevancia.setModel(new DefaultComboBoxModel(Relevancia.values()));
		comboRelevancia.setSelectedIndex(-1);
		comboRelevancia.setEnabled(false);
		comboRelevancia.setBackground(getBackground().brighter());
		gridConst.gridx=7;
		this.add(comboRelevancia, gridConst);
		
		lblTema = new JLabel("Tema: ");
		gridConst.gridx=8;
		this.add(lblTema, gridConst);
		
		comboTema = new JComboBox();
		for(Temas t : Temas.values()) {
			comboTema.addItem(t.getNombre());
		}
		comboTema.setSelectedIndex(-1);
		comboTema.setEnabled(false);
		comboTema.setBackground(getBackground().brighter());
		gridConst.gridx=9;
		this.add(comboTema, gridConst);
		// ----
		
		btnCancelar= new JButton("Cancelar");
		gridConst.gridx=10;
		gridConst.gridy=1;
		gridConst.weightx=1.0;
		gridConst.anchor = GridBagConstraints.LINE_START;
		this.add(btnCancelar, gridConst);
		
		tabla = new JTable(this.tableModel);
		tabla.setFillsViewportHeight(true);
		scrollPane= new JScrollPane(tabla);
		
		//  Seteamos el evento a la tabla
		setEventoMouseClicked(tabla);
		
		gridConst.gridx=0;
		gridConst.gridwidth=11;	
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
	
	public void setListaVideos(List<Video> videosLista,boolean actualizar) {
		this.tableModel.setVideos(videosLista);
		if(actualizar) this.tableModel.fireTableDataChanged();
	}
	
	public ArrayList getFilaSeleccionada() {
		ArrayList lista = new ArrayList();
		int fila = tabla.getSelectedRow();
		for(int i = 0; i < CANTIDAD_COLUMNAS_MODIFICAR; i++) {
			if(i == 0) {
				// Capturo la ID del video seleccionado
				idVideoSeleccionado = (Integer) tabla.getValueAt(fila, i);
			}else {
				lista.add(tabla.getValueAt(fila, i));
			}
		}
		return lista;
	}
	public void cargarCampos(ArrayList lista){
		txtTitulo.setText((String)lista.get(0));
		txtCosto.setText(lista.get(1).toString());
		txtDuracion.setText((String)lista.get(2).toString());
		txtCalificacion.setText((String)lista.get(3).toString());
		comboRelevancia.setEnabled(true);
		comboRelevancia.setSelectedItem(Relevancia.valueOf((String)lista.get(4).toString()));
		comboTema.setEnabled(true);
		comboTema.setSelectedItem( (Temas.getEnum((String)lista.get(5).toString()) ).getNombre());
		
	}
	
	private void setEventoMouseClicked(JTable tbl)
    {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
 
        @Override
        public void mouseClicked(MouseEvent e) {
        	tblEjemploMouseClicked(e);
        }
        });
        
    }
	
	public void tblEjemploMouseClicked(java.awt.event.MouseEvent evt) {
    	  this.cargarCampos(this.getFilaSeleccionada());
       }
}
