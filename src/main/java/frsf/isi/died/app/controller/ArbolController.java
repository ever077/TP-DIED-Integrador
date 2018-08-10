package frsf.isi.died.app.controller;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import frsf.isi.died.app.dao.MaterialCapacitacionDaoDefault;
import frsf.isi.died.app.excepciones.MaterialNotFoundException;
import frsf.isi.died.app.vista.arbol.ArbolPanel;
import frsf.isi.died.tp.estructuras.Nodo;
import frsf.isi.died.tp.estructuras.TipoNodo;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;

public class ArbolController {

	ArbolPanel arbolPanel;
	MaterialCapacitacionDaoDefault materialDao;
	
	
	public ArbolController(ArbolPanel arbolPanel) {
		this.arbolPanel = arbolPanel;
		this.arbolPanel.setController(this);
		this.materialDao = new MaterialCapacitacionDaoDefault();
	}

	public void mostraArbol(Integer idFilaSeleccionada, JFrame framePadre) {
			
		MaterialCapacitacion m = materialDao.findById(idFilaSeleccionada);		
		arbolPanel = new ArbolPanel();
		arbolPanel.setController(this);
		//DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(m.getTitulo());
		arbolPanel.construir(m);
		framePadre.setContentPane(arbolPanel);
		framePadre.pack();
		
		
	}

	public void cargarArbol(DefaultMutableTreeNode nodo,TipoNodo tipo,String valor, JTree arbol) {
		DefaultTreeModel modeloArbol;
		Nodo nuevoNodo = new Nodo(valor, tipo);
		if(nodo != null) {
			modeloArbol = (DefaultTreeModel) arbol.getModel();
			switch(nodo.getLevel()) {
			case 0:	
					if(tipo.toString().equals(TipoNodo.METADATO.toString()) || tipo.toString().equals(TipoNodo.RESUMEN.toString())
							|| tipo.toString().equals(TipoNodo.CAPITULO.toString())){
						
						modeloArbol.insertNodeInto(new DefaultMutableTreeNode(nuevoNodo.toString()), nodo, modeloArbol.getChildCount(nodo));
					}
					
				break;
				
			case 1:
				if(tipo.toString().equals(TipoNodo.PARRAFO.toString()) || tipo.toString().equals(TipoNodo.AUTOR.toString())
						|| tipo.toString().equals(TipoNodo.EDITORIAL.toString())){
					
					modeloArbol.insertNodeInto(new DefaultMutableTreeNode(nuevoNodo.toString()), nodo, modeloArbol.getChildCount(nodo));
				}
				break;
					
			case 2:
				break;
			
			default: 
				break;
			}
		}
		
	}
	
	
	
}
