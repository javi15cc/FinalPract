package asr.proyectoFinal.services;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class Health {
	
	public static String getHealthInformation(String jsonfile) throws IOException{
		
		String jsonfileresponse;
		
		URL url = new URL("https://models.dacadoo.com/score/1");
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("X-dacadoo-Key","AFXq6P8xsudzdXfqcLL59Hg7GGH9oKrjC7sTifyz");
		con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);
		
		try(OutputStream os = con.getOutputStream()) {
		    byte[] input = jsonfile.getBytes("utf-8");
		    os.write(input, 0, input.length);           
		}
		
		try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
		    StringBuilder response = new StringBuilder();
		    String responseLine = null;
		    while ((responseLine = br.readLine()) != null) {
		        response.append(responseLine.trim());
		    }
		    jsonfileresponse = response.toString();
		} finally {
			con.disconnect();
		}
		
		return jsonfileresponse;
		
		
		
		
	}

}
