package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	FoodDao dao = new FoodDao();
	Graph<Food,DefaultWeightedEdge> grafo;
	List<Food> vertici = new LinkedList<>();
	Map<Integer,Food> idMap;
	
	// PUNTO 2
	Simulatore s ;
	
	public String creaGrafo(int N) {
		this.idMap = new HashMap<>();
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		// Aggiungo i vertici
		this.vertici = dao.getVertici(N, this.idMap);
		Graphs.addAllVertices(this.grafo,vertici);
		
		// Aggiungo gli archi
		for(Adiacenza a : this.dao.getArchi(idMap)) 
			if(this.grafo.containsVertex(a.getF1()) && this.grafo.containsVertex(a.getF2()))
				if(!this.grafo.containsEdge(a.getF1(),a.getF2()))
					Graphs.addEdge(this.grafo,a.getF1(),a.getF2(),a.getPeso());
		
		return "Grafo creato!\n\nNumero vertici: "+this.grafo.vertexSet().size()+"\nNumero archi: "+this.grafo.edgeSet().size();
	}
	
	public List<Food> getVertici() {
		return this.vertici;
	}
	
	public String getAdiacenti(Food scelto) {
		List<Adiacenza> calorieMaggiori = new ArrayList<>();
		
		for(Food f : Graphs.neighborListOf(this.grafo, scelto)) {
			DefaultWeightedEdge arco = this.grafo.getEdge(scelto, f);
			double peso = this.grafo.getEdgeWeight(arco);
			calorieMaggiori.add(new Adiacenza(f,scelto,peso));
		}
		Collections.sort(calorieMaggiori);
		
		String result = "";
		
		if(calorieMaggiori.size()>0) {
			result += "\n\nCibi con calorie maggiori adiacenti a "+scelto+":\n";
			for(int i=0;i<5;i++) {
				if(calorieMaggiori.get(i)!=null)
					result += calorieMaggiori.get(i).getF1()+" - "+calorieMaggiori.get(i).getPeso()+"\n";
			}
		}
		else {
			result += "\nNessun vertice adiacente a "+scelto;
		}
		
		return result;
	}
	
	
	// PUNTO 2
	public String simula(int K, Food partenza) {
		this.s = new Simulatore();
		
		this.s.init(grafo, K, partenza);
		
		this.s.run();
		
		String result = "\n\nTempo totale di lavorazione: "+this.s.tempoTotale+" minuti\nCibi lavorati: "+this.s.cibiLavorati+"\n";
		
		return result;
	}
}
