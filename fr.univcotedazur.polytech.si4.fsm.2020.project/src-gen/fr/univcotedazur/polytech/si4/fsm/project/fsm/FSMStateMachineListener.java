package fr.univcotedazur.polytech.si4.fsm.project.fsm;

import fr.univcotedazur.polytech.si4.fsm.project.MachineController;
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
		return machineController.isExpresso();
	}

	@Override
	public boolean isTea() {
		return machineController.isTea();
	}

	@Override
	public void onResetRaised() {
		machineController.reset();
	}

	@Override
	public void onCrushingRaised() {
		System.out.println("crushing()");
		machineController.addLine(machineController.crushing);
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
}
