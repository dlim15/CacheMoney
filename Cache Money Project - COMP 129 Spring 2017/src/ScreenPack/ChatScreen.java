package ScreenPack;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import GamePack.SizeRelated;
import InterfacePack.Sounds;
import MultiplayerPack.MBytePack;
import MultiplayerPack.PlayingInfo;
	public class ChatScreen extends JPanel{
		private final static int SCREEN_WIDTH = 350;
		private final static int SCREEN_HEIGHT = 400;
		private final static int MSGTYPEAREA_HEIGHT = 80;
		private final static int BTN_WIDTH = 80;
		private final static int TITLE_HEIGHT = 40;
		
		private boolean isHide;
		private JTextArea msgDisplayArea, msgTypeArea;
		private JButton btnSend;
		private PrintWriter writer;
		private PlayingInfo playingInfo;
		private MBytePack mPack;
		private int MSG_TYPE;
		private SizeRelated sizeRelated;
		private JScrollPane displayPane;
		private JScrollPane typePane;
		private JComboBox<String> chatSelector;
		private JButton titleBar;
		private LayoutManager gbl;
		private boolean isChatAbled;
		private String msg;
		public ChatScreen(int msgType){
			setting();
			MSG_TYPE = msgType;
			showWelcomeMsg();
		}
		private void setting(){
			init();
//			playingInfo.sendMessageToServer(msg);
		}
		private void init(){

			setLayout(new GridBagLayout());
			
			sizeRelated = SizeRelated.getInstance();
			playingInfo = PlayingInfo.getInstance();
			isChatAbled = true;
			mPack = MBytePack.getInstance();
			titleBar = new JButton("Chatting");
			btnSend = new JButton("Send");
			msgDisplayArea = new JTextArea(10,20);
			msgDisplayArea.setEditable(false);
			msgTypeArea = new JTextArea(2,20);
			msgDisplayArea.setFont(new Font("Serif",Font.BOLD,15));
			msgTypeArea.setFont(new Font("Serif",Font.BOLD,15));
			displayPane = new JScrollPane(msgDisplayArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			typePane = new JScrollPane(msgTypeArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			initChatSelector();
			
			setBounds(sizeRelated.getScreenW()-SCREEN_WIDTH, sizeRelated.getScreenH()-SCREEN_HEIGHT - 200,SCREEN_WIDTH, SCREEN_HEIGHT+200);
			typePane.setBounds(0,SCREEN_HEIGHT - MSGTYPEAREA_HEIGHT + TITLE_HEIGHT, SCREEN_WIDTH-BTN_WIDTH, MSGTYPEAREA_HEIGHT);
			btnSend.setBounds(SCREEN_WIDTH-BTN_WIDTH, SCREEN_HEIGHT - MSGTYPEAREA_HEIGHT + TITLE_HEIGHT, BTN_WIDTH,MSGTYPEAREA_HEIGHT);
			displayPane.setBounds(0, TITLE_HEIGHT, SCREEN_WIDTH, SCREEN_HEIGHT - MSGTYPEAREA_HEIGHT);
			titleBar.setBounds(0,0,SCREEN_WIDTH,TITLE_HEIGHT);
			
			GridBagConstraints gbc = new GridBagConstraints();
			Insets i = new Insets(5,5,5,5);
			
			gbc.fill = GridBagConstraints.BOTH;
			gbc.insets = i;
			
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.gridwidth = 4;
			gbc.gridheight = 2;
			add(titleBar,gbc);
			
			gbc.gridy = 3;
			gbc.gridheight = 3;
			gbc.gridwidth = 4;
			gbc.weightx = 2;
			gbc.weighty = 2;
			add(displayPane,gbc);
			
			gbc.gridy = 6;
			gbc.gridheight = 1;
			gbc.gridwidth = 4;
			gbc.weightx = 1;
			gbc.weighty = 1;
			add(chatSelector,gbc);
			
			gbc.gridy = 7;
			gbc.gridheight = 1;
			gbc.gridwidth = 2;
			add(typePane,gbc);
			
			gbc.gridx = 2;
			gbc.gridwidth = 2;
			add(btnSend,gbc);
			
			gbl = this.getLayout();
			
			addListeners();
			msgDisplayArea.setLineWrap(true);
			msgTypeArea.setLineWrap(true);
			
		}
		
		private void initChatSelector(){
			chatSelector = new JComboBox<String>();
			chatSelector.addItem("All Users");
		}
		
		private void addListeners(){
			btnSend.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					if(isChatAbled){
						msg = msgTypeArea.getText();
						sendMsg();
						msgTypeArea.setText("");
					}
				}
				@Override
				public void mouseEntered(MouseEvent arg0) {
					
				}
				@Override
				public void mouseExited(MouseEvent arg0) {
					
				}
				@Override
				public void mousePressed(MouseEvent arg0) {
					
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					
				}
			});
			titleBar.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					Sounds.quickDisplay.playSound();
					minimizeAndMaximize();
				}
				@Override
				public void mouseEntered(MouseEvent arg0) {
					
				}
				@Override
				public void mouseExited(MouseEvent arg0) {
					
				}
				@Override
				public void mousePressed(MouseEvent arg0) {
					
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					
				}
			});
			msgTypeArea.addKeyListener(new KeyListener() {
				@Override
				public void keyPressed(KeyEvent e) {
					if(isChatAbled && e.getKeyCode() == KeyEvent.VK_ENTER){
						msg = msgTypeArea.getText();
						if(msg.length() > 0 && msg.charAt(0) != '\n'){
							sendMsg();
						}
						msgTypeArea.setText("");
						
					}
				}
				@Override
				public void keyReleased(KeyEvent e) {
					if(isChatAbled && e.getKeyCode() == KeyEvent.VK_ENTER){
						msgTypeArea.setText("");
					}
				}
				@Override
				public void keyTyped(KeyEvent e) {
//					if(isChatAbled && e.getKeyCode() == KeyEvent.VK_ENTER){
//						sendMsg();
//						msgTypeArea.setText("");
//					}
						
				}
			});
		}
		private void showWelcomeMsg(){
			msgDisplayArea.setText("Welcome to Cache Chat!\n\n------------------------------------------------\n\n");
		}
		private void minimizeAndMaximize(){
			displayPane.setVisible(isHide);
			typePane.setVisible(isHide);
			btnSend.setVisible(isHide);
			chatSelector.setVisible(isHide);
			if(isHide){
				this.setLayout(gbl);
				this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT+200);
				titleBar.setLocation(0,0);
			}else{
				this.setLayout(null);
				titleBar.setLocation(0,0);
				this.setSize(titleBar.getSize());
				
			}
			
			isHide = !isHide;
		}
		private void sendMsg(){
			if(chatSelector.getSelectedIndex() == 0)
				playingInfo.sendMessageToServer(mPack.packStrStrBoolStr(MSG_TYPE,playingInfo.getLoggedInId(),msg, false, ""));
			else if(!chatSelector.getSelectedItem().equals(""))
				playingInfo.sendMessageToServer(mPack.packStrStrBoolStr(MSG_TYPE,playingInfo.getLoggedInId(),msg, true,(String)chatSelector.getSelectedItem()));
				
		}
		public void receiveMsg(String id, String msg , boolean isDirect, String toId){
			msgDisplayArea.append("[" + id +"]" + (isDirect ? " -> [" + toId+"]" : "") + ":\n    " + msg+"\n");
			msgDisplayArea.setCaretPosition(msgDisplayArea.getDocument().getLength());
			if(isDirect)
				toUser(playingInfo.isLoggedInId(id) ? toId : id);
		}
		public void receiveErrMsg(String id, String toId){
			msgDisplayArea.append("[" + id +"]" + " -> [" + toId+"]" + ":\n     That user is not online.\n");
			msgDisplayArea.setCaretPosition(msgDisplayArea.getDocument().getLength());
			chatSelector.removeItemAt(1);
		}
		public void toUser(String userId){
			if(chatSelector.getItemCount() > 1)
				chatSelector.removeItemAt(1);
			chatSelector.insertItemAt(userId, 1);
			chatSelector.setSelectedIndex(1);
		}
		public void clearArea(){
			msgDisplayArea.setText("");
			msgTypeArea.setText("");
		}
		public void ableChatSys(boolean isAble){
			isChatAbled = isAble;
			btnSend.setEnabled(isChatAbled);
		}
//		public void serverDisconnected() throws SocketException{
//			Sounds.buttonCancel.playSound();
//			isServerClosed = true;
//			msgDisplayArea.append("---------------------------------------------\n");
//			msgDisplayArea.append("Connecting to the host was lost.\n");
//			msgDisplayArea.append("---------------------------------------------\n");
//			msgTypeArea.setEditable(false);
//			btnSend.setEnabled(false);
//			throw new SocketException();
//			
//		}
		
		
		
}
