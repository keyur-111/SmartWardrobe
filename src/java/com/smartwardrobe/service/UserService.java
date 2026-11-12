package com.smartwardrobe.service;
import com.smartwardrobe.dao.UserDAO;
import com.smartwardrobe.model.User;
import java.sql.SQLException;
import java.util.List;
public class UserService {
    private final UserDAO dao=new UserDAO();
    public User login(String e,String p)throws SQLException {
        return dao.login(e,p);
    }
    public boolean exists(String e)throws SQLException {
        return dao.emailExists(e);
    }
    public int register(User u)throws SQLException {
        return dao.create(u);
    }
    public List<User> all()throws SQLException {
        return dao.all();
    }
    public boolean delete(int id)throws SQLException {
        return dao.delete(id);
    }

}
