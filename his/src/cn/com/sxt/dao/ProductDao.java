package cn.com.sxt.dao;
import java.util.*;

import cn.com.sxt.entity.Product;
/**
 * Product����
 * @author xz
 */
public class ProductDao {
	private static List<Product> data = new ArrayList<Product>();
	static{
		for(int i=1;i<=10;i++){
			data.add(new Product(i+"", "�ʼǱ�"+i, "L000"+i, 3400));
		}
	}
	
	/**
	 * ��ѯ����
	 * @return list
	 */
	public List<Product> showAll(){
		return data;
	}
	
	public Product findById(String id){
		for(Product p : data){
			if(p.getId().equals(id)){
				return p;
			}
		}
		return null;
	}
}
