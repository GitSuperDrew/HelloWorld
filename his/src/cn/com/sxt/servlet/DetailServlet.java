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
 * չʾ��Ʒ����
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
		html+="<head><title>��Ʒ����</title></head>";
		html+="<body>";
		html+="<table border='1' align='center' width='600px'>";
		html+="<tr><td>��Ʒ���</td><td>"+product.getId()+"</td></tr>";
		html+="<tr><td>��Ʒ����</td><td>"+product.getName()+"</td></tr>";
		html+="<tr><td>��Ʒ�ͺ�</td><td>"+product.getType()+"</td></tr>";
		html+="<tr><td>��Ʒ�۸�</td><td>"+product.getPrice()+"</td></tr>";
		html+="</table>";
		html+="<br/><br/>";
		html+="<a href='"+request.getContextPath()+"/ListServlet'>[�����б�]</a>";
		html+="</body>";
		html+="</html>";
		
		//����cookie�����͸������
		Cookie cookie = new Cookie("proHis", createValue(request,id));//
		cookie.setMaxAge(1*60*60*24*30);//����һ����
		response.addCookie(cookie);
		response.getWriter().write(html);
	}
	
	/**
	 * ����cookie��ֵ
	 * @param request
	 * @param id
	 * @return
	 * 		��ǰcookie��ֵ			�����id��ֵ			���յ�cookie��ֵ
	 * 	 null ���� proHisû��			1					 1
	 * 			1						2					 2,1  (û���ظ�)
	 * 			2��1					1					 1,2  (���ظ���С��3��) 
	 * 			3��2��1					2					 2��3��1 (���ظ�������3��) 
	 * 			3��2��1					4					 4��3��2 (û���ظ�����������)
	 * 
	 */
	private String createValue(HttpServletRequest request, String id) {
		Cookie [] cookies = request.getCookies();
		String proHis = null;
		if(cookies!=null){
			for (Cookie cookie : cookies) {
				if("proHis".equals(cookie.getName())){//cookid������ʷ��¼
					proHis= cookie.getValue();
					break;
				}
			}
		}
		
		//Ϊnull ����û��proHis
		if(cookies==null || proHis==null){
			return id;
		}
		
		//ʹ��LinkedList���� 
		String [] ids = proHis.split(",");
		Collection coll = Arrays.asList(ids);//��������һ��List����
		//Collection -->LinkedList
		LinkedList list = new LinkedList(coll);
		if(list.size()<3){//cookie��ֵС��3����ʱ��
			//id�ظ�
			if(list.contains(id)){
				//ȥ���ظ� ��id�ŵ���ǰ��
				list.remove(id);
				list.addFirst(id);
			}else{//���ظ�������� ֱ�Ӽӵ���ǰ��
				list.addFirst(id);
			}
		}else{//����3��
			//id�ظ�
			if(list.contains(id)){
				list.remove(id);
				list.addFirst(id);
			}else{//���ظ�
				//ȥ�����һ�����ټ��뵽��ǰ��һ��
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
