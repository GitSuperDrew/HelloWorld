package cn.com.sxt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.sxt.dao.ProductDao;
import cn.com.sxt.entity.Product;

/**
 * չʾ������Ʒ
 * @author Administrator
 *
 */
public class ListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		
		ProductDao dao = new ProductDao();
		List<Product> list = dao.showAll();
		
		String html="";
		html+="<html>";
		html+="<head><title>չʾ��Ʒ</title></head>";
		html+="<body>";
		html+="<table border='1' align='center' width='600px'>";
		html+="<tr><td>��Ʒ���</td><td>��Ʒ����</td><td>��Ʒ�ͺ�</td><td>��Ʒ�۸�</td></tr>";
		if(list!=null){
			for (Product product : list) {
				html+="<tr><td>"+product.getId()
				+"</td><td><a href='"+request.getContextPath()+"/DetailServlet?id="+product.getId()
				+"'>"+product.getName()+"</a></td><td>"+product.getType()+"</td><td>"
				+product.getPrice()+"</td></tr>";
			}
		}
		html+="</table>";
		html+="������������Ʒ<br>";
		//ȡ��proHis��cookieֵ
		Cookie [] cookies = request.getCookies();
		if(cookies!=null){
			for(Cookie cookie:cookies){
				if("proHis".equals(cookie.getName())){//�ж�cookie���Ƿ���proHis
					String proHis = cookie.getValue();//1/2/3../10
					
					String [] ids = proHis.split(",");//3,2,1
					for(String id:ids){
						Product product = dao.findById(id);
						html+=product.getId()+"&nbsp;"+product.getName()+"&nbsp;"+product.getPrice()+"<br/>";
					}
				}
			}
		}
		
		html+="</body>";
		html+="</html>";
		
		response.getWriter().write(html);
		

	}
}
