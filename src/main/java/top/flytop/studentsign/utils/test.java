package top.flytop.studentsign.utils;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName test
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/2/18 16:51
 * @Version 1.0
 */
public class test {
    public static XSSFWorkbook exportExcelInfo(String idList) {
        XSSFWorkbook xssfWorkbook = null;
        try {
            //根据ID查找数据
            String[] list = idList.split(",");
//            List<AzcItemInfo> azcItemInfoList = new ArrayList<AzcItemInfo>();
//            for(int i=0;i<list.length;i++){
//                AzcItemInfo azcItemInfo = azcItemInfoMapper.selectByPrimaryKey(list[i]);
//                AzcItemInfo azcItemInfo = new AzcItemInfo(1,"233","324333ddd对对对","", Date.valueOf("2017-01-01"));
//                AzcItemInfo azcItemInfo2 = new AzcItemInfo(2,"233","对对对","23432", Date.valueOf("2018-01-01"));
//                azcItemInfoList.add(azcItemInfo);
//                azcItemInfoList.add(azcItemInfo2);
//            }

            List<ExcelBean> excel = new ArrayList<>();
            Map<Integer, List<ExcelBean>> map = new LinkedHashMap<>();

            //设置标题栏
            excel.add(new ExcelBean("ID", "id", 0));
            excel.add(new ExcelBean("名称", "name", 0));
            excel.add(new ExcelBean("itemurl", "itemurl", 0));
            excel.add(new ExcelBean("zrf", "zrf", 0));
            excel.add(new ExcelBean("date", "date", 0));
            map.put(0, excel);
            String sheetName = "AzcItemInfo";
//            xssfWorkbook = ExcelUtil.createExcelFile(AzcItemInfo.class, azcItemInfoList, map, sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xssfWorkbook;
    }

    public static void main(String[] args) {
        XSSFWorkbook x = exportExcelInfo("1,2,3,r,5,6");
        OutputStream output;
        try {
            output = new FileOutputStream("232.xls");
            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
            bufferedOutPut.flush();
            x.write(bufferedOutPut);
            bufferedOutPut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
