/**
 * @version $Id: Digger.java v,1.0.0 2010-1-7 下午01:43:38 孙豪杰  $
 * @copyright (c) 2010 北京叁加伍网络科技有限公司
 * @link  http://java.plugbase.org
 * 
 * diggold 功能描述
*/
package com.javaeye.sunhj.url;

import java.io.IOException;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class Digger extends Thread{
	private Url url;
	
	public Digger() {
		super();
	}
	
	public Digger(Url url) {
		this.url = url;
	}
	
	public static void main(String[] args) throws IOException {
		FileReader reader = new FileReader("D:\\Workspaces\\wolf\\wolf-eye\\src\\main\\resources\\url.txt");
		List<String> urls = reader.getLines();
		
		for (String string : urls) {
			Url url = new Url(string);
			Digger digger = new Digger(url);
			digger.start();
		}
	}

	@Override
	public void run() {
//		if(url.isExist("free_msg")) {
			try {
				Runtime.getRuntime().exec("F:\\Program Files\\ChromePlus\\chrome.exe   " + url.getUrl());
			} catch (IOException e) {
				e.printStackTrace();
			}
//		}
		System.out.println(url.getUrl() + "END!");
	}
}