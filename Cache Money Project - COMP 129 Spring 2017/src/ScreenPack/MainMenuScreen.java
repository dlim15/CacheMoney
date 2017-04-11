package ScreenPack;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import GamePack.PathRelated;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.io.IOException;

import javax.swing.*;

import com.sun.java.swing.plaf.windows.resources.windows;

import java.util.Timer;
import java.util.TimerTask;

import GamePack.ImageRelated;
import GamePack.SizeRelated;
import InterfacePack.BackgroundImage;
import InterfacePack.Sounds;
import MultiplayerPack.*;
import sun.util.resources.cldr.mr.TimeZoneNames_mr;

public class MainMenuScreen {
	private Font mainfont;
	private JPanel mainPanel;
	private JFrame mainmenuframe;
	private JButton SinglePButton;
	private JButton MultiPButton;
	private JLabel HelloThere;
	private JButton ExitButton;
	private JButton MiniGamesButton;
	private Integer[] numPlayer = {2,3,4};
	private JComboBox cmbNumP;
	private Object[] messages;
	private LoadingScreen loadingScreen;
	private GameScreen gameScreen;
	private PathRelated pathRelated;
	private JLabel backgroundpic;
	private JButton loginBtn;
	private PlayingInfo playingInfo;

	public MainMenuScreen(){
		
		init();
		createMenuWindow();
		Sounds.register.playSound();
		addMouseListen();


	}

	private void initializeLoadingScreen(){
		if (loadingScreen == null){
			GraphicsDevice screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			loadingScreen = new LoadingScreen(screenSize.getDisplayMode().getWidth() / 2, screenSize.getDisplayMode().getHeight() / 2);
		}
	}

	private void hideAndDisposeLoadingScreen(){
		loadingScreen.setVisible(false);
		loadingScreen.dispose();
	}

	private void init(){
		
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setOpaque(false);

		scaleBoardToScreenSize();
		mainfont = new Font("Serif", Font.PLAIN, 18);
		mainmenuframe = new JFrame("Main Menu");
		MultiPButton = new JButton("Multiplayer");
		MultiPButton.setEnabled(false);
		SinglePButton = new JButton("Single Player");
		HelloThere = new JLabel("Cache Money", SwingConstants.CENTER);
		ExitButton = new JButton("Exit Game");
		MiniGamesButton = new JButton("Play Minigames");
		cmbNumP = new JComboBox(numPlayer);
		cmbNumP.setSelectedIndex(0);
		messages = new Object[2];
		messages[0] = "How many players would you like to play with:";
		messages[1] = cmbNumP;
		initializeLoadingScreen();

		loginBtn = new JButton("Login");
		playingInfo = PlayingInfo.getInstance();
		disableEnableBtns(playingInfo.isLoggedIn());
		Runtime.getRuntime().addShutdownHook(new Thread(){
			public void run(){
				exitAction();
			}
		});

	}
	private void scaleBoardToScreenSize() {
		GraphicsDevice screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		SizeRelated sizeRelated = SizeRelated.getInstance();
		sizeRelated.setScreen_Width_Height((int)screenSize.getDisplayMode().getWidth(), (int)screenSize.getDisplayMode().getHeight());
	}

	private void addMouseListen(){
		SinglePButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e){

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				Sounds.buttonConfirm.playSound();
				(new startSinglePlayer()).start();
			}

			class startSinglePlayer extends Thread{
				@Override
				public void run(){
					startSinglePlayer();
				}
			}

		});
		MultiPButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e){

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if(MultiPButton.isEnabled()){
					Sounds.buttonConfirm.playSound();
					(new beginMultiplayer()).start();
				}
				
			}

			class beginMultiplayer extends Thread{
				@Override
				public void run(){
//					AskUserMultiplayerDialogBox mwr = new AskUserMultiplayerDialogBox();
//					displayHostOrClientDialogBox(mwr);
					setupClient();
				}
			}

		});
		MiniGamesButton.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e){

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				Sounds.buttonConfirm.playSound();
				(new playMinigames()).start();
			}

			class playMinigames extends Thread{
				@Override
				public void run(){
					hideAndDisposeMainMenuScreen();
					loadingScreen.setVisible(true);
					new MiniGamePractice();
					hideAndDisposeLoadingScreen();
					Sounds.waitingRoomJoin.playSound();
				}
			}


		});
		ExitButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e){
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				Timer t = new Timer();
				t.schedule(new TimerTask(){

					@Override
					public void run() {
						System.exit(0);
					}

				}, 1500);
				Sounds.landedOnJail.playSound();
				hideAndDisposeMainMenuScreen();
			}
		});
		loginBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e){
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if(loginBtn.isEnabled()){
					LoginDialog loginDialog = new LoginDialog(mainmenuframe);
					loginDialog.setVisible(true);
					if(loginDialog.isSucceeded()){
						disableEnableBtns(true);
					}
				}
			}
		});
	}

	private void disableEnableBtns(boolean isLoggedIn){
		if(isLoggedIn)
			loginBtn.setText("Hi " + playingInfo.getLoggedInId() + "!");
		loginBtn.setEnabled(!isLoggedIn);
		MultiPButton.setEnabled(isLoggedIn);
	}
	public void createMenuWindow(){
		setMenuBackgroundColor();
		mainmenuframe.setSize(500,500);
		pathRelated = PathRelated.getInstance();
		//		backgroundpic = new JLabel(new ImageIcon(pathRelated.getImagePath() + "background.jpg"));
		//		System.out.println(mainmenuframe.getWidth() + " : " + mainmenuframe.getHeight());
		//		backgroundpic.setBounds(0, 0, mainmenuframe.getWidth(), mainmenuframe.getHeight());
		//		mainPanel.add(backgroundpic);

		mainmenuframe.setResizable(false);
		addActionListenerForExit();
		mainmenuframe.setVisible(true);



		HelloThere.setFont(new Font("Serif", Font.PLAIN, 30));
		HelloThere.setBounds(100,50,300,50);
		mainPanel.add(HelloThere);
		SinglePButton.setFont(mainfont);
		SinglePButton.setBounds(175,125,150,50);
		mainPanel.add(SinglePButton);
		MultiPButton.setFont(mainfont);
		MultiPButton.setBounds(175,185,150,50);
		mainPanel.add(MultiPButton);
		MiniGamesButton.setFont(mainfont);
		MiniGamesButton.setBounds(175,245,150,50);
		mainPanel.add(MiniGamesButton);
		ExitButton.setFont(mainfont);
		loginBtn.setBounds(175,305,150,50);
		loginBtn.setFont(mainfont);
		mainPanel.add(loginBtn);
		ExitButton.setBounds(175,365,150,50);
		mainPanel.add(ExitButton);
		mainmenuframe.add(mainPanel);
		mainPanel.add(new BackgroundImage(pathRelated.getImagePath() + "background.jpg", mainmenuframe.getWidth(), mainmenuframe.getHeight()));
		//		mainPanel.setComponentZOrder(backgroundpic, 0);
		//		mainPanel.setComponentZOrder(HelloThere, 1);
		//
		//		mainPanel.setComponentZOrder(SinglePButton, 1);
		//
		//		mainPanel.setComponentZOrder(MultiPButton, 1);
		//
		//		mainPanel.setComponentZOrder(MiniGamesButton, 1);
		//
		//		mainPanel.setComponentZOrder(ExitButton, 1);

		mainPanel.repaint();
		mainPanel.revalidate();
		mainmenuframe.repaint();
		mainmenuframe.revalidate();
	}

	private void setMenuBackgroundColor() {
		Color menuBackgroundColor = new Color(105, 177, 255); // LIGHT BLUE
		//		mainPanel.setBackground(menuBackgroundColor);
	}

	private void startSinglePlayer() {
		int gNumP;
		getNumPlayers();
		Sounds.buttonConfirm.playSound();
		gNumP = (Integer)cmbNumP.getSelectedItem();
		hideAndDisposeMainMenuScreen();
		loadingScreen.setResizable(false);
		loadingScreen.setVisible(true);
		gameScreen = new GameScreen(true, gNumP);
		gameScreen.setNumPlayer(gNumP);
		hideAndDisposeLoadingScreen();
		Sounds.buttonCancel.playSound();
	}
	private boolean getNumPlayers(){
		int res = JOptionPane.showConfirmDialog(null, messages,"Enter the number of total players:", JOptionPane.YES_NO_OPTION);
		return res == JOptionPane.YES_OPTION ? true : false;
	}
	private void hideAndDisposeMainMenuScreen() {
		mainmenuframe.setVisible(false);
		mainmenuframe.dispose();
	}


	private void displayHostOrClientDialogBox(AskUserMultiplayerDialogBox mwr) {
		switch (mwr.askUserHostOrClient()){
		case 0:
			hideAndDisposeMainMenuScreen();
			loadingScreen.setVisible(true);
			gameScreen = new GameScreen(false, 4);
			hideAndDisposeLoadingScreen();
			break;
		case 1:
			setupClient();
			break;
		case 2:
			// USER CLOSED THE DIALOG WINDOW. DO NOTHING HERE.
			break;
		default:
			System.out.println("***** THERE'S SOMETHING WRONG INSIDE OF GAMEBUTTON MOUSE CLICKED ASKING USER HOST/CLIENT");
			break;
		}
	}
	private void addActionListenerForExit(){
		mainmenuframe.addWindowListener( new WindowAdapter() {
			@Override
            public void windowClosing(java.awt.event.WindowEvent e) {
            	super.windowClosing(e);
            	mainmenuframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        } );
	}
	private void exitAction(){
		if(playingInfo.isLoggedIn())
			SqlRelated.loginAndOutAction(playingInfo.getLoggedInId(), false);
	}

	private void setupClient(){
		gameScreen = null;
		mainmenuframe.setVisible(true);
		try {
			mainmenuframe.setVisible(false);
			loadingScreen.setVisible(true);
			gameScreen = new GameScreen(false);
			loadingScreen.setVisible(false);
			hideAndDisposeMainMenuScreen();
			hideAndDisposeLoadingScreen();
		} catch (IOException e) {
			loadingScreen.setVisible(false);
		}
	}
}

