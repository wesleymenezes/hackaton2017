package com.ericsson.edb.hackaton.heroku.database;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

@Stateless
@Resource(name = "serverDS", type = DataSource.class)
public class DataSourceLoader {

	private Connection connection = null;
	
//	@Inject
//	private DataSource ds;

  @Resource(name = "serverDS")
  private DataSource ds;
	
	public Connection getConnection() throws SQLException {
		 
//		try{
//		    Context init = new InitialContext(); 
//		    Context ctx = (Context) init.lookup("java:"); 
//		    ds = (DataSource)ctx.lookup("ServerDS"); 
//		    ctx.close();
//		} catch (NamingException e) {
//		    e.printStackTrace();
//		}
	  
    try {
      connection = ds.getConnection();
      System.out.println(">>> " + connection.getMetaData().getURL());
    } catch (SQLException e) {
        e.printStackTrace();
    }

		return connection;
		 
	}
}
