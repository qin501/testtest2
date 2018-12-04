package cn.wsq.controller;

import com.baidu.ueditor.ActionEnter;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;

@Controller
public class JSPController {
    /*
    * {"videoMaxSize":102400000,"videoActionName":"uploadvideo","fileActionName":"uploadfile","fileManagerListPath":"/ueditor/jsp/upload/file/","imageCompressBorder":1600,"imageManagerAllowFiles":[".png",".jpg",".jpeg",".gif",".bmp"],"imageManagerListPath":"/ueditor/jsp/upload/image/","fileMaxSize":51200000,"fileManagerAllowFiles":[".png",".jpg",".jpeg",".gif",".bmp",".flv",".swf",".mkv",".avi",".rm",".rmvb",".mpeg",".mpg",".ogg",".ogv",".mov",".wmv",".mp4",".webm",".mp3",".wav",".mid",".rar",".zip",".tar",".gz",".7z",".bz2",".cab",".iso",".doc",".docx",".xls",".xlsx",".ppt",".pptx",".pdf",".txt",".md",".xml"],"fileManagerActionName":"listfile","snapscreenInsertAlign":"none","scrawlActionName":"uploadscrawl","videoFieldName":"upfile","imageCompressEnable":true,"videoUrlPrefix":"","fileManagerUrlPrefix":"","catcherAllowFiles":[".png",".jpg",".jpeg",".gif",".bmp"],"imageManagerActionName":"listimage","snapscreenPathFormat":"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}","scrawlPathFormat":"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}","scrawlMaxSize":2048000,"imageInsertAlign":"none","catcherPathFormat":"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}","catcherMaxSize":2048000,"snapscreenUrlPrefix":"","imagePathFormat":"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}","imageManagerUrlPrefix":"","scrawlUrlPrefix":"","scrawlFieldName":"upfile","imageMaxSize":2048000,"imageAllowFiles":[".png",".jpg",".jpeg",".gif",".bmp"],"snapscreenActionName":"uploadimage","catcherActionName":"catchimage","fileFieldName":"upfile","fileUrlPrefix":"","imageManagerInsertAlign":"none","catcherLocalDomain":["127.0.0.1","localhost","img.baidu.com"],"filePathFormat":"/ueditor/jsp/upload/file/{yyyy}{mm}{dd}/{time}{rand:6}","videoPathFormat":"/ueditor/jsp/upload/video/{yyyy}{mm}{dd}/{time}{rand:6}","fileManagerListSize":20,"imageActionName":"uploadimage","imageFieldName":"upfile","imageUrlPrefix":"./","scrawlInsertAlign":"none","fileAllowFiles":[".png",".jpg",".jpeg",".gif",".bmp",".flv",".swf",".mkv",".avi",".rm",".rmvb",".mpeg",".mpg",".ogg",".ogv",".mov",".wmv",".mp4",".webm",".mp3",".wav",".mid",".rar",".zip",".tar",".gz",".7z",".bz2",".cab",".iso",".doc",".docx",".xls",".xlsx",".ppt",".pptx",".pdf",".txt",".md",".xml"],"catcherUrlPrefix":"","imageManagerListSize":20,"catcherFieldName":"source","videoAllowFiles":[".flv",".swf",".mkv",".avi",".rm",".rmvb",".mpeg",".mpg",".ogg",".ogv",".mov",".wmv",".mp4",".webm",".mp3",".wav",".mid"]}
    *代替了controller.jsp
    * */
    @RequestMapping("/jsp/config")
    public void entryTest(HttpServletRequest request, HttpServletResponse response){
        String path = "";
        try {
            File file = new File(ResourceUtils.getURL("classpath:").getPath());
            path=file.getAbsolutePath()+File.separator+"static"+File.separator;
            System.out.println(path);
            System.out.println("new file:"+new File("").getAbsolutePath());
            response.setContentType("application/json");
            //String exec = new ActionEnter(request,path ).exec();
            String exec = new ActionEnter(request,new File("").getAbsolutePath()+File.separator).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
