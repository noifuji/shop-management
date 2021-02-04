package app.shopmanagement.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

import app.shopmanagement.bean.*;
import app.shopmanagement.dao.AdminDAO;
import app.shopmanagement.tool.Constants;

/**
 * ユーザー名とパスワードの組み合わせをチェックする
 */
 
public class LoginAction {

    private String LOGIN_PAGE_NAME = Constants.PATH_LOGIN_JSP;
    private String SEARCH_PAGE_NAME = "ProduceSearchView.action";

	public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        String adminId=request.getParameter("admin_id"); 
        String password=request.getParameter("password"); 
        
        ArrayList<String> errorMessages = new ArrayList<String>();
        
        
        //入力チェック
		if(adminId==null || adminId=="") {
		    errorMessages.add(Constants.ERROR_MESSAGE_301);
        }  
        
        if(password==null || password=="") {
		    errorMessages.add(Constants.ERROR_MESSAGE_302);
		    request.setAttribute("admin_id", adminId);
        }
        
        if(errorMessages.size() > 0) {
        	request.setAttribute("message", errorMessages);
        	return LOGIN_PAGE_NAME;
        }
        
        
        AdminDAO dao=new AdminDAO();
        Admin admin=dao.selectByIdAndPassword(adminId, password);
        
        //登録チェック
        if(admin==null) {
            errorMessages.add(Constants.ERROR_MESSAGE_303);
        	request.setAttribute("message", errorMessages);
        	request.setAttribute("admin_id", adminId);
        	return LOGIN_PAGE_NAME;
        }
        
        HttpSession session=request.getSession(true);
        session.setAttribute("admin", admin);
		
        return SEARCH_PAGE_NAME;
    }
}