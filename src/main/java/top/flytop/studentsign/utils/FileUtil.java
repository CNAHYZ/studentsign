package top.flytop.studentsign.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import top.flytop.studentsign.dto.BaseResult;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.UUID;

/**
 * @ClassName FileUtil
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/5 18:12
 * @Version 1.0
 */
@Component
public class FileUtil {

    /**
     * @param filename
     * @param request
     * @param dir
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 根据Base64保存图片文件
     * @Date 2019/3/2 22:31
     */
    public static BaseResult saveImage(HttpServletRequest request, String filename, String dir) {
        String basePath = request.getSession().getServletContext().getRealPath("userUpload");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile faceImage = multipartRequest.getFile("faceImage");
        if (filename == null || faceImage == null) {
            return BaseResult.fail(1, "本地保存失败");
        }
        //图片名称
        String fullName = filename + ".png";
        String realPath = basePath + "/faceImg/" + dir;
        if (!realPath.endsWith("/"))
            realPath += "/";
        File saveDir = new File(realPath);
        //文件夹不存在,且新建失败
        try {
            if (!saveDir.exists() && !saveDir.mkdirs()) {
                return BaseResult.fail(1, "本地保存失败");
            }
            String filePath = (realPath + "/" + fullName);
            File file = new File(filePath);
            faceImage.transferTo(file);

            return BaseResult.success(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail(1, "本地保存失败");
        }
    }

    /**
     * @param request  接收的请求
     * @param fileName 想要命名的文件名称
     * @param paraName request中文件的key
     * @param dir      想要保存的文件夹,*名后加/
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 根据传来的请求保存文件
     * @date 2019/1/3 21:28
     */
    public static BaseResult saveFileByReq(HttpServletRequest request, String fileName, String paraName, String dir) {
        if (fileName == null)
            fileName = UUID.randomUUID().toString();
        //获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("userUpload/" + dir);
        System.out.println("realPath:" + realPath);
        if (!realPath.endsWith("/"))
            realPath += "/";
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
            String relativePath = "/userUpload/" + dir + fileName + suffix;
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

    /**
     * 图片转BASE64
     *
     * @param imagePath 路径
     * @return
     */
    public String imageToBase64(String imagePath) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imagePath);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * BASE转图片 若带有："data:image/jpeg;base64," 解码之前得去掉。
     *
     * @param baseStr   base64字符串
     * @param imagePath 生成的图片
     * @return
     */
    public static boolean base64ToImage(String baseStr, String imagePath) {
        if (baseStr == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(baseStr);
            // 调整异常数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(imagePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
