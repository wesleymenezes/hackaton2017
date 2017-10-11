package com.ericsson.edb.hackaton.heroku.mediator;

import java.sql.SQLException;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.ericsson.edb.hackaton.heroku.database.CustomerDAO;
import com.ericsson.edb.hackaton.heroku.model.Customer;

@Dependent
public class CustomerServicesMediator {

  @Inject
  private CustomerDAO customerDAO;

  public List<Customer> getCustomers(int id) throws SQLException{
    return customerDAO.getCustomer(id);
  }
   
  public void createCustomers(Customer customer) throws SQLException{
    customerDAO.createCustomer(customer);;
  }

  public int deleteCustomers(int id) throws SQLException{
    return customerDAO.deleteCustomer(id);
  }

}
