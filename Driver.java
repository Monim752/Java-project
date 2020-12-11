import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Driver {
	public static String userlist = "userlist.txt";
	public static String bimanlist = "bimanlist.txt";
	public static String bimanlist2 = "bimanlist2.txt";
	public static String ticketlist = "ticketlist.txt";
	public static String ticketlist2 = "ticketlist2.txt";
	public static int lastUser;

	public static void main(String[] args) throws UserNotFoundException, IOException {
		Scanner kb = new Scanner(System.in);
		while(true) {
			System.out.println();
			System.out.println("PLEASE ENTER AN ACTIVITY TO PERFORM\n");
			System.out.println("Terminate, Type: 0");
			System.out.println("Add a New User, Type: 1");
			System.out.println("Sell a Ticket, Type: 2");
			System.out.println("Cancel a Ticket, Type: 3");
			System.out.println("Add a New Flight, Type: 4");
			System.out.println("Cancel a Flight, Type: 5");
			System.out.println("Launch a Flight, Type: 6");
			System.out.println("Show All The Available Flights, Type: 7");
			System.out.println("Show All The Users, Type: 8");
			System.out.println("Show All The Tickets Sold, Type: 9");
			System.out.println();
			int act = Integer.valueOf(kb.nextLine());
			if(act == 0) {
				break;
			}else if(act == 1) {
				registerUser();
			}else if(act == 2) {
				sellTicket();
			}else if(act == 3) {
				cancellTicket();
			}else if(act == 4) {
				registerBiman();
			}else if(act == 5) {
				showFlights();
				System.out.println("Enter the Flight No to Cancell");
				int bimanID = Integer.valueOf(kb.nextLine());
				cancellFlight(bimanID);
			}else if(act == 6) {
				System.out.println("Enter the Flight No Launch Now");
				int bimanID = Integer.valueOf(kb.nextLine());
				runFlight(bimanID);
			}else if(act == 7) {
				showFlights();
			}else if(act == 8) {
				showUsers();
			}else if(act == 9) {
				showTickets();
			}else if(act == 10) {
				break;
			}
		}
	}
	
	public static String getUserNameAndEmail(int userID) throws UserNotFoundException {
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(userlist);
			br = new BufferedReader(fr);
			String cur;
			int found = 0;
			String name = "";
			while ((cur = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(cur);
				String tmp = st.nextToken(",");
				int curid = Integer.valueOf(tmp);
				if(curid == userID) {
					found = 1;
					name = st.nextToken(",");
					name = name + " , " + st.nextToken(",");
					break;
				}
			}
			if(found == 0) {
				throw new UserNotFoundException("Ticket Not Found Exception\n");
			}else {
	            return name;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void sellTicket() throws UserNotFoundException {
		showFlights();
		System.out.println();
		Scanner kb = new Scanner(System.in);
		System.out.println("Please Enter The Flight No");
		System.out.println("Enter -1 If Not Available");
		int flightid = Integer.valueOf(kb.nextLine());
		if(flightid == -1) return;
		
		showUsers();
		System.out.println("Is The User Registered?");
		System.out.println("If Yes, Type 1");
		System.out.println("If No, Type 0");
		int ans = Integer.valueOf(kb.nextLine());
		
		int userid;
		
		if(ans == 0) {
			registerUser();
			userid = lastUser;
		}else {
			System.out.println("Please Enter User ID");
			userid = Integer.valueOf(kb.nextLine());
		}
		
		int ticket_ID = Math.max(newTicketID(ticketlist), newTicketID(ticketlist2)) + 1;
		
		System.out.println("Please Enter Seat Number");
		int seatNo = Integer.valueOf(kb.nextLine());

		System.out.println("Please Enter Ticket Name");
		System.out.println("For Regular, Type 1");
		System.out.println("For Business, Type 2");
		System.out.println("For Premium, Type 3");
		String ticketName;
		int price;
		int regularDiscount, businessDiscount, premiumDiscount;
		
		int typ = Integer.valueOf(kb.nextLine());
		if(typ == 1) {
			ticketName = "Regular";
			System.out.println("Please Enter The Price");
			price = Integer.valueOf(kb.nextLine());
			System.out.println("Please Enter Regular Discount Rate = ? %");
			regularDiscount = Integer.valueOf(kb.nextLine());
			RegularTicket tkt = new RegularTicket(ticket_ID, ticketName, flightid, userid, price, regularDiscount, seatNo);
			tkt.adjustRegularDiscount();
			System.out.println("You Have To Pay: " + tkt.price + " Taka");
			System.out.println("Confirm? YES/NO");
			String cans = kb.nextLine();
			if(cans.equals("NO")) return;
			
			tkt.writeToFile(ticketlist);
			updateFlightByNewTicket(flightid, userid, seatNo);
			System.out.println("Ticket Sold");
		}else if(typ == 2) {
			ticketName = "Business";
			System.out.println("Please Enter The Price");
			price = Integer.valueOf(kb.nextLine());
			System.out.println("Please Enter Regular Discount Rate = ? %");
			regularDiscount = Integer.valueOf(kb.nextLine());
			System.out.println("Please Enter Business Discount Rate = ? %");
			businessDiscount = Integer.valueOf(kb.nextLine());
			BusinessTicket tkt = new BusinessTicket(ticket_ID, ticketName, flightid, userid, price, regularDiscount, businessDiscount, seatNo);
			tkt.adjustRegularDiscount();
			System.out.println("You Have To Pay: " + tkt.price + " Taka");
			System.out.println("Confirm? YES/NO");
			String cans = kb.nextLine();
			if(cans.equals("NO")) return;
			
			tkt.writeToFile(ticketlist);
			updateFlightByNewTicket(flightid, userid, seatNo);
			System.out.println("Ticket Sold");
		}else if(typ == 3) {
			ticketName = "Premium";
			System.out.println("Please Enter The Price");
			price = Integer.valueOf(kb.nextLine());
			System.out.println("Please Enter Regular Discount Rate = ? %");
			regularDiscount = Integer.valueOf(kb.nextLine());
			System.out.println("Please Enter Business Discount Rate = ? %");
			businessDiscount = Integer.valueOf(kb.nextLine());
			System.out.println("Please Enter Premium Discount Rate = ? %");
			premiumDiscount = Integer.valueOf(kb.nextLine());
			PremiumTicket tkt = new PremiumTicket(ticket_ID, ticketName, flightid, userid, price, regularDiscount, businessDiscount, premiumDiscount, seatNo);
			tkt.adjustRegularDiscount();
			System.out.println("You Have To Pay: " + tkt.price + " Taka");
			System.out.println("Confirm? YES/NO");
			String cans = kb.nextLine();
			if(cans.equals("NO")) return;
			
			tkt.writeToFile(ticketlist);
			updateFlightByNewTicket(flightid, userid, seatNo);
			System.out.println("Ticket Sold");
		}
		
	}
	
	public static void cancellTicket() throws UserNotFoundException {
		showTickets();
		System.out.println();
		Scanner kb = new Scanner(System.in);
		System.out.println("Please Enter The User ID");
		int userID = Integer.valueOf(kb.nextLine());
		System.out.println("Please Enter The Ticket No");
		int ticketID = Integer.valueOf(kb.nextLine());
		System.out.println("Please Enter The Flight No");
		int bimanID = Integer.valueOf(kb.nextLine());
		System.out.println("Please Enter The SeatNo No");
		int seatNo = Integer.valueOf(kb.nextLine());
		updateFlightByNewTicket(bimanID, 0, seatNo);
		removeTicket(userID, ticketlist, false);
	}
	
	public static void updateFlightByNewTicket(int bimanid, int userid, int seatno) throws UserNotFoundException {
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(bimanlist);
			br = new BufferedReader(fr);
			String cur;
			int found = 0;
			String bimans = "";
			String bimanName = "";
			Aeroplane ap = null;
			while ((cur = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(cur);
				String tmp = st.nextToken(",");
				int curid = Integer.valueOf(tmp);
				if(curid == bimanid) {
					found = 1;
					bimanName = st.nextToken(",");
				}else {
					bimans = bimans + cur;
					bimans = bimans + "\n";
				}
			}
			if(found == 0) {
				throw new UserNotFoundException("Biman Not Found Exception\n");
			}else {
				if(bimanName.equals("NovoAir")) {
					ap = new NovoAir();
				}else if(bimanName.equals("USBangla")) {
					ap = new USBangla();
				}else {
					System.out.println("Invalid Input");
					return;
				}
				ap.readFromFile(bimanlist, bimanid);
				ap.seats[seatno] = userid;
				BufferedWriter out = new BufferedWriter(new FileWriter(bimanlist, false));
				out.write(bimans);
				out.close();
				
				ap.writeToFile(bimanlist);
			}
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	public static void showTickets() throws NumberFormatException, UserNotFoundException {
		System.out.println("All The Available Tickets Are Shown Below: (Ticket ID, Ticket Name, Flight No, User ID(Details), Price, Discount, Seat No)");
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(ticketlist);
			br = new BufferedReader(fr);
			String cur;
			while ((cur = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(cur);
				String str = "";
				str = str + "ticket_ID: " + st.nextToken(",");
				str = str + " ticketName: " + st.nextToken(",");
				str = str + " biman_ID: " + st.nextToken(",");
				String uid = st.nextToken(",");
				str = str + " user_id: " + uid;
				
				str = str + " " + getUserNameAndEmail(Integer.valueOf(uid)) + ")";
				str = str + " price: " + st.nextToken(",");
				str = str + " Discount: " + st.nextToken(",");
				str = str + " seatNo: " + st.nextToken(",");
				System.out.println(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void showUsers() {
		System.out.println("All The Available Users Are Shown Below: (User ID, Name, Email, Type)");
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(userlist);
			br = new BufferedReader(fr);
			String cur;
			while ((cur = br.readLine()) != null) {
				String s = cur.replace(',', ' ');
				StringTokenizer st = new StringTokenizer(cur);
				String tmp = st.nextToken(",");
				tmp = st.nextToken(",");
				tmp = st.nextToken(",");
				tmp = st.nextToken(",");
				String mys = s;
				int typ = Integer.valueOf(tmp);
				if(typ == 0) {
					mys = mys + "(Regular)";
				}else if(typ == 1) {
					mys = mys + "(Business)";
				}else if(typ == 2) {
					mys = mys + "(Premium)";
				}
				System.out.println(mys);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void showFlights() {
		System.out.println("All The Available Flights Are Shown Below: (Flight No, From, To, Date, Time, Duration, "
				+ "Economy Price, Business Price, Premium Price, Total Seat, Seat Status)");
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(bimanlist);
			br = new BufferedReader(fr);
			String cur;
			while ((cur = br.readLine()) != null) {
				String s = cur.replace(',', ' ');
				System.out.println(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void runFlight(int bimanID) throws UserNotFoundException {
		System.out.println("Flight Number: " + bimanID + " Scheduled to Launch");
		copyFlight(bimanID, bimanlist, bimanlist2);
		removeFlight(bimanID, bimanlist, true);
		System.out.println("Successfully Launched");
	}
	
	public static void cancellFlight(int bimanID) throws UserNotFoundException {
		System.out.println("Flight Number: " + bimanID + " Scheduled to Cancelled");
		removeFlight(bimanID, bimanlist, false);
		System.out.println("Successfully Cancelled");
	}
	
	public static void removeFlight(int bimanid, String bimanlist, boolean f) throws UserNotFoundException {
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(bimanlist);
			br = new BufferedReader(fr);
			String cur;
			int found = 0;
			String bimans = "";
			String bimanName = "";
			Aeroplane ap = null;
			while ((cur = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(cur);
				String tmp = st.nextToken(",");
				int curid = Integer.valueOf(tmp);
				if(curid == bimanid) {
					found = 1;
					bimanName = st.nextToken(",");
				}else {
					bimans = bimans + cur;
					bimans = bimans + "\n";
				}
			}
			if(found == 0) {
				throw new UserNotFoundException("Biman Not Found Exception\n");
			}else {
				if(bimanName.equals("NovoAir")) {
					ap = new NovoAir();
				}else if(bimanName.equals("USBangla")) {
					ap = new USBangla();
				}else {
					System.out.println("Invalid Input");
					return;
				}
				//debug();
				ap.readFromFile(bimanlist, bimanid);
				for(int i = 0;i<ap.maxSeat;i++) {
					int uid = ap.seats[i];
					if(uid == 0) continue;
					removeTicket(uid, ticketlist, f);
				}
				BufferedWriter out = new BufferedWriter(new FileWriter(bimanlist, false));
				out.write(bimans);
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();

		}
	}
	
	public static void removeTicket(int userID, String ticketlist, boolean f) throws UserNotFoundException {
		//System.out.println("Remove ticket: " + ticketID);
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(ticketlist);
			br = new BufferedReader(fr);
			String cur;
			int found = 0;
			String tickets = "";
			String myticket = "";
			while ((cur = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(cur);
				String tmp = st.nextToken(",");
				tmp = st.nextToken(",");
				tmp = st.nextToken(",");
				tmp = st.nextToken(",");
				int curid = Integer.valueOf(tmp);
				if(curid == userID) {
					found = 1;
					myticket = cur;
				}else {
					tickets = tickets + cur;
					tickets = tickets + "\n";
				}
			}
			if(found == 0) {
				throw new UserNotFoundException("Ticket Not Found Exception\n");
			}else {
	            // Open file in write mode.
				BufferedWriter out = new BufferedWriter(new FileWriter(ticketlist, false));
				out.write(tickets);
				out.close();
				
				if(f == true) {
					out = new BufferedWriter(new FileWriter(ticketlist2, true));
					myticket = myticket + "\n";
					out.write(myticket);
					out.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void copyTicket(int ticketID, String file1, String file2) throws UserNotFoundException {
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(file1);
			br = new BufferedReader(fr);
			String cur;
			int found = 0;
			String myticket = "";
			while ((cur = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(cur);
				String tmp = st.nextToken(",");
				int curid = Integer.valueOf(tmp);
				if(curid == ticketID) {
					found = 1;
					myticket = cur;
					break;
				}
			}
			if(found == 0) {
				throw new UserNotFoundException("Ticket Not Found Exception\n");
			}else {
				BufferedWriter out = new BufferedWriter(new FileWriter(file2, true));
				myticket = myticket + "\n";
				out.write(myticket);
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void copyFlight(int bimanID, String file1, String file2) throws UserNotFoundException {
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(file1);
			br = new BufferedReader(fr);
			String cur;
			int found = 0;
			String myflight = "";
			while ((cur = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(cur);
				String tmp = st.nextToken(",");
				int curid = Integer.valueOf(tmp);
				if(curid == bimanID) {
					found = 1;
					myflight = cur;
					break;
				}
			}
			if(found == 0) {
				throw new UserNotFoundException("Biman Not Found Exception\n");
			}else {
				BufferedWriter out = new BufferedWriter(new FileWriter(file2, true));
				myflight = myflight + "\n";
				out.write(myflight);
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void registerUser() {
		System.out.println("\nPlease Enter Following Information to Register New User:");
		int userid = newUserID(userlist) + 1;
		
		System.out.println("Enter User Name:");
		Scanner kb = new Scanner(System.in);
		String name = kb.nextLine();
		
		System.out.println("Enter User Email:");
		String email = kb.nextLine();
		
		System.out.println("Enter User Class");
		System.out.println("For Regular Customer, Enter 0");
		System.out.println("For VIP Customer, Enter 1");
		System.out.println("For Premium Customer, Enter 2");
		int typ = Integer.valueOf(kb.nextLine());
		
		User us = new User(userid, name, email, typ);
		us.writeToFile(userlist);
		lastUser = userid;
	}
	
	public static int newUserID(String userlist) {
		int lastid = 0;
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(userlist);
			br = new BufferedReader(fr);
			String cur = "", s;
			int found = 0;
			while ((s = br.readLine()) != null) {
				cur = s;
				found = 1;
			}
			if(found == 1) {
				//System.out.println("Cur: "+cur);
				StringTokenizer st = new StringTokenizer(cur);
				String tmp = st.nextToken(",");
				lastid = Math.max(lastid, Integer.valueOf(tmp));
			}
		} catch (IOException e) {
			e.printStackTrace();

		}
		return lastid;
	}
	
	public static void registerBiman() {
		System.out.println("\nPlease Enter Following Information to Register New Flight:\n");
		Scanner kb = new Scanner(System.in);

		int bimanid = Math.max(newBimanID(bimanlist), newBimanID(bimanlist2)) + 1;
		
		System.out.println("Select The Biman Company\n");
		System.out.println("For NovoAir Type 1:");
		System.out.println("For USBangla Type 2:");
		int typ = Integer.valueOf(kb.nextLine());
		String bimanName;
		if(typ == 1) {
			bimanName = "NovoAir";
		}else if(typ == 2) {
			bimanName = "USBangla";
		}else {
			System.out.println("Invalid Input");
			return;
		}

		System.out.println("Enter Source:");
		String from  = kb.nextLine();
		System.out.println("Enter Destination:");
		String to  = kb.nextLine();
		System.out.println("Enter Date(DD-MM-YY):");
		String date = kb.nextLine();
		System.out.println("Enter Time(HH:MM:SS):");
		String time = kb.nextLine();
		System.out.println("Enter Duration:");
		String duration = kb.nextLine();

		System.out.println("Enter Regular Class Price:");
		int regularPrice = Integer.valueOf(kb.nextLine());
		System.out.println("Enter Business Class Price:");
		int businessPrice = Integer.valueOf(kb.nextLine());
		System.out.println("Enter Premium Class Price:");
		int premiumPrice = Integer.valueOf(kb.nextLine());

		System.out.println("Enter Maximum Seat:");
		int maxSeat = Integer.valueOf(kb.nextLine());
		Aeroplane ap = null;
		if(bimanName.equals("NovoAir")) {
			ap = new NovoAir(bimanid,bimanName,from,to,date,time,duration,regularPrice,businessPrice,premiumPrice,maxSeat);
		}else if(bimanName.equals("USBangla")) {
			
		}else if(bimanName.equals("BimanBD")) {
			
		}
		ap.writeToFile(bimanlist);
	}
	
	public static int newBimanID(String bimanlist) {
		int lastid = 0;
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(bimanlist);
			br = new BufferedReader(fr);
			String cur = "", s;
			int found = 0;
			while ((s = br.readLine()) != null) {
				cur = s;
				found = 1;
			}
			if(found == 1) {
				System.out.println("Cur: "+cur);
				StringTokenizer st = new StringTokenizer(cur);
				String tmp = st.nextToken(",");
				lastid = Math.max(lastid, Integer.valueOf(tmp));
			}
		} catch (IOException e) {
			e.printStackTrace();

		}
		return lastid;
	}
	
	public static int newTicketID(String ticketlist) {
		int lastid = 0;
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(ticketlist);
			br = new BufferedReader(fr);
			String cur = "", s;
			int found = 0;
			while ((s = br.readLine()) != null) {
				cur = s;
				found = 1;
			}
			if(found == 1) {
				StringTokenizer st = new StringTokenizer(cur);
				String tmp = st.nextToken(",");
				lastid = Math.max(lastid, Integer.valueOf(tmp));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lastid;
	}
	
	public static void debug() {
		System.out.println("Reached");
	}
}
