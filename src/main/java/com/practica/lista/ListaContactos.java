package com.practica.lista;

import com.practica.genericas.FechaHora;
import com.practica.genericas.PosicionPersona;

public class ListaContactos {
	private NodoTemporal lista;
	private int size;

	private void insertarEnLista(NodoTemporal nodo, PosicionPersona p)
	{
		NodoPosicion npActual = nodo.getListaCoordenadas();
		NodoPosicion npAnt=null;
		boolean npEncontrado = false;
		while (npActual!=null && !npEncontrado) {
			if(npActual.getCoordenada().equals(p.getCoordenada())) {
				npEncontrado=true;
				npActual.setNumPersonas(npActual.getNumPersonas()+1);
			}else {
				npAnt = npActual;
				npActual = npActual.getSiguiente();
			}
		}
		if(!npEncontrado) {
			NodoPosicion npNuevo = new NodoPosicion(p.getCoordenada(),1, null);
			if(nodo.getListaCoordenadas()==null)
				nodo.setListaCoordenadas(npNuevo);
			else
				npAnt.setSiguiente(npNuevo);
		}
	}
	/**
	 * Insertamos en la lista de nodos temporales, y a la vez inserto en la lista de nodos de coordenadas. 
	 * En la lista de coordenadas metemos el documento de la persona que está en esa coordenada 
	 * en un instante 
	 */
	public void insertarNodoTemporal (PosicionPersona p) {
		NodoTemporal aux = lista, ant=null;
		boolean salir=false,  encontrado = false;
		/**
		 * Busco la posición adecuada donde meter el nodo de la lista, excepto
		 * que esté en la lista. Entonces solo añadimos una coordenada.
		 */
		while (aux!=null && !salir) {
			if(aux.getFecha().compareTo(p.getFechaPosicion())==0) {
				encontrado = true;
				salir = true;
				/**
				 * Insertamos en la lista de coordenadas
				 */
				insertarEnLista(aux, p);
			}else if(aux.getFecha().compareTo(p.getFechaPosicion())<0) {
				ant = aux;
				aux=aux.getSiguiente();
			}else if(aux.getFecha().compareTo(p.getFechaPosicion())>0) {
				salir=true;
			}
		}
		/**
		 * No hemos encontrado ninguna posición temporal, así que
		 * metemos un nodo nuevo en la lista
		 */
		if(!encontrado) {
			NodoTemporal nuevo = new NodoTemporal();
			nuevo.setFecha(p.getFechaPosicion());


			insertarEnLista(nuevo, p);
			
			if(ant!=null) {
				nuevo.setSiguiente(aux);
				ant.setSiguiente(nuevo);
			}else {
				nuevo.setSiguiente(lista);
				lista = nuevo;
			}
			this.size++;
			
		}
	}
	
	public int tamanioLista () {
		return this.size;
	}

	public String getPrimerNodo() {
		NodoTemporal aux = lista;
		String cadena = aux.getFecha().getFecha().toString();
		cadena+= ";" +  aux.getFecha().getHora().toString();
		return cadena;
	}

	/**
	 * Métodos para comprobar que insertamos de manera correcta en las listas de 
	 * coordenadas, no tienen una utilidad en sí misma, más allá de comprobar que
	 * nuestra lista funciona de manera correcta.
	 */
	public int numPersonasEntreDosInstantes(FechaHora inicio, FechaHora fin) {
		return contar(inicio, fin, 0);
	}
	
	
	
	public int numNodosCoordenadaEntreDosInstantes(FechaHora inicio, FechaHora fin) {
		return contar(inicio, fin, 1);
	}

	private int contar(FechaHora inicio, FechaHora fin, int tipo){
		if(this.size==0)
			return 0;
		NodoTemporal aux = lista;
		int cont = 0;
		int a;
		cont = 0;
		while(aux!=null) {
			if(aux.getFecha().compareTo(inicio)>=0 && aux.getFecha().compareTo(fin)<=0) {
				NodoPosicion nodo = aux.getListaCoordenadas();
				while(nodo!=null) {
					switch(tipo){
						case 0:
							cont = cont + nodo.getNumPersonas();
							break;
						case 1:
							cont = cont + 1;
							break;
					}
					nodo = nodo.getSiguiente();
				}
				aux = aux.getSiguiente();
			}else {
				aux=aux.getSiguiente();
			}
		}
		return cont;
	}
	
	
	
	@Override
	public String toString() {
		String cadena="";
		int a,cont;
		cont=0;
		NodoTemporal aux = lista;
		for(cont=1; cont<size; cont++) {
			cadena += aux.getFecha().getFecha().toString();
			cadena += ";" +  aux.getFecha().getHora().toString() + " ";
			aux=aux.getSiguiente();
		}
		cadena += aux.getFecha().getFecha().toString();
		cadena += ";" +  aux.getFecha().getHora().toString();
		return cadena;
	}
	
	
	
}
