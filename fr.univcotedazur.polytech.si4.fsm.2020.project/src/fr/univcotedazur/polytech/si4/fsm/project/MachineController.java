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

	public String heating = "Chauffage de l'eau";
	public String placingCup = "Placement du gobelet";
	public String crushing = "Broyage des grains";
	public String tamping = "Tassage des grains";
	public String infusion = "Infusion du thé";
	public String puttingBag = "Placement du sachet de thé";
	public String removingBag = "Retrait du sachet de thé";
	public String sugar = "Versement du sucre";
	public String water = "Versement de l'eau";
	
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
		int time = (drinkFactoryMachine.temperatureSlider.getValue() * 5) + 10;
		System.out.println("heating time : " + time);
		return time;
	}

	public long getCrushingTime() {
		int time = drinkFactoryMachine.sizeSlider.getValue() + 3;
		System.out.println("crushing time : " + time);
		return time;
	}
	
	public long getPouringWater() {
		int time = (drinkFactoryMachine.temperatureSlider.getValue() * 4) + 5;
		System.out.println("water time : " + time);
		return time;
	}

	public long getPouringSugar() {
		int time = drinkFactoryMachine.sugarSlider.getValue() + 3;
		System.out.println("sugar time : " + time);
		return time;
	}

	public long getInfusingTime() {
		int time = (drinkFactoryMachine.temperatureSlider.getValue() * 3) + 5;
		System.out.println("infusion time : " + time);
		return time;
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
	
	public void removeLine(String line) {
		drinkFactoryMachine.messagesToUser.setText(drinkFactoryMachine.messagesToUser.getText().replaceAll("<br/><br/>"+line, ""));
	}
	
	public void addLine(String line) {
		String s = drinkFactoryMachine.messagesToUser.getText().replaceAll("</html>", "");
		s += "<br/><br/>" + line + "</html>";
		drinkFactoryMachine.messagesToUser.setText(s);
	}

	public void drinkReady() {
		drinkFactoryMachine.messagesToUser.setText("<html>Votre boisson est prête !</html>");
	}

	public void placeCup() {
		drinkFactoryMachine.changePicture("./picts/gobeletPolluant.jpg");
	}

	public long getPercent() {
		long expressoTime = isExpresso()?((long)(getCrushingTime()*1.25)):0;
		long heatingTime = getHeatingTime();
		long cupTime = 3 + (isTea()?3:0);
		long totalTime = Math.max(Math.max(expressoTime, heatingTime), cupTime);
		totalTime += Math.max(getPouringWater(), getPouringSugar());
		totalTime += isTea()?(getInfusingTime()+3):0;
		return totalTime*10;
	}

	public void progressBar() {
		drinkFactoryMachine.progressBar.setValue(drinkFactoryMachine.progressBar.getValue()+1);
	}

}
