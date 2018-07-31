package frsf.isi.died.app.controller;

import java.util.PriorityQueue;

import frsf.isi.died.app.dao.MaterialCapacitacionDao;
import frsf.isi.died.app.dao.MaterialCapacitacionDaoDefault;
import frsf.isi.died.app.vista.material.BuscarMaterialPanel;
import frsf.isi.died.app.vista.material.WishListPanel;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;
import frsf.isi.died.tp.util.OrdenarMaterialWishList;

public class WishListController {

	private WishListPanel wishListPanel;
	private MaterialCapacitacionDao materialDAO;
	
	private static PriorityQueue<MaterialCapacitacion> colaPrioridad = new PriorityQueue<MaterialCapacitacion>(new OrdenarMaterialWishList());
	
	public WishListController() {
		//this.wishListPanel = wishListPanel;
		materialDAO = new MaterialCapacitacionDaoDefault();
	}
	
	public WishListController(WishListPanel wishListPanel) {
		this.wishListPanel = wishListPanel;
		this.wishListPanel.setController(this);
		materialDAO = new MaterialCapacitacionDaoDefault();
	}
	
	public WishListPanel getPanel() {
		return wishListPanel;
	}
	
	public void crearPanel() {		
		// setear los materiales buscados por el dao
		this.wishListPanel.setListaMateriales(colaPrioridad ,false);
		this.wishListPanel.construir();
	}
	
	public void addMaterial(Integer id) {
		// PriorityQueue<MaterialCapacitacion> colaPrioridad = new PriorityQueue<MaterialCapacitacion>(new OrdenarMaterialWishList());
		colaPrioridad.add(materialDAO.findById(id));
		
		//materialDAO.actualizarMaterialesWishList(colaPrioridad);
		
	}
	
	public PriorityQueue<MaterialCapacitacion> getColaPrioridad(){
		return colaPrioridad;
	}
	
	
}
