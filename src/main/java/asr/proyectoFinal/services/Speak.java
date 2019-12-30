package asr.proyectoFinal.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.text_to_speech.v1.*;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.SynthesizeOptions;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.util.WaveUtils;

public class Speak{

	public static void textToSay(String text, HttpServletResponse response) {
		
		IamOptions options = new IamOptions.Builder().apiKey("VCCPkfMXwPrTytdUHBeXmj8UNLGP1LFBIWRFN0sKSjvw").build();
		TextToSpeech textToSpeech = new TextToSpeech(options);
		textToSpeech.setEndPoint("https://gateway-lon.watsonplatform.net/text-to-speech/api");
		
		try {
	       SynthesizeOptions synthesizeOptions =new SynthesizeOptions.Builder()
												         .text(text)
												         .accept("audio/webm")
												         .voice("es-ES_EnriqueVoice")
												         .build();

	       InputStream inputStream = textToSpeech.synthesize(synthesizeOptions).execute();
	       
	       response.reset();
	       
	       InputStream in = WaveUtils.reWriteWaveHeader(inputStream);
	       OutputStream out = response.getOutputStream();
	       
	       byte[] buffer = new byte[1024];
	       int length;
	       while ((length = in.read(buffer)) > 0) {
	    	   out.write(buffer, 0, length);
	       }

	       out.close();
	       in.close();
	       inputStream.close();
      } catch (IOException e) {
    	  e.printStackTrace();
      }
		
	return ;     	   
	}

}