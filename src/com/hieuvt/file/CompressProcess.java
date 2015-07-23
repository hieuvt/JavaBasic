package com.hieuvt.file;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by hieuvt on 08/07/2015.
 */
public class CompressProcess {

    List<String> fileList;
    private static final String INPUT_ZIP_FILE = "C:\\dev\\kmol\\CDR_Sample_Small_400\\CDR_Sample_Small\\in\\cdr\\in12_neif\\20150622\\acc17300022_1864.05056.zip";
    private static final String OUTPUT_FOLDER = "C:\\dev\\kmol\\file\\CDRFILE\\ICC_NEIF\\In\\acc17300229_1906.92224";


    /**
     * Unzip it
     *
     * @param zipFile input zip file
     * @param output  zip file output folder
     */
    public void unZipIt(String zipFile, String outputFolder) {

        byte[] buffer = new byte[1024];

        try {

            //create output directory is not exists
            File folder = new File(OUTPUT_FOLDER);
            if (!folder.exists()) {
                folder.mkdir();
            }

            //get the zip file content
            ZipInputStream zis =
                    new ZipInputStream(new FileInputStream(zipFile));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {

                String fileName = ze.getName();
                File newFile = new File(outputFolder + File.separator + fileName);

                System.out.println("file unzip : " + newFile.getAbsoluteFile());

                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

            System.out.println("Done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isZipFile(String filePath) throws IOException {
        File suspectFile = new File(filePath);
        if(suspectFile.isDirectory()) {
            return false;
        }
        if(!suspectFile.canRead()) {
            throw new IOException("Cannot read file "+suspectFile.getAbsolutePath());
        }
        if(suspectFile.length() < 4) {
            return false;
        }
        DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(suspectFile)));
        int test = in.readInt();
        in.close();
        return test == 0x504b0304;
    }

    public static void main(String[] args) throws IOException {
        CompressProcess compressProcess = new CompressProcess();
        System.out.println(compressProcess.isZipFile(INPUT_ZIP_FILE));
//        unzipFile.unZipIt(INPUT_ZIP_FILE, OUTPUT_FOLDER);
    }


}
