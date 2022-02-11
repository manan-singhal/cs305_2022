package Project;

import java.sql.ResultSet;
import java.util.Map.Entry;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.sql.DriverManager;

import Model.City;

public class DatabaseConnector implements SqlRunner {
    
    static HashMap<String, String> hashMap = XmlExtractor.getCommandsFromXmlFile();

    public static void main(String args[]) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(     
            "jdbc:mysql://localhost:3306/sakila?characterEncoding=latin1","root","Manans@321");  
            //Statement statement = connection.createStatement();
            for (Entry<String, String> set : hashMap.entrySet()) {
                System.out.println(set.getKey() + ":: " + set.getValue());


                // To check if a constant data is added to a xml file.

                /*String crudOperator = set.getValue().substring(0, set.getValue().indexOf(' '));
				crudOperator = crudOperator.toUpperCase();

                if (crudOperator.equals("SELECT")) {
                    ResultSet resultSet = statement.executeQuery(set.getValue());
                    while (resultSet.next()) {
                        System.out.println(resultSet.getInt(1) + "  " + resultSet.getString(2) + 
                            "  " + resultSet.getString(3));
                    }
                }
                else if (crudOperator.equals("INSERT") || crudOperator.equals("DELETE") || 
                    crudOperator.equals("UPDATE")) {
                    int rowsAffected = statement.executeUpdate(set.getValue());
                    System.out.println(crudOperator + ": Rows affected:- " + rowsAffected);
                }*/
            }
            connection.close();
        }
        catch(Exception e) {
            System.out.println(e);
        } 
    }

    @Override
    public Object selectOne(String queryId, Object queryParam, Class resultType) {
        try {  
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(     
            "jdbc:mysql://localhost:3306/sakila?characterEncoding=latin1","root","Manans@321");  
            Statement statement = connection.createStatement();
            //Create a instance of city class to return
            City city = new City();
            ResultSet resultSet = statement.executeQuery(getCommandInStringFormat(queryId, queryParam));
            resultSet.next();
            city.city_id=resultSet.getInt(1);
            city.city = resultSet.getString(2);
            city.country_id=resultSet.getInt(3);
            city.last_update=resultSet.getString(4);
            connection.close();
            return city;
             
        }
        catch(Exception e) {
            return null;
        }
        
    }

    @Override
    public List<?> selectMany(String queryId, Object queryParam, Class resultItemType) {
        try {  
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(     
            "jdbc:mysql://localhost:3306/sakila?characterEncoding=latin1","root","Manans@321");  
            Statement statement = connection.createStatement();
            //Create a instance of list of city class to return
            List<City> cityList = new ArrayList<City>();
            ResultSet resultSet = statement.executeQuery(getCommandInStringFormat(queryId, queryParam));
            while (resultSet.next()) {
                City city = new City();
                city.city_id = resultSet.getInt(1);
                city.city = resultSet.getString(2);
                city.country_id = resultSet.getInt(3);
                city.last_update = resultSet.getString(4);
                cityList.add(city);
            }
            connection.close();
            return cityList;
             
        }
        catch(Exception e) {
            return null;
        }
    }

    @Override
    public int insert(String queryId, Object queryParam) {
        try {  
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(     
            "jdbc:mysql://localhost:3306/sakila?characterEncoding=latin1","root","Manans@321");  
            Statement statement = connection.createStatement();
            //return rows affected class
            int rowsAffected = statement.executeUpdate(getCommandInStringFormat(queryId, queryParam));
            connection.close();
            return rowsAffected;
             
        }
        catch(Exception e) {
            return 0;
        }
    }

    @Override
    public int update(String queryId, Object queryParam) {
        try {  
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(     
            "jdbc:mysql://localhost:3306/sakila?characterEncoding=latin1","root","Manans@321");  
            Statement statement = connection.createStatement();
            //return rows affected class
            int rowsAffected = statement.executeUpdate(getCommandInStringFormat(queryId, queryParam));
            connection.close();
            return rowsAffected;
        }
        catch(Exception e) {
            return 0;
        }
    }

    @Override
    public int delete(String queryId, Object queryParam) {
        try {  
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(     
            "jdbc:mysql://localhost:3306/sakila?characterEncoding=latin1","root","Manans@321");  
            Statement statement = connection.createStatement();
            //return rows affected class
            int rowsAffected = statement.executeUpdate(getCommandInStringFormat(queryId, queryParam));
            connection.close();
            return rowsAffected;
        }
        catch(Exception e) {
            return 0;
        }
    }

    //Returning a proper query after replacing with a variable
    private String getCommandInStringFormat (String queryId, Object queryParam) {
        String xmlCommand = "";
        for (Entry<String, String> set : hashMap.entrySet()) {
            if (queryId.equals(set.getKey())) {
                xmlCommand = set.getValue();
                String[] queryParamList = ((String) queryParam).split(",");
                
                int count = 0;
                int checkBrackets = xmlCommand.indexOf("${");
                while (checkBrackets != -1) {
                    if (queryParamList.length <= count) {
                        return "";
                    }
                    xmlCommand = xmlCommand.substring(0, 
                        xmlCommand.indexOf("${")) + queryParamList[count].trim() + 
                        xmlCommand.substring(xmlCommand.indexOf("}")+1, 
                        xmlCommand.length());
                    checkBrackets = xmlCommand.indexOf("${");
                    count++;
                }
            }
        }
        return xmlCommand;
    }
}