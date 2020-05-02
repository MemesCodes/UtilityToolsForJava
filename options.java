package Utility;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;

public class options {
	ArrayList<String> options = new ArrayList<String>();
	ArrayList<String> correspondingvalues = new ArrayList<String>();
	String appname = "temp";
	public options(String[] toadd,String newappname) {
		this.options.addAll(Arrays.asList(toadd));
		this.appname = newappname;
		defaultmap();
		this.loadfrommemory();
	}
	private void loadfrommemory() {
		this.correspondingvalues = new ArrayList<String>();
		this.correspondingvalues.addAll(options);
		for (String temp:this.options) 	
			this.correspondingvalues.set(this.options.indexOf(temp),getprop(temp));
	}
	private String getprop(String prop) {
		Preferences prefs = Preferences.userRoot().node("/"+this.appname);
		return prefs.get(prop,"default");
	}
	private void regprop(String propname,String value) {
		Preferences prefs = Preferences.userRoot().node("/"+this.appname);
        prefs.put(propname,value);
	}
	public void changevalue(String value, String tochange) {
		regprop(value, tochange);
	}
	private void defaultmap() {
		for (String temp:this.options) 
			if (getprop(temp).contains("default")) {
				//put in default values here:
				//if (temp.contains("proxytype")) regprop(temp,"HTTPS");


			}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList[] getoptions() {
		ArrayList<String>[] temp= new ArrayList[2];
		temp[0] = this.options;
		temp[1] = this.correspondingvalues;
		
		return temp;
	}
	public String toString() {
		String fin = "";
		for (String option:this.options) 
			fin+="("+this.options.indexOf(option)+") "+option+" = "+this.correspondingvalues.get(this.options.indexOf(option))+"\r\n";
		return fin;
	}
	public boolean contains(String temp) {
		if (this.options.contains(temp))
			return true;
		else return false;
	}
	public int length() {
		return this.options.size();
	}
	public void changebyindex(int index) {
		if(Utility.isBoolean(this.options.get(index))) 
		regprop(this.options.get(index),(!Boolean.parseBoolean(this.getprop(options.get(index))))+"");
		else
		regprop(this.options.get(index),JOptionPane.showInputDialog(options.get(index),getprop(options.get(index))));
		loadfrommemory();
	}	
	public String getvalue(String name) {
		return this.correspondingvalues.get(this.options.indexOf(name));
	}
	public int getvalueasint(String name) {
		return Integer.parseInt(this.correspondingvalues.get(this.options.indexOf(name)).toUpperCase());
	}
	public Boolean getvalueasbool(String name) {
		try {
		return Boolean.parseBoolean(this.correspondingvalues.get(this.options.indexOf(name)).toUpperCase());
		}catch(Exception e) { return false;}
		}
	
	public void ShowMenu() throws InterruptedException, IOException {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		for(;;) {
		Utility.cls();
		Utility.logo();
		System.out.println(this.toString()+"("+this.options.size()+") return to main menu");
		String temp = in.next();
		if (Utility.isInteger(temp) && Integer.parseInt(temp)<=this.options.size())
			if (Integer.parseInt(temp)==this.options.size()) {return;}
			else this.changebyindex(Integer.parseInt(temp));
		else Utility.infoBox("An Error has Occured", "Parsing error while parsing your response.");
		}
	}
}



