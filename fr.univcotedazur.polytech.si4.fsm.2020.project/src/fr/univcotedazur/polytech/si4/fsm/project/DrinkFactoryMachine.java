package fr.univcotedazur.polytech.si4.fsm.project;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.univcotedazur.polytech.si4.fsm.project.Drink;
import fr.univcotedazur.polytech.si4.fsm.project.Drink.Option;
import fr.univcotedazur.polytech.si4.fsm.project.fsm.FSMStateMachineListener;
import fr.univcotedazur.polytech.si4.fsm.project.fsm.FSMStatemachine;

public class DrinkFactoryMachine extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2030629304432075314L;
	private JPanel contentPane;
	/**
	 * @wbp.nonvisual location=311,475
	 */
	
	//private final ImageIcon imageIcon = new ImageIcon();
	
	JLabel messagesToUser, labelForPictures, lblSugar, lblSize;
	JProgressBar progressBar;
	JButton coffeeButton, expressoButton, teaButton, soupButton, icedTeaButton;
	JButton money50centsButton, money25centsButton, money10centsButton, nfcBiiiipButton;
	JButton cancelButton;
	JButton addCupButton;
	JSlider sugarSlider, sizeSlider, temperatureSlider;
	JButton milkCloud, croutons, mapleSyrup, vanilla;
	JTextField nfcBiiiipId;
	private MachineController machineController;
	private FSMStatemachine fsm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DrinkFactoryMachine frame = new DrinkFactoryMachine();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DrinkFactoryMachine() {
		
		setForeground(Color.WHITE);
		setFont(new Font("Cantarell", Font.BOLD, 22));
		setBackground(Color.DARK_GRAY);
		setTitle("Drinking Factory Machine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 665, 760);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
//		JPanel panel_message = new JPanel();
//		panel_message.setBackground(Color.GRAY);
//		panel_message.setBounds(126, 34, 165, 175);
//		contentPane.add(panel_message);

		messagesToUser = new JLabel("<html>Veuillez sélectionner votre boisson !</html>");
		messagesToUser.setForeground(Color.WHITE);
		messagesToUser.setHorizontalAlignment(SwingConstants.LEFT);
		messagesToUser.setVerticalAlignment(SwingConstants.TOP);
		messagesToUser.setToolTipText("message to the user");
		messagesToUser.setBounds(126, 34, 165, 300);
		contentPane.add(messagesToUser);

		JLabel lblCoins = new JLabel("Coins");
		lblCoins.setForeground(Color.WHITE);
		lblCoins.setHorizontalAlignment(SwingConstants.CENTER);
		lblCoins.setBounds(538, 12, 44, 15);
		contentPane.add(lblCoins);

		coffeeButton = new JButton("Coffee");
		coffeeButton.setForeground(Color.WHITE);
		coffeeButton.setBackground(Color.DARK_GRAY);
		coffeeButton.setBounds(12, 34, 96, 25);
		contentPane.add(coffeeButton);

		expressoButton = new JButton("Expresso");
		expressoButton.setForeground(Color.WHITE);
		expressoButton.setBackground(Color.DARK_GRAY);
		expressoButton.setBounds(12, 71, 96, 25);
		contentPane.add(expressoButton);

		teaButton = new JButton("Tea");
		teaButton.setForeground(Color.WHITE);
		teaButton.setBackground(Color.DARK_GRAY);
		teaButton.setBounds(12, 108, 96, 25);
		contentPane.add(teaButton);

		soupButton = new JButton("Soup");
		soupButton.setForeground(Color.WHITE);
		soupButton.setBackground(Color.DARK_GRAY);
		soupButton.setBounds(12, 145, 96, 25);
		contentPane.add(soupButton);

		sugarSlider = new JSlider();
		sugarSlider.setPaintLabels(true);
		sugarSlider.setValue(1);
		sugarSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sugarSlider.setBackground(Color.DARK_GRAY);
		sugarSlider.setForeground(Color.WHITE);
		sugarSlider.setPaintTicks(true);
		sugarSlider.setMinorTickSpacing(1);
		sugarSlider.setMajorTickSpacing(1);
		sugarSlider.setMaximum(4);
		sugarSlider.setBounds(301, 51, 200, 54);
		
		Hashtable<Integer, JLabel> sugarTable = new Hashtable<Integer, JLabel>();
		sugarTable.put(0, new JLabel("0"));
		sugarTable.put(4, new JLabel("Max"));
		for (JLabel l : sugarTable.values()) {
			l.setForeground(Color.WHITE);
		}
		sugarSlider.setLabelTable(sugarTable);
		
		contentPane.add(sugarSlider);

		sizeSlider = new JSlider();
		sizeSlider.setPaintLabels(true);
		sizeSlider.setPaintTicks(true);
		sizeSlider.setValue(1);
		sizeSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sizeSlider.setBackground(Color.DARK_GRAY);
		sizeSlider.setForeground(Color.WHITE);
		sizeSlider.setMinorTickSpacing(1);
		sizeSlider.setMaximum(2);
		sizeSlider.setMajorTickSpacing(1);
		sizeSlider.setBounds(301, sugarSlider.getY() + 110, 200, 54);
		
		Hashtable<Integer, JLabel> sizeTable = new Hashtable<Integer, JLabel>();
		sizeTable.put(0, new JLabel("S"));
		sizeTable.put(1, new JLabel("M"));
		sizeTable.put(2, new JLabel("L"));
		for (JLabel l : sizeTable.values()) {
			l.setForeground(Color.WHITE);
		}
		sizeSlider.setLabelTable(sizeTable);
		
		contentPane.add(sizeSlider);

		temperatureSlider = new JSlider();
		temperatureSlider.setPaintLabels(true);
		temperatureSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		temperatureSlider.setValue(2);
		temperatureSlider.setBackground(Color.DARK_GRAY);
		temperatureSlider.setForeground(Color.WHITE);
		temperatureSlider.setPaintTicks(true);
		temperatureSlider.setMajorTickSpacing(1);
		temperatureSlider.setMaximum(3);
		temperatureSlider.setBounds(301, sizeSlider.getY() + 110, 200, 54);

		Hashtable<Integer, JLabel> temperatureTable = new Hashtable<Integer, JLabel>();
		temperatureTable.put(0, new JLabel("20°C"));
		temperatureTable.put(1, new JLabel("35°C"));
		temperatureTable.put(2, new JLabel("60°C"));
		temperatureTable.put(3, new JLabel("85°C"));
		for (JLabel l : temperatureTable.values()) {
			l.setForeground(Color.WHITE);
		}
		temperatureSlider.setLabelTable(temperatureTable);

		contentPane.add(temperatureSlider);

		icedTeaButton = new JButton("Iced Tea");
		icedTeaButton.setForeground(Color.WHITE);
		icedTeaButton.setBackground(Color.DARK_GRAY);
		icedTeaButton.setBounds(12, 182, 96, 25);
		contentPane.add(icedTeaButton);
		
		JLabel lblOptions = new JLabel("Options");
		lblOptions.setForeground(Color.WHITE);
		lblOptions.setHorizontalAlignment(SwingConstants.CENTER);
		lblOptions.setBounds(12, icedTeaButton.getY() + 40, 44, 15);
		contentPane.add(lblOptions);
		
		milkCloud = new JButton("Milk Cloud");
		milkCloud.setForeground(Color.WHITE);
		milkCloud.setBackground(Color.DARK_GRAY);
		milkCloud.setBounds(12, lblOptions.getY() + 20, 110, 20);
		milkCloud.setEnabled(false);
		contentPane.add(milkCloud);
		
		croutons = new JButton("Croutons");
		croutons.setForeground(Color.WHITE);
		croutons.setBackground(Color.DARK_GRAY);
		croutons.setBounds(12, milkCloud.getY() + 30, 110, 20);
		croutons.setEnabled(false);
		contentPane.add(croutons);
		
		mapleSyrup = new JButton("Maple Syrup");
		mapleSyrup.setForeground(Color.WHITE);
		mapleSyrup.setBackground(Color.DARK_GRAY);
		mapleSyrup.setBounds(12, croutons.getY() + 30, 110, 20);
		mapleSyrup.setEnabled(false);
		contentPane.add(mapleSyrup);
		
		vanilla = new JButton("Vanilla");
		vanilla.setForeground(Color.WHITE);
		vanilla.setBackground(Color.DARK_GRAY);
		vanilla.setBounds(12, mapleSyrup.getY() + 30, 110, 20);
		vanilla.setEnabled(false);
		contentPane.add(vanilla);
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setValue(0);
		progressBar.setForeground(Color.LIGHT_GRAY);
		progressBar.setBackground(Color.DARK_GRAY);
		progressBar.setBounds(12, vanilla.getY() + 40, 622, 26);
		contentPane.add(progressBar);

		lblSugar = new JLabel("Sugar");
		lblSugar.setForeground(Color.WHITE);
		lblSugar.setBackground(Color.DARK_GRAY);
		lblSugar.setHorizontalAlignment(SwingConstants.CENTER);
		lblSugar.setBounds(350, sugarSlider.getY() - 25, 100, 15);
		contentPane.add(lblSugar);

		lblSize = new JLabel("Size");
		lblSize.setForeground(Color.WHITE);
		lblSize.setBackground(Color.DARK_GRAY);
		lblSize.setHorizontalAlignment(SwingConstants.CENTER);
		lblSize.setBounds(350, sizeSlider.getY() - 25, 100, 15);
		contentPane.add(lblSize);

		JLabel lblTemperature = new JLabel("Temperature");
		lblTemperature.setForeground(Color.WHITE);
		lblTemperature.setBackground(Color.DARK_GRAY);
		lblTemperature.setHorizontalAlignment(SwingConstants.CENTER);
		lblTemperature.setBounds(350, temperatureSlider.getY() - 25, 100, 15);
		contentPane.add(lblTemperature);

		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		lblCoins.setLabelFor(panel);
		panel.setBounds(538, 25, 96, 97);
		contentPane.add(panel);

		money50centsButton = new JButton("0.50 €");
		money50centsButton.setForeground(Color.WHITE);
		money50centsButton.setBackground(Color.DARK_GRAY);
		panel.add(money50centsButton);

		money25centsButton = new JButton("0.25 €");
		money25centsButton.setForeground(Color.WHITE);
		money25centsButton.setBackground(Color.DARK_GRAY);
		panel.add(money25centsButton);

		money10centsButton = new JButton("0.10 €");
		money10centsButton.setForeground(Color.WHITE);
		money10centsButton.setBackground(Color.DARK_GRAY);
		panel.add(money10centsButton);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		panel_1.setBounds(538, 154, 100, 60);
		contentPane.add(panel_1);

		nfcBiiiipButton = new JButton("biiip");
		nfcBiiiipButton.setForeground(Color.WHITE);
		nfcBiiiipButton.setBackground(Color.DARK_GRAY);
		panel_1.add(nfcBiiiipButton);
		nfcBiiiipId = new JTextField("0");
		nfcBiiiipId.setColumns(8);
		nfcBiiiipId.setForeground(Color.WHITE);
		nfcBiiiipId.setBackground(Color.DARK_GRAY);
		panel_1.add(nfcBiiiipId);

		JLabel lblNfc = new JLabel("NFC");
		lblNfc.setForeground(Color.WHITE);
		lblNfc.setHorizontalAlignment(SwingConstants.CENTER);
		lblNfc.setBounds(541, 139, 41, 15);
		contentPane.add(lblNfc);

		JSeparator separator = new JSeparator();
		separator.setBounds(12, progressBar.getY() + 36, 622, 15);
		contentPane.add(separator);

		addCupButton = new JButton("Add cup");
		addCupButton.setForeground(Color.WHITE);
		addCupButton.setBackground(Color.DARK_GRAY);
		addCupButton.setBounds(45, separator.getY() + 44, 106, 25);
		contentPane.add(addCupButton);

		labelForPictures = new JLabel();
		labelForPictures.setBounds(175, separator.getY() + 27, 286, 260);
		contentPane.add(labelForPictures);
		changePicture("./picts/vide2.jpg");
		labelForPictures.addMouseListener(new MouseAdapter() {
			
			 public void mouseClicked(MouseEvent e)  
			    {
			      	fsm.getSCInterface().raiseCup_Taken();
			    }  
			
		});

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.DARK_GRAY);
		panel_2.setBounds(538, progressBar.getY() - 50, 96, 33);
		contentPane.add(panel_2);

		cancelButton = new JButton("Cancel");
		cancelButton.setForeground(Color.WHITE);
		cancelButton.setBackground(Color.DARK_GRAY);
		panel_2.add(cancelButton);

		// listeners
		addCupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fsm.getSCInterface().raiseB_Cup();
			}
		});
		
		coffeeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				machineController.addSelection(Drink.COFFEE, coffeeButton);
				fsm.getSCInterface().raiseB_drink();
			}
		});
		
		expressoButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				machineController.addSelection(Drink.EXPRESSO, expressoButton);
				fsm.getSCInterface().raiseB_drink();
			}
		});
		
		teaButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				machineController.addSelection(Drink.TEA, teaButton);
				fsm.getSCInterface().raiseB_drink();
			}
		});
		
		soupButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				machineController.addSelection(Drink.SOUP, soupButton);
				fsm.getSCInterface().raiseB_drink();
			}
		});
		
		icedTeaButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				machineController.addSelection(Drink.ICEDTEAD, icedTeaButton);
				fsm.getSCInterface().raiseB_drink();
			}
		});
		
		milkCloud.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				machineController.addOption(Option.MILKCLOUD, milkCloud);
				fsm.getSCInterface().raiseB_option();
			}
		});
		
		croutons.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				machineController.addOption(Option.CROUTONS, croutons);
				fsm.getSCInterface().raiseB_option();
			}
		});
		
		mapleSyrup.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				machineController.addOption(Option.MAPLESYRUP, mapleSyrup);
				fsm.getSCInterface().raiseB_option();
			}
		});
		
		vanilla.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				machineController.addOption(Option.VANILLA, vanilla);
				fsm.getSCInterface().raiseB_option();
			}
		});
		
		money50centsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				machineController.addCoin(50);
				fsm.getSCInterface().raiseC_coin();
			}
		});
		
		money25centsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				machineController.addCoin(25);
				fsm.getSCInterface().raiseC_coin();
			}
		});
		
		money10centsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				machineController.addCoin(10);
				fsm.getSCInterface().raiseC_coin();
			}
		});
		
		nfcBiiiipButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				messagesToUser.setText("<html>" + machineController + "</html>");
				machineController.nfcPayed();
				fsm.getSCInterface().raiseC_NFC();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fsm.getSCInterface().raiseB_Cancel();
			}
		});
		
		sugarSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				//messagesToUser.setText("<html>" + machineController + "</html>");
				fsm.getSCInterface().raiseS_Slide();
			}
		});
		
		sizeSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				//messagesToUser.setText("<html>" + machineController + "</html>");
				machineController.newPrice();
				fsm.getSCInterface().raiseS_Slide();
			}
		});
		
		temperatureSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				//messagesToUser.setText("<html>" + machineController + "</html>");
				fsm.getSCInterface().raiseS_Slide();
			}
		});
		
		
		machineController = new MachineController(this);
		FSMStateMachineListener fsmListener = new FSMStateMachineListener(machineController);
		fsm = new FSMStatemachine();
		fsm.setTimer(new TimerService());
		fsm.getSCInterface().setSCInterfaceOperationCallback(fsmListener);
		fsm.init();
		fsm.enter();
		fsm.getSCInterface().getListeners().add(fsmListener);
		machineController.resetUi();
	}
	
	public void changePicture(String picturePath) {
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File(picturePath));
		} catch (IOException ee) {
			ee.printStackTrace();
		}
		labelForPictures.setIcon(new ImageIcon(myPicture));
	}
	
	public void changeSugar(String s) {
		this.lblSugar.setText(s);
	}
	
	public void changeSize(String s) {
		this.lblSize.setText(s);
	}
	
	public void badNfcInfut() {
		this.nfcBiiiipId.setText("integer only");
	}
}
