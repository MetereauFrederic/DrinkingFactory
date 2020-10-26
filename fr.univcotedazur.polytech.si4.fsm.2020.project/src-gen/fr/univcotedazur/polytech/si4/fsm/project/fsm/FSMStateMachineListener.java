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
	public void onOnCancelRaised() {
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
	public long getPouringShugar() {
		return machineController.getPouringShugar();
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
	public void onOnResetRaised() {
		machineController.reset();
	}

	@Override
	public void onOnCrushingRaised() {
		machineController.crushing();
	}

	@Override
	public void onOnTampingRaised() {
		machineController.tamping();
	}

	@Override
	public void onOnHeatingRaised() {
		machineController.heating();
	}

	@Override
	public void onOnPlacingCupRaised() {
		machineController.placingCup();
	}

	@Override
	public void onOnPouringWaterRaised() {
		machineController.pourringWater();
	}

	@Override
	public void onOnPouringShugarRaised() {
		machineController.pourringSugar();
	}

	@Override
	public void onOnInfusingRaised() {
		machineController.infusing();
	}

	@Override
	public void onOnRemovingBagRaised() {
		machineController.removingBag();
	}

	@Override
	public void onOnPuttingTeaRaised() {
		machineController.puttingTea();
	}

	@Override
	public void onDoNothingRaised() {
		System.out.println("nothing");
	}
}
