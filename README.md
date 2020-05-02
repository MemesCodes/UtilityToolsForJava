# UtilityToolsForJava

Utility Tools is a small Library that Contains a lot of Tools that I use Commonly while Writing web-testing.

Dependencies:
Jsoup
Gson

Classes::
ProxyManager - 
Easily manage your proxies Using this class.
This Class can Load Proxies from text file or from List given.

You can use this class to Check your proxies (Can also check them Dynamically!) and ask for Working Proxies only.
Automatically distinguishes between Socks/Https/Authentication Proxies.
*Note that it doesn't Currently Support putting multiple Authentication Details in 1 Object. [I.E 2 Different usernames/passwords]

FileList - 
Let you load lists into ArrayLists with 1 function, then ask for a random line or work on the arraylist directly.

CookieJar - 
CookieJar module built for jsoup, let you add Jsoup Cookies (Map<String,String) and automatically disregard old cookies and refreshes them with new one.
Have a function to automatically turn the Cookies into a header.

CLRS - 
Class with all of Windows Support Git Color codes.

options - 
Small wrapper for java class Preferences
Lets you automatically Load and Save options, and change them on the fly

Utility - 
Bunch of random useful Functions.
