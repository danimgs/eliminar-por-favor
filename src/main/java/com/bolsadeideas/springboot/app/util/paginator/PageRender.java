package com.bolsadeideas.springboot.app.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender <T>{
	private String url;
	private Page<T> page; //Listado paginado de los clientes, la lista de los clientes
	private int totalPaginas;
	private int numElementosPorPagina;
	
	private int paginaActual; //Obtenemos la pagina actual
	
	private List<PageItem> paginas; //Al crear la lista lo tenemos que recorrer con un for. La inicializamos con un arraylist en el constructor
	
	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		this.paginas = new ArrayList<PageItem>();
		
		numElementosPorPagina = page.getSize();//Cantidad de elementos por pagina. Los 5 registros que le pasamos en el controlador
		totalPaginas = page.getTotalPages();
		paginaActual = page.getNumber() + 1;//Para que parte de la página 1, ya que la de por defecto es la 0, definido en el método listar 
		
		int desde, hasta;
		if(totalPaginas<= numElementosPorPagina) { //Muestra el paginador de una sola vez
			desde=1;
			hasta = totalPaginas;
		}else { //Si no puede de una sola vez lo hace por rangos
			if(paginaActual <= numElementosPorPagina / 2) {//Rango inicial
				desde = 1;
				hasta = numElementosPorPagina;
			} else if (paginaActual >= totalPaginas - numElementosPorPagina / 2){//Calculamos el rango final
				desde = totalPaginas - numElementosPorPagina + 1;
				hasta = numElementosPorPagina;
			} else {//Rango intermedio
				desde = paginaActual - numElementosPorPagina/2;
				hasta = numElementosPorPagina;
			}
		}
		//For que va desdeel contador 0 hasta el total de paginas a mostrar en ese rango
		for(int i=0; i < hasta; i++) {
			paginas.add(new PageItem(desde + i, paginaActual == desde +i));
		}
	}

	public String getUrl() {
		return url;
	}

	public int getTotalPaginas() {
		return totalPaginas;
	}

	public int getPaginaActual() {
		return paginaActual;
	}

	public List<PageItem> getPaginas() {
		return paginas;
	}
	
	//Metodos para saber si es la primera pagina o si es la última o si tienes páginas atras o si tiene pagina siguiente 
	public boolean isFirst() {
		return page.isFirst();
	}
	
	public boolean isLast() {
		return page.isLast();
	}
	
	public boolean isHasPrevious() {
		return page.hasPrevious();
	}
	
	public boolean isHasNext() {
		return page.hasNext();
	}
	
	
	

}
