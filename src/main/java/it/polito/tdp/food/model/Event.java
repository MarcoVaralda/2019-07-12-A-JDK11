package it.polito.tdp.food.model;

public class Event implements Comparable<Event>{
	
	public enum Tipo {
		INIZIO_LAVORAZIONE,
		FINE_LAVORAZIONE
	}
	
	private Double tempo; // tempo in minuti
	private Tipo tipo;
	private Integer stazione;
	private Food cibo;
	
	public Event(Double tempo, Tipo tipo, Integer stazione, Food cibo) {
		this.tempo = tempo;
		this.tipo = tipo;
		this.stazione = stazione;
		this.cibo = cibo;
	}
	
	public Double getTempo() {
		return tempo;
	}

	public void setTempo(Double tempo) {
		this.tempo = tempo;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Integer getStazione() {
		return stazione;
	}

	public void setStazione(Integer stazione) {
		this.stazione = stazione;
	}

	public Food getCibo() {
		return cibo;
	}

	public void setCibo(Food cibo) {
		this.cibo = cibo;
	}

	@Override
	public int compareTo(Event altro) {
		return this.tempo.compareTo(altro.getTempo());
	}

}
