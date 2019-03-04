package top.flytop.studentsign.utils;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import top.flytop.studentsign.dto.BaseResult;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

/**
 * @ClassName FileUtil
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/5 18:12
 * @Version 1.0
 */
public class FileUtil {

    /**
     * @param sno
     * @param image
     * @param realPath
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @Date 2019/3/2 22:31
     */
    public static BaseResult saveImage(String sno, String image, String realPath) {
        if (sno != null && image != null) {
            //图片名称
            String fileName = sno + ".png";
            File saveDir = new File(realPath);
            //文件夹不存在则新建
            if (!saveDir.exists())
                saveDir.mkdirs();
            String filePath = (realPath + fileName);
            ImageUtil.base64ToImage(image, filePath);
            return new BaseResult<>(true, "stu_pic/" + fileName);
        } else
            return new BaseResult(false, 1, "保存失败");
    }

    /**
     * @param request  接收的请求
     * @param fileName 想要命名的文件名称
     * @param paraName request中文件的key
     * @param dir      想要保存的文件夹,*名后加/
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @date 2019/1/3 21:28
     */
    public static BaseResult saveFileByReq(HttpServletRequest request, String fileName, String paraName, String dir) {
        if (fileName == null)
            fileName = UUID.randomUUID().toString();
        //获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath(dir);
        System.out.println("realPath:" + realPath);
        if (!realPath.endsWith("\\"))
            realPath += "\\";
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        /*页面控件的文件流**/
        MultipartFile multipartFile = multipartRequest.getFile(paraName);
        try {
            /*根据真实路径创建目录**/
            File saveDir = new File(realPath);
            if (!saveDir.exists())
                saveDir.mkdirs();
            /*获取文件的后缀**/
            String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            /*拼成完整的文件保存路径**/
            String filePath = realPath + fileName + suffix;
            System.out.println("filePath:" + filePath);

            File file = new File(filePath);
            multipartFile.transferTo(file);
            //相对Web项目的路径
            String relativePath = "/" + dir + fileName + suffix;
            return new BaseResult<>(true, relativePath);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResult(false, 1, "文件上传保存失败" + e.getMessage());
        }
    }


    /**
     * @param oldPath 旧文件的路径，必须包括文件名
     * @param newPath
     * @param cover
     * @return void
     * @Description TODO 对文件进行移动和重命名
     * @date 2019/1/5 18:30
     */
    public static Boolean MoveRename(String oldPath, String newPath, boolean cover) {
        if (!oldPath.equals(newPath)) {
            try {
                File oldFile = new File(oldPath);
                File newFile = new File(newPath);
                if (newFile.exists()) {
                    //若在待转移目录下，已经存在待转移文件
                    if (cover)
                        //覆盖
                        oldFile.renameTo(newFile);
                    else
                        System.out.println("在新目录下已经存在同名文件");
                } else {
                    oldFile.renameTo(newFile);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    /*
     *//**
     * @param path
     * @param oldName
     * @param newName
     * @return void
     * @Description TODO 文件重命名
     * @date 2019/1/5 18:21
     *//*
    public void renameFile(String path, String oldName, String newName) {
        if (!oldName.equals(newName)) {
            try {
                //新的文件名和以前文件名不同时,才有必要进行重命名
                File oldFile = new File(path + "/" + oldName);
                File newFile = new File(path + "/" + newName);
                if (newFile.exists())
                    //若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                    System.out.println(newName + "已经存在！");
                else {
                    oldFile.renameTo(newFile);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    *//**
     * @param oldPath 旧文件的路径，必须包括文件名
     * @param newPath
     * @param cover   是否覆盖
     * @return void
     * @Description TODO 文件移动
     * @date 2019/1/5 18:19
     *//*
    public void MoveFile(String oldPath, String newPath, boolean cover) {
        if (!oldPath.equals(newPath)) {
            try {
                File oldFile = new File(oldPath);
                File newFile = new File(newPath);
                if (newFile.exists()) {//若在待转移目录下，已经存在待转移文件
                    if (cover)//覆盖
                        oldFile.renameTo(newFile);
                    else
                        System.out.println("在新目录下已经存在同名文件");
                } else {
                    oldFile.renameTo(newFile);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/
}
