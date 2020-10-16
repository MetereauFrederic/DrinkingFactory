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
	public double newPrice() {
		return machineController.newPrice();
	}

	@Override
	public double currentMoney() {
		return machineController.currentMoney();
	}
}
