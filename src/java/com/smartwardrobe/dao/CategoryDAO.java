package com.smartwardrobe.dao;
import com.smartwardrobe.model.Category;
import com.smartwardrobe.util.DBConnection;
import java.sql.*;
import java.util.*;
public class CategoryDAO {
    public List<Category> all()throws SQLException {
        List<Category>a=new ArrayList<>();
        try(Connection c=DBConnection.getConnection();
        PreparedStatement p=c.prepareStatement("SELECT * FROM categories ORDER BY category_name");
        ResultSet r=p.executeQuery()) {
            while(r.next()) {
                Category x=new Category();
                x.setCategoryId(r.getInt("category_id"));
                x.setCategoryName(r.getString("category_name"));
                x.setDescription(r.getString("description"));
                a.add(x);
            }

        }
        return a;
    }
    public int create(Category x)throws SQLException {
        try(Connection c=DBConnection.getConnection();
        PreparedStatement p=c.prepareStatement("INSERT INTO categories(category_name,description) VALUES(?,?)",Statement.RETURN_GENERATED_KEYS)) {
            p.setString(1,x.getCategoryName());
            p.setString(2,x.getDescription());
            p.executeUpdate();
            try(ResultSet r=p.getGeneratedKeys()) {
                return r.next()?r.getInt(1):0;
            }

        }

    }
    public boolean delete(int id)throws SQLException {
        try(Connection c=DBConnection.getConnection();
        PreparedStatement p=c.prepareStatement("DELETE FROM categories WHERE category_id=?")) {
            p.setInt(1,id);
            return p.executeUpdate()>0;
        }

    }

}