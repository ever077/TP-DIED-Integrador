package frsf.isi.died.app.controller;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import frsf.isi.died.app.vista.material.BuscarMaterialPanel;

/*import frsf.isi.died.app.vista.grafo.ControlPanel;     
import frsf.isi.died.app.vista.grafo.GrafoPanel;*/
//importar cuando hagamos lode grafo *************************************************************************************

import frsf.isi.died.app.vista.material.LibroPanel;
import frsf.isi.died.app.vista.material.LibroPanelEliminacion;
import frsf.isi.died.app.vista.material.LibroPanelModificacion;
import frsf.isi.died.app.vista.material.VideoPanel;
import frsf.isi.died.app.vista.material.VideoPanelEliminacion;
import frsf.isi.died.app.vista.material.VideoPanelModificacion;
import frsf.isi.died.app.vista.material.WishListPanel;

public class MenuController {

	private JFrame framePrincipal;
	
	public MenuController(JFrame f) {
		this.framePrincipal = f;
	}
	
	public void showView(TiposAcciones accion) {
		switch (accion) {
		case ALTA_LIBROS:
			LibroPanel panelLibros = new LibroPanel();
			LibroController controllerLibro = new LibroController(panelLibros);
			controllerLibro.crearPanel();
			framePrincipal.setContentPane((LibroPanel) controllerLibro.getPanelLibro());
			break;
		case MODIFICACION_LIBROS:
			
			 LibroPanelModificacion lPanelModificacion = new LibroPanelModificacion();
			 LibroController lControllerModificacion = new LibroController(lPanelModificacion);
			 lControllerModificacion.crearPanel();
			 framePrincipal.setContentPane(lControllerModificacion.getPanelLibro());
			// ArrayList filaSeleccionada = controller2.getFilaSeleccionada();
			 //controller2.cargarCampos(filaSeleccionada);
			 break;
		case BAJA_LIBROS:
			
			 LibroPanelEliminacion panelEliminacion = new LibroPanelEliminacion();
			 LibroController controllerEliminacion = new LibroController(panelEliminacion);
			 controllerEliminacion.crearPanel();
			 framePrincipal.setContentPane(controllerEliminacion.getPanelLibro());
			 // ArrayList filaSeleccionada = controller2.getFilaSeleccionada();
			 // controller2.cargarCampos(filaSeleccionada);
			 break;
		case ALTA_VIDEOS:
			 VideoPanel panelVideos = new VideoPanel();
			 VideoController controllerVideo = new VideoController(panelVideos);
			 controllerVideo.crearPanel();
			 framePrincipal.setContentPane((VideoPanel) controllerVideo.getPanelVideo());
			 break;
		case MODIFICACION_VIDEOS:
			 VideoPanelModificacion vPanelModificacion = new VideoPanelModificacion();
			 VideoController vControllerModificacion = new VideoController(vPanelModificacion);
			 vControllerModificacion.crearPanel();
			 framePrincipal.setContentPane(vControllerModificacion.getPanelVideo());
			 break;
		case BAJA_VIDEOS:
			 VideoPanelEliminacion vPanelEliminacion = new VideoPanelEliminacion();
			 VideoController vControllerEliminacion = new VideoController(vPanelEliminacion);
			 vControllerEliminacion.crearPanel();
			 framePrincipal.setContentPane(vControllerEliminacion.getPanelVideo());
			 break;
		case BUSCAR_MAT:
			 BuscarMaterialPanel buscarMaterialPanel = new BuscarMaterialPanel();
			 WishListPanel wishListPanel = new WishListPanel();
			 buscarMaterialPanel.setWishListController(new WishListController(wishListPanel));
			 BuscarController buscarController = new BuscarController(buscarMaterialPanel);
			 buscarController.crearPanel();
			 framePrincipal.setContentPane(buscarController.getPanelBuscar());
			 break;
		case BUSCAR_WISH:
			WishListPanel wishListPanel2 = new WishListPanel();
			WishListController wishListController = new WishListController(wishListPanel2);
			wishListController.crearPanel();
			framePrincipal.setContentPane(wishListController.getPanel());
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
