package app.shopmanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import javax.naming.NamingException;

import app.shopmanagement.bean.*;

/**Itemテーブルに対する処理を行う
 * 商品検索
 * 商品情報の新規登録
 * 商品情報変更
 * 商品情報削除
 */
 
 public class ShopDAO {
 	private String ITEM_NO_COLUMN_NAME = "item_no";
 	private String ITEM_CATEGORY_CODE_COLUMN_NAME = "item_category_code";
 	private String ITEM_CATEGORY_NAME_COLUMN_NAME = "item_category_name";
 	private String ITEM_NAME_COLUMN_NAME = "item_name";
 	private String EXPLANATION_COLUMN_NAME = "explanation";
 	private String PRICE_COLUMN_NAME = "price";
 	private String RECOMMEND_FLG_COLUMN_NAME = "recommend_flg";
 	private String LAST_UPDATE_DATE_TIME_COLUMN_NAME= "last_update_date_time";
 	

	
	public List<Shop> selectAll()
	throws NamingException, SQLException {
	 	List<Shop> list = new ArrayList<>();
	 	
	 	for (int i = 0; i < 150; i++) {
	 		Shop tmp = new Shop(String.valueOf(i+1),"Shop"+(i+1));
	 		if(Math.random() > 0.5) {
	 		    tmp.setStatus("CLOSED");
	 	    }
	 	    list.add(tmp);
	 	}
	 	
	 	
	 	
	 	return list; 
	}
	
}