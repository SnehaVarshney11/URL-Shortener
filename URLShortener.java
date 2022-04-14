import java.util.*;
import java.util.HashMap;
import java.util.Random;

public class URLShortener
{
    private HashMap<String, String> keyMap; //key-url map 
    private HashMap<String, String> valMap; //value-key map to check
    
    private String domain;
    private char myChars[];
    
    private Random rand; //generate random Integer
    private int keyLen; //key length in URL defaults to 8
    
    //Constructor
    URLShortener(){
        keyMap = new HashMap<String, String>();
        valMap = new HashMap<String, String>();
        rand = new Random();
        keyLen = 8;
        myChars = new char[62];
        
        for(int i = 0; i < 62; i++){
            int j = 0;
            if(i < 10){
                j = i + 48;
            }else if(i > 9 && i <= 35){
                j = i + 55;
            }else{
                j = i + 61;
            }
            myChars[i] = (char)j;
        }
        domain = "http://abc.in";
    }
    
    // Constructor which enables you to define tiny URL key length and base URL name
    URLShortener(int length, String newDomain){
        this();
        this.keyLen = length;
        if(! newDomain.isEmpty()){
            newDomain = sanitizeURL(newDomain);
            domain = newDomain;
        }
    }
    
    String sanitizeURL(String url){
        if(url.substring(0,7).equals("http://")){
            url = url.substring(7);
        }
        if(url.substring(0,8).equals("https://")){
            url = url.substring(8);
        }
        if(url.charAt(url.length() - 1) == '/'){
            url = url.substring(0, url.length() - 1);
        }
        return url;
    }
    
    //Method to shorten the given url
    public String shortenURL(String longURL){
        String shortURL = "";
        if(validURL(longURL)){
            longURL = sanitizeURL(longURL);
            if(valMap.containsKey(longURL)){
                shortURL = domain + "/" + valMap.get(longURL);
            }else{
                shortURL = domain + "/" + getKey(longURL);
            }
        }
        return shortURL;
    }
    //Method to check whether the url is valid or not 
    boolean validURL(String url){
        return true;
    }
    
    //getKey Method
    private String getKey(String longURL){
        String key;
        key = generateKey();
        keyMap.put(key, longURL);
        valMap.put(longURL, key);
        return key;
    }
    
    //generate key
    private String generateKey(){
        String key = "";
        boolean flag = true;
        while(flag){
            key = "";
            for(int i = 0; i <= keyLen; i++){
                key += myChars[rand.nextInt(62)];
            }
            if(!keyMap.containsKey(key)){
                flag = false;
            }
        }
        return key;
    }
    
    // method to return back original url
    public String originalURL(String shortURL){
        String longURL = "";
        String key = shortURL.substring(domain.length() + 1);
        longURL = keyMap.get(key);
        return longURL;
    }
    
    // Function to generate a short url from integer ID
    static String idToShortURL(int n)
    {
        // Map to store 62 possible characters
        char map[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
     
        StringBuffer shorturl = new StringBuffer();
     
        // Convert given integer id to a base 62 number
        while (n > 0)
        {
            // use above map to store actual character
            // in short url
            shorturl.append(map[n % 62]);
            n = n / 62;
        }
     
        // Reverse shortURL to complete base conversion
        return shorturl.reverse().toString();
    }
    
    // Function to get integer ID back from a short url
    static int shortURLtoID(String shortURL)
    {
        int id = 0; // initialize result
     
        // A simple base conversion logic
        for (int i = 0; i < shortURL.length(); i++)
        {
            if ('a' <= shortURL.charAt(i) &&
                       shortURL.charAt(i) <= 'z')
            id = id * 62 + shortURL.charAt(i) - 'a';
            if ('A' <= shortURL.charAt(i) &&
                       shortURL.charAt(i) <= 'Z')
            id = id * 62 + shortURL.charAt(i) - 'A' + 26;
            if ('0' <= shortURL.charAt(i) &&
                       shortURL.charAt(i) <= '9')
            id = id * 62 + shortURL.charAt(i) - '0' + 52;
        }
        return id;
    }
    
	public static void main(String[] args) {
	    Scanner sc = new Scanner(System.in);

		URLShortener url = new URLShortener();
		
		System.out.println("Choose 1: To generate Short URL from user id \nChoose 2: To generate Short URL from Long URL");
		int ch = sc.nextInt();
	
	    switch(ch){
	        case 1:
	            
	            System.out.println("Enter id to generate url: ");
	            int n = sc.nextInt();
                String shorturl = idToShortURL(n);
                System.out.println("Generated short url is " + shorturl);
                System.out.println("Id from url is " +shortURLtoID(shorturl));
                break;
                
	       case 2:
	           System.out.println("Enter urls which you want to convert into shorturl");
	           String[] urls = new String[1];
	           for(int i = 0; i < 1; i++){
	               urls[i] = sc.next();
	           }
	
               for(int i = 0; i < urls.length; i++){
                System.out.println("URL: " + urls[i] + "\t tiny: " + url.shortenURL(urls[i]) + "\tOriginal url: " 
                + url.originalURL(url.shortenURL(urls[i])));
               } 
            break;
        }	
		
		sc.close();
	}
}
