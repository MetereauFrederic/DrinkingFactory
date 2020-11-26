package fr.univcotedazur.polytech.si4.fsm.project;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class IngredientList extends ArrayList<Stock> {
	
	public enum Ingredient {
		COFFEE_POD(),
		CUP(),
		SUGAR(),
		GRAINS(),
		TEA_BAG(),
		MILK(),
		CROUTONS(),
		MAPLESYRUP(),
		VANILLA(),
		SPICE(),
		SOUP_POD(),
		ICEDTEA_POD(),
		NITROGEN();
	}
	
	public IngredientList() {
		this.add(new Stock(Ingredient.COFFEE_POD, 3));
		this.add(new Stock(Ingredient.CUP, 3));
		this.add(new Stock(Ingredient.SUGAR, 10));
		this.add(new Stock(Ingredient.GRAINS, 10));
		this.add(new Stock(Ingredient.TEA_BAG, 3));
		this.add(new Stock(Ingredient.MILK, 3));
		this.add(new Stock(Ingredient.CROUTONS, 3));
		this.add(new Stock(Ingredient.MAPLESYRUP, 10));
		this.add(new Stock(Ingredient.VANILLA, 3));
		this.add(new Stock(Ingredient.SPICE, 10));
		this.add(new Stock(Ingredient.SOUP_POD, 3));
		this.add(new Stock(Ingredient.ICEDTEA_POD, 3));
		this.add(new Stock(Ingredient.NITROGEN, 10));
	}
	
	public void removeQuantity(Ingredient ingredient, int quantity) {
		for (Stock stock : this) {
			if (stock.getIngredient().equals(ingredient)) {
				stock.removeQuantity(quantity); 
				break;
			}
		}
	}
	
	public boolean haveQuantity(Ingredient ingredient, int quantity) {
		for (Stock stock : this) {
			if (stock.getIngredient().equals(ingredient)) {
				return stock.getQuantity() >= quantity;
			}
		}
		return false;
	}
}
