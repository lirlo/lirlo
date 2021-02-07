package com.lirlo.system.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileModifiedTimeSortUtils {

    public static List<File> getFiles(String folderPath,List<File> fileList){
        File folderFile = new File(folderPath);
        if(folderFile.exists()&&folderFile.isDirectory()){
            File[] files = folderFile.listFiles();
            for (File file:files) {
                if(file.isDirectory()){
                    getFiles(file.getPath(),fileList);
                }else{
                    fileList.add(file);
                }
            }
        }

        return fileList;
    }

    public static void compareModifyTime(String folderPath){
        List<File> fileList = new ArrayList<>();
        List<File> files = getFiles(folderPath,fileList);
        Collections.sort(files,(o1, o2) -> {
            long diff = o2.lastModified() - o1.lastModified();
            if(diff>0){
                return 1;
            }else if(diff==0){
                return 0;
            }else{
                return -1;
            }
        });
        FileOutputStream fos = null;
        File f = new File(folderPath+"\\检测结果.txt");
        try {
            if(!f.exists()){
                f.createNewFile();
            }
            fos = new FileOutputStream(f);
            for (int i = 0; i < files.size(); i++) {
                String modifyDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        .format(LocalDateTime.ofInstant(Instant.ofEpochMilli(files.get(i).lastModified()), ZoneId.of("Asia/Shanghai")));
                String text = "文件序号："+(i+1)+"，文件路径："+files.get(i).getPath()+"，修改时间："+modifyDate+System.getProperty("line.separator");
                byte[] b = text.getBytes();
                fos.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args){
        compareModifyTime("F:\\inspur\\code\\01-kfpt");
    }
}
