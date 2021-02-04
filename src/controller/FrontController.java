package app.shopmanagement.controller;

import java.io.IOException;
import java.io.PrintWriter;
import app.shopmanagement.action.LoginAction;
import app.shopmanagement.action.ProduceSearchViewAction;
import app.shopmanagement.action.SearchAction;
import app.shopmanagement.tool.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HTTPリクエストを受け取る
 * Actionの取得
 * Actionの実行
 * JSPへのフォワード
 */
@WebServlet(urlPatterns={"*.action"}) //http://AAAAAAAAAAAA/ItemManagement/XXXXXX.actionというURLにアクセスされると、このサーブレットが動く
public class FrontController extends HttpServlet {

    public void doPost(
        HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        PrintWriter out=response.getWriter();
        try {
            String path=request.getServletPath().substring(1); //getServletでリクエストされたURLを取得する。
            String name=path.replace(".a", "A").replace('/', '.'); //replaceでpath変数の.aをAに、/を.に置き換えている
            
            //ここでnameの内容を出力してください
            System.out.println("Action Name = [" + name + "]");//こう変更しました。見えないスペースなどがはいっていてもこれならわかります。
            
            //アクション名に応じて分岐する
            //アクション名がLoginのとき、ActionLoginインスタンスを生成する、
            //actionに生成したインスタンスを格納する。
            
            String url="";
            if (name.equals("LoginAction")) {//ログイン機能
                LoginAction action = new LoginAction();
                url = action.execute(request, response);
            } else if (name.equals("ProduceSearchViewAction")) {
                ProduceSearchViewAction action = new ProduceSearchViewAction();
                url = action.execute(request, response);
            } else if (name.equals("SearchAction")) {//商品検索機能
                SearchAction action = new SearchAction();
                url = action.execute(request, response);
            }
            
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }

    public void doGet(
    	HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
    	ServletContext context = getServletContext();
    	RequestDispatcher dispatcher = context.getRequestDispatcher(Constants.PATH_LOGIN_JSP);
    	dispatcher.forward(request, response);

    }
}