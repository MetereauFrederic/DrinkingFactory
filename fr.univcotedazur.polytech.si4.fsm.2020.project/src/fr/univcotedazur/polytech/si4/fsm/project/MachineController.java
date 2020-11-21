package fr.univcotedazur.polytech.si4.fsm.project;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import fr.univcotedazur.polytech.si4.fsm.project.IngredientList.Ingredient;

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
		EXPRESSO(499, "expresso", Option.MILKCLOUD, Option.MAPLESYRUP, Option.VANILLA),
		SOUP(75, "thé", Option.CROUTONS),
		ICEDTEAD(50, "soupe");
		
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
	private Card nfc;
	private boolean aCup;
	private int money;
	private int price;
	private Drink drink;
	private List<Option> options;
	private List<Card> fidelityCards;
	private IngredientList ingredientList;

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
		this.nfc = null;
		this.aCup = false;
		this.money = 0;
		this.price = 0;
		this.options = new ArrayList<>();
		this.fidelityCards = new ArrayList<>();
		this.ingredientList = new IngredientList();
	}

	public void cancel() {
		if(this.nfc != null) refound("paiement annulé");
		else refound("");
		drinkFactoryMachine.messagesToUser.setText("<html>" + drinkFactoryMachine.messagesToUser.getText() +
				"Commande annulée <br><br>");		
		this.drink = null;
		System.out.println("cancel()");
		drinkFactoryMachine.progressBar.setValue(0);
		this.options.clear();
		lockUi(true);
		resetUi();
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
		this.options.clear();
		for (Option option : drink.options) {
			switch (option) {
				case MILKCLOUD:
					if (ingredientList.haveQuantity(Ingredient.MILK, 1)) unlockButton(drinkFactoryMachine.milkCloud);
					break;
				case CROUTONS:
					if (ingredientList.haveQuantity(Ingredient.CROUTONS, 1)) unlockButton(drinkFactoryMachine.croutons);
					break;
				case MAPLESYRUP:
					if (ingredientList.haveQuantity(Ingredient.MAPLESYRUP, 4)) unlockButton(drinkFactoryMachine.mapleSyrup);
					break;
				case VANILLA:
					if (ingredientList.haveQuantity(Ingredient.VANILLA, 1)) unlockButton(drinkFactoryMachine.vanilla);
					break;
				default:
					break;
			}
		}
		if (drink.equals(Drink.SOUP)) {
			if (ingredientList.haveQuantity(Ingredient.SPICE, 4)) {
				drinkFactoryMachine.sugarSlider.setValue(1);
				drinkFactoryMachine.sugarSlider.setEnabled(true);
			}
		}
	}

	public void addCoin(int payed) {
		this.money += payed;
	}
	
	public void nfcPayed() {
		Integer ident = Integer.parseInt(drinkFactoryMachine.nfcBiiiipId.getText());
		for(Card card : fidelityCards) {
			if(card.is(ident)) this.nfc = card;
		}
		if(this.nfc == null) {
			Card newCard = new Card(ident);
			fidelityCards.add(newCard);
			this.nfc = newCard;
		}
	}
	
	public void preparing() {
		lockUi(true);
//		drinkFactoryMachine.changePicture("./picts/gobeletPolluant.jpg");
		if(this.nfc == null) this.money -= this.drink.price;
		if(this.nfc != null) {
			Boolean reduction = this.nfc.hasReduction(this.price);
			if(reduction) {
				this.nfc.reductionApplied();
			} else {
				this.nfc.addPayment(this.drink.price);
			}
			String s = ((reduction)?"0€ payés (reduction de fidélité)":(((double)this.price)/100.0) + "€ payés");
			System.out.println(s);
			refound(s);
		}
		else refound("");
		drinkFactoryMachine.messagesToUser.setText("<html>" + drinkFactoryMachine.messagesToUser.getText() +
				"Boisson en<br/>préparation</html>");
	}

	public void lockUi(boolean state) {
		drinkFactoryMachine.sugarSlider.setEnabled(!state);
		drinkFactoryMachine.sizeSlider.setEnabled(!state);
		drinkFactoryMachine.temperatureSlider.setEnabled(!state);
		drinkFactoryMachine.nfcBiiiipButton.setEnabled(!state);
		drinkFactoryMachine.coffeeButton.setEnabled(!state);
		drinkFactoryMachine.coffeeButton.setBackground(Color.DARK_GRAY);
		drinkFactoryMachine.expressoButton.setEnabled(!state);
		drinkFactoryMachine.expressoButton.setBackground(Color.DARK_GRAY);
		drinkFactoryMachine.teaButton.setEnabled(!state);
		drinkFactoryMachine.teaButton.setBackground(Color.DARK_GRAY);
		drinkFactoryMachine.soupButton.setEnabled(!state);
		drinkFactoryMachine.soupButton.setBackground(Color.DARK_GRAY);
		drinkFactoryMachine.icedTeaButton.setEnabled(!state);
		drinkFactoryMachine.icedTeaButton.setBackground(Color.DARK_GRAY);
		drinkFactoryMachine.cancelButton.setEnabled(!state);
		drinkFactoryMachine.addCupButton.setEnabled(!state);
		disableOptions();
	}

	private void disableOptions() {
		drinkFactoryMachine.milkCloud.setEnabled(false);
		drinkFactoryMachine.milkCloud.setBackground(Color.DARK_GRAY);
		drinkFactoryMachine.croutons.setEnabled(false);
		drinkFactoryMachine.croutons.setBackground(Color.DARK_GRAY);
		drinkFactoryMachine.mapleSyrup.setEnabled(false);
		drinkFactoryMachine.mapleSyrup.setBackground(Color.DARK_GRAY);
		drinkFactoryMachine.vanilla.setEnabled(false);
		drinkFactoryMachine.vanilla.setBackground(Color.DARK_GRAY);
	}

	public int newPrice() {
		System.out.println("newPrice()");
		if(this.drink!=null) {
			price = this.drink.price - ((aCup)?10:0);
			for (Option option : options) price += option.price;
		}
		if (price != 0 || money != 0) drinkFactoryMachine.messagesToUser.setText("<html>" + this + "</html>");
		return (price == 0) ? Integer.MAX_VALUE : price;
	}

	public int currentMoney() {
		System.out.println("currentMoney()");
		drinkFactoryMachine.messagesToUser.setText("<html>" + this + "</html>");
		if(this.nfc != null) return Integer.MAX_VALUE;
		return this.money;
	}
	
	@Override
	public String toString() {
		String s = "Veuillez sélectionner votre boisson !";
		if(this.nfc != null) 
			s = "carte acceptée" + this.nfc.toString();
		else if(this.price != 0 || this.money !=0) s = "\t" + (((double)this.money)/100.0) + "€ / " 
			+ ((this.drink==null)?"___":(((double)this.price)/100.0)) + "€";
		System.out.println(s);
		return s;
	}
	
	private void refound(String s) {
		if(this.money>0) {
			if(this.nfc != null) s += "<br/>";
			s += (((double)money)/100.0) + "€ rendus";
		}
		if(!s.equals("")) s += "<br/><br/>" ;
		drinkFactoryMachine.messagesToUser.setText(s);
		this.nfc = null;
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
		int time = drinkFactoryMachine.sugarSlider.getValue();
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
		//drinkFactoryMachine.messagesToUser.setText("<html>" + drinkFactoryMachine.messagesToUser.getText() +
		//		"Veuillez sélectionner votre boisson !<html>");		
		this.drink = null;
		System.out.println("reset()");
		drinkFactoryMachine.progressBar.setValue(0);
		this.options.clear();
		resetUi();
	}
	
	public void resetUi() {
		drinkFactoryMachine.sugarSlider.setValue(0);
		drinkFactoryMachine.sizeSlider.setValue(1);
		drinkFactoryMachine.temperatureSlider.setValue(2);
		if (ingredientList.haveQuantity(Ingredient.CUP, 1)) {
			unlockDrink();
		} else {
			if (aCup) {
				unlockDrink();
			} else {
				lockUi(true);
			}
		}
		unlockButton(drinkFactoryMachine.addCupButton);
		if (drinkFactoryMachine.messagesToUser.getText().equals(""))
			drinkFactoryMachine.messagesToUser.setText("<html>");
		drinkFactoryMachine.messagesToUser.setText(drinkFactoryMachine.messagesToUser.getText() + "" + this + "</html>");
	}
	
	private void unlockDrink() {
		unlockButton(drinkFactoryMachine.nfcBiiiipButton);
		unlockButton(drinkFactoryMachine.cancelButton);
		drinkFactoryMachine.temperatureSlider.setEnabled(true);
		drinkFactoryMachine.sizeSlider.setEnabled(true);
		if (ingredientList.haveQuantity(Ingredient.SUGAR, 4)) {
			drinkFactoryMachine.sugarSlider.setValue(1);
			drinkFactoryMachine.sugarSlider.setEnabled(true);
		}
		if (ingredientList.haveQuantity(Ingredient.COFFEE_POD, 1)) unlockButton(drinkFactoryMachine.coffeeButton);
		if (ingredientList.haveQuantity(Ingredient.GRAINS, 1)) unlockButton(drinkFactoryMachine.expressoButton);
		if (ingredientList.haveQuantity(Ingredient.TEA_BAG, 1)) unlockButton(drinkFactoryMachine.teaButton);
		if (ingredientList.haveQuantity(Ingredient.SOUP_POD, 1)) unlockButton(drinkFactoryMachine.soupButton);
		if (ingredientList.haveQuantity(Ingredient.ICEDTEA_POD, 1) && ingredientList.haveQuantity(Ingredient.NITROGEN, 4)) unlockButton(drinkFactoryMachine.icedTeaButton);
	}
	
	private void lockButton(JButton button) {
		button.setEnabled(false);
	}
	
	private void unlockButton(JButton button) {
		button.setEnabled(true);
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
		long cupTime = (aCup)?0:3 + (isTea()?3:0);
		long totalTime = Math.max(Math.max(expressoTime, heatingTime), cupTime);
		totalTime += Math.max(getPouringWater(), getPouringSugar()); // getPurringSugar() pour sucre et sirop d'érable
		totalTime += isTea()?(getInfusingTime()+3):isVanilla()?8:0; //8 pour la vanille versement + mixage
		totalTime += isMilkCloud()?3:0; //Nuage de lait
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
		if (option.equals(Option.MAPLESYRUP)) {
			drinkFactoryMachine.sugarSlider.setValue(1);
			drinkFactoryMachine.sugarSlider.setEnabled(true);
		}
	}

	public void aCupAction() {
		this.aCup = !this.aCup;
		if(aCup) {
			drinkFactoryMachine.changePicture("./picts/ownCup.jpg");
			drinkFactoryMachine.addCupButton.setText("Remove cup");
		} else {
			drinkFactoryMachine.changePicture("./picts/vide2.jpg");
			drinkFactoryMachine.addCupButton.setText("Add cup");
		}
		resetUi();
	}
	
	public void removeQuantity(Ingredient ingredient) {
		if (ingredient.equals(Ingredient.SPICE) || ingredient.equals(Ingredient.SUGAR) || ingredient.equals(Ingredient.MAPLESYRUP)) 
			ingredientList.removeQuantity(ingredient, drinkFactoryMachine.sugarSlider.getValue());
		ingredientList.removeQuantity(ingredient, 1);
	}

}
