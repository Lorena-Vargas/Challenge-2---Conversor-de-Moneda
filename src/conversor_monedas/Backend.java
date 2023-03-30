package conversor_monedas;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Backend {
	
	//PF = Punto Final al que le haremos solicitud 
	//TCUSD = Tasas de Cambio USD
	private static final String PF_MONEDAS = "https://openexchangerates.org/api/currencies.json"; 
	private static final String PF_TCUSD = "https://openexchangerates.org/api/latest.json"; 
	private static final String APP_ID = "?app_id=d07e10549a0941b2b7c176bc338f5a94";
	private static final String SOLICITUD_TCUSD = PF_TCUSD + APP_ID;
	
	
	//Asignar atributo estatico para guardarnuestras monedas, constructor static, se inicializa antes, el primer arranque de una clase
	 private static final Map<String, String> MONEDAS;
	 private static final List<String> NOMBRES;
	 private static final Map<String, Double> TCUSD;
	 
	static {
		MONEDAS = recuperarMonedas();
		NOMBRES = obtenerNombresDeMoneda();
		TCUSD = recuperarTasasDeCambioUSD();
	}
	
	private static List<String> obtenerNombresDeMoneda(){
		return MONEDAS.keySet().stream().sorted().collect(Collectors.toList());
	}
	
	
	//Crear método genérico que obtenga InputStream, para las 2 solicitudes que haremos, monedas y tasas de cambio. 
	private static InputStream recuperarDatos(String PF) {
		InputStream datosPuros = null;
		
		//Solicitud de API, crear objeto URL y pasarle el punto final
		//Si URL no existe o hay error me lanzará una exception 
		
		try {
			URL url = new URL(PF);
		
		//Establecer la conexión
			HttpURLConnection conexion = (HttpURLConnection)url.openConnection();
		
		//Checar que tipo de conexion permite API
			conexion.setRequestMethod("GET");
		
		//Recuperar los datos; si la condición es exitosa dejame continuar si no lanza exception
			if(conexion.getResponseCode() != 200) throw new IOException("Conexión fallida");
			datosPuros = conexion.getInputStream();
			} catch(Exception e) {
				e.printStackTrace();
				}
		return datosPuros;
	}
	
	//Método para que nos devuelva map y pasarlo a JSON, 2 strings, código de moneda y nombre de moneda
	public static Map<String, String> recuperarMonedas() {
		Map<String, String> monedas = null;
		
		InputStream datosPuros = recuperarDatos(PF_MONEDAS);
		ObjectMapper json = new ObjectMapper();
			
		
		//Convertir JSON a map string, string y convertir a variable monedasa
		
		//bytes que vienen deserializados a desearilizar
		
		//new TypeReference<T>() {}
		//T: Map<String, String
		//new TypeReference<Map<String>> () {}
		
		
		try { 
			monedas = json.readValue(datosPuros, new TypeReference<Map<String, String>>() {});
			monedas = monedas.entrySet().stream().collect(
					Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey)
			);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return monedas;
	}

	public static List<String> getNombres() {
		return NOMBRES;
	}
	
	private static Map<String, Double > recuperarTasasDeCambioUSD() {


		Map<String, Double> tasasDeCambioUSD = null;
		
		InputStream datosPuros = recuperarDatos(SOLICITUD_TCUSD);
		ObjectMapper json = new ObjectMapper();
		
		try {
			tasasDeCambioUSD = json.readValue(datosPuros, TasasDeCambio.class).getValores();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return tasasDeCambioUSD;
	}
	
	private static Double recuperarTasaDeCambioUSD(String moneda) {
		String codigo = MONEDAS.get(moneda);
		return TCUSD.get(codigo);
	}
	
	public static Double recuperarTasaDeCambio(String moneda1, String moneda2) {
		Double tasaDeCambioUSD1 = recuperarTasaDeCambioUSD(moneda1);
		Double tasaDeCambioUSD2 = recuperarTasaDeCambioUSD(moneda2);
		
		return tasaDeCambioUSD2 / tasaDeCambioUSD1; 
	}

}
