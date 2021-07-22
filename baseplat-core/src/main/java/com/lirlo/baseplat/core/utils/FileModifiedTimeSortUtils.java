package com.lirlo.baseplat.core.utils;

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
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class FileModifiedTimeSortUtils {

    public static List<File> getFiles(String folderPath,List<File> fileList){
        File folderFile = new File(folderPath);
        if(folderFile.exists()&&folderFile.isDirectory()&&folderFile!=null){
            File[] files = folderFile.listFiles();
            if(files != null){
                for (File file:files) {
                    if(file.isDirectory()){
                        getFiles(file.getPath(),fileList);
                    }else{
                        fileList.add(file);
                    }
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

    public static void getFileSize(String folderPath){
        File folderFile = new File(folderPath);
        if(folderFile.exists()&&folderFile.isDirectory()){
            File[] files = folderFile.listFiles();
            for (File file : files) {
                System.out.println(file.getPath()+"："+file.length());
            }
        }
    }

    public static String fileName = "D:\\";

    class SubDirectoriesAndSize {
        final public long size;
        final public List<File> subDirectories;
        public SubDirectoriesAndSize(final long totalSize,
                                     final List<File> theSubDirs) {
            size = totalSize;
            subDirectories = Collections.unmodifiableList(theSubDirs);
        }
    }
    private SubDirectoriesAndSize getTotalAndSubDirs(final File file) {
        long total = 0;
        final List<File> subDirectories = new ArrayList<File>();
        if (file.isDirectory()) {
            final File[] children = file.listFiles();
            if (children != null)
                for (final File child : children) {
                    if (child.isFile())
                        total += child.length();
                    else
                        subDirectories.add(child);
                }
        }
        return new SubDirectoriesAndSize(total, subDirectories);
    }
    private long getTotalSizeOfFilesInDir(final File file)
            throws InterruptedException, ExecutionException, TimeoutException {
        final ExecutorService service = Executors.newFixedThreadPool(100);
        try {
            long total = 0;
            final List<File> directories = new ArrayList<File>();
            directories.add(file);
            while (!directories.isEmpty()) {
                final List<Future<SubDirectoriesAndSize>> partialResults = new ArrayList<Future<SubDirectoriesAndSize>>();
                for (final File directory : directories) {
                    partialResults.add(service
                            .submit(new Callable<SubDirectoriesAndSize>() {
                                public SubDirectoriesAndSize call() {
                                    return getTotalAndSubDirs(directory);
                                }
                            }));
                }
                directories.clear();
                for (final Future<SubDirectoriesAndSize> partialResultFuture : partialResults) {
                    final SubDirectoriesAndSize subDirectoriesAndSize = partialResultFuture
                            .get(100, TimeUnit.SECONDS);
                    directories.addAll(subDirectoriesAndSize.subDirectories);
                    total += subDirectoriesAndSize.size;
                }
            }
            return total;
        } finally {
            service.shutdown();
        }
    }

    public static void main(String[] args){
        final long start = System.nanoTime();
        final long total;
        try {
            total = new FileModifiedTimeSortUtils().getTotalSizeOfFilesInDir(new File(fileName));
            final long end = System.nanoTime();
            System.out.println("Total Size: " + total);
            System.out.println("Time taken: " + (end - start) / 1.0e9);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
