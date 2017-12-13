package cn.com.sxt.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.sun.mail.imap.protocol.Item;

/**
 * 使用fileUpload组件实现文件的上传
 * @author Administrator
 *
 */
public class FileUploadServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//创建一个文件上传工厂类
		FileItemFactory fac = new DiskFileItemFactory();
		
		//创建文件上传的核心对象
		ServletFileUpload upload =  new ServletFileUpload(fac);
		
		//设置上传文件的大小
		upload.setFileSizeMax(1024*1024*30);//30M
		
		//设置总文件大小
		upload.setSizeMax(50*1024*1024);//50M
		
		//设置文件上传编码
		upload.setHeaderEncoding("utf-8"); //request.setChar...
		
		
		//判断，当前表单是否为文件上传的表单
		if(upload.isMultipartContent(request)){
			try {
				//读取请求数据转换成 FileItem 对象 的集合(List)
				List<FileItem> list = upload.parseRequest(request);
				
				//遍历 得到每一个上传项
				for(FileItem fileItem :list){
					//判断 是普通表单项  还是文件上传项
					if(fileItem.isFormField()){//普通表单项
						String fileName = fileItem.getFieldName();//文本框名字
						String content = fileItem.getString(); //  文本框的值
					}else{
						//文件上传项
						/*String fileName = fileItem.getFieldName();//文本框名字
						String conteType = fileItem.getContentType();//文件类型
						String name = fileItem.getName();// 文件名
						String content = fileItem.getString(); //  文本框的值
						InputStream in = fileItem.getInputStream();//文件流*/
						
						//保存文件到服务器
						String name = fileItem.getName();//文件名
						
						String id = UUID.randomUUID().toString();//产生一个32的随机数 唯一的
						name = id+"_"+name;
						//获取  指定保存的路径
						String path = this.getServletContext().getRealPath("/upload");
						
						//创建目标文件
						File file = new File(path,name);
						//直接写入到文件
						fileItem.write(file);
						
						//删除系统产生的临时文件
						fileItem.delete();
					}
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("当前表单不是文件上传的表单");
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
