package prcse.pp.db;

import javafx.stage.Screen;
import prcse.pp.controller.ScreensFramework;
import prcse.pp.model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Create a new connection to the database and handle any SQL queries through
 * its methods.
 */
public class Database {

    // Variables which describe a connection
    private String db_host;
    private String db_user;
    private String db_pass;

    // Reference to global data store objects
    UserList     tenantList   = ScreensFramework.tenants;
    PropertyList propertyList = ScreensFramework.properties;

    /**
     * Create the connection
     * @param host the host address of the database
     * @param user the user we are logging in as
     * @param password the password for that user
     */
    public Database(String host, String user, String password)
    {
        // set default connection
        if(host == null && user == null && password == null){
            this.db_host = "jdbc:oracle:thin:@tom.uopnet.plymouth.ac.uk:1521:orcl";
            this.db_user = "PRCSE";
            this.db_pass = "PRCSE";
        } else {
            this.db_host = host;
            this.db_user = user;
            this.db_pass = password;
        }
    }

    /**
     * Builds all objects in the database in precedence
     * @return true if the objects were built, else return false
     */
    public Boolean buildObjects()
    {
        Boolean objectsBuilt = false;
        int buildCount = 0;

        // Check that all of the database objects have been built by
        // incrementing an objectCounter each time a query executes and returns true
        if(objectsBuilt == false)
        {
            if(buildProperties()) { buildCount++; }
            if(buildRequests()) {buildCount++; };
            //buildTracking();
            //buildPayments();
            //buildNotes();
            //buildMessages();
            if(buildUsers()) { buildCount++; }

            // Check if all the objects have been built and return the correct
            // value.
            if(buildCount >= 2) {
                objectsBuilt = true;
            } else {
                objectsBuilt = false;
            }
        }

        return objectsBuilt;
    }

    /**
     * Queries the database and builds all property objects
     * @return true if all property objects have been made, else return false
     */
    public Boolean buildProperties(){
        Boolean propertiesBuilt = false;
        try {
            Connection con = DriverManager.getConnection(this.db_host, this.db_user, this.db_pass);
            Statement  st  = con.createStatement();
            ResultSet  res = st.executeQuery("SELECT * FROM properties");

            while(res.next()) {
                Property p = new Property(res.getInt("property_id"), res.getString("prop_track_code"), res.getString("addr_line_1"),
                        res.getString("addr_line_2"), res.getString("addr_postcode"), res.getString("addr_district"), res.getString("city_name"),
                        res.getString("prop_details"), res.getInt("prop_num_rooms"));

                // Builds and adds all rooms relative to this property
                buildRooms(p);

                // Add the property to the global list of properties
                propertyList.addProperty(p);
            }

            propertiesBuilt = true;

        } catch (SQLException e) {
            System.out.println("Error handling query: " + e.getMessage());
            propertiesBuilt = false;
        }

        return propertiesBuilt;
    }

    /**
     * Queries the database and builds all room objects for the property
     * @param p The property which we are building its rooms for
     * @return true if all room objects have been made, else return false
     */
    public Boolean buildRooms(Property p){
        Boolean roomsBuilt = false;
        try {
            // Get the correct ID
            int property_id = p.getPropertyId();

            Connection con = DriverManager.getConnection(this.db_host, this.db_user, this.db_pass);
            Statement  st  = con.createStatement();
            ResultSet  res = st.executeQuery("SELECT * FROM rooms WHERE property_id = " + property_id);


            while(res.next()) {
                Room r = new Room(res.getInt("room_id"), res.getInt("property_id"), res.getString("room_price"),
                                  res.getString("room_details"));

                // Add the room object to this property
                p.addRoom(r);
            }

            roomsBuilt = true;

        } catch (SQLException e) {
            System.out.println("Error handling query: " + e.getMessage());
            roomsBuilt = false;
        }

        return roomsBuilt;
    }

    /**
     * Build all user objects for the system
     * @return true if all user objects were built, else return false
     */
    public Boolean buildUsers(){

        Boolean usersBuilt = false;

        try {
            // Make a connection to the database
            Connection con = DriverManager.getConnection(this.db_host, this.db_user, this.db_pass);
            Statement  st  = con.createStatement();
            ResultSet  res = st.executeQuery("SELECT * FROM users");

            while(res.next()) {
                Property p = getProperty(res.getInt("user_property"));
                Room     r = getRoom(p, res.getInt("user_prop_room"));

                Tenant   u = new Tenant(res.getInt("user_id"), res.getString("user_title"), res.getString("user_forename"), res.getString("user_surname"),
                                        res.getString("user_email"), res.getString("user_phone"), res.getString("addr_line_1"), res.getString("addr_line_2"),
                                        res.getString("addr_postcode"), res.getString("city_name"), p, r);

                tenantList.addUser(u);
            }
            System.out.println(tenantList.size());
            usersBuilt = true;
        } catch (SQLException e) {
            System.out.println("Error handling query: " + e.getMessage());
            usersBuilt = false;
        }

        return usersBuilt;
    }

    /**
     * Build all request objects for the system
     * @return true if all request objects were built, else return false
     */
    public Boolean buildRequests(){

        Boolean requestsBuilt = false;

        try {
            // Make a connection to the database
            Connection con = DriverManager.getConnection(this.db_host, this.db_user, this.db_pass);
            Statement  st  = con.createStatement();
            ResultSet  res = st.executeQuery("SELECT * FROM requests");

            while(res.next()) {
                //Request r = new Request();

                //tenantList.addUser(r);
            }

            requestsBuilt = true;
        } catch (SQLException e) {
            System.out.println("Error handling query: " + e.getMessage());
            requestsBuilt = false;
        }

        return requestsBuilt;
    }

    /**
     * Searches for a property with the given ID
     * @return the Property object found in the Property Array, else return empty property
     */
    public Property getProperty(int propertyId)
    {
        Property result = new Property();

        if(propertyId > 0)
        {

            // Loop through each property in the global list
            for(int i = 0; i < propertyList.size(); i++)
            {
                Property thisProperty = propertyList.getPropertyAt(i);
                if( thisProperty.getPropertyId() == propertyId &&
                    thisProperty != null )
                {
                    result = thisProperty;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Searches for a room with the given ID
     * @return the Room object found in the Room Array, else return empty room
     */
    public Room getRoom(Property p, int roomId)
    {
        Room result = new Room();

        if(roomId > 0)
        {
            // Loop through each property in the global list
            for(int i = 0; i < p.numRooms(); i++)
            {
                Room thisRoom = p.getRoomAt(i);
                if( thisRoom.getRoomId() == roomId &&
                    thisRoom != null )
                {
                    result = thisRoom;
                    break;
                }
            }
        }

        return result;
    }


    // Getters and setters
    public String getDb_host() {
        return db_host;
    }

    public void setDb_host(String db_host) {
        this.db_host = db_host;
    }

    public String getDb_user() {
        return db_user;
    }

    public void setDb_user(String db_user) {
        this.db_user = db_user;
    }

    public String getDb_pass() {
        return db_pass;
    }

    public void setDb_pass(String db_pass) {
        this.db_pass = db_pass;
    }

}
