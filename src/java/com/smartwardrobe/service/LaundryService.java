package com.smartwardrobe.service;
import com.smartwardrobe.dao.LaundryDAO;
import com.smartwardrobe.model.Laundry;
import java.sql.SQLException;
import java.util.List;
public class LaundryService {
    private final LaundryDAO dao=new LaundryDAO();
    public int add(Laundry x)throws SQLException {
        return dao.create(x);
    }
    public List<Laundry> list(int u)throws SQLException {
        return dao.byUser(u);
    }
    public boolean complete(int id,int u)throws SQLException {
        return dao.complete(id,u);
    }

}
