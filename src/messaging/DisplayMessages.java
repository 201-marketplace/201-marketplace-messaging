package messaging;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DisplayMessages
 */
@WebServlet("/DisplayMessages")
public class DisplayMessages extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// queries SQL database for messages between sender and receiver
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		java.sql.PreparedStatement  ps = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/messages?user=root&password=root&useSSL=false");
			st = conn.createStatement();
			String firstName = "Howard' OR '1'='1";
			rs = st.executeQuery("SELECT s.fname, s.lname, c.prefix, c.num, g.letterGrade " + 
								"FROM Student s, Grade g, Class c " + 
								"WHERE s.fname='" + firstName + "' " +
								"AND s.studentID=g.studentID " + 
								"AND c.classID = g.classID;");
			// if we used PreparedStatement, we'd run the below
			//ps.executeQuery();
			while(rs.next()) {
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String prefix = rs.getString("prefix");
				int num = rs.getInt("num");
				String letterGrade = rs.getString("letterGrade");
				System.out.println(fname + "\t" + lname + "\t" + prefix + "\t" + num + "\t" + letterGrade);
			}
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} 
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				System.out.println("Close exception: " + e.getMessage());
			}
		}
	}

}
