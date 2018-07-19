package frsf.isi.died.app.vista.material;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import frsf.isi.died.tp.modelo.productos.Libro;

public class LibroTableModel  extends AbstractTableModel {

	private List<Libro> libros;
	private String[] columnas = {"ID","Titulo","Precio Compra","Costo publicacion","Paginas","Precio Suscripcion"};
	
	
	@Override
	public String getColumnName(int indice) {
		return this.columnas[indice];
	}
	
	public List<Libro> getLibros() {
		return libros;
	}

	public void setLibros(List<Libro> libros) {
		this.libros = libros;
	}

	@Override
	public int getRowCount() {
		return libros.size();
	}

	@Override
	public int getColumnCount() {
		return columnas.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object valor = null;
		switch (columnIndex) {
		case 0:
			valor = this.libros.get(rowIndex).getId();
			break;
		case 1:
			valor = this.libros.get(rowIndex).getTitulo();
			break;
		case 2:
			valor = this.libros.get(rowIndex).getPrecioCompra();
			break;
		case 3:
			valor = this.libros.get(rowIndex).getCosto();
			break;
		case 4:
			valor = this.libros.get(rowIndex).getPaginas();
			break;
		case 5:
			valor = this.libros.get(rowIndex).precio();
			break;
		default:
			System.out.println("Indice fuera de rango");
			valor = "S/D";
			break;
		}
		return valor;
	}

}
