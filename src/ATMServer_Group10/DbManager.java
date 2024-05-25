package ATMServer_Group10;

import java.awt.GridLayout;
import java.awt.GridLayout;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ATMServer_Group10.User;

public class DbManager {
	private Connection connection;
    //数据库连接字符串，这里的deom为数据库名
    private static final String URL="jdbc:mysql://localhost:3306/javaex?useUnicode=true&characterEncoding=utf8&useSSL=true&serverTimezone=UTC";
    private static final String USERNAME="root";//登录名
    private static final String PASSWORD="drj38784543.";//密码
    

    public DbManager() {
    	//1.加载驱动
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
            //添加一个println，如果加载驱动异常，检查是否添加驱动，或者添加驱动字符串是否错误
			System.out.println("未能成功加载驱动程序，请检查是否导入驱动程序！");
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		        System.out.println("获取数据库连接成功！");
		} catch (SQLException e) {
            //添加一个println，如果连接失败，检查连接字符串或者登录名以及密码是否错误
			System.out.println("获取数据库连接失败！");
			e.printStackTrace();
		}
    }
    
    public boolean addUser(User user) {
        try {
            String query = "INSERT INTO atm_data (userid, passwd, amount) VALUES (?, ?, ?)";
        	PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getUserid());
            statement.setString(2, user.getPasswd());
            statement.setInt(3, 0);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public User getUser(String userid) {
        try {
            String query = "SELECT * FROM atm_data WHERE userid = ?";
        	PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userid);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String passwd = resultSet.getString("passwd");
                return new User(userid, passwd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Double getAmount(String userid) {
        try {
        	String query = "SELECT * FROM atm_data WHERE userid = ?";
        	PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userid);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Double amount = resultSet.getDouble("amount");
                return amount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean checkUserid(String userid) {
    	try {
            String query = "SELECT * FROM atm_data WHERE userid = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userid);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            } else {
            	return false;
            }
    	} catch (SQLException e) {
            e.printStackTrace();
            return false;
    	}
    }
    
    public boolean updateAmount(String userid, double wdra) {
    	try {
            String query = "UPDATE atm_data SET amount = amount - " + wdra + " WHERE userid = '" + userid + "'";
            PreparedStatement statement = connection.prepareStatement(query);
            int rowsAffected = statement.executeUpdate(query);
            
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void insertJournal(String userid, String operation) {
        String query = "INSERT INTO journal (time, userid, operation) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            statement.setTimestamp(1, currentTime);
            statement.setString(2, userid);
            statement.setString(3, operation);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        
}
