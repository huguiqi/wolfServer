package com.wolf.dom;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.springframework.util.ResourceUtils;


public class DOMReaderTest {

    @Test
    public void readAccount(){
		try {
			SAXReader sReader=new SAXReader();
			Document doc=sReader.read("test.xml");

			List list=doc.selectNodes("//root");
			Iterator it=list.iterator();
			while(it.hasNext()){
				Element el=(Element)it.next();
				System.out.println("元素有:"+el.getName());
				Iterator it2=el.elementIterator();
				while(it2.hasNext()){
					Element el2=(Element)it2.next();
					System.out.println("子元素有"+el2.getName());
					System.out.println("子元素内容有:"+el2.getText());
				}
			}

//			List list2=doc.selectNodes("//帐号信息//@开户名");
//			for(int i=0;i<list2.size();i++){
//				Attribute att=(Attribute)list2.get(i);
//				System.out.println("属性名="+att.getName());
//				System.out.println("属性值="+att.getValue());
//			}
		} catch (Exception e) {
			e.printStackTrace();
	    }
    }


}


