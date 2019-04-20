package top.flytop.studentsign.utils;

import com.baidu.aip.face.AipFace;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import top.flytop.studentsign.dto.BaseResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class FaceUtil {

    // 设置APPID/AK/SK
    private static final String APP_ID = "14988441";
    private static final String API_KEY = "r3pu9yACLK2m0zit0stAd47v";
    private static final String SECRET_KEY = "BLkcLLdexDdoco1GExFfpjp43CG8y7jz";
    private AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
    private JSONObject res = null;
    private String faceToken = null;// 人脸图片的唯一标识
    private BaseResult temp;// 临时存储BaseResult对象

    FaceUtil() {
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(4000);
        client.setSocketTimeoutInMillis(60000);
        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        // System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

    }

    /**
     * @param res
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 用于判断是否返回错误代码
     * @date 2019/1/11 15:52
     */
    private BaseResult isNormal(JSONObject res) {
        int errCode = res.getInt("error_code");
        switch (errCode) {
            case 0:
                //状态正常
                return BaseResult.success(null);
            case 222202:
                //pic not has face
                return temp = BaseResult.fail(errCode, "图片中没有人脸信息，请重试！");
            default:
                // 状态异常
                String errMsg = res.getString("error_msg");
                return temp = BaseResult.fail(errCode, "操作失败！  " + errMsg);
        }
    }

    /**
     * @param uid
     * @return void
     * @Description TODO 用于获取一个用户的全部人脸列表。
     * @date 2019/1/11 15:54
     */
    public BaseResult getUserFace(String uid) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        String groupId = "user";
        // 获取用户人脸列表
        res = client.faceGetlist(uid, groupId, options);
        if (!isNormal(res).isSuccess())
            // errCode不为0，直接返回错误信息
            return temp;
        else {
            System.out.println("=====================获取人脸列表结果=====================");
            String a = res.toString(2);
            System.out.println(a);
            JSONArray faceList = res.getJSONObject("result").getJSONArray("face_list");
            ArrayList<Map> faceMapList = new ArrayList<>();
            for (int i = 0; i < faceList.length(); i++) {
                String ctime = faceList.getJSONObject(i).getString("ctime");
                String faceToken = faceList.getJSONObject(i).getString("face_token");

                Map<String, String> faceMap = new HashMap<>();
                faceMap.put("ctime", ctime);
                faceMap.put("faceToken", faceToken);
                faceMapList.add(faceMap);
            }
            System.out.println(faceMapList);
            return BaseResult.success(faceMapList);
        }
    }

    /**
     * @param image
     * @return top.flytop.studentsign.dto.BaseResult<java.lang.String>
     * @Description TODO 人脸检测方法
     * @date 2019/1/11 15:55
     */
    public BaseResult faceChecker(String image) {
        String imageType = "BASE64";
        // 人脸完整度，0或1, 0为人脸溢出图像边界，1为人脸都在图像边界内
        int completeness;
        // 人脸置信度，范围【0~1】，代表这是一张人脸的概率，0最小、1最大。
        double faceProbability;
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("face_field", "quality");
        // 人脸检测
        res = client.detect(image, imageType, options);
        System.out.println("=====================检测返回结果=====================");
        System.out.println(res.toString(2));

        if (!isNormal(res).isSuccess())
            // errCode不为0，直接返回错误信息
            return temp;
        else {
            JSONObject faceList0 = res.getJSONObject("result").getJSONArray("face_list").getJSONObject(0);
            completeness = faceList0.getJSONObject("quality").getInt("completeness");
            faceProbability = faceList0.getDouble("face_probability");
            if (faceProbability < 0.8) {
                return BaseResult.fail(1, "无法检测到人脸");
            } else if (completeness == 0) {
                return BaseResult.fail(1, "面部图像不完整");
            } else {
                faceToken = faceList0.getString("face_token");
                return BaseResult.success(faceToken);
            }
        }
    }

    /**
     * @param uid
     * @param image
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 人脸注册
     * @date 2019/1/11 15:55
     */
    public BaseResult faceReg(String uid, String image) {
        String imageType = "FACE_TOKEN";
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        /*
         * options.put("user_info", "user's info"); options.put("quality_control",
         * "NORMAL"); options.put("liveness_control", "LOW");
         */
        String groupId = "user";
        // 人脸注册
        res = client.addUser(image, imageType, groupId, uid, options);
        System.out.println("=====================注册返回结果=====================");
        System.out.println(res.toString(2));
        String faceToken = res.getJSONObject("result").getString("face_token");

        if (isNormal(res).isSuccess())
            // 注册成功
            return BaseResult.success(faceToken);
        else
            return new BaseResult(false, 1, "上传失败：" + res.getString("error_msg"));
    }

    /**
     * @param image
     * @param uid
     * @return top.flytop.studentsign.dto.BaseResult<java.util.Map>
     * @Description TODO 人脸搜索，用于签到、登录功能
     * @date 2019/1/11 15:56
     */
    public BaseResult<Map> faceSearch(String image, String uid) {
        System.out.println(uid);
        String imageType = "BASE64";
        Map<String, Object> param = new HashMap<String, Object>();
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
//        options.put("quality_control", "NORMAL");
        /*活体检测控制 NONE: 不进行控制 LOW:较低的活体要求
         * NORMAL:一般的活体要求(平衡的攻击拒绝率, 通过率) HIGH:
         * 较高的活体要求(高攻击拒绝率 低通过率)*/
//        options.put("liveness_control", "NORMAL");
        String groupIdList = "user";
        options.put("user_id", uid);
        res = client.search(image, imageType, groupIdList, options);
        System.out.println("=====================搜素返回结果=====================");
        System.out.println(res.toString(2));
        if (!isNormal(res).isSuccess()) {
            return isNormal(res);
        }
        JSONObject userList0 = res.getJSONObject("result").getJSONArray("user_list").getJSONObject(0);
        // 取出返回的uid和score
        String sNo = userList0.getString("user_id");
        param.put("sNo", sNo);
        Double score = userList0.getDouble("score");
        param.put("score", score);
        return new BaseResult<Map>(true, param);
    }

    /**
     * @param uid
     * @param faceToken
     * @return void
     * @Description TODO 人脸删除
     * @date 2019/1/11 15:56
     */
    public BaseResult faceRemove(String uid, String faceToken) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        String groupId = "user";

        // 人脸删除
        res = client.faceDelete(uid, groupId, faceToken, options);
        System.out.println("=====================删除返回结果=====================");
        System.out.println(res.toString(2));
        if (!isNormal(res).isSuccess()) {
            return isNormal(res);
        }
        return BaseResult.success("删除成功!");
    }
}
