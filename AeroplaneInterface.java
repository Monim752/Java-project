import java.io.IOException;

public interface AeroplaneInterface {
	void writeToFile(String filename);
	void readFromFile(String filename, int myid) throws UserNotFoundException, IOException;
}
