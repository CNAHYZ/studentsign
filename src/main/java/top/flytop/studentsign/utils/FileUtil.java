package top.flytop.studentsign.utils;

import java.io.File;

/**
 * @ClassName FileUtil
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/5 18:12
 * @Version 1.0
 */
public class FileUtil {
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
