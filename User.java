import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class User {
	int user_ID;
	String name;
	String email;
	int typ; // 0: regular, 1: vip, 2: premium
	
	User(){
		
	}
	
	User(int user_ID, String name, String email, int typ){
		this.user_ID = user_ID;
		this.name = name;
		this.email = email;
		this.typ = typ;
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
					this.user_ID = curid;
					this.name = st.nextToken(",");
					this.email = st.nextToken(",");
					this.typ = Integer.valueOf(st.nextToken(","));
				}
			}
			if(found == 0) {
				throw new UserNotFoundException("User Not Found Exception\n");
			}
		} catch (IOException e) {
			e.printStackTrace();

		}
	}
	
	public String toString() {
		return user_ID + "," + name + "," + email + "," + typ + "\n";
	}
}
