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
		pageRankPanel.construir();
		framePrincipal.setContentPane(pageRankPanel);
		framePrincipal.pack();
	}
	
	//***************************************************************************************************************************************************
	//***************************************************************************************************************************************************

	public List<Integer> calcuarCi (List<Vertice<MaterialCapacitacion>> listaVertices, List<Arista<MaterialCapacitacion>> listaAristas) {
		
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
	public List<List<Double>> crearPRMatriz (List<Vertice<MaterialCapacitacion>> listaVertices, List<Arista<MaterialCapacitacion>> listaAristas, List<Integer> vectorCi){
		
		List<List<Double>> matrizPR = new  ArrayList<List<Double>>();
		List<Double> primeraFila = new ArrayList<Double>();
		
		//aca cargo la primera fila de la matriz con 1 
		for(int i=0; i<listaVertices.size();i++)
		{
			primeraFila.add(1.0);
		}
		//aca la añado
		matrizPR.add(primeraFila);
		
		int i=1;
		while(!condicionDeCorte(matrizPR.get(i), matrizPR.get(i-1) )) //mientras no se cumpla la condicion de corte sigue
		{
			for(int j=0; j<listaVertices.size(); j++) {
				
				(matrizPR.get(i)).add(formatearDecimales(calcularPRi(j,listaVertices,listaAristas, vectorCi, matrizPR.get(i-1) ), decimales));
			}
			i++;
		}
		
		return matrizPR;
	}
	
	//suponemos que todos estan en true, que todas las restas entre los valores  actuales y anteriores ameritan a que termine de cargar la matriz
	//pero al primero que no cumple la concion de corte retorna false y el while de la matriz se sigue ejecutando
	
	private boolean condicionDeCorte(List<Double> actual, List<Double> anterior) {
		boolean marca=true;
		
		for(int i=0; i<actual.size(); i++) {
			if(Math.abs(actual.get(i)-anterior.get(i)) < e) {
				
				marca=false;
			}
		}
		return marca;
	}
	
	//calcula un Pr(i)
	// lo que hace es me fijo en la lista de vertices (lv), tomo el vertice, me fijo en la de aristas (la)
	//donde es que aparece como nodo final, y tomo el nodo inicial de ese par, lo busco en la lista de vertices (lv)
	// y agarro el indice, entonces divido al valor de este ultimo nodo, por C(i) correspondiente al indice en vCi
	
	private Double calcularPRi(int j, List<Vertice<MaterialCapacitacion>> lv, List<Arista<MaterialCapacitacion>> la, List<Integer> vCi,List<Double> anterior ){
		
		Double k=0.0;
		Vertice<MaterialCapacitacion> nodo = lv.get(j);
		int i=0;
		for(Arista<MaterialCapacitacion> a : la) {
			
			if(nodo.equals(a.getFin())) {
				
				while(!a.getInicio().equals(lv.get(i))) {
					i++;
				}
					
			}
			k= k+(anterior.get(i)/vCi.get(i)); //si no anda probar con castear a (Double) vCi.get(i)
		}
		
		
		return (1-d)+k;
	}
	
	//***************************************************************************************************************************************************
	//***************************************************************************************************************************************************
}
