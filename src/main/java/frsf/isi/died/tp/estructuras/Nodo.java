package frsf.isi.died.tp.estructuras;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

public class Nodo extends DefaultMutableTreeNode {

	 private String valor;
	 private TipoNodo tipoNodo;
	 private List<Nodo> hijos;
		
	 public Nodo(String valor) {
		 this.valor = valor;
		 this.tipoNodo = TipoNodo.TITULO;
	 }
 
	public Nodo(String valor, TipoNodo tipoNodo) {
		this.valor = valor;
		this.tipoNodo = tipoNodo;
	}
	
	public String toString() {
		return "Tipo : " + this.tipoNodo + " Valor : " + this.valor;	
	}

}
