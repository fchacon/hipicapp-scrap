package com.trabajando.services.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.trabajando.services.entity.Race;
import com.trabajando.services_core.dao.BaseDAO;
import com.trabajando.services_core.entity.User;
import com.trabajando.services_core.util.ResultSetMapper;

/**
 * @author fchacon
 * DAO para la clase Race
 */
public class RaceDAO extends BaseDAO {
	
	private static RaceDAO raceDAO;
	public static RaceDAO getInstance() {
		if (raceDAO == null)
			raceDAO = new RaceDAO();
		
		return raceDAO;
	}
	
	private RaceDAO() {}

	static final Logger log = Logger.getLogger(RaceDAO.class);
	
	private static final String SAVE_RACE_LINK = "INSERT INTO race (rowDate, raceDate, link) VALUES (?, ?, ?)";
	
	private static final String GET_RACE_LINKS = "SELECT link FROM race WHERE YEAR(raceDate) = ? AND parsed = 0";
	
	public void saveResultLinks(List<Race> races) throws SQLException {

		try(Connection conn = getDatabaseInstance().getConnection();) {
			
			conn.setAutoCommit(false);
			
			try(PreparedStatement ps = conn.prepareStatement(SAVE_RACE_LINK);) {
			
				ps.setObject(1, new Date());
			
				for(Race race: races) {
					ps.setObject(2, race.getDate());
					ps.setString(3, race.getLink());
					ps.addBatch();
				}

				log.debug("[Query]: "+ps.toString());

				ps.executeBatch();
				
			} catch(SQLException e) {
				conn.rollback();
				conn.setAutoCommit(true);
				throw e;
			}
			
			conn.setAutoCommit(true);
		}
	}
	
	public List<String> getRaceLinks(long year) throws SQLException {
		
		try(
			Connection conn = getDatabaseInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(GET_RACE_LINKS);
		) {
			ps.setLong(1, year);
			log.debug("[Query]: "+ps.toString());
			
			List<String> response = new LinkedList<String>();
			try(ResultSet rs = ps.executeQuery();){
				while(rs.next()){
					response.add(rs.getString("link"));
				}
				
				return response;
			}
		}
	}

	/**
	 * Método que llena un objeto de tipo user considerando las notaciones de columna de la clase USER, esto para poder usar el mapper.
	 * @param rs
	 * @return
	 */
	public User fill(ResultSet rs) {
		ResultSetMapper<User> resultSetMapper = new ResultSetMapper<User>();	
		// Obtener datos basicos del usuario
		User user = resultSetMapper.mapResultSetToObject(rs, User.class);

		return user;
	}
}
