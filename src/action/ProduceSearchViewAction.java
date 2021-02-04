package app.shopmanagement.action;

import java.util.*;
import java.sql.SQLException;
import java.lang.Exception;

import javax.naming.NamingException;
import javax.servlet.http.*;
import javax.servlet.ServletContext;

import app.shopmanagement.bean.*;
import app.shopmanagement.dao.*;
import app.shopmanagement.tool.Constants;

/**
 * 店舗のリストを取得して、セッションで保持する。
 * 検索画面の初期表示として10件表示する。
 * 
 */
public class ProduceSearchViewAction {
    private int[] DISPLAY_LIMIT_LIST = {10, 50, 100};
    
    public String execute(
        HttpServletRequest request, HttpServletResponse response
        ) throws Exception {
            
             HttpSession session=request.getSession(false);
            if(session == null) {
                throw new Exception("セッションがありません。");
            }
            
            ServletContext context = request.getServletContext();
            context.log("ProduceSearchViewAction");
            
            /**************************************/
            /*********検索画面の初期設定***********/
            /**************************************/
            //セッションに値がない場合はデータベースからデータを取得する。
            if(session.getAttribute("shopList") == null) {
                ShopDAO dao = new ShopDAO();
                List<Shop> shopList = dao.selectAll();
                session.setAttribute("shopList", shopList);
            }
            
            //表示件数の上限が設定されていない場合は初期値として10を設定する。
            if(session.getAttribute("displayLimit") == null) {
                session.setAttribute("displayLimit", DISPLAY_LIMIT_LIST[0]);
                session.setAttribute("displayLimitList", DISPLAY_LIMIT_LIST);
            }
            
            //フィルタされた店舗リストがない場合は、全データを登録しておく。
            if(session.getAttribute("filteredShopList") == null) {
                session.setAttribute("filteredShopList", session.getAttribute("shopList"));
            }
            
            
            /**************************************************/
            /**********リクエストパラメータのチェック**********/
            /**************************************************/
            
            //表示件数上限が指定されている場合は、その件数を表示上限に設定する。
            String requestDisplayLimit = request.getParameter("requestDisplayLimit");
            if(requestDisplayLimit != null) {
                session.setAttribute("displayLimit", Integer.parseInt(requestDisplayLimit));
            }
            
            //初期表示は1ページ目とする。
            //リクエストされたページ番号がある場合は、そのページを表示する。
            int displayPageNum;
            String requestPageNum = request.getParameter("requestPageNum");
            if(requestPageNum == null) {
                displayPageNum = 1;
            } else {
                displayPageNum = Integer.parseInt(requestPageNum);
            }
            
            /**************************************/
            /**********画面表示項目の準備**********/
            /**************************************/
            
            int displayLimit = (int)session.getAttribute("displayLimit");
            List<Shop> filteredShopList = (List<Shop>)session.getAttribute("filteredShopList");
            
            
            
            //表示する店舗一覧の取得
            List<Shop> shopListForDisplay = new ArrayList<>();
            for(int i = (displayPageNum-1)*displayLimit; i < filteredShopList.size(); i++) {
                shopListForDisplay.add(filteredShopList.get(i));
                
                if(shopListForDisplay.size() >= displayLimit) {
                    break;
                }
             }
             
             //店舗ステータス集計結果の取得
             //ステータスはOPENとCLOSEDがある。
             int openCount = 0;
             int closedCount = 0;
             int othersCount = 0;
             for(int i = 0; i < filteredShopList.size(); i++) {
                 if(filteredShopList.get(i).getStatus().equals("OPEN")) {
                     openCount++;
                 }else if(filteredShopList.get(i).getStatus().equals("CLOSED")) {
                     closedCount++;
                 } else {
                     othersCount++;
                 }
             }
             
             
             /**************************************/
             /**********レスポンスの格納**********/
             /**************************************/
             request.setAttribute("shopListForDisplay", shopListForDisplay);
             request.setAttribute("pageNum", displayPageNum);
             request.setAttribute("statusOpenCount", openCount);
             request.setAttribute("statusClosedCount", closedCount);
             request.setAttribute("statusOthersCount", othersCount);
             
             return Constants.PATH_SEARCH_JSP;
        }
}
