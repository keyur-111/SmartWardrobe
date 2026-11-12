package com.smartwardrobe.dao;
import com.smartwardrobe.model.Outfit;
import com.smartwardrobe.util.DBConnection;
import java.sql.*;
import java.util.*;
public class OutfitDAO {
    public int create(Outfit x,List<Integer> ids)throws SQLException {
        try(Connection c=DBConnection.getConnection()) {
            c.setAutoCommit(false);
            try(PreparedStatement p=c.prepareStatement("INSERT INTO outfits(user_id,outfit_name,occasion,season) VALUES(?,?,?,?)",Statement.RETURN_GENERATED_KEYS)) {
                p.setInt(1,x.getUserId());
                p.setString(2,x.getOutfitName());
                p.setString(3,x.getOccasion());
                p.setString(4,x.getSeason());
                p.executeUpdate();
                int oid;
                try(ResultSet r=p.getGeneratedKeys()) {
                    if(!r.next())throw new SQLException("Could not create outfit");
                    oid=r.getInt(1);
                }
                try(PreparedStatement q=c.prepareStatement("INSERT INTO outfit_items(outfit_id,clothing_id) VALUES(?,?)")) {
                    for(Integer id:ids) {
                        q.setInt(1,oid);
                        q.setInt(2,id);
                        q.addBatch();
                    }
                    q.executeBatch();
                }
                c.commit();
                return oid;
            }
            catch(SQLException e) {
                c.rollback();
                throw e;
            }

        }

    }
    public List<Outfit> byUser(int uid)throws SQLException {
        List<Outfit>a=new ArrayList<>();
        try(Connection c=DBConnection.getConnection();
        PreparedStatement p=c.prepareStatement("SELECT * FROM outfits WHERE user_id=? ORDER BY created_at DESC")) {
            p.setInt(1,uid);
            try(ResultSet r=p.executeQuery()) {
                while(r.next()) {
                    Outfit x=new Outfit();
                    x.setOutfitId(r.getInt("outfit_id"));
                    x.setUserId(uid);
                    x.setOutfitName(r.getString("outfit_name"));
                    x.setOccasion(r.getString("occasion"));
                    x.setSeason(r.getString("season"));
                    x.setCreatedAt(r.getTimestamp("created_at").toString());
                    a.add(x);
                }

            }

        }
        return a;
    }
    public boolean delete(int id,int uid)throws SQLException {
        try(Connection c=DBConnection.getConnection();
        PreparedStatement p=c.prepareStatement("DELETE FROM outfits WHERE outfit_id=? AND user_id=?")) {
            p.setInt(1,id);
            p.setInt(2,uid);
            return p.executeUpdate()>0;
        }

    }

}
