package fr.univcotedazur.polytech.si4.fsm.project;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

public class MachineController {
	
	public enum Option {
		
		MILKCLOUD(10, "nuage de lait"), CROUTONS(30, "croutons"), MAPLESYRUP(10, "sirop d'érable"), VANILLA(40, "glace vanille mixée");
		
		private int price;
		private String name;
		
		Option(int price, String name) {
			this.price = price;
			this.name = name;
		}
	}
	
	public enum Drink {
		COFFEE(300,"café", Option.MILKCLOUD, Option.MAPLESYRUP, Option.VANILLA),
		TEA(250, "thé", Option.MILKCLOUD, Option.MAPLESYRUP),
		EXPRESSO(499, "expresso", Option.MILKCLOUD, Option.MAPLESYRUP, Option.VANILLA);
		
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
	}
	
	private DrinkFactoryMachine drinkFactoryMachine;
	private boolean nfc;
	private int money;
	private int price;
	private Drink drink;
	private List<Option> options;

	public String heating = "Chauffage de l'eau";
	public String placingCup = "Placement du gobelet";
	public String crushing = "Broyage des grains";
	public String tamping = "Tassage des grains";
	public String infusion = "Infusion du thé";
	public String puttingBag = "Placement du sachet de thé";
	public String removingBag = "Retrait du sachet de thé";
	public String sugar = "Versement du sucre";
	public String water = "Versement de l'eau";
	public String cleanning = "Nettoyage de la machine";
	public String pouringMapleSyrup ="Versement du sirop d'érable";
	public String pouringVanilla ="Versement de la glace vanille";
	public String mixVanilla ="Mixage de la préparation";
	public String pouringMilkCloud ="Versement du nuage de lait";
	
	public MachineController(DrinkFactoryMachine drinkFactoryMachine) {
		this.drinkFactoryMachine = drinkFactoryMachine;
		this.nfc = false;
		this.money = 0;
		this.price = 0;
		this.options = new ArrayList<>();
	}

	public void cancel() {
		if(this.nfc) refound("paiement annulé");
		else refound("");
		drinkFactoryMachine.messagesToUser.setText("<html>" + drinkFactoryMachine.messagesToUser.getText() +
				"Commande annulée<html>");		
		this.drink = null;
		System.out.println("cancel()");
		drinkFactoryMachine.progressBar.setValue(0);
		this.options.clear();
		blockUi(false);
	}

	public void addSelection(Drink drink, JButton button) {
		this.drink = drink;
		drinkFactoryMachine.coffeeButton.setBackground(Color.DARK_GRAY);
		drinkFactoryMachine.expressoButton.setBackground(Color.DARK_GRAY);
		drinkFactoryMachine.teaButton.setBackground(Color.DARK_GRAY);
		drinkFactoryMachine.soupButton.setBackground(Color.DARK_GRAY);
		drinkFactoryMachine.icedTeaButton.setBackground(Color.DARK_GRAY);
		button.setBackground(Color.GRAY);
		disableOptions();
		deleteOptions();
		for (Option option : drink.options) {
			switch (option) {
				case MILKCLOUD:
					drinkFactoryMachine.milkCloud.setEnabled(true);
					break;
				case CROUTONS:
					drinkFactoryMachine.croutons.setEnabled(true);
					break;
				case MAPLESYRUP:
					drinkFactoryMachine.mapleSyrup.setEnabled(true);
					break;
				case VANILLA:
					drinkFactoryMachine.vanilla.setEnabled(true);
					break;
				default:
					break;
			}
		}
	}
	
	private void deleteOptions() {
		List<Option> delete = new ArrayList<>();
		for (Option option : options) {
			if (!drink.options.contains(option)) {
				delete.add(option);
			}
		}
		options.removeAll(delete);
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
		resetUi();
		blockUi(true);
	}

	private void blockUi(boolean state) {
		drinkFactoryMachine.sugarSlider.setEnabled(!state);
		drinkFactoryMachine.sizeSlider.setEnabled(!state);
		drinkFactoryMachine.temperatureSlider.setEnabled(!state);
		drinkFactoryMachine.nfcBiiiipButton.setEnabled(!state);
		drinkFactoryMachine.coffeeButton.setEnabled(!state);
		drinkFactoryMachine.expressoButton.setEnabled(!state);
		drinkFactoryMachine.teaButton.setEnabled(!state);
		drinkFactoryMachine.soupButton.setEnabled(!state);
		drinkFactoryMachine.icedTeaButton.setEnabled(!state);
		drinkFactoryMachine.cancelButton.setEnabled(!state);
		drinkFactoryMachine.addCupButton.setEnabled(!state);
		disableOptions();
	}

	private void disableOptions() {
		drinkFactoryMachine.milkCloud.setEnabled(false);
		drinkFactoryMachine.croutons.setEnabled(false);
		drinkFactoryMachine.mapleSyrup.setEnabled(false);
		drinkFactoryMachine.vanilla.setEnabled(false);
	}

	public int newPrice() {
		System.out.println("newPrice()");
		if(this.drink!=null) {
			price = this.drink.price;
			for (Option option : options) price += option.price;
		}
		drinkFactoryMachine.messagesToUser.setText("<html>" + this + "</html>");
		return (price == 0) ? Integer.MAX_VALUE : price;
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
				+ ((this.drink==null)?"___":(((double)this.price)/100.0)) + "€";
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
		this.price = 0;
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
		refound("");
		drinkFactoryMachine.messagesToUser.setText("<html>" + drinkFactoryMachine.messagesToUser.getText() +
				"Veuillez sélectionner votre boisson !<html>");		
		this.drink = null;
		System.out.println("reset()");
		drinkFactoryMachine.progressBar.setValue(0);
		this.options.clear();
		blockUi(false);
	}
	
	private void resetUi() {
		drinkFactoryMachine.sugarSlider.setValue(1);
		drinkFactoryMachine.sizeSlider.setValue(1);
		drinkFactoryMachine.temperatureSlider.setValue(2);
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

	public boolean isMapleSyrup() {
		return this.options.contains(Option.MAPLESYRUP);
	}

	public boolean isVanilla() {
		return this.options.contains(Option.VANILLA);
	}

	public boolean isMilkCloud() {
		return this.options.contains(Option.MILKCLOUD);
	}

	public void addOption(Option option, JButton button) {
		if (this.options.contains(option)) {
			this.options.remove(option);
			button.setBackground(Color.DARK_GRAY);
		} else {
			this.options.add(option);
			button.setBackground(Color.GRAY);
		}
		
	}

}
