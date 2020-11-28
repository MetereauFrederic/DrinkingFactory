package fr.univcotedazur.polytech.si4.fsm.project;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import fr.univcotedazur.polytech.si4.fsm.project.Drink.Option;
import fr.univcotedazur.polytech.si4.fsm.project.IngredientList.Ingredient;

public class MachineController {
	
	private DrinkFactoryMachine drinkFactoryMachine;
	private Card nfc;
	private boolean aCup;
	private int money;
	private int price;
	private Drink drink;
	private List<Option> options;
	private List<Card> fidelityCards;
	private IngredientList ingredientList;
	private UIController uiController;

	public String heating = "Chauffage de l'eau";
	public String placingCup = "Placement du gobelet";
	public String crushing = "Broyage des grains";
	public String tamping = "Tassage des grains";
	public String infusion = "Infusion du thé";
	public String coffeePod = "Placement de la dosette";
	public String puttingBag = "Placement du sachet de thé";
	public String removingBag = "Retrait du sachet de thé";
	public String sugar = "Versement du sucre";
	public String water = "Versement de l'eau";
	public String soup = "Versement d'une dose de soupe";
	public String spices = "Versement des épices";
	public String cleanning = "Nettoyage de la machine";
	public String pouringMapleSyrup = "Versement du sirop d'érable";
	public String pouringVanilla = "Versement de la glace vanille";
	public String mixVanilla = "Mixage de la préparation";
	public String pouringMilkCloud = "Versement du nuage de lait";
	public String pouringCroutons = "Versement des croutons";
	public String doorClosed = "porte verrouillée";
	public String doorOpen = "déverrouillage de la porte";
	public String nitrogen = "refroidissement à l'azote";
	
	private String paymentCanceled = "paiement annulé";
	private String orderCanceled = "Commande annulée";
	private String currency = "€";
	private String payed = "payés";
	private String refounded = "rendus";
	private String hello = "Veuillez sélectionner votre boisson !";
	private String fidelity = "reduction de fidélité";
	private String preparing = "Boisson en préparation";
	private String nfcAccepted = "carte acceptée";
	private String drinkReady = "Votre boisson est prête !";
	
	public MachineController(DrinkFactoryMachine drinkFactoryMachine) {
		this.drinkFactoryMachine = drinkFactoryMachine;
		this.nfc = null;
		this.aCup = false;
		this.money = 0;
		this.price = 0;
		this.options = new ArrayList<>();
		this.fidelityCards = new ArrayList<>();
		this.ingredientList = new IngredientList();
		this.uiController = new UIController(this);
	}

	public void cancel() {
		if(this.nfc != null) refound(paymentCanceled);
		else refound("");
		addLine(orderCanceled);	
		this.drink = null;
		System.out.println("cancel()");
		drinkFactoryMachine.progressBar.setValue(0);
		this.options.clear();
		uiController.lockUi(true);
		uiController.darkAll();
		uiController.resetUi();
	}

	public void addSelection(Drink drink, JButton button) {
		this.drink = drink;
		uiController.select(button);
		uiController.disableOptions();
		this.options.clear();
		uiController.unlockOptions(drink);
		uiController.resetSliders(drink);
	}

	public void addCoin(int payed) {
		this.money += payed;
	}
	
	public void nfcPayed() {
		try {
			Integer ident = Integer.parseInt(drinkFactoryMachine.nfcBiiiipId.getText());
			for(Card card : fidelityCards) {
				if(card.is(ident)) this.nfc = card;
			}
			if(this.nfc == null) {
				Card newCard = new Card(ident);
				fidelityCards.add(newCard);
				this.nfc = newCard;
			}
		} catch(Exception e) {
			drinkFactoryMachine.badNfcInfut();
		}
	}
	
	public void preparing() {
		uiController.lockUi(true);
//		drinkFactoryMachine.changePicture("./picts/gobeletPolluant.jpg");
		if(this.nfc == null) this.money -= this.price;
		if(this.nfc != null) {
			Boolean reduction = this.nfc.hasReduction(this.price);
			if(reduction) {
				this.nfc.reductionApplied();
			} else {
				this.nfc.addPayment(this.drink.getPrice());
			}
			String s = ((reduction)?
					"0" + currency + " " + payed + " (" + fidelity + ")"
					:(((double)this.price)/100.0) + currency + " " + payed + nfc.toString());
			System.out.println(s);
			refound(s);
		}
		else refound("");
		addLine(preparing);
	}

	public int newPrice() {
		System.out.println("newPrice()");
		if(this.drink!=null) {
			if(isDrink(Drink.ICEDTEAD)&&drinkFactoryMachine.sizeSlider.getValue()==1&&!options.contains(Option.LARGE_ICEDTEA)) options.add(Option.LARGE_ICEDTEA);
			else if(isDrink(Drink.ICEDTEAD)&&drinkFactoryMachine.sizeSlider.getValue()==0) options.remove(Option.LARGE_ICEDTEA);
			price = this.drink.getPrice() - ((aCup)?10:0);
			for (Option option : options) price += option.getPrice();
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
		String s = hello;
		if(this.nfc != null) 
			s = nfcAccepted + this.nfc.toString();
		else if(this.price != 0 || this.money !=0) s = "\t" + ((this.drink!=null)?drink.getName()+" : ":"") + (((double)this.money)/100.0) + currency + " / " 
			+ ((this.drink==null)?"___":(((double)this.price)/100.0)) + currency;
		System.out.println(s);
		return s;
	}
	
	private void refound(String s) {
		String refound = "<html>" + s;
		if(this.money>0) {
			if(this.nfc != null) refound += "<br/>";
			refound += (((double)money)/100.0) + currency + " " + refounded;
		}
		drinkFactoryMachine.messagesToUser.setText((refound.equals("<html>"))?"":refound + "</html>");
		this.nfc = null;
		this.money = 0;
		this.price = 0;
	}

	public long getHeatingTime() {
		int value = (isDrink(Drink.ICEDTEAD))?
				(drinkFactoryMachine.sizeSlider.getValue()+2):
					(drinkFactoryMachine.temperatureSlider.getValue() + drinkFactoryMachine.sizeSlider.getValue());
		int time = (value * 4) + 6;
		System.out.println("heating time : " + time);
		return time;
	}

	public long getCrushingTime() {
		int time = drinkFactoryMachine.sizeSlider.getValue() + 3;
		System.out.println("crushing time : " + time);
		return time;
	}
	
	public long getPouringWater() {
		int time = (((isDrink(Drink.EXPRESSO))?1:drinkFactoryMachine.sizeSlider.getValue()) * 3) + 5;
		System.out.println("water time : " + time);
		return time;
	}

	public long getPouringSugar() {
		int time = drinkFactoryMachine.sugarSlider.getValue();
		System.out.println("sugar time : " + time);
		return time;
	}
	
	public long getPouringSpices() {
		int time = drinkFactoryMachine.sugarSlider.getValue();
		System.out.println("spices time : " + time);
		return time;
	}

	public long getInfusingTime() {
		int time = (drinkFactoryMachine.sizeSlider.getValue() * 4) + 5;
		System.out.println("infusion time : " + time);
		return time;
	}
	
	public long getNitrogenTime() {
		int time = ((drinkFactoryMachine.sizeSlider.getValue() - drinkFactoryMachine.temperatureSlider.getValue() + 5) *2) + 3;
		System.out.println("nitrogen time : " + time);
		return time;
	}

	public void reset() {
		refound("");
		//drinkFactoryMachine.messagesToUser.setText("<html>" + drinkFactoryMachine.messagesToUser.getText() +
		//		"Veuillez sélectionner votre boisson !<html>");		
		this.drink = null;
		this.aCup = false;
		drinkFactoryMachine.progressBar.setValue(0);
		this.options.clear();
		uiController.darkAll();
		uiController.resetUi();
		addLine(this.toString());
	}

	public void removeLine(String line) {
		drinkFactoryMachine.messagesToUser.setText(drinkFactoryMachine.messagesToUser.getText().replaceAll("<br/><br/>"+line, ""));
	}
	
	public void addLine(String line) {
		String s = drinkFactoryMachine.messagesToUser.getText().replaceAll("</html>", "");
		s += ((s.equals(""))?"<html>":"<br/><br/>") + line + "</html>";
		drinkFactoryMachine.messagesToUser.setText(s);
	}

	public void drinkReady() {
		drinkFactoryMachine.messagesToUser.setText("<html>" + drinkReady + "</html>");
	}

	public void placeCup() {
		drinkFactoryMachine.changePicture("./picts/gobeletPolluant.jpg");
	}

	public long getPercent() {
		long expressoTime = isDrink(Drink.EXPRESSO)?((long)(getCrushingTime()*1.25)):0;
		long heatingTime = getHeatingTime();
		long cupTime = (aCup)?0:3 + ((isDrink(Drink.TEA)||isDrink(Drink.ICEDTEAD))?3:0);
		long totalTime = Math.max(Math.max(expressoTime, heatingTime), cupTime);
		totalTime += Math.max(getPouringWater(), getPouringSugar()); // getPurringSugar() pour sucre et sirop d'érable
		totalTime += (isDrink(Drink.TEA)||isDrink(Drink.ICEDTEAD))?(getInfusingTime()+3):isOption(Option.VANILLA)?8:0; //8 pour la vanille versement + mixage
		totalTime += isOption(Option.MILKCLOUD)?3:0; //Nuage de lait
		totalTime += isDrink(Drink.ICEDTEAD)?getNitrogenTime():0; //thé glacé
		System.out.println("total time : " + totalTime);
		return totalTime*10;
	}

	public void progressBar() {
		drinkFactoryMachine.progressBar.setValue(drinkFactoryMachine.progressBar.getValue()+1);
	}

	public void addOption(Option option, JButton button) {
		if (this.options.contains(option)) {
			this.options.remove(option);
			button.setBackground(Color.DARK_GRAY);
			if (option.equals(Option.MAPLESYRUP)) {
				uiController.resetSugar(Drink.COFFEE);
			}
		} else {
			this.options.add(option);
			button.setBackground(Color.GRAY);
			if (option.equals(Option.MAPLESYRUP)) {
				drinkFactoryMachine.changeSugar("Maple syrup");
				drinkFactoryMachine.sugarSlider.setValue(1);
				drinkFactoryMachine.sugarSlider.setEnabled(true);
			}
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
		uiController.lockUi(true);
		uiController.resetUi();
	}
	
	public void takeCup() {
		drinkFactoryMachine.changePicture("./picts/vide2.jpg");
		drinkFactoryMachine.addCupButton.setText("Add cup");
	}
	
	public void setPictureClosed(boolean closed) {
		if (closed)
			drinkFactoryMachine.changePicture("./picts/ferme.jpg");
		else if (aCup)
			drinkFactoryMachine.changePicture("./picts/ownCup.jpg");
		else
			drinkFactoryMachine.changePicture("./picts/gobeletPolluant.jpg");
	}
	
	public void removeQuantity(Ingredient ingredient) {
		if (ingredient.equals(Ingredient.SPICE) || ingredient.equals(Ingredient.SUGAR) || ingredient.equals(Ingredient.MAPLESYRUP)) 
			ingredientList.removeQuantity(ingredient, drinkFactoryMachine.sugarSlider.getValue());
		else if(ingredient.equals(Ingredient.GRAINS))
			ingredientList.removeQuantity(ingredient, drinkFactoryMachine.sizeSlider.getValue()+1);
		else if(ingredient.equals(Ingredient.NITROGEN))
			ingredientList.removeQuantity(ingredient, drinkFactoryMachine.sizeSlider.getValue()-drinkFactoryMachine.temperatureSlider.getValue()+5);
		else ingredientList.removeQuantity(ingredient, 1);
	}
	
	public boolean isDrink(Drink drink) {
		return this.drink.equals(drink);
	}
	
	public boolean isOption(Option option) {
		return this.options.contains(option);
	}
	
	public DrinkFactoryMachine getDrinkFactoryMachine() {
		return drinkFactoryMachine;
	}
	
	public IngredientList getIngredientList() {
		return ingredientList;
	}
	
	public boolean aCup() {
		return aCup;
	}
	
	public void resetUi() {
		uiController.resetUi();
	}
	
	public Drink getDrink() {
		return drink;
	}
}