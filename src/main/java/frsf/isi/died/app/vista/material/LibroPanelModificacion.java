package frsf.isi.died.app.vista.material;

import java.awt.Dimension;
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

import frsf.isi.died.app.controller.LibroController;
import frsf.isi.died.app.excepciones.DataOutOfBoundException;
import frsf.isi.died.app.excepciones.MaterialNotFoundException;
import frsf.isi.died.tp.modelo.productos.Libro;
import frsf.isi.died.tp.modelo.productos.Relevancia;

public class LibroPanelModificacion extends LPanel {
	private JScrollPane scrollPane;
	private JTable tabla;
	private JLabel lblTitulo;
	private JLabel lblCosto;
	private JLabel lblPrecioCompra;
	private JLabel lblPaginas;
	private JLabel lblCalificacion;
	private JLabel lblRelevancia;
	private JTextField txtTitulo;
	private JTextField txtCosto;
	private JTextField txtPrecioCompra;
	private JTextField txtPaginas;
	private JTextField txtCalificacion;
	private JButton btnModificar;
	private JButton btnCancelar;
	private JComboBox comboRelevancia;

	private LibroTableModel tableModel;

	private LibroController controller;
	// Id del libro que selecciona el usuario
	private Integer idLibroSeleccionado = 0;
	
	private static final Integer CANTIDAD_COLUMNAS_MODIFICAR = 7;
	
	public LibroPanelModificacion() {
		this.setLayout(new GridBagLayout());
		tableModel = new LibroTableModel();
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
				Double precio = Double.valueOf(txtPrecioCompra.getText());
				Integer paginas = Integer.valueOf(txtPaginas.getText());
				Integer calificacion = Integer.valueOf(txtCalificacion.getText());
				String relevancia = (String) comboRelevancia.getSelectedItem().toString();
				// Verifica que el titulo no este vacio
				controller.verificarTitulo(txtTitulo.getText());
				// Verifica que la calificacion este entre 0 - 100
				controller.verificarCalificacion(Integer.valueOf(calificacion));
				
				controller.modificarLibro(idLibroSeleccionado, txtTitulo.getText(), costo, precio, paginas,calificacion, relevancia);
				
				txtTitulo.setText("");
				txtCosto.setText("");
				txtPrecioCompra.setText("");
				txtPaginas.setText("");
				txtCalificacion.setText("");
				comboRelevancia.setSelectedIndex(-1);
				comboRelevancia.setEnabled(false);
				
			}
			catch(DataOutOfBoundException d){
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
		
		lblPrecioCompra= new JLabel("Precio Compra: ");
		gridConst.gridx=2;
		this.add(lblPrecioCompra, gridConst);
		
		txtPrecioCompra = new JTextField();
		txtPrecioCompra.setColumns(5);
		gridConst.gridx=3;
		this.add(txtPrecioCompra, gridConst);
		
		lblPaginas= new JLabel("Paginas: ");		
		gridConst.gridx=4;
		this.add(lblPaginas, gridConst);
		
		txtPaginas = new JTextField();
		txtPaginas.setColumns(5);
		gridConst.gridx=5;
		this.add(txtPaginas, gridConst);

		// ----
		lblCalificacion = new JLabel("Calificacion: ");		
		gridConst.gridx=6;
		this.add(lblCalificacion, gridConst);
		
		txtCalificacion = new JTextField();
		txtCalificacion.setColumns(5);
		gridConst.gridx=7;
		this.add(txtCalificacion, gridConst);
			
		lblRelevancia = new JLabel("Relevancia: ");
		gridConst.gridx=8;
		this.add(lblRelevancia, gridConst);
		
		comboRelevancia = new JComboBox();
		// cargo el combo
		comboRelevancia.setModel(new DefaultComboBoxModel(Relevancia.values()));	
		comboRelevancia.setSelectedIndex(-1);
		comboRelevancia.setEnabled(false);
		comboRelevancia.setBackground(getBackground().brighter());
		gridConst.gridx=9;
		this.add(comboRelevancia, gridConst);
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

	public LibroController getController() {
		return controller;
	}

	public void setController(LibroController controller) {
		this.controller = controller;
	}
	
	public void setListaLibros(List<Libro> librosLista,boolean actualizar) {
		this.tableModel.setLibros(librosLista);
		if(actualizar) this.tableModel.fireTableDataChanged();
	}
	
	public ArrayList getFilaSeleccionada() {
		ArrayList lista = new ArrayList();
		int fila = tabla.getSelectedRow();
		for(int i = 0; i < CANTIDAD_COLUMNAS_MODIFICAR; i++) {
			if(i == 0) {
				// Capturo la ID del libro seleccionado
				idLibroSeleccionado = (Integer) tabla.getValueAt(fila, i);
			}else {
				lista.add(tabla.getValueAt(fila, i));
			}
		}
		return lista;
	}
	public void cargarCampos(ArrayList lista){
		txtTitulo.setText((String)lista.get(0));
		txtCosto.setText(lista.get(1).toString());
		txtPaginas.setText((String)lista.get(3).toString());
		txtPrecioCompra.setText((String)lista.get(2).toString());
		txtCalificacion.setText((String)lista.get(4).toString());
		comboRelevancia.setEnabled(true);
		comboRelevancia.setSelectedItem(Relevancia.valueOf((String)lista.get(5).toString()));
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
