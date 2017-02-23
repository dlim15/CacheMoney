package GamePack;

public class StandardProperty extends Property{
	private int housePrice;
	private int hotelPrice;
	
	public StandardProperty(int cost, String name) {
		super(cost, name);		
	}
	
	@Override
	protected void init()
	{
		rentMultiplier = 0;
		housePrice = roundUp(6*buyingPrice,10);
		hotelPrice = (5*housePrice);		
		rentValues.add(roundUp(buyingPrice,12));
		rentValues.add(5*rentValues.get(0));
		rentValues.add(15*rentValues.get(0));
		rentValues.add(45*rentValues.get(0));
		rentValues.add(70*rentValues.get(0));
	}

	public int getHousePrice() {
		return housePrice;
	}

	public int getHotelPrice() {
		return hotelPrice;
	}

}
