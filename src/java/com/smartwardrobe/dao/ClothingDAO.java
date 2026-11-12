package com.smartwardrobe.dao;
import com.smartwardrobe.model.Clothing;
import com.smartwardrobe.util.DBConnection;
import java.sql.*;
import java.util.*;
public class ClothingDAO {
    private static final String BASE="SELECT c.*, cat.category_name FROM clothing_items c JOIN categories cat ON c.category_id=cat.category_id";
    public List<Clothing> findByUser(int uid,String search,String category,String status)throws SQLException {
        List<Clothing>a=new ArrayList<>();
        String q=BASE+" WHERE c.user_id=? AND c.name LIKE ?";
        if(category!=null&&!category.isEmpty())q+=" AND c.category_id=?";
        if(status!=null&&!status.isEmpty())q+=" AND c.status=?";
        q+=" ORDER BY c.created_at DESC";
        try(Connection c=DBConnection.getConnection();
        PreparedStatement p=c.prepareStatement(q)) {
            int i=1;
            p.setInt(i++,uid);
            p.setString(i++,"%"+(search==null?"":search)+"%");
            if(category!=null&&!category.isEmpty())p.setInt(i++,Integer.parseInt(category));
            if(status!=null&&!status.isEmpty())p.setString(i++,status);
            try(ResultSet r=p.executeQuery()) {
                while(r.next())a.add(map(r));
            }

        }
        return a;
    }
    public List<Clothing> all()throws SQLException {
        List<Clothing>a=new ArrayList<>();
        try(Connection c=DBConnection.getConnection();
        PreparedStatement p=c.prepareStatement(BASE+" ORDER BY c.created_at DESC");
        ResultSet r=p.executeQuery()) {
            while(r.next())a.add(map(r));
        }
        return a;
    }
    public Clothing find(int id,int uid)throws SQLException {
        return findInternal(id,uid,false);
    }
    public Clothing findAdmin(int id)throws SQLException {
        return findInternal(id,0,true);
    }
    private Clothing findInternal(int id,int uid,boolean admin)throws SQLException {
        String q=BASE+" WHERE c.clothing_id=?"+(admin?"":" AND c.user_id=?");
        try(Connection c=DBConnection.getConnection();
        PreparedStatement p=c.prepareStatement(q)) {
            p.setInt(1,id);
            if(!admin)p.setInt(2,uid);
            try(ResultSet r=p.executeQuery()) {
                return r.next()?map(r):null;
            }

        }

    }
    public int create(Clothing x)throws SQLException {
        String q="INSERT INTO clothing_items(user_id,category_id,name,color,fabric,season,occasion,image,status,usage_count) VALUES(?,?,?,?,?,?,?,?,?,0)";
        try(Connection c=DBConnection.getConnection();
        PreparedStatement p=c.prepareStatement(q,Statement.RETURN_GENERATED_KEYS)) {
            set(p,x,false);
            p.executeUpdate();
            try(ResultSet r=p.getGeneratedKeys()) {
                return r.next()?r.getInt(1):0;
            }

        }

    }
    public boolean update(Clothing x)throws SQLException {
        String q="UPDATE clothing_items SET category_id=?,name=?,color=?,fabric=?,season=?,occasion=?,image=?,status=? WHERE clothing_id=? AND user_id=?";
        try(Connection c=DBConnection.getConnection();
        PreparedStatement p=c.prepareStatement(q)) {
            int i=1;
            p.setInt(i++,x.getCategoryId());
            p.setString(i++,x.getName());
            p.setString(i++,x.getColor());
            p.setString(i++,x.getFabric());
            p.setString(i++,x.getSeason());
            p.setString(i++,x.getOccasion());
            p.setString(i++,x.getImage());
            p.setString(i++,x.getStatus());
            p.setInt(i++,x.getClothingId());
            p.setInt(i,x.getUserId());
            return p.executeUpdate()>0;
        }

    }
    public boolean delete(int id,int uid)throws SQLException {
        try(Connection c=DBConnection.getConnection();
        PreparedStatement p=c.prepareStatement("DELETE FROM clothing_items WHERE clothing_id=? AND user_id=?")) {
            p.setInt(1,id);
            p.setInt(2,uid);
            return p.executeUpdate()>0;
        }

    }
    public boolean markLaundry(int id,int uid,String status)throws SQLException {
        try(Connection c=DBConnection.getConnection();
        PreparedStatement p=c.prepareStatement("UPDATE clothing_items SET status=? WHERE clothing_id=? AND user_id=?")) {
            p.setString(1,status);
            p.setInt(2,id);
            p.setInt(3,uid);
            return p.executeUpdate()>0;
        }

    }
    public boolean incrementUsage(int id,int uid)throws SQLException {
        try(Connection c=DBConnection.getConnection();
        PreparedStatement p=c.prepareStatement("UPDATE clothing_items SET usage_count=usage_count+1 WHERE clothing_id=? AND user_id=?")) {
            p.setInt(1,id);
            p.setInt(2,uid);
            return p.executeUpdate()>0;
        }

    }
    public List<Clothing> outfitPool(int uid,String occasion,String season)throws SQLException {
        String q=BASE+" WHERE c.user_id=? AND c.status='Clean' AND (c.occasion=? OR c.occasion='Any' OR c.occasion IS NULL OR c.occasion='') AND (c.season=? OR c.season='All' OR c.season IS NULL OR c.season='') ORDER BY c.category_id,c.usage_count";
        List<Clothing>a=new ArrayList<>();
        try(Connection c=DBConnection.getConnection();
        PreparedStatement p=c.prepareStatement(q)) {
            p.setInt(1,uid);
            p.setString(2,occasion);
            p.setString(3,season);
            try(ResultSet r=p.executeQuery()) {
                while(r.next())a.add(map(r));
            }

        }
        return a;
    }
    private void set(PreparedStatement p,Clothing x,boolean update)throws SQLException {
        int i=1;
        p.setInt(i++,x.getUserId());
        p.setInt(i++,x.getCategoryId());
        p.setString(i++,x.getName());
        p.setString(i++,x.getColor());
        p.setString(i++,x.getFabric());
        p.setString(i++,x.getSeason());
        p.setString(i++,x.getOccasion());
        p.setString(i++,x.getImage());
        p.setString(i++,x.getStatus());
    }
    private Clothing map(ResultSet r)throws SQLException {
        Clothing x=new Clothing();
        x.setClothingId(r.getInt("clothing_id"));
        x.setUserId(r.getInt("user_id"));
        x.setCategoryId(r.getInt("category_id"));
        x.setName(r.getString("name"));
        x.setColor(r.getString("color"));
        x.setFabric(r.getString("fabric"));
        x.setSeason(r.getString("season"));
        x.setOccasion(r.getString("occasion"));
        x.setImage(r.getString("image"));
        x.setStatus(r.getString("status"));
        x.setUsageCount(r.getInt("usage_count"));
        x.setCategoryName(r.getString("category_name"));
        return x;
    }

}
