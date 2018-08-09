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
import frsf.isi.died.tp.modelo.productos.Temas;
import frsf.isi.died.tp.util.OrdenarMaterialPorPageRank;

public class PageRankController {

	private PageRankPanel pageRankPanel;
	private MaterialCapacitacionDao materialDAO;
	private GrafoController grafoController;
	//***************************************************************************************************************************************************
	 private static Double d = 0.5;
	 private static Double e = 0.00001;
	 private static Integer decimales = 5;
	 
	//***************************************************************************************************************************************************
	 
	public PageRankController(PageRankPanel pageRankPanel, GrafoController grafoController) {
		this.pageRankPanel = pageRankPanel;
		this.pageRankPanel.setController(this);
		this.grafoController = grafoController;
		materialDAO = new MaterialCapacitacionDaoDefault();
	}
	
	public void showPageRank(List<Vertice<MaterialCapacitacion>> listaVertices, List<Arista<MaterialCapacitacion>> listaAristas, JFrame framePrincipal) {
		pageRankPanel.setListaVertices(listaVertices);
		pageRankPanel.setListaAristas(listaAristas);
		pageRankPanel.construir();
		framePrincipal.setContentPane(pageRankPanel);
		framePrincipal.pack();
	}
	
	//***************************************************************************************************************************************************
	//***************************************************************************************************************************************************

	private List<Integer> calcuarCi (List<Vertice<MaterialCapacitacion>> listaVertices, List<Arista<MaterialCapacitacion>> listaAristas) {
		
		List<Integer> vectorCi = new ArrayList<Integer>();
		
		for(Vertice<MaterialCapacitacion> v : listaVertices) {
			Integer cont=0;
			
			for(Arista<MaterialCapacitacion> a : listaAristas) {
				
				if(a.getInicio().getValor().equals(v.getValor())) {
					cont++;
				}
			}
			vectorCi.add(cont);
		}
		return vectorCi;
	}
	
	//te trunca un numero dado un double y la cantidad de decimales despues de la coma que se desean
	//OJO, no redondea, trunca nomas
	
	public Double formatearDecimales(Double numero, Integer numeroDecimales) {
		return Math.round(numero * Math.pow(10, numeroDecimales)) / Math.pow(10, numeroDecimales);
	}
		
	//crea la matriz con todos los Pr(i)
/*	public List<Vertice<MaterialCapacitacion>> crearPRMatriz (List<Vertice<MaterialCapacitacion>> listaVertices, List<Arista<MaterialCapacitacion>> listaAristas, List<Integer> vectorCi){
		
		
		List<Double> pageRank = new ArrayList<Double>();
		
		//aca cargo la primera fila de la matriz con 1 
		for(int i=0; i<listaVertices.size();i++)
		{
			pageRank.add(1.0);
		}
		//aca la añado
	

		
		int i=1;
		
		do{
			for(int j=0; j<listaVertices.size(); j++) {
				
				formatearDecimales(calcularPRi(j,listaVertices,listaAristas, vectorCi, pageRank ), decimales);
			}
			i++;
			
		}while(!condicionDeCorte(matrizPR.get(i), matrizPR.get(i-1) )); //mientras no se cumpla la condicion de corte sigue
		
		return matrizPR;
	}
	
	//suponemos que todos estan en true, que todas las restas entre los valores  actuales y anteriores ameritan a que termine de cargar la matriz
	//pero al primero que no cumple la concion de corte retorna false y el while de la matriz se sigue ejecutando
*/
	
	private boolean condicionDeCorte(List<Double> actual, List<Double> anterior) {
		boolean marca=true;
		
		for(int i=0; i<actual.size(); i++) {
			if(Math.abs(actual.get(i)-anterior.get(i)) > e) {
				
				marca=false;
			}
		}
		return marca;
	}
	
	public void calcularPageRank(List<Vertice<MaterialCapacitacion>> lv, List<Arista<MaterialCapacitacion>> la, String tema) {
		// filtrar vertices segun temas
		List<Vertice<MaterialCapacitacion>> listaVerticesFiltrada = new ArrayList<Vertice<MaterialCapacitacion>>();
		for(Vertice<MaterialCapacitacion> v : lv) {
			if(v.getValor().getTema().equals(tema)) {
				listaVerticesFiltrada.add(v);
			}
		}
		// filtrar aristas segun temas
		List<Arista<MaterialCapacitacion>> listaAristasFiltrada = new ArrayList<Arista<MaterialCapacitacion>>();
			for(Arista<MaterialCapacitacion> a : la) {
				if(a.getInicio().getValor().getTema().equals(tema) && a.getFin().getValor().getTema().equals(tema)) {
					listaAristasFiltrada.add(a);
				}
			}
			List<Double> listaPageRanks = this.calcularPRi(listaVerticesFiltrada, listaAristasFiltrada);
			List<MaterialCapacitacion> listaMateriales = this.setPageRankToMateriales(listaPageRanks, listaVerticesFiltrada);
			listaMateriales.sort(new OrdenarMaterialPorPageRank());
			this.pageRankPanel.setListaMateriales(listaMateriales, false);
	}
	
	private List<Double> calcularPRi(List<Vertice<MaterialCapacitacion>> lv, List<Arista<MaterialCapacitacion>> la){
		
		List<Double> pageRank = new ArrayList<Double>();
		List<Double> copiaPageRank;
		List<Double> aux;
		Boolean seguirIterando;
		
		List<Integer> vCi = this.calcuarCi(lv, la);
		
		//aca cargo la primera fila de la matriz con 1 
		for(int i=0; i<lv.size();i++)
		{
			pageRank.add(1.0);
		}
		
		do {
			copiaPageRank = new ArrayList<Double>(pageRank);
			aux = new ArrayList<Double>();
			
			for(Vertice<MaterialCapacitacion> v : lv) 	{
				
				Double k=0.0;
				int i = 0;
				
				for(Arista<MaterialCapacitacion> a : la) {
					
					if(v.equals(a.getFin())) {
						i = lv.indexOf(a.getInicio());	
					
						k= k+(copiaPageRank.get(i)/vCi.get(i)); //si no anda probar con castear a (Double) vCi.get(i)
					}
					
				}
				aux.add( formatearDecimales( ((1-d)+d*k), decimales ) );
			}
			if(!condicionDeCorte(aux, pageRank)) {
				pageRank.clear();
				pageRank.addAll(aux);
				seguirIterando = true;
			}else {
				seguirIterando = false;
			}
		
		}while(seguirIterando);
	
		return pageRank;
	}
	
	private List<MaterialCapacitacion> setPageRankToMateriales(List<Double> listaPageRanks, List<Vertice<MaterialCapacitacion>> listaVerticesFiltrada){
		List<MaterialCapacitacion> listaMateriales = new ArrayList<MaterialCapacitacion>();
		for(int i = 0; i < listaVerticesFiltrada.size(); i++) {
			listaVerticesFiltrada.get(i).getValor().setPageRank(listaPageRanks.get(i));
			listaMateriales.add(listaVerticesFiltrada.get(i).getValor());
		}
		return listaMateriales;
	}
	
	//***************************************************************************************************************************************************
	//***************************************************************************************************************************************************
}
