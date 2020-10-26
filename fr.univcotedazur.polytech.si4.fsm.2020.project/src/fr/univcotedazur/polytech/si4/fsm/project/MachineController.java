package fr.univcotedazur.polytech.si4.fsm.project;


public class MachineController {
	
	public enum Drink {
		COFFEE(300,"café"),
		TEA(250, "thé"),
		EXPRESSO(499, "expresso");
		
		private int price;
		private String name;
		
		Drink(int price, String name) {
			this.price = price;
			this.name = name;
		}
	}
	
	private DrinkFactoryMachine drinkFactoryMachine;
	private boolean nfc;
	private int money;
	private Drink drink;
	
	public MachineController(DrinkFactoryMachine drinkFactoryMachine) {
		this.drinkFactoryMachine = drinkFactoryMachine;
		this.nfc = false;
		this.money = 0;
	}

	public void cancel() {
		if(this.nfc) refound("paiement annulé");
		else refound("");
		drinkFactoryMachine.messagesToUser.setText("<html>" + drinkFactoryMachine.messagesToUser.getText() +
				"Commande annulée<html>");		
		this.drink = null;
		System.out.println("cancel()");
	}
	
	public void addSelection(Drink drink) {
		this.drink = drink;
	}
	
	public void addCoin(int payed) {
		this.money += payed;
	}
	
	public void nfcPayed() {
		this.nfc = true;
	}
	
	public void preparing() {
//		drinkFactoryMachine.changePicture("./picts/gobeletPolluant.jpg");
		if(!this.nfc) this.money -= this.drink.price;
		if(this.nfc) refound("paiement accepté");
		else refound("");
		drinkFactoryMachine.messagesToUser.setText("<html>" + drinkFactoryMachine.messagesToUser.getText() +
				"Boisson en<br/>préparation</html>");
	}

	public int newPrice() {
		System.out.println("newPrice()");
		drinkFactoryMachine.messagesToUser.setText("<html>" + this + "<br/><br/>" + this.drink.name + " sélectioné</html>");
		if(this.drink!=null) return this.drink.price;
		return Integer.MAX_VALUE;
	}

	public int currentMoney() {
		System.out.println("currentMoney()");
		drinkFactoryMachine.messagesToUser.setText("<html>" + this + "</html>");
		if(this.nfc) return Integer.MAX_VALUE;
		return this.money;
	}
	
	@Override
	public String toString() {
		String s = "";
		if(this.nfc) s += "carte acceptée" ;
		else s += "\t" + (((double)this.money)/100.0) + "€ / "
				+ ((this.drink==null)?"___":(((double)this.drink.price)/100.0)) + "€";
		return s;
	}
	
	private void refound(String s) {
		if(this.money>0) {
			if(this.nfc) s += "<br/>";
			s += (((double)money)/100.0) + "€ rendus";
		}
		if(!s.equals("")) s += "<br/><br/>" ;
		drinkFactoryMachine.messagesToUser.setText(s);
		this.nfc = false;
		this.money = 0;
	}

	public long getHeatingTime() {
		return (drinkFactoryMachine.temperatureSlider.getValue() * 5) + 10;
	}

	public long getCrushingTime() {
		return drinkFactoryMachine.sizeSlider.getValue() + 3;
	}
	
	public long getPouringWater() {
		return (drinkFactoryMachine.temperatureSlider.getValue() * 4) + 5;
	}

	public long getPouringShugar() {
		return drinkFactoryMachine.sugarSlider.getValue() + 3;
	}

	public long getInfusingTime() {
		return (drinkFactoryMachine.temperatureSlider.getValue() * 3) + 5;
	}

	public boolean isExpresso() {
		return this.drink.equals(Drink.EXPRESSO);
	}

	public boolean isTea() {
		return this.drink.equals(Drink.TEA);
	}

	public void reset() {
		System.out.println("reset()");
	}

	public void crushing() {
		System.out.println("crushing()");
	}

	public void tamping() {
		System.out.println("tamping()");
	}

	public void heating() {
		System.out.println("heating()");
	}

	public void placingCup() {
		System.out.println("placingCup()");
	}

	public void pourringWater() {
		System.out.println("pourringWater()");
	}

	public void pourringSugar() {
		System.out.println("purringSugar()");
	}

	public void infusing() {
		System.out.println("infusing()");
	}

	public void removingBag() {
		System.out.println("removingBag()");
	}

	public void puttingTea() {
		System.out.println("puttingTea()");
	}

}
