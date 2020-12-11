import java.io.IOException;

public class Aeroplane implements AeroplaneInterface {
	int id;
	String bimanName;
	String from;
	String to;
	String date;
	String time;
	String duration;
	int regularPrice;
	int businessPrice;
	int premiumPrice;
	int maxSeat;
	int seats[];
	
	@Override
	public void writeToFile(String filename) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void readFromFile(String filename, int myid) throws UserNotFoundException, IOException {
		// TODO Auto-generated method stub
		
	}
}
