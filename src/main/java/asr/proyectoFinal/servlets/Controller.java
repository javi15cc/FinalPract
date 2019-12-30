package asr.proyectoFinal.servlets;

import java.io.BufferedWriter;
import java.net.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import asr.proyectoFinal.dao.CloudantPalabraStore;
import asr.proyectoFinal.dominio.Palabra;
import asr.proyectoFinal.services.Health;
import asr.proyectoFinal.services.Speak;
import asr.proyectoFinal.services.Traductor;
//import asr.proyectoFinal.services.Health;

/**
 * Servlet implementation class Controller
 */
@WebServlet(urlPatterns = {"/listar", "/instrucciones", "/Controller"})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		out.println("<html><head><meta charset=\"UTF-8\"></head><body>");
		
		System.out.println(request.getServletPath());
		
		String jsonfile="";
		String jsonresponse;
		
		CloudantPalabraStore store = new CloudantPalabraStore();
		
		switch(request.getServletPath())
		{
			case "/listar":
				if(store.getDB() == null) {
					out.println("No hay DB");
				}else {
					out.println("Clientes:<br />" + store.getAll());
					out.println("<br><br>");
					out.println("<a href=\"javascript:history.back()\">Atras</a>");
				}
			break;
			case "/instrucciones":
				String respuestaTexto = "Fill all the fields and press the button to check your health";
				String respuestaHablar = Traductor.translate(respuestaTexto, "en", "es", false);
				Speak.textToSay(respuestaHablar, response);
			break;
				
		}
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
		PrintWriter out = response.getWriter();
		out.println("<html><head><meta charset=\"UTF-8\"></head><body>");
		
		
		int cambio = 1;
		request.setAttribute("change", cambio);
		
		
		//Guardar Nombre
		CloudantPalabraStore store = new CloudantPalabraStore();
		String nombre = request.getParameter("nombre");
		String nombreInsertado;
		Palabra palabra = new Palabra();
		
		if(nombre==null)
		{
			out.println("usage: /insertar?palabra=palabra_a_traducir");
		}
		else
		{
			if(store.getDB() == null) 
			{
				out.println(String.format("Palabra: %s", palabra));
			}
			else
			{
				@SuppressWarnings("unused")
				String textoInsertar = "Insertando el cliente: " + nombre;
				palabra.setName(nombre);
				store.persist(palabra);
				nombreInsertado = nombre;
				request.setAttribute("nombre", nombreInsertado);
			    out.println(String.format("Almacenado el cliente: %s", palabra.getName()));			    	  
			}
		}
		
		// Calcular Health Score
		//String years = request.getParameter("years");
		String age = request.getParameter("edad");
		String height = request.getParameter("altura");
		String weight = request.getParameter("peso");
		String sex = request.getParameter("genero");
		String smknow = request.getParameter("smoker");
		String smkevr = request.getParameter("smokerever");
		String smktime = request.getParameter("tiempoFumando");
		String smknday = request.getParameter("numeroAlDia");
		String movement = request.getParameter("movement");
		int[] movementArray = {new Integer(movement).intValue()};
		//int[] mvnArray = new int[] {new Integer(movement).intValue()};
		//String nutrition = request.getParameter("nutrition");
		String qlife = request.getParameter("q01");
		//String cliq = "false";
		String bedtime = request.getParameter("tiempoCama");
		int[] bedtimeArray = {new Integer(bedtime).intValue()};
		String sleeptime = request.getParameter("tiempoDormido");
		int[] sleeptimeArray = {new Integer(sleeptime).intValue()};
		String timesawake = request.getParameter("vecesDespertandose");
		int[] timesawakeArray = {new Integer(timesawake).intValue()};
		
		String jsonresponse;
		
		JsonObject jsonfile = new JsonObject();
		//jsonfile.addProperty("years", new Integer(years).intValue());
		//jsonfile.addProperty("mhm", new Integer(years).intValue());
		
		JsonObject mhm = new JsonObject();
		mhm.addProperty("age", new Integer(age).intValue());
		mhm.addProperty("hgt", new Integer(height).intValue());
		mhm.addProperty("sex", new Integer(sex).intValue());
		mhm.addProperty("wgt", new Integer(weight).intValue());
		
		jsonfile.add("mhm", mhm);
		
		JsonObject smk = new JsonObject();
		smk.addProperty("now", new Integer(smknow).intValue());
		smk.addProperty("evr", new Integer(smkevr).intValue());
		smk.addProperty("yrs", new Integer(smktime).intValue());
		smk.addProperty("num", new Integer(smknday).intValue());
		
		jsonfile.add("smk", smk);
		
		JsonObject slp = new JsonObject();
		
		JsonArray jsonArray = new JsonArray();
		jsonArray.add(new JsonPrimitive(bedtimeArray[0]));
		slp.add("bed", jsonArray);
		
		JsonArray jsonArray2 = new JsonArray();
		jsonArray2.add(new JsonPrimitive(sleeptimeArray[0]));
		slp.add("slp", jsonArray2);
		
		JsonArray jsonArray3 = new JsonArray();
		jsonArray3.add(new JsonPrimitive(timesawakeArray[0]));
		slp.add("awk", jsonArray3);
		//slp.addProperty("slp", new Integer(sleeptime).intValue());
		//slp.addProperty("awk", new Integer(timesawake).intValue());
		
		jsonfile.add("slp", slp);
		
		JsonObject mvm = new JsonObject();
		JsonArray jsonArray4 = new JsonArray();
		jsonArray4.add(new JsonPrimitive(movementArray[0]));
		mvm.add("nrg", jsonArray);
		
		jsonfile.add("mvm", mvm);
		
		JsonObject qlm = new JsonObject();
		mvm.addProperty("q01", qlife);
		
		jsonfile.add("qlm", qlm);

		jsonresponse = Health.getHealthInformation(jsonfile.toString());
		
		String respuestaTexto = "Your health score is: ";
		
		JsonObject jsonObj = JsonParser.parseString(jsonresponse).getAsJsonObject();
		
		String scr = jsonObj.get("scr").getAsString();
		request.setAttribute("score", scr);
		String ucl = jsonObj.get("ucl").getAsString();
		String lcl = jsonObj.get("lcl").getAsString();
		String rqy = jsonObj.get("rqy").getAsString();
		String lfs = jsonObj.getAsJsonObject("components").get("lfs").getAsString();
		String bdy = jsonObj.getAsJsonObject("components").get("bdy").getAsString();
		String fee = jsonObj.getAsJsonObject("components").get("fee").getAsString();
		String mvmm = jsonObj.getAsJsonObject("subscores").get("mvm").getAsString();
		String nut = jsonObj.getAsJsonObject("subscores").get("nut").getAsString();
		String smk2 = jsonObj.getAsJsonObject("subscores").get("smk").getAsString();
		String obe = jsonObj.getAsJsonObject("subscores").get("obe").getAsString();
		String slp2 = jsonObj.getAsJsonObject("subscores").get("slp").getAsString();
		String dep = jsonObj.getAsJsonObject("subscores").get("dep").getAsString();
		String wel = jsonObj.getAsJsonObject("subscores").get("wel").getAsString();
		String str = jsonObj.getAsJsonObject("subscores").get("str").getAsString();
		
		
		String respuestaTraducida = Traductor.translate(respuestaTexto, "en", "es", false);
		//String respuestaHablar = Traductor.translate(respuestaTexto, "en", "es", false) + scr;
		
		request.setAttribute("respuestaTexto", respuestaTraducida);
		request.setAttribute("jsonfile", jsonresponse);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
		
		//}else if(request.getParameter("send").equals("Instrucciones")) {
			//String respuestaTexto = "Fill all the fields and press the button";
			//String respuestaHablar = Traductor.translate(respuestaTexto, "en", "es", false);
			//Speak.textToSay(respuestaHablar, response);
			//request.getRequestDispatcher("/index.jsp").forward(request, response);
			//out.println("Escuchando...");
			//out.println("<br><br>");
			//out.println("<a href=\"/\">Atrás a página principal</a>");
		//}
		
	
		
		
		
	}

}