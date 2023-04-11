package conversor_monedas.ComponenteVisual;

import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;

public class Cantidad extends JFormattedTextField { //Validación de texto, solo int.
	
	//Crear constructor de instancia 
	public Cantidad(){
		super();
		
		//clase anónima, creamos instancia pero no la vamos a asignar a nada (crear una variable).
		//Desde dentro del código de la clase anonimo voy a ponerlo a la instancia que se esta creando
		//this aquí seria referencia a la cantidad
		
		new NumberFormatter() {                //Cada que ingreso texto en cantidad verifica todo el tiempo que estas ingresando
			
			{
			   DecimalFormat formato = new DecimalFormat();
			   formato. setGroupingUsed(true);  
			   formato.setGroupingSize(3);  //Formato, cada cuantos digitos da 	
			   
			   this.setFormat(formato);          // Inicializar el formateador
			   this.setAllowsInvalid(false);     //No permitiremos letras ni signos
			   this.setValueClass(Double.class); //Solo numeros double
			   this.setMinimum(0d);              //Desde double, numero minimo
			   this.setMaximum(10000000d);       //Maximo numero de digitos que valide
			   
			   this.install(Cantidad.this);      //No se puede poner el this desde el constructor de formater porque haría referencia a number formatter
			                                     //Por eso hacemos referencia a la clase cantidad desde dentro de la clase anonima
			   
		     }
			
			@Override
			   public Object stringToValue(String s) throws ParseException { //Invalidar este metodo del formateador, que verifica y arroja el valor en pantalla,deja el ultimo numero en pantalla
				   try {                                                    //Lanza una excepción 
					   String contenido = Cantidad.this.getText();  //Que cadena no tenga ningun caracter ni numero 
					   if("0".equals(contenido)) s= s.replaceAll("0", ""); //Quitar el 0
					   return super.stringToValue(s);

				   } catch(ParseException e) {
					 if(s.isEmpty())  return 0;
					 else throw e;                                    //Si contenido es vacio dejame un 0 
					 }
			   }
		};
	}
	
	public double obtenerValor() {     //Tomar lo introducido al campo de texto para quitar las comas
		String entrada = this.getText().replace(",", "");       //Entrar al campo de texto en variable lo guardamos para poderlo obtener  
		// entrada = entrada.replace(",", "");                  Lo mismo de arriba, paso a paso
		return Double.parseDouble(entrada);     //Ya quitamos las comas, queremos que nos devuelva la cantidad 
	}

}
