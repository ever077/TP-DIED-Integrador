package frsf.isi.died.app.controller;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

/*import frsf.isi.died.app.vista.grafo.ControlPanel;     
import frsf.isi.died.app.vista.grafo.GrafoPanel;*/
//importar cuando hagamos lode grafo *************************************************************************************

import frsf.isi.died.app.vista.material.LibroPanel;
import frsf.isi.died.app.vista.material.LibroPanelModificacion;

public class MenuController {

	private JFrame framePrincipal;
	
	public MenuController(JFrame f) {
		this.framePrincipal = f;
	}
	
	public void showView(TiposAcciones accion) {
		switch (accion) {
		case ALTA_LIBROS:
			LibroPanel panelLibros = new LibroPanel();
			LibroController controller = new LibroController(panelLibros);
			controller.crearPanel();
			framePrincipal.setContentPane((LibroPanel) controller.getPanelLibro());
			break;
		case MODIFICACION_LIBROS:
			
			 LibroPanelModificacion panelModificacion = new LibroPanelModificacion();
			 LibroController controllerModificacion = new LibroController(panelModificacion);
			 controllerModificacion.crearPanel();
			 framePrincipal.setContentPane(controllerModificacion.getPanelLibro());
			// ArrayList filaSeleccionada = controller2.getFilaSeleccionada();
			 //controller2.cargarCampos(filaSeleccionada);
			 break;
		case VER_GRAFO:
			/*JPanel panel = new JPanel(new BorderLayout());
			ControlPanel controlPanel = new ControlPanel();
			GrafoPanel grafoPanel = new GrafoPanel();
			GrafoController grfController = new GrafoController(grafoPanel,controlPanel);
			panel.add(controlPanel , BorderLayout.PAGE_START);
			panel.add(grafoPanel , BorderLayout.CENTER);
			
			framePrincipal.setContentPane(panel);*/
			break;			
		default:
			break;
		}
		framePrincipal.pack();

	}
	
	
}
