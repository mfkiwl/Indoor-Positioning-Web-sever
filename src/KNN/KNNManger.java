package KNN;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class KNNManger
 */
@WebServlet("/KNNManger")
public class KNNManger extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	Float Xcoord;
	Float Ycoord;
	Float AP1;
	Float AP2;
	Float AP3;
	Float AP4;
	Float AP5;
	String url = "jdbc:mysql://localhost:3306/rssi";
	String username="root";
    String password="password";
    Connection con;
    Statement stat;
    List<KNNData> kd;
	
	
    public KNNManger() throws ClassNotFoundException, SQLException {
        super();
        // TODO Auto-generated constructor stub
        Class.forName("com.mysql.jdbc.Driver");
		con = (Connection) DriverManager.getConnection(url, username, password);
		stat=(Statement) con.createStatement();//statment���󣬽�sql��䷢�͸����ݿ�
        
		String sql="select * from information";
  		ResultSet rs=stat.executeQuery(sql);    //���ؽ����
		
		System.out.println(rs);
		
		kd = new ArrayList<KNNData>();
		while(rs.next())
		{
			
				Xcoord=Float.valueOf(rs.getString(1));
				Ycoord=Float.valueOf(rs.getString(2));
				AP1=Float.valueOf(rs.getString(3));
				AP2=Float.valueOf(rs.getString(4));
				AP3=Float.valueOf(rs.getString(5));
				AP4=Float.valueOf(rs.getString(6));
				AP5=Float.valueOf(rs.getString(7));
				kd.add(new KNNData(Xcoord,Ycoord,AP1,AP2,AP3,AP4,AP5));
		}
		
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
		
		doGet(request, response);
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));//ʹ���ַ�����ȡ�ͻ��˷�����������
		String line = null;
		StringBuffer buffer = new StringBuffer();
		while ((line = br.readLine()) != null) {
			buffer.append(line);
		}
		br.close();
		//���ݿ����
		try {
			JSONObject jsonObject=new JSONObject(buffer.toString());
			System.out.println(jsonObject);
			AP1=Float.valueOf(jsonObject.getString("AP1"));
			AP2=Float.valueOf(jsonObject.getString("AP2"));
			AP3=Float.valueOf(jsonObject.getString("AP3"));
			AP4=Float.valueOf(jsonObject.getString("AP4"));
			AP5=Float.valueOf(jsonObject.getString("AP5"));
		} catch (JSONException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
		
		KNNData locat=KNN.knnCal(1, new KNNData(0,0,AP1,AP2,AP3,AP4,AP5), kd);

        System.out.println(locat.x);
        System.out.println(locat.y);
        
        JSONObject jsonObject=new JSONObject();
        try {
			jsonObject.put("X",locat.x);
			jsonObject.put("Y",locat.y);
		} catch (JSONException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
        
        //���͸�Android�˵�����
		ServletOutputStream out = response.getOutputStream();        //��װ���괫�͵�Android�ͻ���
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
		bw.write(jsonObject.toString());
		System.out.println(jsonObject.toString());   
		bw.flush();//ˢ�»������������ݷ��ͳ�ȥ
        out.close();
        bw.close();//ʹ����ر�
	}
}
