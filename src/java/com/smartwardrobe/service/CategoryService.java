package com.smartwardrobe.service;
import com.smartwardrobe.dao.CategoryDAO;
import com.smartwardrobe.model.Category;
import java.sql.SQLException;
import java.util.List;
public class CategoryService {
    private final CategoryDAO dao=new CategoryDAO();
    public List<Category> all()throws SQLException {
        return dao.all();
    }
    public int add(Category x)throws SQLException {
        return dao.create(x);
    }
    public boolean delete(int id)throws SQLException {
        return dao.delete(id);
    }

}
