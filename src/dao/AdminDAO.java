package app.shopmanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import app.shopmanagement.bean.*;


/**
 * Adminの情報をデータベースに問い合わせて結果を返却する
 * 
 */
public class AdminDAO {
    private String ADMIN_ID_COLUMN_NAME = "ADMIN_ID";
    private String PASSWORD_COLUMN_NAME = "PASSWORD";
    private String ADMIN_USER_TABLE_NAME = "ADMIN_USER";
    public Admin selectByIdAndPassword (String adminId, String password)
        throws NamingException, SQLException {
        Admin admin = null;
            admin=new Admin();
            admin.setAdminId(adminId);
            admin.setPassword(password);
        return admin;
    }
}