package cn.wsq.controller;
import cn.wsq.utils.JerseyUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/*
* {"state": "SUCCESS","original": "IMG_20170622_181210.jpg","size": "1949898","title": "1541560885363053708.jpg","type": ".jpg","url": "/ueditor/jsp/upload/image/20181107/1541560885363053708.jpg"}
* 文件上传
* */
@Controller
public class UploadImagController {
    @Value("${WebImgServerUrl}")
    private String WebImgServerUrl;
    /**
     * 上传图片
     */
    @RequestMapping("uploadImage")
    @ResponseBody
    public Map<String,String> uploadPic(HttpServletRequest request ) {

        Map<String,String> map=new HashMap<>();
        //转换为多文件上传对象
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest= (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multipartHttpServletRequest.getFileMap();
            Set<Map.Entry<String, MultipartFile>> entries = fileMap.entrySet();
            for(Map.Entry<String, MultipartFile> entry:entries) {
                MultipartFile entryValue = entry.getValue();
                if (entryValue != null) {
                    //图片名
                    long millis = System.currentTimeMillis();
                    Random random = new Random();
                    int nextInt = random.nextInt(10);
                    String name = "JMPT" + millis + nextInt;
                    //文件扩展名
                    String extension = FilenameUtils.getExtension(entryValue.getOriginalFilename());
                    String imgName = name + "." + extension;

                    String realPath = WebImgServerUrl+ imgName;
                    System.out.println(WebImgServerUrl+"----------");
                    try {
                        //利用jersey上传到另一个tomcat上
                        boolean upload = JerseyUtils.upload(realPath, entryValue.getBytes());
                        if (!upload) return map;
                        map.put("state", "SUCCESS");
                        map.put("original", entryValue.getOriginalFilename());
                        map.put("size", String.valueOf(entryValue.getSize()));
                        map.put("title", imgName);
                        map.put("type", "." + extension);
                        map.put("url", realPath);
                        return map;
                    } catch (Exception e) {
                        
                        e.printStackTrace();
                    }
                    //{"state": "SUCCESS","original": "IMG_20170622_181210.jpg","size": "1949898","title": "1541560885363053708.jpg","type": ".jpg","url": "/ueditor/jsp/upload/image/20181107/1541560885363053708.jpg"}
                }
            }
        }
        return map;
    }
}
