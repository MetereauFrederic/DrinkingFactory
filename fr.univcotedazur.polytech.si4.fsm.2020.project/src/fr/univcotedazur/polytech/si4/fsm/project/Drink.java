package fr.univcotedazur.polytech.si4.fsm.project;

import java.util.ArrayList;
import java.util.List;

public enum Drink {
	
	COFFEE(35,"café", Option.MILKCLOUD, Option.MAPLESYRUP, Option.VANILLA),
	TEA(40, "thé", Option.MILKCLOUD, Option.MAPLESYRUP),
	EXPRESSO(50, "expresso", Option.MILKCLOUD, Option.MAPLESYRUP, Option.VANILLA),
	SOUP(75, "soupe", Option.CROUTONS),
	ICEDTEAD(50, "thé glacé", Option.MAPLESYRUP, Option.LARGE_ICEDTEA);
	
	public enum Option {
		
		MILKCLOUD(10, "nuage de lait"),
		CROUTONS(30, "croutons"),
		MAPLESYRUP(10, "sirop d'érable"),
		VANILLA(60, "glace vanille mixée"),
		LARGE_ICEDTEA(25, "large thé glacé");
		
		private int price;
		private String name;
		
		Option(int price, String name) {
			this.price = price;
			this.name = name;
		}
		
		public int getPrice() {
			return price;
		}
	}
	
	private int price;
	private String name;
	private List<Option> options;
	
	Drink(int price, String name, Option ... options) {
		this.price = price;
		this.name = name;
		this.options = new ArrayList<>();
		for (Option option : options) {
			this.options.add(option);
		}
	}
	
	public List<Option> getOptions() {
		return options;
	}
	
	public int getPrice() {
		return price;
	}
	
	public String getName() {
		return name;
	}
}
