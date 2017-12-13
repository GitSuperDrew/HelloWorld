package cn.com.sxt.dao;
import java.util.*;

import cn.com.sxt.entity.Product;
/**
 * Product操作
 * @author xz
 */
public class ProductDao {
	private static List<Product> data = new ArrayList<Product>();
	static{
		for(int i=1;i<=10;i++){
			data.add(new Product(i+"", "笔记本"+i, "L000"+i, 3400));
		}
	}
	
	/**
	 * 查询所有
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
