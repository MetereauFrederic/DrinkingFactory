package fr.univcotedazur.polytech.si4.fsm.project;


public class MachineController {
	
	public enum Drink {
		COFFEE(3.0);
		
		private double price;
		
		Drink(double price) {
			this.price = price;
		}
	}
	
	private DrinkFactoryMachine drinkFactoryMachine;
	private boolean nfc;
	private double money;
	private Drink drink;
	
	public MachineController(DrinkFactoryMachine drinkFactoryMachine) {
		this.drinkFactoryMachine = drinkFactoryMachine;
		this.nfc = false;
		this.money = 0;
	}

	public void cancel() {
		
	}

	public boolean enoughMoney() {
		if(this.drink != null) {
			return this.money >= this.drink.price || this.nfc;
		}
		return false;
	}
	
	public void addSelection(Drink drink) {
		this.drink = drink;
	}
	
	public void addCoin(double payed) {
		this.money += payed;
	}
	
	public void nfcPayed() {
		this.nfc = true;
	}
	
	public void preparing() {
		drinkFactoryMachine.messagesToUser.setText("Boisson en préparation");
	}

}
