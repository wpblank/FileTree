package cn.lzumi;

//import java.io.FileNotFoundException;
//import java.io.IOException;

import java.io.File;
import java.util.Arrays;

import static cn.lzumi.GetPinYIn.getPinYin;

public class FileTree {
    public static void main(String[] args) {
        String file = "D:\\D\\【he11oworld.com】毕向东25天";
        //String s = getPinYin("一个拼音testTT");
        //System.out.println(s);
        showDir(file, 0);
        //System.out.println(getDirSize(new File(file)));
    }


    //文件层级信息
    private static String levelSign(int level) {
        StringBuilder sb = new StringBuilder();
        sb.append("▕——");
        for (int x = 0; x < level; x++) {
            sb.insert(0, "▕  ");
        }
        return sb.toString();
    }

    //目录分隔线
    private static String separateDir(int level) {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < 20 - level; x++) {
            sb.insert(0, "——");
        }
        return sb.toString();
    }

    //打印目录文件和文件夹名称和大小
    //path为文件夹路径，level为初始层级，一般为0
    private static void showDir(String path, int level) {

        File file = new File(path);
        level++;
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files != null) {
                if (files.length == 0)
                    System.out.println(file.getAbsolutePath() + " 文件夹为空");
                else {
                    //对文件和文件夹进行排序
                    Arrays.sort(files, (o1, o2) -> {
                        //将文件夹与文件分开排序
                        if (o1.isDirectory() && o2.isFile())
                            return -1;
                        else if (o2.isDirectory() && o1.isFile())
                            return 1;
                        //对字符串大写处理，使返回的拼音为小写、英文为大写，从而将英文和中文分开排序。
                        return getPinYin(o1.getName().toUpperCase()).compareTo(getPinYin(o2.getName().toUpperCase()));
                    });

                    for (File file2 : files) {
                        if (file2.isDirectory()) {
                            System.out.println(levelSign(level) + "【" + file2.getName() + "】" + "\t\t\t大小：" + sizeFormat(getDirSize(file2)));
                            showDir(file2.getAbsolutePath(), level);
                        } else {
                            System.out.println(levelSign(level) + file2.getName() + "\t\t\t大小：" + sizeFormat(file2.length()));
                        }
                    }
                    //对每个文件夹进行一下分隔
                    System.out.println(levelSign(level) + separateDir(level));
                }
            }
        } else {
            System.out.println("文件夹不存在!");
        }
    }


    //获取文件大小
    private static long getFileSize(File file) {
        return file.length();
    }


    //获取文件夹目录大小
    private static long getDirSize(File file) {
        long size = 0;
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files != null) {
                if (files.length == 0)
                    return 0;
                else {
                    for (File file1 : files) {
                        if (file1.isDirectory()) {
                            size = size + getDirSize(file1);
                        } else {
                            size = size + file1.length();
                        }
                    }
                }
            }
        }
        return size;
    }

    //文件大小格式化
    private static String sizeFormat(long size) {
        if (size < 1024)
            return size + "B";
        else if (size < 1024 * 1024)
            return String.format("%.2f", size / 1024.0) + "KB";
        else if (size < 1024 * 1024 * 1024)
            return String.format("%.2f", size / (1024 * 1024.0)) + "MB";
        else
            return String.format("%.2f", size / (1024 * 1024 * 1024.0)) + "GB";
    }

//    abstract static class com<T> implements Comparator<T> {
//
//        abstract int compares(T t1, T t2);
//
//        @Override
//        public int compare(T o1, T o2) {
//            return compares(o1, o2);
//        }
//    }
}
