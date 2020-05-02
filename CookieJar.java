package Utility;
import java.util.HashMap;
import java.util.Map;

public class CookieJar {
	public Map<String,String> cookies = new HashMap<>();
	private String header = "";
 public CookieJar() {
	 this.cookies = new HashMap<>();
 }
 public void addcookies(Map<String, String> cookies) {
	 cookies.forEach((key,value) -> {
	 if (this.cookies.containsKey(key)) 
		 {
		 this.cookies.remove(key);
		 this.cookies.put(key, value);
		 }
	 else
		 this.cookies.put(key, value);
	 }); 
 }
 public String toheader() {
	 this.header = "";
	 this.cookies.forEach((key,value) -> {
		 header+=key+"="+value+"; ";
	 });
	 return header;
 }
 public void addcookies(String name,String value) {
	 this.cookies.put(name, value);
 }
}