package cn.wsq.utils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/*
* jersey文件上传
* */
public class JerseyUtils {
    /*
    * url是上传文件的路径，bytes是要上传的文件
    * */
    public static boolean upload(String url,byte[] bytes){
        try {
            Client client = new Client();
            WebResource resource = client.resource(url);
            resource.put(String.class, bytes);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        boolean upload = upload("", "123".getBytes());
        System.out.println(upload);
    }
}
