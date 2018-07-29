package frsf.isi.died.app.vista.material;

import java.util.List;
import java.util.PriorityQueue;

import javax.swing.table.AbstractTableModel;

import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;;

public class WishTableModel extends AbstractTableModel {

	private List<MaterialCapacitacion> materiales;
	private String[] columnas = {"ID","Titulo","Relevancia","Calificacion","Precio"};
	
	
	@Override
	public String getColumnName(int indice) {
		return this.columnas[indice];
	}
	
	public List<MaterialCapacitacion> getMateriales() {
		return materiales;
	}

	public void setMateriales(PriorityQueue<MaterialCapacitacion> colaPrioridad) {
		this.materiales = (List<MaterialCapacitacion>) colaPrioridad;
	}

	@Override
	public int getRowCount() {
		return materiales.size();
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
			valor = this.materiales.get(rowIndex).getId();
			break;
		case 1:
			valor = this.materiales.get(rowIndex).getTitulo();
			break;
		case 2:
			valor = this.materiales.get(rowIndex).getRelevancia();
			break;
		case 3:
			valor = this.materiales.get(rowIndex).getCalificacion();
			break;
		case 4:
			valor = this.materiales.get(rowIndex).precio();
			break;
		default:
			System.out.println("Indice fuera de rango");
			valor = "S/D";
			break;
		}
		return valor;
	}
	
	public void deleteRow(int indice) {
		materiales.remove(indice);
		this.fireTableRowsDeleted(indice, indice);
	}
	
	public Boolean isNull() {
		return (materiales == null);
	}

	
}