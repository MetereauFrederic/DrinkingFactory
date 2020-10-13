package fr.univcotedazur.polytech.si4.fsm.project;

public class MachineController {
	
	private DrinkFactoryMachine drinkFactoryMachine;
	private boolean enoughMoney;
	private boolean selectionned;
	private boolean payment;
	
	public MachineController(DrinkFactoryMachine drinkFactoryMachine) {
		this.drinkFactoryMachine = drinkFactoryMachine;
		this.enoughMoney = false;
		this.selectionned = false;
		this.payment = false;
	}

	public void cancel() {
		
	}

	public boolean enoughMoney() {
		return enoughMoney;
	}
	
	public void addSelection() {
		this.selectionned = true;
		if (payment) {

		}
	}
	
	public void pay() {
		this.payment = true;
		if (selectionned) {

		}
	}
	
	public void preparing() {
		drinkFactoryMachine.messagesToUser.setText("Boisson en préparation");
	}

}
