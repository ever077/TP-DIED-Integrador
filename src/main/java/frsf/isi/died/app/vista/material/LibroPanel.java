package frsf.isi.died.app.vista.material;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import frsf.isi.died.app.controller.LibroController;
import frsf.isi.died.app.excepciones.DataOutOfBoundException;
import frsf.isi.died.tp.modelo.productos.Libro;
import frsf.isi.died.tp.modelo.productos.Relevancia;

public class LibroPanel extends LPanel{
	
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
	private JButton btnAgregar;
	private JButton btnCancelar;
	private JComboBox comboRelevancia;

	private LibroTableModel tableModel;

	private LibroController controller;
	
	public LibroPanel() {
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
		

		btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener( e ->{
			try {
				Double costo = Double.valueOf(txtCosto.getText());
				Double precio = Double.valueOf(txtPrecioCompra.getText());
				Integer paginas = Integer.valueOf(txtPaginas.getText());
				Integer calificacion = Integer.valueOf(txtCalificacion.getText());
				String relevancia = (String) comboRelevancia.getSelectedItem().toString();
				
				// Verificar calificacion
				controller.verificarCalificacion(Integer.valueOf(calificacion));
				// Verifico que el titulo no este vacio
				controller.verificarTitulo(txtTitulo.getText());
				controller.agregarLibro(txtTitulo.getText(), costo, precio, paginas, calificacion, relevancia);
				txtTitulo.setText("");
				txtCosto.setText("");
				txtPrecioCompra.setText("");
				txtPaginas.setText("");
				txtCalificacion.setText("");
				comboRelevancia.setSelectedIndex(0);
				
			}
			catch(DataOutOfBoundException d){
				 JOptionPane.showMessageDialog(this, d.getMessage(), "Dato fuera de rango", JOptionPane.ERROR_MESSAGE);
			}
			catch(Exception ex) {
			    JOptionPane.showMessageDialog(this, ex.getMessage(), "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
			}
		});
		gridConst.gridwidth=1;
		gridConst.weightx=1.0;
		gridConst.anchor = GridBagConstraints.LINE_START;
		gridConst.gridx=10;
		this.add(btnAgregar, gridConst);
		
		
		lblCosto = new JLabel("Costo: ");		
		gridConst.gridx=0;
		gridConst.gridy=1;
		gridConst.weightx=0.0;
		this.add(lblCosto, gridConst);
		
		txtCosto = new JTextField();
		txtCosto.setColumns(5);
		gridConst.gridx=1;
		this.add(txtCosto, gridConst);
		
		lblPrecioCompra = new JLabel("Precio Compra: ");
		gridConst.gridx=2;
		this.add(lblPrecioCompra, gridConst);
		
		txtPrecioCompra = new JTextField();
		txtPrecioCompra.setColumns(5);
		gridConst.gridx=3;
		this.add(txtPrecioCompra, gridConst);
	
		
		lblPaginas = new JLabel("Paginas: ");		
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
		comboRelevancia.setModel(new DefaultComboBoxModel(Relevancia.values()));	
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

}