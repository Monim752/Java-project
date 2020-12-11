import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class BusinessTicket extends Ticket{
	int regularDiscount;
	int businessDiscount;
	int seatNo;
	
	public BusinessTicket() {
		// TODO Auto-generated constructor stub
	}
	
	public BusinessTicket(int ticket_ID, String ticketName, int biman_ID, int user_id, int price, int regularDiscount, int businessDiscount, int seatNo) {
		super.ticket_ID = ticket_ID;
		super.ticketName = ticketName;
		super.biman_ID = biman_ID;
		super.user_id = user_id;
		super.price = price;
		this.regularDiscount = regularDiscount;
		this.businessDiscount = businessDiscount;
		this.seatNo = seatNo;
	}
	
	void adjustRegularDiscount() {
		int dec = regularDiscount*super.price/100;
		super.price -= dec;
		dec = businessDiscount*super.price/100;
		super.price -= dec;
		regularDiscount += businessDiscount;
		businessDiscount = 0;
	}
	
	void writeToFile(String filename) {
		try {
            // Open file in append mode.
			BufferedWriter out = new BufferedWriter(new FileWriter(filename, true));
			String str = toString();
			out.write(str);
			out.close(); 
        } 
        catch (IOException e) { 
            System.out.println("IO Exception Occoured" + e); 
        }
	}
	
	void readFromFile(String filename, int myid) throws UserNotFoundException {
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			String cur;
			int found = 0;
			while ((cur = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(cur);
				String tmp = st.nextToken(",");
				int curid = Integer.valueOf(tmp);
				if(curid == myid) {
					found = 1;
					super.ticket_ID = curid;
					super.ticketName = st.nextToken(",");
					super.biman_ID = Integer.valueOf(st.nextToken(","));
					super.user_id = Integer.valueOf(st.nextToken(","));
					super.price = Integer.valueOf(st.nextToken(","));
					this.regularDiscount = Integer.valueOf(st.nextToken(","));
					this.regularDiscount = Integer.valueOf(st.nextToken(","));
					this.seatNo = Integer.valueOf(st.nextToken(","));
				}
			}
			if(found == 0) {
				throw new UserNotFoundException("Ticket Not Found Exception\n");
			}
		} catch (IOException e) {
			e.printStackTrace();

		}
	}
	
	public String toString() {
		return ticket_ID + "," + ticketName + "," + biman_ID + "," + user_id + "," + price + "," + regularDiscount + "," + seatNo + "\n";
	}
	
}
