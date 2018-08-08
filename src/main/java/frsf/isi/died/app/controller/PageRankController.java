package frsf.isi.died.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import frsf.isi.died.app.dao.MaterialCapacitacionDao;
import frsf.isi.died.app.dao.MaterialCapacitacionDaoDefault;
import frsf.isi.died.app.vista.grafo.ControlPanel;
import frsf.isi.died.app.vista.grafo.GrafoPanel;
import frsf.isi.died.app.vista.material.BuscarMaterialPanel;
import frsf.isi.died.app.vista.pageRank.PageRankPanel;
import frsf.isi.died.app.vista.wishlist.WishListPanel;
import frsf.isi.died.tp.estructuras.Arista;
import frsf.isi.died.tp.estructuras.Vertice;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;

public class PageRankController {

	private PageRankPanel pageRankPanel;
	private MaterialCapacitacionDao materialDAO;
	private GrafoController grafoController;
	
	
	
	public PageRankController(PageRankPanel pageRankPanel, GrafoController grafoController) {
		this.pageRankPanel = pageRankPanel;
		this.pageRankPanel.setController(this);
		this.grafoController = grafoController;
		materialDAO = new MaterialCapacitacionDaoDefault();
	}
	
	public void showPageRank(List<Vertice<MaterialCapacitacion>> listaVertices, List<Arista<MaterialCapacitacion>> listaAristas, JFrame framePrincipal) {
		pageRankPanel.construir();
		framePrincipal.setContentPane(pageRankPanel);
		framePrincipal.pack();
	}
	
}
