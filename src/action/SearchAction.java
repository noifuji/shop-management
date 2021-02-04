package app.shopmanagement.action;

import java.lang.Exception;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;

import app.shopmanagement.bean.*;
import app.shopmanagement.tool.Constants;


public class SearchAction {
	public String execute(
		HttpServletRequest request, HttpServletResponse response
	) throws Exception {

		HttpSession session=request.getSession(false);
        if(session == null) {
            throw new Exception(Constants.ERROR_MESSAGE_401);
        }
        
        ServletContext context = request.getServletContext();
            context.log("SearchAction");
            
        //検索キーワードのチェック
        String keyword;
        String searchKeyword = request.getParameter("searchKeyword");
        if(searchKeyword == null || searchKeyword.equals("")) {
            keyword = null;
        } else {
            keyword = searchKeyword;
        }
        
        //キーワードが入力されてるい場合、キーワードを含む店舗リストを作成してセッションに登録しておく。
        List<Shop> shopList = (List<Shop>)session.getAttribute("shopList");
        List<Shop> filteredShopList = new ArrayList<>();
        if(keyword != null) {
           for(int i = 0; i < shopList.size(); i++) {
                if(shopList.get(i).getShopName().contains(keyword)) {
                    filteredShopList.add(shopList.get(i));
                }
            }
            session.setAttribute("filteredShopList", filteredShopList);
            session.setAttribute("keywordHistory", keyword);
        } else {
        	//キーワードが入力されていない場合は削除する。
        	session.removeAttribute("filteredShopList");
            session.removeAttribute("keywordHistory");
        }
		
		return "ProduceSearchView.action";
   }
}