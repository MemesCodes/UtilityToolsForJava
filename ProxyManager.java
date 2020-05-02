package Utility;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Random;

public class ProxyManager {
	public FileList Proxies = new FileList();
	public ArrayList<Proxy> GoodProxies;
	private Random r = new Random();
	public ProxyManager(ArrayList<String> proxies) {
		this.Proxies.List.addAll(proxies);
		this.GoodProxies = new ArrayList<Proxy>();
		
	}
	public ProxyManager(String Filename) {
		this.Proxies.LoadFile(Filename);
		this.GoodProxies = new ArrayList<Proxy>();
		
	}
	public ProxyManager() {
		this.GoodProxies = new ArrayList<Proxy>();
	}
	public void CheckProxies(int Threads) throws IOException {
		ArrayList<String> temp = new ArrayList<String>();
		temp.addAll(this.Proxies.List);
		for(int i=0;i<Threads;i++)
			new Thread(() -> {
		while(temp.size()>0) {
			try {
			String tempproxy = temp.remove(0);
			Proxy t = Utility.returnproxy(tempproxy);
			if (t!=null) this.GoodProxies.add(t);
			}catch(Exception e) {}
		}
			}).start();
		
	}
	public Proxy getGoodProxy() throws IOException {
		if (this.Proxies.List.size()==0) return getrandomproxy();
		String temp = this.Proxies.List.remove(0);
		if (temp.split(":").length>=4) {
			SetProxyUserPass(temp.split(":")[2],temp.split(":")[3]);
		}
		Proxy p = Utility.returnproxy(temp);
		if (p==null) return getGoodProxy();
		this.GoodProxies.add(p);
		return p;
		
	}
	public Proxy getrandomproxy() {
		if (this.GoodProxies.size()==0) return null;
		return (this.GoodProxies.get(r.nextInt(this.GoodProxies.size())));
	}

	public static void SetProxyUserPass(String user,String pass) {
		System.setProperty("jdk.http.auth.tunneling.disabledSchemes","true");
		System.setProperty("jdk.http.auth.proxying.disabledSchemes","true");
		System.setProperty("http.proxyUser", user);
		System.setProperty("http.proxyPassword", pass);
		System.setProperty("https.proxyUser", user);
		System.setProperty("https.proxyPassword", pass);
		final String authUser = user;
		final String authPassword = pass;
		Authenticator.setDefault(
	               new Authenticator() {
	                  public PasswordAuthentication getPasswordAuthentication() {
	                     return new PasswordAuthentication(
	                           authUser, authPassword.toCharArray());
	                  }
	               }
	            );
	}
	
	public static void main(String[] args) throws IOException {
		ProxyManager pm = new ProxyManager("Proxies.txt");
		System.out.println(pm.getGoodProxy());
	}

}
