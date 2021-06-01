package com.naturecloud.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;


public class Response  extends HttpServlet{
	static ReadFile read=new ReadFile();
	String str="";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
		resp.sendError(405, "failed");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		BufferedReader reader =null;
		try {
			reader=req.getReader();
			StringBuffer content =new StringBuffer();
			String line;
			while((line =reader.readLine())!=null){
				content.append(line);
			}
			String result=content.toString();
			String sys=update(result);
			resp.getWriter().write("send:"+result+"\r\n"+sys);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			log(e.toString());
			e.printStackTrace();
		}finally {
			try {
				reader.close();
				reader=null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static  String send(JSONObject json) {
		DataOutputStream out=null;
		BufferedReader in=null;
		String result="";
		try {
		//	URL realUrl=new URL("http://"+read.readValue("node.host")+":"+read.readValue("node.port")+"/apiApplication/autoupd");
			URL realUrl=new URL("http://"+read.readValue("node.url")+"/apiApplication/autoupd");
			HttpURLConnection conn=(HttpURLConnection) realUrl.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.connect();
			out=new DataOutputStream(conn.getOutputStream());
			out.writeBytes(json.toString());
			out.flush();
			in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while((line=in.readLine())!=null){
				result+=line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(out!=null){
					out.close();
				}
				if(in!=null){
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	public String update(String param) {
		String result1 = null;
		String param1=param.split("-")[0];
		String username=param1.split("/")[0];
		String image=param1.split("/")[1];
		String version=param.split("-")[1];
		Connection connection=new Connection();
		List list=connection.getServices(username);
		for(int i=0;i<list.size();i++){
			JSONObject service=JSONObject.fromObject(list.get(i));
			try {
				String shortname=service.getJSONObject("node").getString("imageshortname").toString();
				String updatestatus="";
				if(!"".equals(service.get("updateEnabled").toString())){
				updatestatus=service.get("updateEnabled").toString();
				}
			if("true".equals(updatestatus)){
				if("running".equals(service.get("totalStatue").toString())){
					if(image.equals(shortname)){
						String oldver=service.get("ver").toString();
						service.remove("_id");
						JSONObject json=new JSONObject();
						json.put("data", service);
						json.put("ver", version);
						result1=Response.send(json);
					}
				}
			
			}
				} catch (Exception e) {
			}
		}
		return result1;
	}

}
