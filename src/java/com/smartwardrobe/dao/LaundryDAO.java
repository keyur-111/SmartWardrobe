package com.smartwardrobe.dao;
import com.smartwardrobe.model.Laundry;
import com.smartwardrobe.util.DBConnection;
import java.sql.*;
import java.util.*;
public class LaundryDAO {
    public int create(Laundry x)throws SQLException {
        try(Connection c=DBConnection.getConnection();
        PreparedStatement p=c.prepareStatement("INSERT INTO laundry(clothing_id,laundry_date,return_date,status) VALUES(?,?,?,?)",Statement.RETURN_GENERATED_KEYS)) {
            p.setInt(1,x.getClothingId());
            p.setDate(2,java.sql.Date.valueOf(x.getLaundryDate()));
            if(x.getReturnDate()==null||x.getReturnDate().isEmpty())p.setNull(3,Types.DATE);
            else p.setDate(3,java.sql.Date.valueOf(x.getReturnDate()));
            p.setString(4,x.getStatus());
            p.executeUpdate();
            try(ResultSet r=p.getGeneratedKeys()) {
                return r.next()?r.getInt(1):0;
            }

        }

    }
    public List<Laundry> byUser(int uid)throws SQLException {
        List<Laundry>a=new ArrayList<>();
        String q="SELECT l.*,c.name clothing_name FROM laundry l JOIN clothing_items c ON l.clothing_id=c.clothing_id WHERE c.user_id=? ORDER BY l.laundry_date DESC";
        try(Connection c=DBConnection.getConnection();
        PreparedStatement p=c.prepareStatement(q)) {
            p.setInt(1,uid);
            try(ResultSet r=p.executeQuery()) {
                while(r.next()) {
                    Laundry x=new Laundry();
                    x.setLaundryId(r.getInt("laundry_id"));
                    x.setClothingId(r.getInt("clothing_id"));
                    x.setClothingName(r.getString("clothing_name"));
                    x.setLaundryDate(r.getDate("laundry_date").toString());
                    java.sql.Date d=r.getDate("return_date");
                    x.setReturnDate(d==null?"":d.toString());
                    x.setStatus(r.getString("status"));
                    a.add(x);
                }

            }

        }
        return a;
    }
    public boolean complete(int id,int uid)throws SQLException {
        String q="UPDATE laundry l JOIN clothing_items c ON l.clothing_id=c.clothing_id SET l.status='Completed',l.return_date=CURDATE(),c.status='Clean' WHERE l.laundry_id=? AND c.user_id=?";
        try(Connection c=DBConnection.getConnection();
        PreparedStatement p=c.prepareStatement(q)) {
            p.setInt(1,id);
            p.setInt(2,uid);
            return p.executeUpdate()>0;
        }

    }

}
