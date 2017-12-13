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
 * ʹ��fileUpload���ʵ���ļ����ϴ�
 * @author Administrator
 *
 */
public class FileUploadServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//����һ���ļ��ϴ�������
		FileItemFactory fac = new DiskFileItemFactory();
		
		//�����ļ��ϴ��ĺ��Ķ���
		ServletFileUpload upload =  new ServletFileUpload(fac);
		
		//�����ϴ��ļ��Ĵ�С
		upload.setFileSizeMax(1024*1024*30);//30M
		
		//�������ļ���С
		upload.setSizeMax(50*1024*1024);//50M
		
		//�����ļ��ϴ�����
		upload.setHeaderEncoding("utf-8"); //request.setChar...
		
		
		//�жϣ���ǰ���Ƿ�Ϊ�ļ��ϴ��ı�
		if(upload.isMultipartContent(request)){
			try {
				//��ȡ��������ת���� FileItem ���� �ļ���(List)
				List<FileItem> list = upload.parseRequest(request);
				
				//���� �õ�ÿһ���ϴ���
				for(FileItem fileItem :list){
					//�ж� ����ͨ����  �����ļ��ϴ���
					if(fileItem.isFormField()){//��ͨ����
						String fileName = fileItem.getFieldName();//�ı�������
						String content = fileItem.getString(); //  �ı����ֵ
					}else{
						//�ļ��ϴ���
						/*String fileName = fileItem.getFieldName();//�ı�������
						String conteType = fileItem.getContentType();//�ļ�����
						String name = fileItem.getName();// �ļ���
						String content = fileItem.getString(); //  �ı����ֵ
						InputStream in = fileItem.getInputStream();//�ļ���*/
						
						//�����ļ���������
						String name = fileItem.getName();//�ļ���
						
						String id = UUID.randomUUID().toString();//����һ��32������� Ψһ��
						name = id+"_"+name;
						//��ȡ  ָ�������·��
						String path = this.getServletContext().getRealPath("/upload");
						
						//����Ŀ���ļ�
						File file = new File(path,name);
						//ֱ��д�뵽�ļ�
						fileItem.write(file);
						
						//ɾ��ϵͳ��������ʱ�ļ�
						fileItem.delete();
					}
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("��ǰ�������ļ��ϴ��ı�");
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
