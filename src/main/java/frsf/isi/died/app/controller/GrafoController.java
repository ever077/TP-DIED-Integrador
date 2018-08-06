package frsf.isi.died.app.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import frsf.isi.died.app.dao.MaterialCapacitacionDao;
import frsf.isi.died.app.dao.MaterialCapacitacionDaoDefault;
import frsf.isi.died.app.vista.grafo.AristaView;
import frsf.isi.died.app.vista.grafo.ControlPanel;
import frsf.isi.died.app.vista.grafo.GrafoPanel;
import frsf.isi.died.app.vista.grafo.VerticeView;
import frsf.isi.died.tp.estructuras.Arista;
import frsf.isi.died.tp.estructuras.Grafo;
import frsf.isi.died.tp.estructuras.Vertice;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;
import frsf.isi.died.tp.util.ManipularFecha;
import frsf.isi.died.tp.util.ManipularListas;

public class GrafoController {

	private GrafoPanel vistaGrafo;
	private ControlPanel vistaControl;
	private MaterialCapacitacionDao materialDao;
	
	private Queue<MaterialCapacitacion> materialesPorPintar = new LinkedList<MaterialCapacitacion>();

	public GrafoController(GrafoPanel panelGrf, ControlPanel panelCtrl) {
		this.vistaGrafo = panelGrf;
		this.vistaGrafo.setController(this);
		this.vistaControl = panelCtrl;
		this.vistaControl.setController(this);
		this.materialDao = new MaterialCapacitacionDaoDefault();
		this.vistaControl.armarPanel(materialDao.listaMateriales());
	}

	public void crearVertice(Integer coordenadaX, Integer coordenadaY, Color color, MaterialCapacitacion mc) {
		VerticeView v = new VerticeView(coordenadaX, coordenadaY, color);
		v.setId(mc.getId());
		v.setNombre(mc.getTitulo());
		this.vistaGrafo.agregar(v);
		this.vistaGrafo.repaint();
	}

	public void crearArista(AristaView arista) {
		this.materialDao.crearCamino(arista.getOrigen().getId(), arista.getDestino().getId());
		this.vistaGrafo.agregar(arista);
		this.vistaGrafo.repaint();
	}

	public void buscarCamino(Integer nodo1, Integer nodo2, Integer saltos) {
		List<MaterialCapacitacion> camino = this.materialDao.buscarCamino(nodo1, nodo2, saltos);
		this.vistaGrafo.caminoPintar(camino);
		this.vistaGrafo.repaint();
	}

	public List<List<MaterialCapacitacion>> buscarCaminos(Integer nodo1, Integer nodo2) {
		List<List<MaterialCapacitacion>> caminos = this.materialDao.buscarCaminos(nodo1, nodo2);
		//this.vistaGrafo.caminoPintar(caminos);
	//	this.vistaGrafo.repaint();
		return caminos;
		
	}

	public List<MaterialCapacitacion> listaVertices() {
		return materialDao.listaMateriales();
	}
	
	public void showRelaciones(Integer id, List<MaterialCapacitacion> materiales, JFrame framePrincipal) {
		
		if(materialesPorPintar.isEmpty()) {
			// Cargo los combo box del ControlPanel con los Materiales de la especialidad buscada
			this.vistaControl.cargarCombosBoxs(materiales);
			
			MaterialCapacitacion mat = materialDao.findById(id);
			materiales = ManipularListas.eliminarElemento(materiales, mat);
			
			// Guardo los materiales hasta que hagan click derecho en la posicion a pintar el procimo.
			materialesPorPintar.addAll(materiales);
			
			// Creo los paneles y se los asigno al frame principal, luego lo muestro.
			JPanel panel = new JPanel(new BorderLayout());
			panel.add(vistaControl , BorderLayout.PAGE_START);
			panel.add(vistaGrafo , BorderLayout.CENTER);
			framePrincipal.setContentPane(panel);
			framePrincipal.pack();
			
			// Pinto el material seleccionado en una posicion fija.
			Color color;
			if(mat.esLibro()) {
				color = Color.BLUE;
			}else {
				color = Color.RED;
			}
			this.crearVertice(100, 170, color, mat);
		}
		
/*		
			Por cada material
		 *  	decirle a la vistaGrafo que muestre en una burbuja el mensaje "hacer click derecho en la posicion a poner los materiales"
		 * 		pintarlo en el lugar seleccionado
		 * 		AZUL -> Libro ; ROJO -> Video
		 
		for(MaterialCapacitacion m : materiales) {
			Point posicion = vistaGrafo.getPosicionVertice();
			if(m.esLibro()) {
				color = Color.BLUE;
			}else {
				color = Color.RED;
			}
			this.crearVertice(posicion.x, posicion.y, color, m);
		}
*/		
	}
	
	public void pintarVertice(Point punto) {
		if(!materialesPorPintar.isEmpty()) {
			MaterialCapacitacion m = materialesPorPintar.poll();
			Color color;
			
			if(m.esLibro()) {
				color = Color.BLUE;
			}else {
				color = Color.RED;
			}
			this.crearVertice(punto.x, punto.y, color, m);
		}
		
	}
	
	
}
