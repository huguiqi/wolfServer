/**
 * @version $Id: FileReader.java v,1.0.0 2010-1-7 下午01:29:47 孙豪杰  $
 * @copyright (c) 2010 北京叁加伍网络科技有限公司
 * @link  http://java.plugbase.org
 * 
 * diggold 功能描述
*/
package com.javaeye.sunhj.url;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class FileReader {
	private String fileName;
	
	public FileReader() {
	}
	
	public FileReader(String fileName) {
		this.fileName = fileName;
	}
	
	public List<String> getLines() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.fileName)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		List<String> lines = new LinkedList<String>();
		String line = null;
		try {
			while ( (line = reader.readLine()) != null) {
			    lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return lines;
	}
}
