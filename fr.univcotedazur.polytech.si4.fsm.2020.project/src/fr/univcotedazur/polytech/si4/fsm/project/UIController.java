package fr.univcotedazur.polytech.si4.fsm.project;

import java.awt.Color;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JLabel;

import fr.univcotedazur.polytech.si4.fsm.project.Drink.Option;
import fr.univcotedazur.polytech.si4.fsm.project.IngredientList.Ingredient;

public class UIController {
	
	private MachineController machineController;
	private DrinkFactoryMachine drinkFactoryMachine;
	private IngredientList ingredientList;
	
	public UIController(MachineController machineController) {
		this.machineController = machineController;
		this.drinkFactoryMachine = machineController.getDrinkFactoryMachine();
		this.ingredientList = machineController.getIngredientList();
	}
	
	public void lockUi(boolean state) {
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
		darkAll();
		disableOptions();
	}
	
	public void select(JButton button) {
		darkAll();
		button.setBackground(Color.GRAY);
	}
	
	private void darkAll() {
		drinkFactoryMachine.coffeeButton.setBackground(Color.DARK_GRAY);
		drinkFactoryMachine.expressoButton.setBackground(Color.DARK_GRAY);
		drinkFactoryMachine.teaButton.setBackground(Color.DARK_GRAY);
		drinkFactoryMachine.soupButton.setBackground(Color.DARK_GRAY);
		drinkFactoryMachine.icedTeaButton.setBackground(Color.DARK_GRAY);
	}

	public void disableOptions() {
		drinkFactoryMachine.milkCloud.setEnabled(false);
		drinkFactoryMachine.milkCloud.setBackground(Color.DARK_GRAY);
		drinkFactoryMachine.croutons.setEnabled(false);
		drinkFactoryMachine.croutons.setBackground(Color.DARK_GRAY);
		drinkFactoryMachine.mapleSyrup.setEnabled(false);
		drinkFactoryMachine.mapleSyrup.setBackground(Color.DARK_GRAY);
		drinkFactoryMachine.vanilla.setEnabled(false);
		drinkFactoryMachine.vanilla.setBackground(Color.DARK_GRAY);
	}
	
	public void unlockOptions(Drink drink) {
		for (Option option : drink.getOptions()) {
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
	}
	
	public void resetUi() {
		drinkFactoryMachine.sugarSlider.setValue(1);
		drinkFactoryMachine.sizeSlider.setValue(1);
		drinkFactoryMachine.temperatureSlider.setValue(2);
		if (ingredientList.haveQuantity(Ingredient.CUP, 1)) {
			unlockDrink();
		} else {
			if (machineController.aCup()) {
				unlockDrink();
			} else {
				lockUi(true);
			}
		}
		unlockButton(drinkFactoryMachine.addCupButton);
	}
	
	public void resetSliders(Drink drink) {
		resetSugar(drink);
		resetSize(drink);
		resetTemperature(drink);
	}
	
	public void resetSugar(Drink drink) {
		if(drink.equals(Drink.SOUP)) {
        	drinkFactoryMachine.changeSugar("Spices");
			if (ingredientList.haveQuantity(Ingredient.SPICE, 4)) unlockSugar();
			else lockSugar();
		} else {
			drinkFactoryMachine.changeSugar("Sugar");
			if (ingredientList.haveQuantity(Ingredient.SUGAR, 4)) unlockSugar();
			else lockSugar();
		}
	}
	
	private void resetSize(Drink drink) {
		drinkFactoryMachine.changeSize("Size");
		Hashtable<Integer, JLabel> sizeTable = new Hashtable<Integer, JLabel>();
        if(drink.equals(Drink.ICEDTEAD)) {
        	sizeTable.put(0, new JLabel("Normal"));
    		sizeTable.put(1, new JLabel("Large"));
            for (JLabel l : sizeTable.values()) {
                l.setForeground(Color.WHITE);
            }
            drinkFactoryMachine.sizeSlider.setMaximum(1);
            drinkFactoryMachine.sizeSlider.setLabelTable(sizeTable);
    		drinkFactoryMachine.sizeSlider.setValue(0);
        } else {
        	sizeTable.put(0, new JLabel("S"));
    		sizeTable.put(1, new JLabel("M"));
    		sizeTable.put(2, new JLabel("L"));
            for (JLabel l : sizeTable.values()) {
                l.setForeground(Color.WHITE);
            }
            drinkFactoryMachine.sizeSlider.setMaximum(2);
            drinkFactoryMachine.sizeSlider.setLabelTable(sizeTable);
    		drinkFactoryMachine.sizeSlider.setValue(1);
    		if(drink.equals(Drink.EXPRESSO)) drinkFactoryMachine.changeSize("Hardness");
        }
		drinkFactoryMachine.sizeSlider.setEnabled(true);
	}
	
	private void resetTemperature(Drink drink) {
		Hashtable<Integer, JLabel> temperatureTable = new Hashtable<Integer, JLabel>();
		if(drink.equals(Drink.ICEDTEAD)) {
	        temperatureTable.put(0, new JLabel("0°C"));
	        temperatureTable.put(1, new JLabel("5°C"));
	        temperatureTable.put(2, new JLabel("10°C"));
	        temperatureTable.put(3, new JLabel("15°C"));
		} else {
	        temperatureTable.put(0, new JLabel("20°C"));
	        temperatureTable.put(1, new JLabel("35°C"));
	        temperatureTable.put(2, new JLabel("60°C"));
	        temperatureTable.put(3, new JLabel("85°C"));
		}
        for (JLabel l : temperatureTable.values()) {
            l.setForeground(Color.WHITE);
        }
        drinkFactoryMachine.temperatureSlider.setLabelTable(temperatureTable);
		drinkFactoryMachine.temperatureSlider.setValue(2);
		drinkFactoryMachine.temperatureSlider.setEnabled(true);;
	}
	
	private void lockSugar() {
		drinkFactoryMachine.sugarSlider.setValue(0);
		drinkFactoryMachine.sugarSlider.setEnabled(false);
	}
	
	private void unlockSugar() {
		drinkFactoryMachine.sugarSlider.setValue(1);
		drinkFactoryMachine.sugarSlider.setEnabled(true);
	}
	
	public void unlockDrink() {
		unlockButton(drinkFactoryMachine.nfcBiiiipButton);
		unlockButton(drinkFactoryMachine.cancelButton);
		resetSliders(Drink.COFFEE);
		if (ingredientList.haveQuantity(Ingredient.COFFEE_POD, 1)) unlockButton(drinkFactoryMachine.coffeeButton);
		if (ingredientList.haveQuantity(Ingredient.GRAINS, 1)) unlockButton(drinkFactoryMachine.expressoButton);
		if (ingredientList.haveQuantity(Ingredient.TEA_BAG, 1)) unlockButton(drinkFactoryMachine.teaButton);
		if (ingredientList.haveQuantity(Ingredient.SOUP_POD, 1)) unlockButton(drinkFactoryMachine.soupButton);
		if (ingredientList.haveQuantity(Ingredient.ICEDTEA_POD, 1) && ingredientList.haveQuantity(Ingredient.NITROGEN, 4)) unlockButton(drinkFactoryMachine.icedTeaButton);
	}
	
	public void unlockButton(JButton button) {
		button.setEnabled(true);
	}
}
