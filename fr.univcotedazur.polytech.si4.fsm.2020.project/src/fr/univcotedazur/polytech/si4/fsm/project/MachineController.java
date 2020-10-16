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
		this.drink = null;
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
				"<br/><br/>Boisson en<br/>préparation<html>");
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
		if(this.nfc) s += "carte acceptee" ;
		else s += "\t" + this.money + "€ / " + ((this.drink==null)?"___":this.drink.price) + "€";
		return s;
	}
	
	private void refound() {
		drinkFactoryMachine.messagesToUser.setText(money + "€ rendus");
		this.nfc = false;
		this.money = 0;
	}

}
