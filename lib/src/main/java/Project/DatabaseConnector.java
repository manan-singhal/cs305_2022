package Project;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import Model.City;

public class DatabaseConnector implements SqlRunner {
    
    static HashMap<String, String> hashMap = XmlExtractor.extractXml();

    public static void main(String args[]) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(     
            "jdbc:mysql://localhost:3306/sakila?characterEncoding=latin1","root","Manans@321");  
            Statement statement = connection.createStatement();

            for (Entry<String, String> set : hashMap.entrySet()) {
                String crudOperator = set.getValue().substring(0, set.getValue().indexOf(' '));
				crudOperator = crudOperator.toUpperCase();

                if (crudOperator.equals("SELECT")) {
                    ResultSet resultSet = statement.executeQuery(set.getValue());
                    while (resultSet.next()) {
                        System.out.println(resultSet.getInt(1) + "  " + resultSet.getString(2) + 
                            "  " + resultSet.getString(3));
                    }
                }
                else if (crudOperator.equals("INSERT") || crudOperator.equals("DELETE") || crudOperator.equals("UPDATE")) {
                    int rowsAffected = statement.executeUpdate(set.getValue());
                    System.out.println(crudOperator + ": Rows affected:- " + rowsAffected);
                }
            }
            connection.close();
        }
        catch(Exception e) {
            System.out.println(e);
        } 
    }

    @Override
    public Object selectOne(String queryId, Object queryParam, Class resultType) {
        
        /*try {  
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(     
            "jdbc:mysql://localhost:3306/sakila?characterEncoding=latin1","root","Manans@321");  
            Statement statement = connection.createStatement();
            Actor x=new Actor();
            
            hashMap = XmlExtractor.extractXml();
            for (Entry<String, String> set : hashMap.entrySet()) {
                if (queryId.equals(set.getKey())) {
                    String var;
                    if(queryParam instanceof Integer) {
                        var = set.getValue().substring(0, set.getValue().indexOf("${")) + queryParam + ";";
                    }
                    else {
                        var = set.getValue().substring(0, set.getValue().indexOf("${")) + "\"" + queryParam + "\";";
                    }
                    ResultSet resultSet = statement.executeQuery(var);
                    resultSet.next();
                    x.actor_id=resultSet.getInt(1);
                    x.first_name = resultSet.getString(2);
                    x.last_name=resultSet.getString(3);
                    x.last_update=resultSet.getString(4);
                    //x.last_update=resultSet.getString(4);
                    while (resultSet.next()) {
                        System.out.println(resultSet.getInt(1) + "  " + resultSet.getString(2) + 
                            "  " + resultSet.getString(3));
                    }
                }
            }
            connection.close();
            return x;
             
        }
        catch(Exception e) {
            System.out.println(e);
            return null;
        }*/
        
        try {  
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(     
            "jdbc:mysql://localhost:3306/sakila?characterEncoding=latin1","root","Manans@321");  
            Statement statement = connection.createStatement();
            City x=new City();
            hashMap = XmlExtractor.extractXml();
            for (Entry<String, String> set : hashMap.entrySet()) {
                if (queryId.equals(set.getKey())) {
                    String var = set.getValue();
                    String[] qpl = ((String) queryParam).split(",");
                    for (int i=0; i<qpl.length; i++) {
                        qpl[i] = qpl[i].trim();
                    }
                    int count = 0;
                    int checkBrackets = var.indexOf("${");
                    String n = "";
					while (checkBrackets != -1) {
                        n = var.substring(0, var.indexOf("${")) + qpl[count] + var.substring(var.indexOf("}")+1, var.length());
						checkBrackets = n.indexOf("${");
                        var=n;
                        count++;
					}
                    ResultSet resultSet = statement.executeQuery(var);
                    resultSet.next();
                    x.city_id=resultSet.getInt(1);
                    x.city = resultSet.getString(2);
                    x.country_id=resultSet.getInt(3);
                    x.last_update=resultSet.getString(4);
                }
            }
            connection.close();
            return x;
             
        }
        catch(Exception e) {
            System.out.println(e);
            return null;
        }
        
    }

    @Override
    public List<?> selectMany(String queryId, Object queryParam, Class resultItemType) {
        /*try {  
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(     
            "jdbc:mysql://localhost:3306/sakila?characterEncoding=latin1","root","Manans@321");  
            Statement statement = connection.createStatement();
            List<Actor> x= new ArrayList<Actor>();
            
            hashMap = XmlExtractor.extractXml();
            for (Entry<String, String> set : hashMap.entrySet()) {
                if (queryId.equals(set.getKey())) {
                    String var;
                    if(queryParam instanceof Integer) {
                        var = set.getValue().substring(0, set.getValue().indexOf("${")) + queryParam + ";";
                    }
                    else {
                        var = set.getValue().substring(0, set.getValue().indexOf("${")) + "\"" + queryParam + "\";";
                    }
                    ResultSet resultSet = statement.executeQuery(var);
                    //resultSet.next();
                    
                    while (resultSet.next()) {
                        Actor x1=new Actor();
                        x1.actor_id=resultSet.getInt(1);
                        x1.first_name = resultSet.getString(2);
                        x1.last_name=resultSet.getString(3);
                        x1.last_update=resultSet.getString(4);
                        x.add(x1);
                    }
                }
            }
            connection.close();
            return x;
             
        }
        catch(Exception e) {
            List<Actor> x= new ArrayList<>();
            Actor x1=new Actor();
                    x1.actor_id=-10;
                    x1.first_name = "resultSet.getString(2)";
                    x1.last_name="resultSet.getString(3)";
                    x1.last_update="aaa";
                    x.add(x1);
            System.out.println(e);
            return x;
        }*/

        try {  
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(     
            "jdbc:mysql://localhost:3306/sakila?characterEncoding=latin1","root","Manans@321");  
            Statement statement = connection.createStatement();
            List<City> x= new ArrayList<City>();
            hashMap = XmlExtractor.extractXml();
            for (Entry<String, String> set : hashMap.entrySet()) {
                if (queryId.equals(set.getKey())) {
                    String var = set.getValue();
                    String[] qpl = ((String) queryParam).split(",");
                    for (int i=0; i<qpl.length; i++) {
                        qpl[i] = qpl[i].trim();
                    }
                    int count = 0;
                    int checkBrackets = var.indexOf("${");
                    String n = "";
					while (checkBrackets != -1) {
                        n = var.substring(0, var.indexOf("${")) + qpl[count] + var.substring(var.indexOf("}")+1, var.length());
						checkBrackets = n.indexOf("${");
                        var=n;
                        count++;
					}
                    ResultSet resultSet = statement.executeQuery(var);
                    
                    while (resultSet.next()) {
                        City x1=new City();
                        x1.city_id=resultSet.getInt(1);
                        x1.city = resultSet.getString(2);
                        x1.country_id=resultSet.getInt(3);
                        x1.last_update=resultSet.getString(4);
                        x.add(x1);
                    }
                }
            }
            connection.close();
            return x;
             
        }
        catch(Exception e) {
            System.out.println(e);
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
            int ra = 0;
            hashMap = XmlExtractor.extractXml();
            for (Entry<String, String> set : hashMap.entrySet()) {
                if (queryId.equals(set.getKey())) {
                    String var = set.getValue();
                    String[] qpl = ((String) queryParam).split(",");
                    for (int i=0; i<qpl.length; i++) {
                        qpl[i] = qpl[i].trim();
                    }
                    
                    int count = 0;
                    int checkBrackets = var.indexOf("${");
                    String n = "";
					while (checkBrackets != -1) {
                        n = var.substring(0, var.indexOf("${")) + qpl[count] + var.substring(var.indexOf("}")+1, var.length());
						checkBrackets = n.indexOf("${");
                        var=n;
                        count++;
					}
                    ra = statement.executeUpdate(var);
                }
            }
            connection.close();
            return ra;
             
        }
        catch(Exception e) {
            System.out.println(e);
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
            int ra = 0;
            hashMap = XmlExtractor.extractXml();
            for (Entry<String, String> set : hashMap.entrySet()) {
                if (queryId.equals(set.getKey())) {
                    String var = set.getValue();
                    String[] qpl = ((String) queryParam).split(",");
                    for (int i=0; i<qpl.length; i++) {
                        qpl[i] = qpl[i].trim();
                    }
                    
                    int count = 0;
                    int checkBrackets = var.indexOf("${");
                    String n = "";
					while (checkBrackets != -1) {
                        n = var.substring(0, var.indexOf("${")) + qpl[count] + var.substring(var.indexOf("}")+1, var.length());
						checkBrackets = n.indexOf("${");
                        var=n;
                        count++;
					}
                    ra = statement.executeUpdate(var);
                }
            }
            connection.close();
            return ra;
             
        }
        catch(Exception e) {
            System.out.println(e);
            return 0;
        }
    }

    @Override
    public int delete(String queryId, Object queryParam) {
        /*try {  
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(     
            "jdbc:mysql://localhost:3306/sakila?characterEncoding=latin1","root","Manans@321");  
            Statement statement = connection.createStatement();
            int ra = 0;
            hashMap = XmlExtractor.extractXml();
            for (Entry<String, String> set : hashMap.entrySet()) {
                if (queryId.equals(set.getKey())) {
                    String var;
                    if(queryParam instanceof Integer) {
                        var = set.getValue().substring(0, set.getValue().indexOf("${")) + queryParam + ";";
                    }
                    else {
                        var = set.getValue().substring(0, set.getValue().indexOf("${")) + "\"" + queryParam + "\";";
                    }
                    ra = statement.executeUpdate(var);
                }
            }
            connection.close();
            return ra;
             
        }
        catch(Exception e) {
            System.out.println(e);
            return 0;
        }*/

        try {  
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(     
            "jdbc:mysql://localhost:3306/sakila?characterEncoding=latin1","root","Manans@321");  
            Statement statement = connection.createStatement();
            int ra = 0;
            hashMap = XmlExtractor.extractXml();
            for (Entry<String, String> set : hashMap.entrySet()) {
                if (queryId.equals(set.getKey())) {
                    String var = set.getValue();
                    String[] qpl = ((String) queryParam).split(",");
                    for (int i=0; i<qpl.length; i++) {
                        qpl[i] = qpl[i].trim();
                    }
                    
                    int count = 0;
                    int checkBrackets = var.indexOf("${");
                    String n = "";
					while (checkBrackets != -1) {
                        n = var.substring(0, var.indexOf("${")) + qpl[count] + var.substring(var.indexOf("}")+1, var.length());
						checkBrackets = n.indexOf("${");
                        var=n;
                        count++;
					}
                    ra = statement.executeUpdate(var);
                }
            }
            connection.close();
            return ra;
             
        }
        catch(Exception e) {
            System.out.println(e);
            return 0;
        }
    }
}