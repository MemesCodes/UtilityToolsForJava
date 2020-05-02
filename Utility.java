package Utility;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;

import com.google.gson.JsonParser;

public class Utility {
	public static String logo = "";
	private static final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static Random r = new Random();
	public static void infoBox(String titleBar, String infoMessage)
	{
	    JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
	}
	public static void Save(String save,String Name) throws IOException
	{
	    File newTextFile = new File(Name+".txt");

	    FileWriter fw = new FileWriter(newTextFile,true);
	    fw.write(save+System.lineSeparator());
	    fw.close();
	}
	   public static String extractUrls(String text)
	   {
	       String containedUrls = "";
	       String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
	       Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
	       Matcher urlMatcher = pattern.matcher(text);

	       while (urlMatcher.find())
	       {
	           containedUrls = (text.substring(urlMatcher.start(0),
	                   urlMatcher.end(0)));
	       }

	       return containedUrls;
	   }
	public static boolean isBoolean(String s) {
	    if (s.equalsIgnoreCase("true")||s.equalsIgnoreCase("false"))
	    return true;
	    else return false;
	}
	public static String uploadfile(File f) throws IOException {
		try {
		if(!f.exists()) return null;
		Connection.Response res = Jsoup.connect("https://api.anonfiles.com/upload")
				.data("file",f.getName(),new FileInputStream(f))
				.method(Method.POST)
				.ignoreContentType(true)
				.ignoreHttpErrors(true)
				.execute();
		return new JsonParser().parse(res.body()).getAsJsonObject().get("data").getAsJsonObject().get("file").getAsJsonObject().get("url").getAsJsonObject().get("short").getAsString();
		}catch(Exception e) {return null;}
		}
	public static boolean nextBoolean(double precentage) {
		if (r.nextFloat()<precentage) return true;
		return false;
	}
	public static boolean isnotAlphanumeric(String str) {
	    for (int i=0; i<str.length(); i++) {
	        char c = str.charAt(i);
	        if (c < 0x30 || (c >= 0x3a && c <= 0x40) || (c > 0x5a && c <= 0x60) || c > 0x7a)
	            return true;
	    }

	    return false;
	}
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	public static String sendcaptcha(String apikey, String gkey,String url) throws IOException {
		Connection.Response res = Jsoup.connect("https://2captcha.com/in.php?key="+apikey+"&method=userrecaptcha&googlekey="+gkey+"&pageurl="+url)
				.ignoreContentType(true)
				.ignoreHttpErrors(true)
				.timeout(20000)
				.execute();
		return (res.body().split("\\|")[1]);
				
	}
	public static String sendinvisiblecaptcha(String apikey, String gkey,String url) throws IOException {
		Connection.Response res = Jsoup.connect("https://2captcha.com/in.php?key="+apikey+"&min_score=1&method=userrecaptcha&invisible=1&googlekey="+gkey+"&pageurl="+url)
				.ignoreContentType(true)
				.ignoreHttpErrors(true)
				.timeout(20000)
				.execute();
		return (res.body().split("\\|")[1]);
				
	}
	public static String sendcaptchav3(String apikey, String gkey,String url,double score) throws IOException {
		Connection.Response res = Jsoup.connect("https://2captcha.com/in.php?key="+apikey+"&min_score="+score+"&version=v3&method=userrecaptcha&action=verify&googlekey="+gkey+"&pageurl="+url)
				.ignoreContentType(true)
				.ignoreHttpErrors(true)
				.timeout(20000)
				.execute();
		return (res.body().split("\\|")[1]);
				
	}
	public static void reportbadCaptcha(String apikey, String id) throws IOException {
		Jsoup.connect("https://2captcha.com/res.php?key="+apikey+"&action=reportbad&id="+id)
		.ignoreContentType(true)
		.timeout(20000)
		.ignoreHttpErrors(true)
		.execute();
		//System.out.println(res0.body());

	}
	public static String ASCIIArt(String text) throws IOException {
		Response r1 = Jsoup.connect("https://www.kammerl.de/ascii/AsciiSignature.php")
				.method(Method.POST)
				.ignoreContentType(true)
				.ignoreHttpErrors(true)
				.data("figlettext",text)
				.data("figletfont","189")
				.data("Style","3")
				.execute();
		return (r1.parse().getElementsByTag("small").text().replaceAll("(Font: )", "By Memes"));
	}
	public static String retrieveCaptcha(String apikey, String id) throws IOException, InterruptedException {
		Connection.Response res  =null;
		 res = Jsoup.connect("https://2captcha.com/res.php?key="+apikey+"&action=get&id="+id)
				.ignoreContentType(true)
				.timeout(20000)
				.ignoreHttpErrors(true)
				.execute();
		if(res.body().contains("CAPCHA_NOT_READY")) {TimeUnit.SECONDS.sleep(5); return retrieveCaptcha(apikey,id);}
		try {
		return (res.body().split("\\|")[1]);}catch(Exception e) {return null;}
				
	}
	 public static void cls() throws InterruptedException, IOException {
	        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	 }
	 public static String timestamp() {
		 Date now = new Date();
		 SimpleDateFormat form =new SimpleDateFormat("[dd-MM, HH:mm:ss]:: ");
		 return form.format(now);
	 }
	 public static void removeFirstLine(String fileName) throws IOException {  
		    RandomAccessFile raf = new RandomAccessFile(fileName, "rw");          
		     //Initial write position                                             
		    long writePosition = raf.getFilePointer();                            
		    raf.readLine();                                                       
		    // Shift the next lines upwards.                                      
		    long readPosition = raf.getFilePointer();                             

		    byte[] buff = new byte[1024];                                         
		    int n;                                                                
		    while (-1 != (n = raf.read(buff))) {                                  
		        raf.seek(writePosition);                                          
		        raf.write(buff, 0, n);                                            
		        readPosition += n;                                                
		        writePosition += n;                                               
		        raf.seek(readPosition);                                           
		    }                                                                     
		    raf.setLength(writePosition);                                         
		    raf.close();                                                          
		}       
	public static  void logo() throws InterruptedException, IOException {
			Utility.cls();
			System.out.println(logo);
		}
	public static void CLILoadingBar(int Precentage, String Name) throws InterruptedException, IOException {
			//██░░░░░░░░░░░░░░░░░░
			String toprint = CLRS.YELLOW+Name+CLRS.RESET+"\n";
			if (Precentage!=100) toprint+=CLRS.BLUE_BOLD;
			else toprint+=CLRS.GREEN;
			int done = Precentage/5;
			for (int i=0;i<done;i++) toprint+="█";
			for (int i=0;i<(20-done);i++) toprint+="░";
			toprint+=CLRS.RESET;
			cls();
			System.out.println(toprint);
		}
	public static String patternresolve(String pattern) {
		while (pattern.contains("@"))
			pattern=pattern.replaceFirst("@",""+ALPHA.charAt(r.nextInt(ALPHA.length())));
		while (pattern.contains("#"))
			pattern=pattern.replaceFirst("#",""+r.nextInt(10));
		while (pattern.contains("&"))
			if (r.nextBoolean())
			pattern=pattern.replaceFirst("&",""+r.nextInt(10));
			else
				pattern=pattern.replaceFirst("&",""+ALPHA.charAt(r.nextInt(ALPHA.length())));
		while (pattern.contains("up"))
			pattern=pattern.replaceFirst("up"+pattern.charAt(pattern.indexOf("up")+2),""+(1+r.nextInt(Integer.parseInt(""+pattern.charAt(pattern.indexOf("up")+2)))));

		return pattern;
	}
	public static long minutetomillis(long minutes) {
			return minutes*60*1000;
		}
	public static int countLinesNew(String filename) throws IOException {
		    InputStream is = new BufferedInputStream(new FileInputStream(filename));
		    try {
		        byte[] c = new byte[1024];

		        int readChars = is.read(c);
		        if (readChars == -1) {
		            // bail out if nothing to read
		            return 0;
		        }

		        // make it easy for the optimizer to tune this loop
		        int count = 0;
		        while (readChars == 1024) {
		            for (int i=0; i<1024;) {
		                if (c[i++] == '\n') {
		                    ++count;
		                }
		            }
		            readChars = is.read(c);
		        }

		        // count remaining characters
		        while (readChars != -1) {
		            System.out.println(readChars);
		            for (int i=0; i<readChars; ++i) {
		                if (c[i] == '\n') {
		                    ++count;
		                }
		            }
		            readChars = is.read(c);
		        }
		        is.close();
		        return count == 0 ? 1 : count;
		    } finally {
		        is.close();
		    }
		}
	public static Proxy.Type detectProxyType(InetSocketAddress proxyAddress)
	    	    throws IOException
	    	    {
	    	        URL url = new URL("https://azenv.net/");
	    	        List<Proxy.Type> proxyTypesToTry = Arrays.asList(Proxy.Type.SOCKS, Proxy.Type.HTTP);

	    	        for (Proxy.Type proxyType : proxyTypesToTry)
	    	        {
	    	            Proxy proxy = new Proxy(proxyType, proxyAddress);

	    	            //Try with SOCKS
	    	            URLConnection connection = null;
	    	            try
	    	            {
	    	                connection = url.openConnection(proxy);

	    	                //Can modify timeouts if default timeout is taking too long
	    	                //connection.setConnectTimeout(1000);
	    	                //connection.setReadTimeout(1000);

	    	                connection.getContent();

	    	                //If we get here we made a successful connection
	    	                return(proxyType);
	    	            }
	    	            catch (SocketException e) //or possibly more generic IOException?
	    	            {
	    	                //Proxy connection failed
	    	            }
	    	            catch(Exception e) {return null;}
	    	        }

	    	        //No proxies worked if we get here
	    	        return(null);
	    	    }      
	public static void deleteLine(String FN, String toremove)
	    	    {
	    	        try
	    	        {
	    	                BufferedReader file = new BufferedReader(new FileReader(FN+".txt"));
	    	                String line;
	    	                String input = "";
	    	                while ((line = file.readLine()) != null) 
	    	                {
	    	                    //System.out.println(line);
	    	                    if (line.contains(toremove))
	    	                    {
	    	                        line = "";
	    	                    }
	    	                    if(!line.equals(""))
	    	                    input += line + '\n';
	    	                }
	    	                FileOutputStream File = new FileOutputStream(FN+".txt");
	    	                File.write(input.getBytes());
	    	                file.close();
	    	                File.close();
	    	        }
	    	        catch (Exception e)
	    	        {
	    	                System.out.println("Problem reading file.");
	    	        }
	    	    }
	public static Proxy returnproxy(String proxy) throws IOException {
		InetSocketAddress inet = new InetSocketAddress(proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1]));
		Proxy.Type type = detectProxyType(inet);
		if (type==null) return null;
		return new Proxy(type,inet);

	}
	public static <T>ArrayList<ArrayList<T>> SplitList( final ArrayList<T> ls, final int iParts )
	{
	    final ArrayList<ArrayList<T>> lsParts = new ArrayList<ArrayList<T>>();
	    final int iChunkSize = ls.size() / iParts;
	    int iLeftOver = ls.size() % iParts;
	    int iTake = iChunkSize;

	    for( int i = 0, iT = ls.size(); i < iT; i += iTake )
	    {
	        if( iLeftOver > 0 )
	        {
	            iLeftOver--;

	            iTake = iChunkSize + 1;
	        }
	        else
	        {
	            iTake = iChunkSize;
	        }

	        lsParts.add( new ArrayList<T>( ls.subList( i, Math.min( iT, i + iTake ) ) ) );
	    }

	    return lsParts;
	}
	
}
