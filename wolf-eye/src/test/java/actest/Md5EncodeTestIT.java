package actest;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class Md5EncodeTestIT {

@Value(value = "${crm.url}")
private String crmPath;
    @Test
    public  void testMd5Encode(){
        System.out.println("----" + getMd5EncodePassword("123456") + "----");
        System.out.println("----crmUrl=" + crmPath + "----");
    }

    private String getMd5EncodePassword(String password){
        Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
        //md5PasswordEncoder.setEncodeHashAsBase64(true);
        String result = md5PasswordEncoder.encodePassword(password, "base64");
        return result;
    }
}
