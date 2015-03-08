package game;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class HangmanGame extends Applet implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9174458546737792927L;

	private static final int MAX_MISTAKES = 13;
	
	private final Dimension WINDOW_SIZE = new Dimension(350,300);
	
	// UI Elements
	private Button restartButton;
	private Button goButton;
	private Button wordButton;
	private TextField letterTextField;
	private TextField wordTextField;
	private Label guessLabel;
	private Label wordLabel;
	
	private int numErrors;
	private String message;
	private String info;
	private String wordChoice; // AI's word choice
	private StringBuilder wordGuess; // User's word guess
	
	/**
	 * Initializes Applet
	 */
	public void init() {
		
		letterTextField = new TextField(5);
		wordTextField = new TextField(10);
		
		goButton = new Button("Go");
		restartButton = new Button("Restart");
		wordButton = new Button("Guess Word");
		
		guessLabel = new Label("Guess Letter:");
		guessLabel.setBackground(Color.CYAN);
		wordLabel = new Label("Guess Whole Word");
		wordLabel.setBackground(Color.RED);
		
		add(restartButton);
		add(guessLabel);
		add(letterTextField);
		add(goButton);
		add(wordLabel);
		add(wordTextField);
		add(wordButton);
		
		setBackground(Color.CYAN);
		
		goButton.addActionListener(this);
		restartButton.addActionListener(this);
		wordButton.addActionListener(this);
		
		setSize(WINDOW_SIZE);
		
		runGame();
		
	}
	
	/**
	 * Game's Main Interface
	 */
	public void runGame() {
		
		numErrors = 0;
		
		// Got 99 words but "I" ain't one -- hehehe xD
		String str = "computer|radio|calculator|teacher|bureau|police|geometry|president|subject|country|environment|classroom|animals|province|month|politics|puzzle|instrument|kitchen|language|vampire|ghost|solution|service|software|virus|security|phone|expert|website|agreement|support|compatibility|advanced|search|race|immediately|encyclopedia|endurance|distance|nature|history|organization|international|championship|government|popularity|thousand|feature|wetsuit|fitness|legendary|variation|equal|approximately|segment|priority|physics|branche|science|mathematics|lightning|dispersion|accelerator|detector|terminology|design|operation|foundation|application|prediction|reference|measurement|concept|perspective|overview|position|airplane|symmetry|dimension|toxic|algebra|illustration|classic|verification|citation|unusual|resource|analysis|license|comedy|screenplay|production|release|emphasis|director|trademark|vehicle|aircraft|experiment";
		
		String[] wordList;
		String delimiter = "\\|";
		wordList = str.split(delimiter);
		
		Random rand = new Random(System.currentTimeMillis());
		int randInt = rand.nextInt(wordList.length);
		wordChoice = new String(wordList[randInt]);
		
		char[] positions = new char[wordChoice.length()];
		for(int i = 0; i < wordChoice.length(); i++) {
			positions[i] = '-';
		}
		
		String s = new String(positions);
		wordGuess = new StringBuilder(s);
		
		letterTextField.setText("");
		
		info = "";
		message = "";
		
		repaint();
		
	}
	
	/**
	 * Processes user's turn after pressing the "Go" button
	 */
	private void runTurn() {
		
		// temporary storage variables
		String x, y;
		char z;
		
		x = letterTextField.getText();
		z = x.charAt(0);
		
		if(!Character.isLetter(z)) {
			message = "Letters Only!";
			return;
		}
		
		if(x.length() > 1) {
			message = "Only one letter, please!";
			return;
		}
		
		y = new String(wordGuess);
		if(y.indexOf(x) != -1) {
			message = "Letter already guessed!";
			return;
		}
		
		if(wordChoice.indexOf(x) == -1) {
			message = "";
			numErrors++;
			if(numErrors == MAX_MISTAKES) {
				message = "You Lose! The word was " + wordChoice + "!";
				info = "Click Restart to play again!";
			}
			return;
		}
		
		for(int i = 0; i < wordChoice.length(); i++) {
			if (wordChoice.charAt(i) == z) {
				wordGuess.setCharAt(i, z);
			}
		}
		y = new String(wordGuess);
		
		if(y.indexOf('-') == -1) {
			message = "You Win!";
			return;
		}
		
		message = "";
		
		repaint();
		
	}
	
	/**
	 * Processes user's turn after pressing the "Guess Word" button
	 */
	public void runTurnWord() {
		
		String temp, y;
		
		temp = wordTextField.getText();
		
		if(wordChoice.indexOf(temp) == -1) {
			message = "";
			numErrors++;
			if(numErrors == MAX_MISTAKES) {
				message = "You Lose! The word was " + wordChoice + "!";
				info = "Click Restart to play again!";
			}
			return;
		}
		
		char[] tempLetters = new char[temp.length()];
		for(int i = 0; i < temp.length(); i++) {
			tempLetters[i] = temp.charAt(i);
		}
		
		if(!(wordChoice.indexOf(temp) == -1)) {
			for(int i = 0; i < wordChoice.length(); i++) {
				wordGuess.setCharAt(i, tempLetters[i]);
			}
		}
		
		y = new String(wordGuess);
		if(y.indexOf('-') == -1) {
			message = "You Win!";
			return;
		}
		
		message = "";
		
		repaint();
		
	}
	
	/**
	 * Paints hangman graphics to the Applet for each error
	 * @param graphic - draws the specified part of the hangman
	 */
	public void paint(Graphics graphic) {
		
		int baseYVal = 250;
		
		if(numErrors > 0) {
			graphic.drawLine(90, baseYVal, 125, baseYVal);
		}
		if(numErrors > 1) {
			graphic.drawLine(125, baseYVal, 125, baseYVal - 100);
		}
		if(numErrors > 2) {
			graphic.drawLine(110, baseYVal, 125, baseYVal-15);
		}
		if(numErrors > 3) {
			graphic.drawLine(140, baseYVal, 125, baseYVal-15);
		}
		if(numErrors > 4) {
			graphic.drawLine(125, baseYVal-10, 175, baseYVal-100);
		}
		if(numErrors > 5) {
			 graphic.drawLine(125, baseYVal-85, 140, baseYVal-100);
		}
		if(numErrors > 6) { 
			graphic.drawLine(175, baseYVal-100, 175, baseYVal-75);
		}
		if(numErrors > 7) {
			graphic.drawOval(170, baseYVal-75, 10, 12);
		}
		if(numErrors > 8) {
			graphic.drawOval(170, baseYVal-65, 15, 25);
		}
		if(numErrors > 9) {
			graphic.drawLine(160, baseYVal-65, 170, baseYVal-60);
		}
		if(numErrors > 10) {
			graphic.drawLine(183,baseYVal-60,193,baseYVal-65);
		}
		if(numErrors > 11) {
			graphic.drawLine(165, baseYVal-30, 170, baseYVal-45);
		}
		if(numErrors > 12) {
			graphic.drawLine(183, baseYVal-45, 193, baseYVal-30);
		}
		
		graphic.drawString(message, 40, baseYVal+25 );
		graphic.drawString(info, 25, baseYVal+45 );
		graphic.drawString(new String(wordGuess), 135, 90);
		
	}
	
	/**
	 * Action Listers for goButton and restartButton
	 * @param event - gets ActionEvent source
	 */
	public void actionPerformed(ActionEvent event) {
		
		if(event.getSource() == goButton) {
			runTurn();
			
			letterTextField.setText("");
			repaint();
		}
		
		if(event.getSource() == restartButton) {
			runGame();
		}
		
		if(event.getSource() == wordButton) {
			runTurnWord();
			
			wordTextField.setText("");
			repaint();
		}
		
	}

}
