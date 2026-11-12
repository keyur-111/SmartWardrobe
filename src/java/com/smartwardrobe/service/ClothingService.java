package com.smartwardrobe.service;
import com.smartwardrobe.dao.ClothingDAO;
import com.smartwardrobe.model.Clothing;
import java.sql.SQLException;
import java.util.*;
public class ClothingService {
    private final ClothingDAO dao=new ClothingDAO();
    public List<Clothing> list(int u,String s,String c,String st)throws SQLException {
        return dao.findByUser(u,s,c,st);
    }
    public List<Clothing> all()throws SQLException {
        return dao.all();
    }
    public Clothing find(int id,int u)throws SQLException {
        return dao.find(id,u);
    }
    public Clothing findAdmin(int id)throws SQLException {
        return dao.findAdmin(id);
    }
    public int add(Clothing x)throws SQLException {
        return dao.create(x);
    }
    public boolean update(Clothing x)throws SQLException {
        return dao.update(x);
    }
    public boolean delete(int id,int u)throws SQLException {
        return dao.delete(id,u);
    }
    public boolean laundry(int id,int u,String s)throws SQLException {
        return dao.markLaundry(id,u,s);
    }
    public boolean usage(int id,int u)throws SQLException {
        return dao.incrementUsage(id,u);
    }
    public List<Clothing> pool(int u,String o,String s)throws SQLException {
        return dao.outfitPool(u,o,s);
    }

}
