<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.net.*" %>
<%@ page import = "com.google.gson.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" href="style.css" type="text/css">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Practica Final</title>
</head>
<body>
	<div id="titulo">
		<h1>Health Score Calculator</h1>
	</div>
	<div id="cuerpo">
		<form method="POST" action="/Controller" id = "riskMeasure">
		<ul>
			<li><a href="listar">Listar clientes</a></li>
			<li><p>Nombre y Apellidos: </p><input type="text" name="nombre"/> <br/></li>
			
		</ul>
		<div id="subtitulo">
			<hr />
			<p><b>Introduzca los datos requeridos:</b></p>
		</div>
		<div id="formulario">
				<b>Metric Health Model: </b><br/>
			<ul>
				<li>Edad : <input type="text" name="edad"/> <br/></li>
				<li>Genero: <p id="genero">Hombre: <input type="radio" name="genero" id="hombre" value ="1"/> Mujer: <input type="radio" name="genero" id="mujer" value ="0"/></li>
			</ul>
			<ul>
				<li>Altura (cm): <input type="text" name="altura"/> cm <br/></li>
				<li>Peso (kg): <input type="text" name="peso"/> kg<br/></li>
			</ul>
				<b>Fumadores: </b><br/>
			<ul>
				<li>Actualmente: <p>Si: <input type="radio" name="smoker" value ="1"/> No: <input type="radio" name="smoker" value="0"/><br/></li>
				<li>En algun momento <p>Si: <input type="radio" name="smokerever" value ="1"/> No: <input type="radio" name="smokerever" value ="0"/><br/></li>
				<li>Años Fumando (0-11 años): <input type="text" name="tiempoFumando"/><br/></li>
				<li>Numero de Cigarros al Día (0-30 años): <input type="text" name="numeroAlDia"/><br/></li>
			</ul>
				<b>Descanso: </b><br/>
			<ul>
				<li>Tiempo en la cama hoy (horas): <input type="text" name="tiempoCama"/><br/></li>
				<li>Tiempo dormido/a hoy (horas): <input type="text" name="tiempoDormido"/><br/></li>
				<li>Veces despertandose: <input type="text" name="vecesDespertandose"/><br/></li>
				<li>Transferencia de Actividades Fisicas (en MET por hora): <input type="text" name="movement"><br></li>
				<li>Calidad de Vida: ¿Crees que estás saludable? (0/1)<input type="text" name="q01"><br></li>
			</ul>
				<input type="submit" name="send" value="CheckmyHealth"/>
			
		</div>
		<div id="respuesta">Resultados: <p id="respuestaTexto" name="respuestaTexto">
		
		<% if (request.getAttribute("change") == null){
			
		%>
		No hay resultados disponibles
			<ul>
				<input type="submit" name="send" value="Instrucciones">
			</ul>
			</form>
		
			</p>
		<% 	
			
			
		}else if (new Integer(request.getAttribute("change").toString()).intValue() == 1){
			
			%>
			<strong>Cliente: <%= request.getAttribute("nombre") %></strong><br>
			<strong><%=request.getAttribute("respuestaTexto").toString() %></strong><br><br>
			<% JsonObject jsonObj = JsonParser.parseString(request.getAttribute("jsonfile").toString()).getAsJsonObject(); %>
			Media total (0-1000) : <%=jsonObj.get("scr").getAsString() %><br>
			Límite inferior de confianza (0-1000): <%=jsonObj.get("lcl").getAsString() %><br>
			Límite superior de confianza (0-1000): <%=jsonObj.get("ucl").getAsString() %><br>
			Años de calidad ganados/perdidos: <%=jsonObj.get("rqy").getAsString() %><br><br>
			
			Principales componentes para la puntuación de la salud<br><br>
			
			Puntuación del estilo de vida (0-1000): <%=jsonObj.getAsJsonObject("components").get("lfs").getAsString() %><br>
			Puntuación corporal (0-1000): <%=jsonObj.getAsJsonObject("components").get("bdy").getAsString() %><br>
			Puntuación sensorial (0-1000): <%=jsonObj.getAsJsonObject("components").get("fee").getAsString() %><br>
			Puntuación de movimiento (0-1000): <%=jsonObj.getAsJsonObject("subscores").get("mvm").getAsString() %><br>
			Puntuación de nutrición (0-1000): <%=jsonObj.getAsJsonObject("subscores").get("nut").getAsString() %><br>
			(Anti-) Puntuación de fumador (0-1000): <%=jsonObj.getAsJsonObject("subscores").get("smk").getAsString() %><br>
			(Anti-) Puntuación de obesidad (0-1000): <%=jsonObj.getAsJsonObject("subscores").get("obe").getAsString() %><br>
			Puntuación de sueño (0-1000): <%=jsonObj.getAsJsonObject("subscores").get("slp").getAsString() %><br>
			(Anti-) Puntuación de depresión (0-1000): <%=jsonObj.getAsJsonObject("subscores").get("dep").getAsString() %><br>
			Puntuación de bienestar (0-1000): <%=jsonObj.getAsJsonObject("subscores").get("wel").getAsString() %><br>
			(Anti-) Puntuación de estrés (0-1000): <%=jsonObj.getAsJsonObject("subscores").get("str").getAsString() %><br>
			
			
			<ul>
				<input type="submit" name="send" value="Instrucciones">
			</ul>
			</form>
			
			</p>
			
			<% 
			
		}else{
			%>
			
			No hay resultados disponibles
			<ul>
				<input type="submit" name="send" value="Instrucciones">
			</ul>
			
			</form>
			
			
			</p>
			
			<% 
		}
			%>
		
		</div>
		<div id="footer">Autores: Javier Caminos Y Rodrigo Lopez de Toledo</div>
	</div>
</body>
</html>