package com.smartwardrobe.dao;
import com.smartwardrobe.model.User;
import com.smartwardrobe.util.DBConnection;
import com.smartwardrobe.util.PasswordUtil;
import java.sql.*;
import java.util.*;
public class UserDAO {
    public User login(String email,String password)throws SQLException {
        String q="SELECT * FROM users WHERE email=? AND password=?";
        try(Connection c=DBConnection.getConnection();
        PreparedStatement p=c.prepareStatement(q)) {
            p.setString(1,email);
            p.setString(2,PasswordUtil.hash(password));
            try(ResultSet r=p.executeQuery()) {
                if(r.next())return map(r);
            }

        }
        return null;
    }
    public boolean emailExists(String email)throws SQLException {
        try(Connection c=DBConnection.getConnection();
        PreparedStatement p=c.prepareStatement("SELECT user_id FROM users WHERE email=?")) {
            p.setString(1,email);
            try(ResultSet r=p.executeQuery()) {
                return r.next();
            }

        }

    }
    public int create(User u)throws SQLException {
        String q="INSERT INTO users(name,email,password,role) VALUES(?,?,?,'USER')";
        try(Connection c=DBConnection.getConnection();
        PreparedStatement p=c.prepareStatement(q,Statement.RETURN_GENERATED_KEYS)) {
            p.setString(1,u.getName());
            p.setString(2,u.getEmail());
            p.setString(3,PasswordUtil.hash(u.getPassword()));
            p.executeUpdate();
            try(ResultSet r=p.getGeneratedKeys()) {
                return r.next()?r.getInt(1):0;
            }

        }

    }
    public List<User> all()throws SQLException {
        List<User> a=new ArrayList<>();
        try(Connection c=DBConnection.getConnection();
        PreparedStatement p=c.prepareStatement("SELECT user_id,name,email,role FROM users ORDER BY user_id DESC");
        ResultSet r=p.executeQuery()) {
            while(r.next())a.add(map(r));
        }
        return a;
    }
    public boolean updateIdentity(int id,String name,String email)throws SQLException {
        try(Connection c=DBConnection.getConnection();
        PreparedStatement p=c.prepareStatement("UPDATE users SET name=?,email=? WHERE user_id=? AND role='USER'")) {
            p.setString(1,name);
            p.setString(2,email);
            p.setInt(3,id);
            return p.executeUpdate()>0;
        }
    }
    public boolean updateAdminPassword(int id,String password)throws SQLException {
        try(Connection c=DBConnection.getConnection();
        PreparedStatement p=c.prepareStatement("UPDATE users SET password=? WHERE user_id=? AND role='ADMIN'")) {
            p.setString(1,PasswordUtil.hash(password));
            p.setInt(2,id);
            return p.executeUpdate()>0;
        }
    }
    public boolean delete(int id)throws SQLException {
        try(Connection c=DBConnection.getConnection();
        PreparedStatement p=c.prepareStatement("DELETE FROM users WHERE user_id=?")) {
            p.setInt(1,id);
            return p.executeUpdate()>0;
        }

    }
    private User map(ResultSet r)throws SQLException {
        User u=new User();
        u.setUserId(r.getInt("user_id"));
        u.setName(r.getString("name"));
        u.setEmail(r.getString("email"));
        u.setRole(r.getString("role"));
        return u;
    }

}
