package com.ericsson.edb.hackaton.heroku.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.ericsson.edb.hackaton.heroku.model.Customer;

public class CustomerDAO {
	
	@Inject
	private DataSourceLoader dsLoader;
	
	public List<Customer> getCustomer(Integer id) throws SQLException {
		String query = "select cst_id, cst_name, cst_email  from hck_customer";
		if (id > 0) {
		  query = query + "where cst_id = " + id; 
		}

		Connection con = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<Customer> customers = new ArrayList<Customer>();
    try {
      con = dsLoader.getConnection();
  		statement = con.prepareStatement(query);
  		result = statement.executeQuery();
  		while (result.next()) {
  		  Customer customer = new Customer();
  		  customer.setId(result.getInt("cst_id"));
  		  customer.setName(result.getString("cst_name"));
  		  customer.setEmail(result.getString("cst_email"));
  		  customers.add(customer);
  		}
		} finally {
		  if (result != null) result.close();
      if (statement != null) statement.close();
		  if (con != null) con.close(); //devolve conexao para o pool
		}
		return customers;
	}
	
  public void createCustomer(Customer customer) throws SQLException {
    String query = "insert into hck_customer (cst_id, cst_name, cst_email) "
                  + "values ('"+customer.getId()+"','"+customer.getName()+"','"+customer.getEmail()+"')";
    Connection con = null;
    PreparedStatement statement = null;
    ResultSet result = null;
    try {
      con = dsLoader.getConnection();
      
      Statement st = con.createStatement();
      st.executeUpdate(query);

    } finally {
      if (result != null) result.close();
      if (statement != null) statement.close();
      if (con != null) con.close(); //devolve conexao para o pool
    }
  }

  public int deleteCustomer(int id) throws SQLException {
    String query = "delete from hck_customer where cst_id = "+id;
    int numberOfCustomers = 0;
    
    Connection con = null;
    PreparedStatement statement = null;
    ResultSet result = null;


    try {
      con = dsLoader.getConnection();
      
      Statement st = con.createStatement();
      numberOfCustomers = st.executeUpdate(query);

    } finally {
      if (result != null) result.close();
      if (statement != null) statement.close();
      if (con != null) con.close(); //devolve conexao para o pool
    }
    
    return numberOfCustomers;
  }

}
