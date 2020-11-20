package fr.univcotedazur.polytech.si4.fsm.project;

import fr.univcotedazur.polytech.si4.fsm.project.IngredientList.Ingredient;

public class Stock {
	
	private Ingredient ingredient;
	private int quantity;
	
	public Stock(Ingredient ingredient, int quantity) {
		this.ingredient = ingredient;
		this.quantity = quantity;
	}
	
	public void removeQuantity(int quantity) {
		this.quantity -= quantity;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public Ingredient getIngredient() {
		return this.ingredient;
	}
}
