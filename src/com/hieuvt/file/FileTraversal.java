package com.hieuvt.file;

import java.io.File;

public class FileTraversal {

    public static void main (String args[]) {

        displayIt(new File("C:\\Users\\Lucky\\Downloads"));
    }

    public static void displayIt(File node){

        System.out.println(node.getAbsoluteFile());

        if(node.isDirectory()){
            String[] subNote = node.list();
            for(String filename : subNote){
                displayIt(new File(node, filename));
            }
        }

    }
}
