package com.smartwardrobe.service;
import com.smartwardrobe.dao.OutfitDAO;
import com.smartwardrobe.model.*;
import java.sql.SQLException;
import java.util.*;
public class OutfitService {
    private final OutfitDAO dao=new OutfitDAO();
    public int save(Outfit o,List<Integer> ids)throws SQLException {
        return dao.create(o,ids);
    }
    public List<Outfit> list(int u)throws SQLException {
        return dao.byUser(u);
    }
    public boolean delete(int id,int u)throws SQLException {
        return dao.delete(id,u);
    }

}
