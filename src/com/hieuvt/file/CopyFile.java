package com.hieuvt.file;

import java.io.*;

/**
 * Created by hieuvt on 10/07/2015.
 */
public class CopyFile {

    public static int NUM_COPY = 20;

    public static void main(String[] args)
    {
        File srcFolder = new File("C:\\dev\\kmol\\CDR_Sample_Small\\CDR_Sample_Small\\in\\cdr");
        for (File inputFile: srcFolder.listFiles()) {
            for (int i = 0; i < NUM_COPY; i++) {
                try {
                    copyFolder(inputFile, new File ((inputFile.getAbsolutePath() + "_cp" + i)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void copyFolder(File src, File dest)
            throws IOException{

        if(src.isDirectory()){

            //if directory not exists, create it
            if(!dest.exists()){
                dest.mkdir();
                System.out.println("Directory copied from "
                        + src + "  to " + dest);
            }

            //list all the directory contents
            String files[] = src.list();

            for (String file : files) {
                //construct the src and dest file structure
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                //recursive copy
                copyFolder(srcFile,destFile);
            }

        }else{
            //if file, then copy it
            //Use bytes stream to support all file types
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);

            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes
            while ((length = in.read(buffer)) > 0){
                out.write(buffer, 0, length);
            }

            in.close();
            out.close();
            System.out.println("File copied from " + src + " to " + dest);
        }
    }
}
