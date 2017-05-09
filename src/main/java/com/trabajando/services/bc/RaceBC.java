package com.trabajando.services.bc;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.trabajando.services.dao.RaceDAO;
import com.trabajando.services.entity.Race;
import com.trabajando.services.util.Constants;
import com.trabajando.services_core.bc.BaseBC;
import com.trabajando.services_core.util.App;
import com.trabajando.services_core.wrapper.ResponseWrapper;

/**
 * @author fchacon
 * Controller para los servicios orientados a users
 */
public class RaceBC extends BaseBC {
	
	static final Logger log = Logger.getLogger(RaceBC.class);
	static final String MEMCACHED_NAMESPACE = App.getInstance().getProperty("MEMCACHED_NAMESPACE");
	
	private static RaceBC userBC;
	public static RaceBC getInstance(String token) {
		if (userBC == null)
			userBC = new RaceBC();
		
		userBC.setToken(token);
		return userBC;
	}
	
	private RaceBC() {}
	
	private List<String> getRaceDayLinks(Long year) {
		
		List<String> links = new LinkedList<String>();
		Document doc;
		try {
			String baseUrl = "http://hipodromo.cl/hipodromochile/";
			doc = Jsoup.connect(baseUrl+"carreras-calendario-anual?id_pais_programa=&fecha_reunion=0000-00-00&fecha_reunion_ano="+year+"&fecha_reunion_mes=12#calendario_anual_12").get();
			
			Elements dates = doc.select("td.cal_resultado a");
			
			for (Element element : dates) {
				
				links.add(baseUrl+element.attr("href"));
	            
	        }
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return links;
	}
	
	/**
	 * @author fchacon
	 * @param year
	 * @param locale
	 * @return
	 */
	public ResponseWrapper getRaceDayLinks(Long year, String locale) {
		
		ResponseWrapper response = new ResponseWrapper();
		
		if(year == null || year <= Constants.SINCE_YEAR) {
			response.setError(-1, t("login.invalid", locale));
			return response;
		}
		
		if(locale == null || locale.trim().isEmpty() || locale.trim().length() != 5)
			locale = App.getInstance().getProperty("DEFAULT_LOCALE");
		
		List<String> links = getRaceDayLinks(year);
		response.setData(links);
		return response;
		
//		try {
//			
//			UserDAO userDAO = UserDAO.getInstance();
//			
//			// Obtener usuario desde BD
//			User user = userDAO.getByEmailPassword(userEmail, userPassword);
//			
//			// Login incorrecto o usuario inexistente
//			if(user == null) {
//				response.setError(-1, t("login.invalid", locale));
//				return response;
//			}
//			
//			// Generar token para el cliente
//			String clientToken = Security.generateClientToken(userEmail+"."+userPassword); // Token SHA-1
//			
//			// Crear response
//			UserTokenWrapper userTokenWrapper = new UserTokenWrapper();
//			userTokenWrapper.setUser(user);
//			userTokenWrapper.setToken(clientToken);
//			
//			// Guardar datos de sesion del usuario en memcache
//			UserTokenExpirationWrapper userTokenExpirationWrapper = Security.generateUserAuthenticationData(user, keepSession, 60);
//			MyCache.getInstance(MEMCACHED_NAMESPACE).set(clientToken, 0, userTokenExpirationWrapper);
//			
//			response.setData(userTokenWrapper);
//		}
//		catch (SQLException e) {
//			log.error("Ha ocurrido un error mysql con código: "+e.getErrorCode()+" y mensaje: "+e.getMessage());
//			//Se setea el error
//			response.setError(-1, t("services.error.internal", locale));
//		}
	}
	
	private List<Race> getResultLinks(Long year) {
		
		List<String> raceDayLinks = getRaceDayLinks(year);
		
		//Cada raceDayLink tiene un formato: http://hipodromo.cl/hipodromochile/carreras-buscar-programas?id_pais_programa=&fecha_reunion=2017-5-4
		
		List<Race> response = new LinkedList<Race>();
		String baseUrl = "http://hipodromo.cl/hipodromochile/";
		
		try {
			
			String indexOf = "fecha_reunion=";
			
			for(String raceDayLink: raceDayLinks) {
				
				int fechaReunionOffset = raceDayLink.indexOf(indexOf);
				int beginning = fechaReunionOffset + indexOf.length();
				String raceDateStr = raceDayLink.substring(beginning, raceDayLink.length() - 1); //formato aaaa-m-d
				String[] parts = raceDateStr.split("-");
				int raceYear = Integer.parseInt(parts[0]);
				int raceMonth = Integer.parseInt(parts[1]);
				int raceDay = Integer.parseInt(parts[2]);
				
				Calendar c = Calendar.getInstance();
				c.set(raceYear, raceMonth - 1, raceDay, 0, 0);  
				
				Date raceDate = c.getTime();
				
				Document doc = Jsoup.connect(raceDayLink).get();
				
				Elements anchors = doc.select("table tbody tr td:last-child a[href^=carreras-resultado-ver?id_carrera]");
				
				for (Element anchor: anchors) {
					
					Race race = new Race();
					race.setLink(baseUrl+anchor.attr("href"));
					race.setDate(raceDate);
					response.add(race);
		        }
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}
	
	public ResponseWrapper getResultLinks(Long year, String locale) {
		
		ResponseWrapper response = new ResponseWrapper();
		
		if(year == null || year <= Constants.SINCE_YEAR) {
			response.setError(-1, t("login.invalid", locale));
			return response;
		}
		
		if(locale == null || locale.trim().isEmpty() || locale.trim().length() != 5)
			locale = App.getInstance().getProperty("DEFAULT_LOCALE");
		
		List<Race> resultLinks = getResultLinks(year);
		response.setData(resultLinks);
		
		return response;
	}
	
	public ResponseWrapper saveResultLinks(Long year, String locale) {
		
		ResponseWrapper response = new ResponseWrapper();
		
		if(year == null || year <= Constants.SINCE_YEAR) {
			response.setError(-1, t("login.invalid", locale));
			return response;
		}
		
		if(locale == null || locale.trim().isEmpty() || locale.trim().length() != 5)
			locale = App.getInstance().getProperty("DEFAULT_LOCALE");
		
		List<Race> links = getResultLinks(year);
		
		RaceDAO raceDAO = RaceDAO.getInstance();
		
		try {
			raceDAO.saveResultLinks(links);
		} catch (SQLException e) {
			
			response.setError(-1, "Error al guardar los links");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.setData(true);
		return response;
	}
	
	public ResponseWrapper parseRaces(Long year, String locale) {
		
		ResponseWrapper response = new ResponseWrapper();
		
		if(year == null || year <= Constants.SINCE_YEAR) {
			response.setError(-1, t("login.invalid", locale));
			return response;
		}
		
		if(locale == null || locale.trim().isEmpty() || locale.trim().length() != 5)
			locale = App.getInstance().getProperty("DEFAULT_LOCALE");
		
		RaceDAO raceDAO = RaceDAO.getInstance();
		
		try {
			List<String> links = raceDAO.getRaceLinks(year.longValue());
//			raceDAO.saveResultLinks(links);
		} catch (SQLException e) {
			
			response.setError(-1, "Error al guardar los links");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.setData(true);
		return response;
	}
}