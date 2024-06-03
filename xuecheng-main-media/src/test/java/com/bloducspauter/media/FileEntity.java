package com.bloducspauter.media;

import lombok.Data;

import javax.swing.*;
import java.io.*;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 文件基本信息
 *
 * @author Bloduc Spauter
 */
@Data
public final class FileEntity {
    /**
     * 分块文件存储的地方
     */
    public static final String TARGET_FILE_PATH = "D:/upload/breakpoint resume repository/";
    /**
     * 每个分块文件大小
     */
    public static int PART_SIZE = 1024 * 1024;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件的MD5
     */
    private String fileMd5;
    /**
     * 所有分块文件
     */
    private List<File> partFiles;
    /**
     * 分块文件存储路径
     */
    private String partFileStorePath;
    /**
     * 分块文件数量
     */
    private int partSizeNum;
    /**
     * 文件大小
     */
    private Long fileLength;

    /**
     * 初始化类
     * @param filePath 文件路径
     */
    private static void initFile(String filePath) {
        File dest = new File(filePath);
        File[] list = dest.listFiles();
        if (list == null) {
            return;
        }
        try {
            for (File f : list) {
                if (f.isDirectory()) {
                    initFile(f.getPath());
                }
                System.out.println("delete "+f.getAbsolutePath());
                f.delete();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提供无参构造
     */
    public FileEntity() {

    }

    /**
     * 提供以文件路径为参数的有参构造
     * @param filePath 文件路径
     */
    public FileEntity(String filePath) throws Exception {
        File file = new File(filePath);
        initFile(TARGET_FILE_PATH);
        init(file);
    }

    /**
     * 提供以{@code  File}为参数的有参构造
     * @param file
     * @throws Exception
     */
    public FileEntity(File file) throws Exception {
        initFile(TARGET_FILE_PATH);
        init(file);
    }

    /**
     * 通过{@code java swing}图形化页面选取文件来设置;
     * @throws NullPointerException 当选取结果为空时抛出异常
     */
    public void selectFile() throws Exception {
        initFile(TARGET_FILE_PATH);
        JOptionPane.showMessageDialog(null, "请随意选择文件", "提示", JOptionPane.PLAIN_MESSAGE);
        JFileChooser open = new JFileChooser();
        open.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        open.showOpenDialog(null);
        open.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (open.getSelectedFile() == null) {
            throw new NullPointerException();
        }
        File selectedFile = open.getSelectedFile();
        init(selectedFile);
    }

    /**
     * 在选取文件后初始化成员数据
     */
    private void init(File file) throws Exception {
        fileLength = file.length();
        fileName = file.getName();
        filePath = file.getAbsolutePath();
        fileMd5 = getMD5Checksum(filePath);
        partFileStorePath = TARGET_FILE_PATH + fileMd5;
        partSizeNum = (int) (Math.ceil(fileLength * 1.0 / PART_SIZE));
        this.division();
        sort();
    }

    /**
     * 私有方法，将分块文件排序
     */
    private void sort() {
        File file = new File(partFileStorePath);
        File[] files = file.listFiles();
        if (files == null) {
            throw new NullPointerException();
        }
        partFiles = Arrays.asList(files);
        partFiles.sort(Comparator.comparingInt(o -> Integer.parseInt(o.getName())));
    }

    /**
     * 私有方法，文件分块
     */
    private void division() throws IOException {
        File partStoreFile = new File(partFileStorePath);
        if (!partStoreFile.exists()) {
            partStoreFile.mkdir();
        }
        RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r");
        byte[] bytes = new byte[1024];
        for (int i = 0; i < partSizeNum; i++) {
            File chunkFile = new File(partFileStorePath + "/" + i);
            RandomAccessFile partFile = new RandomAccessFile(chunkFile, "rw");
            int len;
            while ((len = randomAccessFile.read(bytes)) != -1) {
                partFile.write(bytes, 0, len);
                if (partFile.length() >= PART_SIZE) {
                    break;
                }
            }
            System.out.println("本地分块"+i+"成功");
            partFile.close();
        }
        randomAccessFile.close();
    }

    public void output(File target) throws IOException {
        output(target.getAbsolutePath());
    }

    public void output(String target) throws IOException {
        File file = new File(target);
        if (file.isDirectory()) {
            target = target + File.separator + fileName;
        }
        RandomAccessFile randomAccessFile = new RandomAccessFile(target, "rw");
        byte[] bytes = new byte[1024];
        for (File f : partFiles) {
            RandomAccessFile read = new RandomAccessFile(f, "r");
            int len = -1;
            while ((len = read.read(bytes)) != -1) {
                randomAccessFile.write(bytes, 0, len);
            }
            read.close();
        }
        randomAccessFile.close();
    }

    /**
     * 通过{@code  java swing}选取输出目录
     */
    public void outputFile() throws IOException {
        JFileChooser save = new JFileChooser();
        save.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
        save.showSaveDialog(null);
        save.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (save.getSelectedFile() == null) {
            throw new NullPointerException();
        }
        File saveFile = save.getSelectedFile();
        this.output(saveFile);
    }

    private byte[] createChecksum(String filename) throws Exception {
        InputStream fis = new FileInputStream(filename);

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;

        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        fis.close();
        return complete.digest();
    }

    /**
     * 获取MD5值
     * @param filename 文件路径
     */
    public String getMD5Checksum(String filename) throws Exception {
        byte[] b = createChecksum(filename);
        StringBuilder result = new StringBuilder();
        for (byte value : b) {
            result.append(Integer.toString((value & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

    /**
     * 获取文件单位（在二进制下）
     */
    public String getBinarySystemLong() {
        //计算数量级
      int magnitude=getMagnitude(1024);
      double result=getResult(fileLength,magnitude,1024);
      String unit=getUnit(magnitude);
      return String.format("%.2f",result)+" "+unit;
    }

    /**
     * 获取文件单位（在十进制下）
     */
    public String getDecimal() {
        int magnitude=getMagnitude(1000);
        double result=getResult(fileLength,magnitude,1000);
        String unit=getUnit(magnitude);
        return String.format("%.2f",result)+" "+unit;
    }

    /**
     * 获取数量级
     * @param var 进制单位
     */
    private int getMagnitude(int var) {
        return (int) (Math.log(fileLength * 1.0) / Math.log(var));
    }

    /**
     * 获取合适的大小单位
     * @param magnitude 数量集
     */
    private String getUnit(int magnitude) {
        String unit="B";
        switch (magnitude) {
            case 0:unit="B";break;
            case 1:unit="KB";break;
            case 2:unit="MB";break;
            case 3:unit="GB";break;
            case 4:unit="TB";break;
            case 5:unit="PB";break;
        }
        return unit;
    }

    private double getResult(double origin, int magnitude,int var) {
        for (int i = 0; i < magnitude; i++) {
            origin/=var;
        }
        return origin;
    }
}
