/**
 * @version $Id: Url.java v,1.0.0 2010-1-7 下午01:09:25 孙豪杰  $
 * @copyright (c) 2010 北京叁加伍网络科技有限公司
 * @link  http://java.plugbase.org
 * 
 * diggold 功能描述
*/
package com.javaeye.sunhj.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Administrator
 *
 */
public class Url {
	private String url;
	
	public Url() {
	}
	
	public Url(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public URLConnection getConnection() {
		URL httpUrl = null;
		try {
			httpUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		URLConnection conn = null;
		
		if(httpUrl != null) {
			try {
				conn = httpUrl.openConnection();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return conn;
	}
	
	public BufferedReader getReader() {
		URLConnection conn = getConnection();
		BufferedReader br = null;
		if(conn == null) {
			return null;
		}
		conn.setConnectTimeout(9000);
		try {
			conn.connect();
			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return br;
	}
	
	public boolean isExist(String str) {
		BufferedReader bis = getReader();
		boolean exist = false;
		
		String line = null;
		try {
			while ( (line = bis.readLine()) != null) {
			    exist = line.contains(str);
			    if(exist) {
			    	break;
			    }
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return exist;
	}
}
