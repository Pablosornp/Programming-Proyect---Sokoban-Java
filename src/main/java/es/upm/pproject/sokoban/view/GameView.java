package es.upm.pproject.sokoban.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import es.upm.pproject.sokoban.controller.SokobanController;
import es.upm.pproject.sokoban.controller.SokobanElements;
import es.upm.pproject.sokoban.controller.SokobanAction;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameView extends JFrame implements KeyListener {

	private SokobanController controller;

	private JPanel gamePanel;
	private JTextField textFieldGameScore;
	private JTextField textFieldLevelScore;
	private JLabel textFieldLevelName;

	private static final int size = 32;

	/**
	 * Create the application.
	 */
	public GameView(SokobanController controller) {
		this.controller=controller;
		setResizable(false);
		initialize();
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void enableKeyboard() {
		this.toFront();
		this.requestFocus();
	}

	public void setLevelScoreValue(String levelScore) {
		textFieldLevelScore.setText(levelScore);
	}

	public void setGameScoreValue(String gameScore) {
		textFieldGameScore.setText(gameScore);
	}

	public void setLevelName(String levelName) {
		textFieldLevelName.setText(levelName);
	}



	public void drawWarehousePanel(SokobanElements[][] elements) {
		int m = elements.length;
		int n = elements[0].length;

		this.remove(gamePanel);
		this.gamePanel = new JPanel();
		Dimension dim = new Dimension(size*m, size*n);
		this.gamePanel.setPreferredSize(dim);
		this.gamePanel.setLayout(new GridLayout(m,n));
		this.getContentPane().add(gamePanel, BorderLayout.CENTER);

		ImagePanel panel;
		for(int i=0; i<m; i++) {
			for(int j=0; j<n; j++) {
				panel = new ImagePanel(elements[i][j]);
				panel.setSize(size, size);
				this.gamePanel.add(panel);
			}
		}
		this.pack();
	}

	/**
	 * Initialize the frame components.
	 */
	private void initialize() {

		initializeGamePanel();
		initializeScorePanels();
		initializeMenuPanel();
		initializeButtonPanel();

		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}


	private void initializeGamePanel() {
		this.gamePanel = new JPanel();
		this.getContentPane().add(gamePanel, BorderLayout.CENTER);

	}

	private void initializeScorePanels() {

		//InfoPanel
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(0, 1, 0, 0));
		this.getContentPane().add(infoPanel, BorderLayout.NORTH);

		////levelName panel
		JPanel levelNamePanel = new JPanel();
		textFieldLevelName = new JLabel();
		levelNamePanel.add(textFieldLevelName);

		infoPanel.add(levelNamePanel);

		////ScorePanel
		JPanel scorePanel = new JPanel();
		scorePanel.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel gameScore = new JPanel();
		JLabel lblGameScore = new JLabel("Game score");
		lblGameScore.setHorizontalAlignment(SwingConstants.LEFT);
		gameScore.add(lblGameScore);
		textFieldGameScore = new JTextField();
		textFieldGameScore.setEditable(false);
		textFieldGameScore.setColumns(4);
		gameScore.add(textFieldGameScore);
		scorePanel.add(gameScore);

		JPanel levelScore = new JPanel();
		JLabel lblLevelScore = new JLabel("Level score");
		levelScore.add(lblLevelScore);
		textFieldLevelScore = new JTextField();
		textFieldLevelScore.setEditable(false);
		textFieldLevelScore.setColumns(4);
		levelScore.add(textFieldLevelScore);
		scorePanel.add(levelScore);

		infoPanel.add(scorePanel);


	}

	private void initializeMenuPanel() {
		JPanel menuPanel = new JPanel();
		this.getContentPane().add(menuPanel, BorderLayout.EAST);

		JButton btnNewGame = new JButton("Start new game");
		btnNewGame.addActionListener(event -> controller.onRestart());

		JButton btnRestartLevel = new JButton("Restart level");
		btnRestartLevel.addActionListener(event -> controller.onRestart());
		menuPanel.add(btnRestartLevel);

		menuPanel.setLayout(new GridLayout(0, 1, 0, 0));
		menuPanel.add(btnNewGame);
		menuPanel.add(btnRestartLevel);

		JButton btnUndo = new JButton("Undo movement");
		btnUndo.addActionListener(event -> controller.onUndoMove());
		menuPanel.add(btnUndo);

		JButton btnSave = new JButton("Save Game");
		btnSave.addActionListener(event -> controller.onSave());
		menuPanel.add(btnSave);

		JButton btnLoad = new JButton("Load Game");
		btnLoad.addActionListener(event -> controller.onLoad());
		menuPanel.add(btnLoad);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(event -> controller.onExit() );
		menuPanel.add(btnExit);
	}

	private void initializeButtonPanel() {
		JPanel buttonsPanel = new JPanel();
		getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

		JButton buttonUp = new JButton("UP");
		buttonUp.addActionListener(event -> controller.onMove(SokobanAction.UP) );

		buttonsPanel.setLayout(new GridLayout(0, 3, 0, 0));

		JButton blank1 = new JButton("");
		blank1.setEnabled(false);
		buttonsPanel.add(blank1);
		buttonsPanel.add(buttonUp);

		JButton buttonRight = new JButton("RIGHT");
		buttonRight.addActionListener(event -> controller.onMove(SokobanAction.RIGHT) );

		JButton blank2 = new JButton("");
		blank2.setEnabled(false);
		buttonsPanel.add(blank2);

		JButton buttonLeft = new JButton("LEFT");
		buttonLeft.addActionListener(event -> controller.onMove(SokobanAction.LEFT) );
		buttonsPanel.add(buttonLeft);

		JButton buttonDown = new JButton("DOWN");
		buttonDown.addActionListener(event -> controller.onMove(SokobanAction.DOWN) );
		buttonsPanel.add(buttonDown);
		buttonsPanel.add(buttonRight);

		buttonsPanel.setVisible(false);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {

		int key = arg0.getKeyCode();

		if (key == KeyEvent.VK_UP) {
			this.controller.onMove(SokobanAction.UP);
		}
		if (key == KeyEvent.VK_DOWN) {
			this.controller.onMove(SokobanAction.DOWN);
		}
		if (key == KeyEvent.VK_RIGHT) {
			this.controller.onMove(SokobanAction.RIGHT);
		}
		if (key == KeyEvent.VK_LEFT) {
			this.controller.onMove(SokobanAction.LEFT);
		}
		if (key == KeyEvent.VK_U) {
			this.controller.onUndoMove();
		}

		//		KeyStroke.getKeyStroke(
		//		        KeyEvent.VK_Z, ActionEvent.CTRL_MASK)
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}
