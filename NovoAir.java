import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class NovoAir extends Aeroplane implements AeroplaneInterface {
	
	public NovoAir() {
		
	}
	
	public NovoAir(int id,String bimanName,String from,String to,String date,String time,String duration,int regularPrice,int businessPrice,int premiumPrice,int maxSeat) {
		super.id = id;
		super.bimanName = bimanName;
		super.from = from;
		super.to = to;
		super.date = date;
		super.time = time;
		super.duration = duration;
		super.regularPrice = regularPrice;
		super.businessPrice = businessPrice;
		super.premiumPrice = premiumPrice;
		super.maxSeat = maxSeat;
		super.seats = new int[maxSeat];
		Arrays.fill(seats, 0);
	}
	
	public void writeToFile(String filename) {
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
	
	public void readFromFile(String filename, int myid) throws UserNotFoundException, IOException {
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			String cur;
			int found = 0;
			//System.out.println("Reached Novo Air");
			while ((cur = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(cur);
				String tmp = st.nextToken(",");
				int curid = Integer.valueOf(tmp);
				if(curid == myid) {
					//System.out.println("Found");
					found = 1;
					super.id = curid;
					
					super.bimanName = st.nextToken(",");
					super.from = st.nextToken(",");
					super.to = st.nextToken(",");
					super.date = st.nextToken(",");
					super.time = st.nextToken(",");
					super.duration = st.nextToken(",");
					
					super.regularPrice = Integer.valueOf(st.nextToken(","));
					super.businessPrice = Integer.valueOf(st.nextToken(","));
					super.premiumPrice = Integer.valueOf(st.nextToken(","));
					super.maxSeat = Integer.valueOf(st.nextToken(","));
					//System.out.println("Max "+super.maxSeat);
					
					if(super.seats == null) {
						super.seats = new int[super.maxSeat];
					}
					
					for(int i = 0;i<super.maxSeat;i++) {
						super.seats[i] = Integer.valueOf(st.nextToken(","));
					}
					//System.out.println(toString());
				}
			}
			if(found == 0) {
				throw new UserNotFoundException("Biman Not Found Exception\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		String str = id + "," + bimanName + "," + from + "," + to + "," + date + "," + time + "," + duration + "," + regularPrice + ","
				+ businessPrice + "," + premiumPrice + "," + maxSeat;
		for(int i = 0;i<maxSeat;i++) {
			str = str + "," + seats[i];
		}
		str = str + "\n";
		return str;
	}
	
	public void debug() {
		System.out.println("Reached NovoAir");
	}

}
