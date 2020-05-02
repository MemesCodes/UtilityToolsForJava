package Utility;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FileList {
public ArrayList<String> List = new ArrayList<>();
public Random r = new Random();
public FileList() {
	this.List = new ArrayList<>();
}
public FileList(String FileLocaiton) {
	this.List = new ArrayList<>();
	this.LoadFile(FileLocaiton);
}
public void LoadFile(String Filename) {
	try {
		Scanner f = new Scanner(new File(Filename),"UTF-8");
		while (f.hasNext()) this.List.add(f.nextLine());
		f.close();
	}catch(Exception e) {e.printStackTrace();}
}
public String getrandomline() {
	return List.get(r.nextInt(List.size()));
}
}
