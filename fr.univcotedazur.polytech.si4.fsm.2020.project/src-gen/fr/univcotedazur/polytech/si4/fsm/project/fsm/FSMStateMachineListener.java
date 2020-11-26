package fr.univcotedazur.polytech.si4.fsm.project.fsm;

import fr.univcotedazur.polytech.si4.fsm.project.IngredientList.Ingredient;
import fr.univcotedazur.polytech.si4.fsm.project.MachineController;
import fr.univcotedazur.polytech.si4.fsm.project.Drink;
import fr.univcotedazur.polytech.si4.fsm.project.Drink.Option;
import fr.univcotedazur.polytech.si4.fsm.project.fsm.IFSMStatemachine.SCInterfaceListener;
import fr.univcotedazur.polytech.si4.fsm.project.fsm.IFSMStatemachine.SCInterfaceOperationCallback;

public class FSMStateMachineListener implements SCInterfaceListener, SCInterfaceOperationCallback {
	
	private MachineController machineController;
	
	public FSMStateMachineListener(MachineController machineController) {
		this.machineController = machineController;
	}

	@Override
	public void onCancelRaised() {
		machineController.cancel();
	}

	@Override
	public void onPreparingRaised() {
		machineController.preparing();
	}

	@Override
	public long newPrice() {
		return machineController.newPrice();
	}

	@Override
	public long currentMoney() {
		return machineController.currentMoney();
	}

	@Override
	public long getHeatingTime() {
		return machineController.getHeatingTime();
	}

	@Override
	public long getCrushingTime() {
		return machineController.getCrushingTime();
	}

	@Override
	public long getPouringWater() {
		return machineController.getPouringWater();
	}

	@Override
	public long getPouringSugar() {
		return machineController.getPouringSugar();
	}

	@Override
	public long getInfusingTime() {
		return machineController.getInfusingTime();
	}

	@Override
	public boolean isExpresso() {
		return machineController.isDrink(Drink.EXPRESSO);
	}

	@Override
	public boolean isTea() {
		return machineController.isDrink(Drink.TEA);
	}

	@Override
	public void onResetRaised() {
		System.out.println("reset()");
		machineController.reset();
	}

	@Override
	public void onCrushingRaised() {
		System.out.println("crushing()");
		machineController.addLine(machineController.crushing);
		machineController.removeQuantity(Ingredient.GRAINS);
	}

	@Override
	public void onTampingRaised() {
		System.out.println("tamping()");
		machineController.addLine(machineController.tamping);
	}

	@Override
	public void onHeatingRaised() {
		System.out.println("heating()");
		machineController.addLine(machineController.heating);
	}

	@Override
	public void onPlacingCupRaised() {
		System.out.println("placingCup()");
		machineController.addLine(machineController.placingCup);
		machineController.placeCup();
		machineController.removeQuantity(Ingredient.CUP);
	}

	@Override
	public void onPouringWaterRaised() {
		System.out.println("pouringWater()");
		machineController.addLine(machineController.water);
	}

	@Override
	public void onPouringSugarRaised() {
		System.out.println("pouringSugar()");
		machineController.addLine(machineController.sugar);
		machineController.removeQuantity(Ingredient.SUGAR);
	}

	@Override
	public void onInfusingRaised() {
		System.out.println("infusing()");
		machineController.addLine(machineController.infusion);
	}

	@Override
	public void onRemovingBagRaised() {
		System.out.println("removingBag()");
		machineController.addLine(machineController.removingBag);
	}

	@Override
	public void onPuttingTeaRaised() {
		System.out.println("puttingTea()");
		machineController.addLine(machineController.puttingBag);
		machineController.removeQuantity(Ingredient.TEA_BAG);
	}

	@Override
	public void onEndCrushingRaised() {
		System.out.println("endCrushing()");
		machineController.removeLine(machineController.crushing);
		
	}

	@Override
	public void onEndTampingRaised() {
		System.out.println("endTamping()");
		machineController.removeLine(machineController.tamping);
		
	}

	@Override
	public void onEndHeatingRaised() {
		System.out.println("endHeating()");
		machineController.removeLine(machineController.heating);
		
	}

	@Override
	public void onEndPlacingCupRaised() {
		System.out.println("endPlacingCup()");
		machineController.removeLine(machineController.placingCup);
		
	}

	@Override
	public void onEndPouringWaterRaised() {
		System.out.println("endWater()");
		machineController.removeLine(machineController.water);
	}

	@Override
	public void onEndPouringSugarRaised() {
		System.out.println("endSugar()");
		machineController.removeLine(machineController.sugar);
	}

	@Override
	public void onEndInfusingRaised() {
		System.out.println("endInfusion()");
		machineController.removeLine(machineController.infusion);
	}

	@Override
	public void onEndRemovingBagRaised() {
		System.out.println("endRemoveBag()");
		machineController.removeLine(machineController.removingBag);
	}

	@Override
	public void onEndPuttingTeaRaised() {
		System.out.println("endPutBag()");
		machineController.removeLine(machineController.puttingBag);
	}

	@Override
	public void onDrinkReadyRaised() {
		System.out.println("drinkReady()");
		machineController.drinkReady();
	}

	@Override
	public long getPercent() {
		return machineController.getPercent();
	}

	@Override
	public void onProgressBarRaised() {
		machineController.progressBar();
	}

	@Override
	public void onCleanningRaised() {
		System.out.println("cleanning()");
		machineController.addLine(machineController.cleanning);
	}

	@Override
	public void onEndCleanningRaised() {
		System.out.println("endCleanning()");
		machineController.removeLine(machineController.cleanning);
	}

	@Override
	public boolean isMapleSyrup() {
		return machineController.isOption(Option.MAPLESYRUP);
	}

	@Override
	public boolean isVanilla() {
		return machineController.isOption(Option.VANILLA);
	}

	@Override
	public boolean isMilkCloud() {
		return machineController.isOption(Option.MILKCLOUD);
	}

	@Override
	public void onPouringMapleSyrupRaised() {
		System.out.println("pouringMapleSyrup()");
		machineController.addLine(machineController.pouringMapleSyrup);
		machineController.removeQuantity(Ingredient.MAPLESYRUP);
	}

	@Override
	public void onEndPouringMapleSyrupRaised() {
		System.out.println("endPouringMapleSyrup()");
		machineController.removeLine(machineController.pouringMapleSyrup);
	}

	@Override
	public void onPouringVanillaRaised() {
		System.out.println("pouringVanilla()");
		machineController.addLine(machineController.pouringVanilla);
		machineController.removeQuantity(Ingredient.VANILLA);
	}

	@Override
	public void onMixVanillaRaised() {
		System.out.println("pouringMixVanilla()");
		machineController.removeLine(machineController.pouringVanilla);
		machineController.addLine(machineController.mixVanilla);
	}

	@Override
	public void onEndVanillaRaised() {
		System.out.println("endVanilla()");
		machineController.removeLine(machineController.mixVanilla);
	}

	@Override
	public void onPouringMilkCloudRaised() {
		System.out.println("pouringMilkCloud()");
		machineController.addLine(machineController.pouringMilkCloud);
		machineController.removeQuantity(Ingredient.MILK);
	}

	@Override
	public void onEndMilkCloudRaised() {
		System.out.println("endMilkCloud()");
		machineController.removeLine(machineController.pouringMilkCloud);
	}

	@Override
	public void onACupRaised() {
		machineController.aCupAction();
	}

	@Override
	public boolean isCoffee() {
		return machineController.isDrink(Drink.COFFEE);
	}

	@Override
	public void onCoffeePodRaised() {
		System.out.println("coffeePod()");
		machineController.addLine(machineController.coffeePod);
		machineController.removeQuantity(Ingredient.COFFEE_POD);
	}

	@Override
	public void onEndCoffeePodRaised() {
		System.out.println("endCoffeePod()");
		machineController.removeLine(machineController.coffeePod);
	}

	@Override
	public boolean isSoup() {
		return machineController.isDrink(Drink.SOUP);
	}

	@Override
	public boolean isSugar() {
		return !(machineController.isOption(Option.MAPLESYRUP) || machineController.isDrink(Drink.SOUP));
	}

	@Override
	public void onPouringSoupRaised() {
		System.out.println("pouringSoup()");
		machineController.addLine(machineController.soup);
		machineController.removeQuantity(Ingredient.SOUP_POD);
	}

	@Override
	public void onPouringSpiceRaised() {
		System.out.println("pouringSpice()");
		machineController.removeLine(machineController.soup);
		machineController.addLine(machineController.spices);
		machineController.removeQuantity(Ingredient.SPICE);
	}

	@Override
	public void onEndPouringSpiceRaised() {
		System.out.println("endPouringSpice()");
		machineController.removeLine(machineController.spices);
	}

	@Override
	public long getPouringSpices() {
		return machineController.getPouringSpices();
	}

	@Override
	public boolean isCroutons() {
		return machineController.isOption(Option.CROUTONS);
	}

	@Override
	public void onPouringCroutonsRaised() {
		System.out.println("pouringCroutons()");
		machineController.addLine(machineController.pouringCroutons);
		machineController.removeQuantity(Ingredient.CROUTONS);
	}

	@Override
	public void onEndPouringCroutonsRaised() {
		System.out.println("endPouringCroutons()");
		machineController.removeLine(machineController.pouringCroutons);
	}

	@Override
	public long getNitrogenTime() {
		return machineController.getNitrogenTime();
	}

	@Override
	public boolean isIceTea() {
		return machineController.isDrink(Drink.ICEDTEAD);
	}

	@Override
	public void onNitrogenRaised() {
		System.out.println("nitrogen()");
		machineController.setPictureClosed(true);
		machineController.addLine(machineController.doorClosed);
		machineController.addLine(machineController.nitrogen);
		machineController.removeQuantity(Ingredient.NITROGEN);
	}

	@Override
	public void onEndNitrogenRaised() {
		System.out.println("endNitrogen()");
		machineController.setPictureClosed(false);
		machineController.removeLine(machineController.nitrogen);
		machineController.removeLine(machineController.doorClosed);
		machineController.addLine(machineController.doorOpen);
	}
}
