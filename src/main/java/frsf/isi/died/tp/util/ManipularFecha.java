package frsf.isi.died.tp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ManipularFecha {

	
	public static Date getDateFromString(String s) {
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
		Date fecha = null;
		try {
			
			fecha = formatoDelTexto.parse(s);
			
		} catch (ParseException ex) {

			ex.printStackTrace();

		}
		return fecha;
	}
	
	public static String getFechaActual() {
	    Date ahora = new Date();
	    SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
	    return formateador.format(ahora);
	}
	
}
