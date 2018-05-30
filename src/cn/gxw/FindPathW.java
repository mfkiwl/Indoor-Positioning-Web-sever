package cn.gxw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class FindPathW
 */
@WebServlet("/FindPathW")
public class FindPathW extends HttpServlet {              //��������ʵ��·�����ݵĴ���
	private static final long serialVersionUID = 1L;
    
	int Spoint;     //���
	int Epoint;     //�յ�
	static ArrayList<Integer> list = new ArrayList<Integer>();  //ʹ������洢·��
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindPathW() {
        super();
        // TODO Auto-generated constructor stub
        
    }

    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	
	
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		//�õ�Ҫ����������
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));//ʹ���ַ�����ȡ�ͻ��˷�����������
		String line = null;
		StringBuffer buffer = new StringBuffer();
		while ((line = br.readLine()) != null) {
			buffer.append(line);
		}
		br.close();
		
		try {
			JSONObject jsonObject=new JSONObject(buffer.toString());
			System.out.println(jsonObject);
			Spoint=Integer.parseInt(jsonObject.getString("Spoint"));
			Epoint=Integer.parseInt(jsonObject.getString("Epoint"));
		} catch (JSONException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
		list=FindPath.getList(Spoint,Epoint);        //�õ�·��
		
		System.out.println(list);
		System.out.println(Spoint);
		System.out.println(Epoint);
		
		
		int[] d = new int[list.size()];
		for(int i = 0;i<list.size();i++){
		    d[i] = list.get(i);
		}
		
		JSONObject jsonObject=new JSONObject();
        try {
        	for(int i=0;i<d.length;i++)
        	{
    			jsonObject.put("��"+i+"��",d[i]);
        	}
        	jsonObject.put("�ܲ���",d.length);
        	list.clear();      //���list�е�����
		} catch (JSONException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
        
        //���͸�Android�˵�����
		ServletOutputStream out = response.getOutputStream();        //��װ���괫�͵�Android�ͻ���
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out,"utf-8"));
//		new String(s.getBytes("gbk"),"utf-8")   jsonObject.toString()
		bw.write(jsonObject.toString());
		System.out.println(jsonObject.toString());   
		bw.flush();//ˢ�»������������ݷ��ͳ�ȥ
        out.close();
        bw.close();//ʹ����ر�
	}

}
