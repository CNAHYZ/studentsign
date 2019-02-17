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

@Component
public class ImageUtil {

    /**
     * @param sno
     * @param image
     * @return
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
            base64ToImage(image, filePath);
            return new BaseResult(true, "stu_pic/" + fileName);
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
    public static BaseResult saveImgByReq(HttpServletRequest request, String fileName, String paraName, String dir) {
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
