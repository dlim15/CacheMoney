package ScreenPack;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import GamePack.ImageRelated;
import GamePack.Property;
import GamePack.SizeRelated;

public final class PropertyDisplay extends JPanel{
	private SizeRelated sizeRelated;
	private static final PropertyDisplay pDisplay = new PropertyDisplay();
	private ArrayList<JLabel> labels;
	private int width;
	private ImageRelated imageRelated;
	private PropertyDisplay(){
		init();
	}
	public static PropertyDisplay getInstance(){
		return pDisplay;
	}
	private void init(){
		setLayout(null);
		sizeRelated = SizeRelated.getInstance();
		imageRelated = ImageRelated.getInstance();
		width = sizeRelated.getSpaceColHeight()*3;
		setBounds(0,0,width,width);
		setBackground(new Color(255,248,220));
		initLabels();
	}
	private void initLabels(){
		labels = new ArrayList<>();
		labels.add(new JLabel("Name"));
		labels.add(new JLabel("Owned:"));
		labels.add(new JLabel("No"));
		labels.add(new JLabel("Houses:"));
		labels.add(new JLabel("0"));
		labels.add(new JLabel("Hotels:"));
		labels.add(new JLabel("0"));
		labels.add(new JLabel("Mortgaged:"));
		labels.add(new JLabel("Yes"));
		labels.add(new JLabel("Minigame:"));
		labels.add(new JLabel(""));
		labels.get(0).setBounds(width/11, width / 11, width, width / 11);
		add(labels.get(0));
		for(int i=1; i<labels.size(); i+=2){
			labels.get(i).setBounds(width/7, width*(i+2) / 13, width * 3/5, width / 11);
			add(labels.get(i));
		}
		for(int i=2; i<labels.size(); i+=2){
			labels.get(i).setBounds(width / 7 + width*7/15, width*(i+1) / 13, width * 2/5, width / 11);
			add(labels.get(i));
		}
	}
	public int getStartX(int x){
		return x < sizeRelated.getScreenW()/2 ? x : x-width;
	}
	public int getStartY(int y){
		return y < sizeRelated.getScreenH()/2 ? y : y-width;
	}
	public void setProperty(Property info){
		labels.get(0).setText(info.getName());
		if(info.isOwned()){
			labels.get(2).setText("");
			labels.get(2).setIcon(imageRelated.getSmallPieceImg(info.getOwner()));
		}
		else {
			labels.get(2).setIcon(null);
			labels.get(2).setText("No");
		}
		labels.get(4).setText(info.getNumHouse()+"");
		labels.get(6).setText(info.getNumHotel()+"");
		labels.get(8).setText(info.isMortgaged() ? "Yes" : "No");
		labels.get(10).setText(miniGameText(info.getPropertyFamilyIdentifier()));
	}
	private String miniGameText(int propertyFamilyIdentifier) {
		switch (propertyFamilyIdentifier){
		case 1:
			return "Box";
		case 2:
			return "RSP";
		case 3:
			return "Spam";
		case 4:
			return "React";
		case 5:
			return "TicTacToe";
		case 6:
			return "Eliminate";
		case 7:
			return "Math";
		case 8:
			return "Memory";
		case 10:
			return "Utility";
		case 9:
			return "Random";
		default:
			return "ERROR";
		}
	}
}
