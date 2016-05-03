package com.mystrutsdemo.cai.app.service.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mystrutsdemo.cai.app.dao.user.UserBean;
import com.mystrutsdemo.cai.app.dao.user.User;


public class UserServices 
{

	private String url = "jdbc:mysql://localhost:3306/mystrutsdemo";
	private String user ="root";
	private String pass = "123456";
	public User serchByusername (String username) throws Exception
	{
		String sq = "select * from user where username=";
		String sql = sq+change(username);
		//加载驱动
		Class.forName("com.mysql.jdbc.Driver");
		//建立连接
		Connection conn =DriverManager.getConnection(url,user,pass);
		//创建sql语句
		Statement st = conn.createStatement();
		User userBean = new User();
		ResultSet rs =st.executeQuery(sql);
		while(rs.next())
		{
			userBean.setUsername(rs.getString("username"));
			userBean.setPassword(rs.getString("password"));
		}
		rs.close();
		st.close();
		conn.close();
		return userBean;
	}
	//插入用户信息
	public int insert(User userBean) throws Exception
	{
//		String user = userBean.getUsername();
//		String pass =userBean.getPassword();
//		String mail = userBean.getMail();
//		String sql = sq+user+","+pass+","+mail+")";
		//加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //建立连接
        Connection conn =DriverManager.getConnection(url,user,pass);
        
        String sql ="insert into user values (null,?,?,?,?,?,?,?,?,?,now())";
        //创建sql语句
        PreparedStatement PreparedStatement =conn.prepareStatement(sql);
        PreparedStatement.setString(1,userBean.getUsername());
        PreparedStatement.setString(2,userBean.getPassword());
        PreparedStatement.setString(3,userBean.getEmail());
        PreparedStatement.setString(4, userBean.getGender());
        
        PreparedStatement.setTimestamp(5, userBean.getBirthday());
        PreparedStatement.setString(6,userBean.getQq());
        PreparedStatement.setString(7,userBean.getPhone());
        PreparedStatement.setString(8, userBean.getDescription());
        PreparedStatement.setTimestamp(9, userBean.getRegtime());
        
        
        int result = PreparedStatement.executeUpdate();
        
        PreparedStatement.close();
        conn.close();
        return result;
        
	}
	
	public int getUserAmount() throws Exception
	{
		int i = 0;
		String sql = "select count(*) from user";
		PreparedStatement PreparedStatement = null;
		ResultSet  rs = null;
		//加载驱动
        
			Class.forName("com.mysql.jdbc.Driver");
			 //建立连接
	        Connection conn =DriverManager.getConnection(url,user,pass);
			
			PreparedStatement =conn.prepareStatement(sql);
			rs = PreparedStatement.executeQuery();
			while(rs.next())
			{
				i = rs.getInt(1);
			}
			rs.close();
			PreparedStatement.close();
		return i;
       
		
	}
	
	public List<User> getUserList(int pageNumber,int pageSize) throws Exception
	{
		List<User> userList = new ArrayList<User>();
		String sql = "select * from user limit ?,?";
		
		PreparedStatement PreparedStatement = null;
		ResultSet  rs = null;
		//加载驱动
        
			Class.forName("com.mysql.jdbc.Driver");
			 //建立连接
	        Connection conn =DriverManager.getConnection(url,user,pass);
			
			PreparedStatement =conn.prepareStatement(sql);
			PreparedStatement.setInt(1, (pageNumber-1)*pageSize);
			PreparedStatement.setInt(2, pageSize);
			
			rs = PreparedStatement.executeQuery();
			while(rs.next())
			{
				User user = new User();
				user.setId(rs.getInt("id"));
				userList.add(user);
			}
			rs.close();
			PreparedStatement.close();
		
		return userList;
		
	}
	
	public String change(String  s)
	{
		String  sc = "'"+s+"'";
		return sc;
	}
	
//	
//	public static void main(String args[])
//	{
//		UserService lg = new UserService();
//		UserBean userBean = new UserBean();
////		userBean.setUsername("lala");
////		userBean.setPassword("123");
////		userBean.setMail("1234@qq.com");
//		try {
//			System.out.println(lg.serchByusername("lala").getUsername());
//			System.out.println(lg.insert(userBean));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}