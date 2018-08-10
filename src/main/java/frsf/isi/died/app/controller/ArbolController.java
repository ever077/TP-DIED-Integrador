package frsf.isi.died.app.controller;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import frsf.isi.died.app.dao.MaterialCapacitacionDaoDefault;
import frsf.isi.died.app.excepciones.MaterialNotFoundException;
import frsf.isi.died.app.vista.arbol.ArbolPanel;
import frsf.isi.died.tp.estructuras.ArbolVacio;
import frsf.isi.died.tp.estructuras.Nodo;
import frsf.isi.died.tp.estructuras.TipoNodo;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;

public class ArbolController {

	ArbolPanel arbolPanel;
	MaterialCapacitacionDaoDefault materialDao;
	
	private boolean editorialB = true;
	private boolean publicacionB = true;
	private boolean claveB = true;
	private boolean metaDatosB = true;
	
	
	public ArbolController(ArbolPanel arbolPanel) {
		this.arbolPanel = arbolPanel;
		this.arbolPanel.setController(this);
		this.materialDao = new MaterialCapacitacionDaoDefault();;
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
				if(nodo.getUserObject().toString().contains(TipoNodo.METADATO.toString())) {
					if(tipo.toString().equals(TipoNodo.EDITORIAL.toString()) && editorialB == true) {
						editorialB = false;
						modeloArbol.insertNodeInto(new DefaultMutableTreeNode(nuevoNodo.toString()), nodo, modeloArbol.getChildCount(nodo));
						//nuevoNodo.setAllowsChildren(false);	
						
					}else if(tipo.toString().equals(TipoNodo.FECHA_PUBLICACION.toString()) && publicacionB == true) {
						publicacionB = false;
						modeloArbol.insertNodeInto(new DefaultMutableTreeNode(nuevoNodo.toString()), nodo, modeloArbol.getChildCount(nodo));
					//	nodo.setAllowsChildren(false);
						
					}else if(tipo.toString().equals(TipoNodo.PALABRA_CLAVE.toString()) && claveB == true) {
						claveB = false;
						modeloArbol.insertNodeInto(new DefaultMutableTreeNode(nuevoNodo.toString()), nodo, modeloArbol.getChildCount(nodo));
						//nodo.setAllowsChildren(false);
						
					}else if(tipo.toString().equals(TipoNodo.AUTOR.toString())) {
						modeloArbol.insertNodeInto(new DefaultMutableTreeNode(nuevoNodo.toString()), nodo, modeloArbol.getChildCount(nodo));
						
					}
				}else if(nodo.getUserObject().toString().contains(TipoNodo.RESUMEN.toString())) {
					
					if(tipo.toString().equals(TipoNodo.PARRAFO.toString())) {
						modeloArbol.insertNodeInto(new DefaultMutableTreeNode(nuevoNodo.toString()), nodo, modeloArbol.getChildCount(nodo));
					}
				}else if(nodo.getUserObject().toString().contains(TipoNodo.CAPITULO.toString())) {
					
					if(tipo.toString().equals(TipoNodo.METADATO.toString()) && metaDatosB == true) {
						metaDatosB = false;
						modeloArbol.insertNodeInto(new DefaultMutableTreeNode(nuevoNodo.toString()), nodo, modeloArbol.getChildCount(nodo));
						//nodo.setAllowsChildren(false);
						
					}else if(tipo.toString().equals(TipoNodo.SECCION.toString())) {
						modeloArbol.insertNodeInto(new DefaultMutableTreeNode(nuevoNodo.toString()), nodo, modeloArbol.getChildCount(nodo));
					}
				}
				break;
					
			case 2:
				if(nodo.getUserObject().toString().contains(TipoNodo.METADATO.toString())) {
					if(tipo.toString().equals(TipoNodo.PALABRA_CLAVE.toString())){
						modeloArbol.insertNodeInto(new DefaultMutableTreeNode(nuevoNodo.toString()), nodo, modeloArbol.getChildCount(nodo));
					}	
				}else if(nodo.getUserObject().toString().contains(TipoNodo.SECCION.toString())) {
					if(tipo.toString().equals(TipoNodo.PARRAFO.toString())){
						modeloArbol.insertNodeInto(new DefaultMutableTreeNode(nuevoNodo.toString()), nodo, modeloArbol.getChildCount(nodo));
					}
				}
				break;
				
			default: 
				break;
			}
		}
		//this.guardarArbol(arbol);
	}

	public void guardarArbol(MaterialCapacitacion m, JTree arbol) {
		// crear archivo, si ya existe lo pisa
		String nombreArchivo = ("tree_"+m.getTitulo()+"_"+String.valueOf(m.getId())+".csv");
		materialDao.crearArchivoArbol(nombreArchivo);
		materialDao.guardarArbol(arbol, nombreArchivo);
		
	}
	
	
	
}
