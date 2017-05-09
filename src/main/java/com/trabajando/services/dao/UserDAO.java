package com.trabajando.services.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;

import com.trabajando.services_core.dao.BaseDAO;
import com.trabajando.services_core.entity.User;
import com.trabajando.services_core.util.ResultSetMapper;
import com.trabajando.services_core.util.Util;

/**
 * @author fchacon
 * DAO para la clase User
 */
public class UserDAO extends BaseDAO {
	
	private static UserDAO userDAO;
	public static UserDAO getInstance() {
		if (userDAO == null)
			userDAO = new UserDAO();
		
		return userDAO;
	}
	
	private UserDAO() {}

	static final Logger log = Logger.getLogger(UserDAO.class);

	private final static String GET_BY_EMAIL_PASSWORD = "SELECT u.userId AS userId, "
			+ "u.username AS userUsername, "
			+ "u.firstname AS userFirstname, "
			+ "u.secondname AS userSecondname, "
			+ "u.lastname AS userLastname, "
			+ "u.secondlastname AS userSecondlastname, "
			+ "u.email AS userEmail, "
			+ "u.phoneNumber AS userPhoneNumber, "
			+ "u.cellphoneNumber AS userCellphoneNumber, "
			+ "u.locale AS userLocale, "
			+ "ua.active AS userActive,"
			+ "ua.lastLogin AS userLastLogin,"
			+ "u.confirmedAccount AS userConfirmedAccount "
			+ "FROM user u "
			+ "INNER JOIN user_admin ua ON u.userId = ua.userId "
			+ "WHERE (u.email = ? OR u.username = ?) "
			+ "AND u.password = SHA1(CONCAT(SHA1(?), salt)) "
			+ "AND ua.active = 1 "
			+ "AND ua.deleted = 0";

	private final static String DELETE_USER = "UPDATE user u "
			+ "INNER JOIN user_admin ua ON ua.userId = u.userId "
			+ "SET ua.deleted = 1, ua.deletionDate = ? "
			+ "WHERE u.userId IN (%s) "
			+ "AND ua.adminId = ? "
			+ "AND ua.deleted = 0";
	
	private static final String CHECK_USERS = "SELECT COUNT(*) AS numberOfUsers "
			+ "FROM user_admin "
			+ "WHERE userId IN (%s) "
			+ "AND adminId = ? "
			+ "AND deleted = 0";


	/**
	 * @param email_username
	 * @param password
	 * @param localefill
	 * @return Un User en caso que exista un registro en BD con dicho email (o username) y password. Retorna null en caso contrario.
	 * @throws SQLException
	 */
	public User getByEmailPassword(String email_username, String password) throws SQLException {

		try(
			Connection conn = getDatabaseInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(GET_BY_EMAIL_PASSWORD);
		) {
			
			int i = 1;
			ps.setString(i++, email_username);
			ps.setString(i++, email_username);
			ps.setString(i++, password);

			log.debug("[Query]: "+ps.toString());

			try(ResultSet rs = ps.executeQuery();){
				if(rs.next()) {
					User user = fill(rs);
					return user;
				}
			}
		}

		return null;
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

	/**
	 * @author Carlos Dettoni
	 * Método que llama a la base de datos para realizar la eliminación lógica
	 * de una lista de cuentas de usuario.
	 * @param userIds
	 * @param adminId
	 * @throws SQLException
	 */
	public void delete(Set<Long> userIds, long adminId) throws SQLException {
		if(userIds == null || userIds.isEmpty() || adminId <= 0)
			return;
		
		String userIdsComma = Util.printObjectIdToComma(userIds);
		String DELETE_USER_REPLACED = String.format(DELETE_USER, userIdsComma);
		try(
			Connection conn = getDatabaseInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(DELETE_USER_REPLACED);
		) {
			ps.setObject(1, new Date());
			ps.setLong(2, adminId);

			log.debug("[UPDATE]: "+ps.toString());
			ps.executeUpdate();
		}
	}
	
	/**
	 * Método que retorna true si todos los ids de usuarios pertenecen al adminId entregado como parámetro. Retorna false en caso contrario.
	 * Se ocupa como argumento un Set para no permitir ids duplicados
	 * @author fchacon
	 * @param userIds
	 * @param adminId
	 * @return
	 * @throws SQLException
	 */
	public boolean checkUsers(Set<Long> userIds, long adminId) throws SQLException {
		if(userIds == null || userIds.isEmpty() || adminId <= 0)
			return false;
		
		String userIdsComma = Util.printObjectIdToComma(userIds);
		String CHECK_USERS_REPLACED = String.format(CHECK_USERS, userIdsComma);
		
		try(
			Connection conn = getDatabaseInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(CHECK_USERS_REPLACED);
		) {
			ps.setLong(1, adminId);
			log.debug("[Query]: "+ps.toString());
			
			try(ResultSet rs = ps.executeQuery();){
				if(rs.next()){
					
					long numberOfUsers = rs.getLong("numberOfUsers");
					if(numberOfUsers == userIds.size())
						return true;
				}
			}
		}
		
		return false;
	}
}
