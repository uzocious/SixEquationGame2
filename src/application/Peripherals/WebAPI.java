package application.Peripherals;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import org.json.JSONObject;
import java.io.InputStream;

public class WebAPI {
	
	private String ipAddress;
	private String location = "";
	private String timezone;
	
	public WebAPI() 
	{
		URL url;
        try {
   
            url = new URL("http://ip-api.com/json/?fields=country,regionName,city,timezone,query");
            InputStream inputStream = url.openStream();

            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }

            String result2parse = result.toString();            

            String jsonString = result2parse; 
            JSONObject obj = new JSONObject(jsonString);
            
            ipAddress = obj.getString("query");
            timezone = obj.getString("timezone");
            location += obj.getString("city") + ", ";
            location += obj.getString("regionName") + ", ";
            location += obj.getString("country");
            
            
        } catch (Exception e) {
            System.out.println("An Error occured: "+e.toString());
            e.printStackTrace();
        }
		
	}
	
	
	/**
	 * Returns the ip address. 
	 * @return
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	
	
	/**
	 * Returns the location. 
	 * @return
	 */
	public String getLocation() {
		return location;
	}
	
	
	/**
	 * Returns the time zone. 
	 * @return
	 */
	public String getTimezone() {
		return timezone;
	}

}
