package fr.univcotedazur.polytech.si4.fsm.project;

import java.util.ArrayList;
import java.util.List;


public class Card {

	public static final int PAYMENTS_FOR_REDUCTION = 5;
	private int ident;
	private List<Integer> payments;
	
	public Card(int ident) {
		this.ident = ident;
		this.payments = new ArrayList<>();
	}
	
	public boolean hasReduction() {
		return this.payments.size() >= PAYMENTS_FOR_REDUCTION;
	}
	
	public boolean hasReduction(int price) {
		return hasReduction() && getAveragePrice()>=price;
	}
	
	public void addPayment(int price) {
		if(hasReduction()) {
			Integer min = this.payments.get(0);
			for(Integer i : this.payments) {
				if (i<min) min = i;
			}
			this.payments.remove(min);
		}
		this.payments.add(new Integer(price));
	}
	
	public void reductionApplied() {
		this.payments.clear();
	}
	 
	public int getAveragePrice() {
		int average = 0;
		for(Integer i : this.payments) {
			average += i;
		}
		return average/this.payments.size();
	}
	
	public boolean is(int ident) {
		return ident == this.ident;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Card)) return false;
		if(obj == this) return false;
		Card c = (Card) obj;
		return c.ident == this.ident;
	}
	
	@Override
	public String toString() {
		int left = PAYMENTS_FOR_REDUCTION - this.payments.size();
		return "<br/>" + ((left>0)?(left + " achats avant remise"):("remise pour un prix inferieur à " + ((double)getAveragePrice())/100 + "€"));
	}
	
}
