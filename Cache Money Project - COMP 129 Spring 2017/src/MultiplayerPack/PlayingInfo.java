package MultiplayerPack;

import java.io.IOException;
import java.io.OutputStream;

public final class PlayingInfo {
	private boolean isSingle;
	private OutputStream outputStream;
	private int myPlayerNum;
	private int numberOfPlayer;
	private boolean isLoggedIn;
	private String loggedInId;
	private int gamePart;
	private boolean hasGameStarted;
	private boolean isLoadingGame;
	private int loadingGameNum;
	public static int PORT_NUM = 7777;
	//public final static String IP_ADDRESS = "10.15.157.201";
	private boolean isDisconnectedByOther;
	public static String IP_ADDRESS = "10.15.17.88";
//	public final static String IP_ADDRESS = "10.0.0.3";
	private static final PlayingInfo PLAYING_INFO = new PlayingInfo();
	private PlayingInfo(){
		outputStream = null;
	}
	public static PlayingInfo getInstance(){
		return PLAYING_INFO;
	}
	public void setOutputStream(OutputStream outputStream){
		this.outputStream = outputStream;
	}
	public void sendMessageToServer(byte[] msg){
		if (outputStream != null){
			try {
				outputStream.write(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			System.out.println("WARNING: writer == null");
		}
	}
	public boolean isSingle(){
		return isSingle;
	}
	public void setIsSingle(boolean isSingle){
		this.isSingle = isSingle;
	}
	public int getMyPlayerNum(){
		return myPlayerNum;
	}
	public boolean isMyPlayerNum(int playerNum){
		return myPlayerNum == playerNum;
	}
	public void setMyPlayerNum(int myPlayerNum){
		this.myPlayerNum = myPlayerNum;
	}
	public OutputStream getOutputStream(){
		return outputStream;
	}
	public void setLoggedIn(){
		isLoggedIn = true;
	}
	public void setLoggedInId(String id){
		loggedInId = id;
	}
	public boolean isLoggedIn(){
		return isLoggedIn;
	}
	public String getLoggedInId(){
		return loggedInId;
	}
	public boolean isOutputSet(){
		return outputStream!=null;
	}
	public boolean isLoggedInId(String id){
		return loggedInId.toUpperCase().equals(id.toUpperCase());
	}
	public int getGamePart(){
		return gamePart;
	}
	public void setGamePart(int gamePart){
		this.gamePart = gamePart;
	}
	public int getNumberOfPlayer(){
		return numberOfPlayer;
	}
	public void setNumberOfPlayer(int numberOfPlayer){
		this.numberOfPlayer = numberOfPlayer;
	}
	public boolean getIsDisconnectedByOther(){
		return isDisconnectedByOther;
	}
	public void setIsDisconnectedByOther(){
		isDisconnectedByOther = true;
	}
	public boolean getHasGameStarted(){
		return hasGameStarted;
	}
	public void setGameStarted(){
		hasGameStarted = true;
	}
	public boolean isLoadingGame(){
		return isLoadingGame;
	}
	public void setLoadingGame(){
		isLoadingGame = true;
	}
	public int getLoadingGameNum(){
		return loadingGameNum;
	}
	public void setLoadingGameNum(int num){
		loadingGameNum = num;
	}
	public void setIPAddress(String ipAddress){
		IP_ADDRESS = ipAddress;
	}
	public void setPortNum(String portNum){
		try{
			PORT_NUM = Integer.parseInt(portNum);
		}
		catch (Exception e){
			PORT_NUM = 0;
		}
	}
}
