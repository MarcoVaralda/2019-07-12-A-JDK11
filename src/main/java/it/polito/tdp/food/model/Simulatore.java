package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.food.model.Event.Tipo;

public class Simulatore {
	
	// Eventi
	PriorityQueue<Event> queue = new PriorityQueue<>();
	
	// Parametri
	Graph<Food,DefaultWeightedEdge> grafo;
	int K; // Numero stazioni di lavoro
	Food partenza;
	List<Adiacenza> adiacenti ;
	
	// Stato del sistema
	Map<Integer,Food> inLavorazione; // Stazione 'key' lavora il cibo 'value'
	List<Food> lavorati ;
	List<Adiacenza> rimanenti;
	
	// Misure in uscita
	int cibiLavorati;
	double tempoTotale;
	
	public void init(Graph<Food,DefaultWeightedEdge> grafo, int K, Food partenza) {
		this.grafo=grafo;
		this.K=K;
		this.partenza=partenza;
		
		this.adiacenti = new ArrayList<>();
		this.adiacenti = getAdiacenti(partenza,grafo);
		
		this.rimanenti = new ArrayList<>(adiacenti);
		
		this.inLavorazione = new HashMap<>();
		this.inLavorazione.put(1, partenza);
		for(int i=0;i<=K && i<=adiacenti.size();i++) {
			this.inLavorazione.put(i+2, adiacenti.get(i).getF1());
			
			Event e = new Event(adiacenti.get(i).getPeso(),Tipo.FINE_LAVORAZIONE,i+2,adiacenti.get(i).getF1());
			this.queue.add(e);
			
			this.rimanenti.remove(adiacenti.get(i));
		}
		if(this.inLavorazione.size()<K) {
			for(int j=inLavorazione.size()+1;j<=K;j++) 
				this.inLavorazione.put(j, null);
		}
		
		this.lavorati = new ArrayList<>();
		
		this.cibiLavorati=0;
		this.tempoTotale=0.0;
		
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
		}
	}
	
	
	private void processEvent(Event e) {
		switch(e.getTipo()) {
		case INIZIO_LAVORAZIONE:
			// Scelgo quale adiacente lavorare
			List<Adiacenza> vicini = getAdiacenti(e.getCibo(),this.grafo);
			Adiacenza prossimo = null;
			for(Adiacenza a : vicini)
				if(!this.lavorati.contains(a.getF1()) && !this.inLavorazione.containsValue(a.getF1())) {
					prossimo = a;
					break;
				}
			
			if(prossimo!=null) { 
				// Cerco una stazione libera
				for(int i=1;i<=K;i++)
					if(this.inLavorazione.get(i)==null) {
						this.inLavorazione.replace(i, prossimo.getF1());
						Event nuovoEvento = new Event(e.getTempo()+prossimo.getPeso(),Tipo.FINE_LAVORAZIONE,i,prossimo.getF1());
						this.queue.add(nuovoEvento);
						break;
					}
			}
			
			break;
			
		case FINE_LAVORAZIONE: // Inizio a lavorare il cibo che avevo impostato
			this.cibiLavorati++;
			this.tempoTotale = e.getTempo();
			
			this.inLavorazione.replace(e.getStazione(), null);
			this.lavorati.add(e.getCibo());
			
			Event e2 = new Event(e.getTempo(), Tipo.INIZIO_LAVORAZIONE, e.getStazione(), e.getCibo()) ;
			
			this.queue.add(e2) ;
			
			break;
		}
	}
	
	public int cibiLavorati() {
		return this.cibiLavorati;
	}
	
	public double tempoTotale() {
		return this.tempoTotale;
	}
	
	

	public List<Adiacenza> getAdiacenti(Food partenza,Graph<Food,DefaultWeightedEdge> grafo) {
		List<Adiacenza> calorieMaggiori = new ArrayList<>();
		
		for(Food f : Graphs.neighborListOf(this.grafo, partenza)) {
			DefaultWeightedEdge arco = this.grafo.getEdge(partenza, f);
			double peso = this.grafo.getEdgeWeight(arco);
			calorieMaggiori.add(new Adiacenza(f,partenza,peso));
		}
		Collections.sort(calorieMaggiori);
		
		return calorieMaggiori;		
	}

}
