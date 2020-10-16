package fr.univcotedazur.polytech.si4.fsm.project;


public class MachineController {
	
	public enum Drink {
		COFFEE(3.0,"coffee");
		
		private double price;
		private String name;
		
		Drink(double price, String name) {
			this.price = price;
			this.name = name;
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
		refound();
		drinkFactoryMachine.messagesToUser.setText("<html>" + drinkFactoryMachine.messagesToUser.getText() +
				"Commande annulée<html>");		
		this.drink = null;
		System.out.println("cancel()");
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
		if(!this.nfc) this.money -= this.drink.price;
		refound();
		drinkFactoryMachine.messagesToUser.setText("<html>" + drinkFactoryMachine.messagesToUser.getText() +
				"Boisson en<br/>préparation<html>");
	}

	public double newPrice() {
		System.out.println("newPrice()");
		drinkFactoryMachine.messagesToUser.setText("<html>" + this + "<br/><br/>" + this.drink.name + " sélectioné</html>");
		if(this.drink!=null) return this.drink.price;
		return Double.MAX_VALUE;
	}

	public double currentMoney() {
		System.out.println("currentMoney()");
		drinkFactoryMachine.messagesToUser.setText("<html>" + this + "</html>");
		if(this.nfc) return Double.MAX_VALUE;
		return this.money;
	}
	
	@Override
	public String toString() {
		String s = "";
		if(this.nfc) s += "carte acceptée" ;
		else s += "\t" + this.money + "€ / " + ((this.drink==null)?"___":this.drink.price) + "€";
		return s;
	}
	
	private void refound() {
		String s = "";
		if(this.nfc) s += "paiement annulé" ;
		if(this.money>0) {
			if(this.nfc) s += "<br/>";
			s += money + "€ rendus";
		}
		if(!s.equals("")) s += "<br/><br/>" ;
		drinkFactoryMachine.messagesToUser.setText(s);
		this.nfc = false;
		this.money = 0;
	}

}
