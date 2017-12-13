package cn.com.sxt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.sxt.dao.ProductDao;
import cn.com.sxt.entity.Product;
/**
 * 展示商品详情
 * @author xz
 *
 */
public class DetailServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		
		String id = request.getParameter("id");
		ProductDao dao = new ProductDao();
		Product product = dao.findById(id);
		String html="";
		html+="<html>";
		html+="<head><title>商品详情</title></head>";
		html+="<body>";
		html+="<table border='1' align='center' width='600px'>";
		html+="<tr><td>商品编号</td><td>"+product.getId()+"</td></tr>";
		html+="<tr><td>商品名称</td><td>"+product.getName()+"</td></tr>";
		html+="<tr><td>商品型号</td><td>"+product.getType()+"</td></tr>";
		html+="<tr><td>商品价格</td><td>"+product.getPrice()+"</td></tr>";
		html+="</table>";
		html+="<br/><br/>";
		html+="<a href='"+request.getContextPath()+"/ListServlet'>[返回列表]</a>";
		html+="</body>";
		html+="</html>";
		
		//创建cookie并发送给浏览器
		Cookie cookie = new Cookie("proHis", createValue(request,id));//
		cookie.setMaxAge(1*60*60*24*30);//保存一个月
		response.addCookie(cookie);
		response.getWriter().write(html);
	}
	
	/**
	 * 创建cookie的值
	 * @param request
	 * @param id
	 * @return
	 * 		当前cookie的值			传入的id的值			最终的cookie的值
	 * 	 null 或者 proHis没有			1					 1
	 * 			1						2					 2,1  (没有重复)
	 * 			2，1					1					 1,2  (有重复，小于3个) 
	 * 			3，2，1					2					 2，3，1 (有重复，等于3个) 
	 * 			3，2，1					4					 4，3，2 (没有重复，等于三个)
	 * 
	 */
	private String createValue(HttpServletRequest request, String id) {
		Cookie [] cookies = request.getCookies();
		String proHis = null;
		if(cookies!=null){
			for (Cookie cookie : cookies) {
				if("proHis".equals(cookie.getName())){//cookid中有历史记录
					proHis= cookie.getValue();
					break;
				}
			}
		}
		
		//为null 或者没有proHis
		if(cookies==null || proHis==null){
			return id;
		}
		
		//使用LinkedList操作 
		String [] ids = proHis.split(",");
		Collection coll = Arrays.asList(ids);//把数组变成一个List集合
		//Collection -->LinkedList
		LinkedList list = new LinkedList(coll);
		if(list.size()<3){//cookie的值小于3个的时候
			//id重复
			if(list.contains(id)){
				//去除重复 把id放到最前面
				list.remove(id);
				list.addFirst(id);
			}else{//不重复的情况下 直接加到最前面
				list.addFirst(id);
			}
		}else{//等于3个
			//id重复
			if(list.contains(id)){
				list.remove(id);
				list.addFirst(id);
			}else{//不重复
				//去除最后一个，再加入到最前面一个
				list.removeLast();
				list.addFirst(id);
			}
		}
		
		//list-->string   //3,2,1,
		StringBuffer sb = new StringBuffer();
		for(Object obj:list){
			sb.append(obj+",");
		}
		String result = sb.toString();
		result = result.substring(0, result.length()-1);
		return result;
	}

}
