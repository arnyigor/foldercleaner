package com.arny.java.data.utils;

import java.io.*;
import java.net.URL;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.zip.DataFormatException;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.ZipOutputStream;

import okhttp3.ResponseBody;

public class FileUtils {

    public static byte[] getFileBytes(String pathname) {
        File file = new File(pathname);
        ByteArrayOutputStream bos = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                bos.write(buf, 0, readNum); //no doubt here is 0
                //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
            System.out.println("ERROR!");
        }
        if (bos != null) {
            return bos.toByteArray();
        }
        return null;
    }

    public static void unzipGZ(String sourcePath, String destinationPath) throws IOException, DataFormatException {
        //Allocate resources.
        FileInputStream fis = new FileInputStream(sourcePath);
        FileOutputStream fos = new FileOutputStream(destinationPath);
        GZIPInputStream gzis = new GZIPInputStream(fis);
        byte[] buffer = new byte[1024];
        int len = 0;

        //Extract compressed content.
        while ((len = gzis.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
        }

        //Release resources.
        fos.close();
        fis.close();
        gzis.close();
        buffer = null;
    }

    public static void downloadUsingStream(String urlStr, String file) throws IOException {
        BufferedInputStream bis = null;
        FileOutputStream fis = null;
        try {
            URL url = new URL(urlStr);
            bis = new BufferedInputStream(url.openStream());
            fis = new FileOutputStream(file);
            byte[] buffer = new byte[10485760];
            int count = 0;
            while ((count = bis.read(buffer, 0, 10485760)) != -1) {
                fis.write(buffer, 0, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void unzipFunction(String destinationFolder, String zipFile) {
        File directory = new File(destinationFolder);
        System.out.println("directory = " + directory.exists());
        // if the output directory doesn't exist, create it
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // buffer for read and write data to file
        byte[] buffer = new byte[2048];

        try {
            FileInputStream fInput = new FileInputStream(zipFile);
            ZipInputStream zipInput = new ZipInputStream(fInput);

            ZipEntry entry = zipInput.getNextEntry();

            while (entry != null) {
                String entryName = entry.getName();
                File file = new File(destinationFolder + File.separator + entryName);
                System.out.println("Unzip file " + entryName + " to " + file.getAbsolutePath());
                // create the directories of the zip directory
                if (entry.isDirectory()) {
                    File newDir = new File(file.getAbsolutePath());
                    if (!newDir.exists()) {
                        boolean success = newDir.mkdirs();
                        if (success == false) {
                            System.out.println("Problem creating Folder");
                        }
                    }
                } else {
                    FileOutputStream fOutput = new FileOutputStream(file);
                    int count = 0;
                    while ((count = zipInput.read(buffer)) > 0) {
                        // write 'count' bytes to the file output stream
                        fOutput.write(buffer, 0, count);
                    }
                    fOutput.close();
                }
                // close ZipEntry and take the next one
                zipInput.closeEntry();
                entry = zipInput.getNextEntry();
            }

            // close the last ZipEntry
            zipInput.closeEntry();

            zipInput.close();
            fInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isFileExist(String fileName) {
        File file = new File(fileName);
        return file.exists() && file.isFile();
    }

    public static boolean isPathExist(String path) {
        File folder = new File(path);
        return folder.exists() || folder.mkdirs();
    }

    public static String getFilenameWithExtention(String path) {
        File file = new File(path);
        return file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(File.separator) + 1);
    }

    public static String getFilePath(String path) {
        File file = new File(path);
        String absolutePath = file.getAbsolutePath();
        return absolutePath.substring(0, absolutePath.lastIndexOf(File.separator)) + File.separator;
    }

    public static String getFileExtension(String path) {
        File file = new File(path);
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean createFile(String filePath) {
        boolean newFile = true;
        try {
            File file = new File(filePath);
            File folder = new File(FileUtils.getFilePath(filePath));
            if (!folder.exists()) {
                file.mkdirs();
            }
            if (!file.exists()) {
                newFile = file.createNewFile();
            }
            return newFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean writeToFile(String pathWithName, String data) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        boolean result = false;
        try {
            fw = new FileWriter(pathWithName);
            bw = new BufferedWriter(fw);
            bw.write(data);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String readFromFile(String pathWithName) {
        String ret = "";
        try {
            File f = new File(pathWithName);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            StringBuffer sb = new StringBuffer();
            String eachLine = br.readLine();
            while (eachLine != null) {
                sb.append(eachLine);
                sb.append("\n");
                eachLine = br.readLine();
            }
            ret = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static boolean renameFile(String SavePath, String filePath) {
        return new File(filePath).renameTo(new File(SavePath));
    }

    public static void moveFile(String inputPath, String inputFile, String outputPath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            //create output directory if it doesn't exist
            File dir = new File(outputPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(outputPath + inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file
            out.flush();
            out.close();
            out = null;

            // delete the original file
            new File(inputPath + inputFile).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteFile(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                return file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String formatFileSize(long size) {
        if (size <= 0)
            return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.##").format(size
                / Math.pow(1024, digitGroups))
                + " " + units[digitGroups];
    }

    public static String formatFileSize(long size, int digits) {
        if (size <= 0)
            return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        StringBuilder digs = new StringBuilder();
        for (int i = 0; i < digits; i++) {
            digs.append("#");
        }
        return new DecimalFormat("#,##0." + digs.toString()).format(size
                / Math.pow(1024, digitGroups))
                + " " + units[digitGroups];
    }

    public static byte[] fileToBytes(File file) throws NullPointerException, IOException {
        if (file == null) {
            throw new NullPointerException("Not Found");
        }
        byte[] bytes = new byte[((int) file.length())];
        InputStream input = new FileInputStream(file);
        int iter = 0;
        while (iter < bytes.length) {
            int block = input.read(bytes, iter, bytes.length - iter);
            if (block < 0) {
                break;
            }
            iter += block;
        }
        input.close();
        return bytes;
    }

    private static byte[] createChecksum(String filename) throws Exception {
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

    public static String getMD5File(String filename) throws Exception {
        byte[] b = createChecksum(filename);
        String result = "";
        for (byte aB : b) {
            result += Integer.toString((aB & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    public static byte[] readFile(File file) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new
                FileInputStream(file));
        int bytes = (int) file.length();
        byte[] buffer = new byte[bytes];

        int readBytes = bis.read(buffer);
        bis.close();
        return buffer;
    }

    public static byte[] readBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        // Get the size of the file
        long length = file.length();
        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            throw new IOException("Could not completely read file " + file.getName() + " as it is too long (" + length + " bytes, max supported " + Integer.MAX_VALUE + ")");
        }
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        // Close the input stream and return bytes
        is.close();
        return bytes;
    }

    public static boolean writeFile(byte[] data, String fileName) throws IOException {
        File file = new File(getFilePath(fileName));
        boolean folderFilesExist = file.exists() || file.mkdirs();
        if (!folderFilesExist) {
            return false;
        }
        FileOutputStream out = new FileOutputStream(fileName, false);
        out.write(data);
        out.close();
        return true;
    }

    public static void writeBytesToFile(byte[] bytes, String path) throws IOException {
        if (!isPathExist(path)) {
            return;
        }
        File file = new File(path);
        file.createNewFile();
        BufferedOutputStream bos = null;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } finally {
            if (bos != null) {
                try {
                    //flush and close the BufferedOutputStream
                    bos.flush();
                    bos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            if (files == null) {
                return true;
            }
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        return path.delete();
    }

    // Copy an InputStream to a File.
    public static boolean saveFile(InputStream in, File file) {
        OutputStream out = null;
        boolean result;
        try {
            out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        } finally {
            // Ensure that the InputStreams are closed even if there's an exception.
            try {
                if (out != null) {
                    out.close();
                }
                // If you want to close the "in" InputStream yourself then remove this
                // from here but ensure that you close it yourself eventually.
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void copyFile(String inputPath, String inputFile, String outputPath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            //create output directory if it doesn't exist
            File dir = new File(outputPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(outputPath + inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static byte[] getInpitStreamBytes(InputStream is) {
        try {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File copyFile(File file, String newFile) {
        String absolutePath = file.getAbsolutePath();
        System.out.println("absolutePath:" + absolutePath);
        String filePath = getFilePath(absolutePath);
        System.out.println("filePath:" + filePath);
        String outputPath = filePath + File.separator + newFile;
        System.out.println("outputPath:" + outputPath);
        copyFile(filePath, file.getName(), outputPath);
        File fileOut = new File(filePath + File.separator + newFile);
        System.out.println("fileOut:" + fileOut);
        return fileOut;
    }

    public static File getOutputZipFile(String workDir, String name) {
        File dir = new File(workDir);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return null;
            }
        }
        return new File(dir.getPath() + File.separator + name);
    }

    public static void zip(File[] files, String zipFile) throws IOException {
        final int BUFFER_SIZE = 10 * 1024;
        BufferedInputStream origin = null;
        try (ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)))) {
            byte data[] = new byte[BUFFER_SIZE];
            for (File file : files) {
                String absolutePath = file.getAbsolutePath();
                FileInputStream fi = new FileInputStream(absolutePath);
                origin = new BufferedInputStream(fi, BUFFER_SIZE);
                try {
                    ZipEntry entry = new ZipEntry(absolutePath.substring(absolutePath.lastIndexOf("/") + 1));
                    out.putNextEntry(entry);
                    int count;
                    while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
                        out.write(data, 0, count);
                    }
                } finally {
                    origin.close();
                }
            }
        }
    }

    public static boolean zipFileAtPath(String sourcePath, String zipFileName) {
        File dir = new File(sourcePath);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return false;
            }
        }
        File zipFile = new File(dir.getPath() + File.separator + zipFileName);
        try {
            zipFile.createNewFile();
            BufferedInputStream origin;
            FileOutputStream dest = new FileOutputStream(zipFile);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            int BUFFER = 2048;
            byte data[] = new byte[BUFFER];
            File srcFolder = new File(sourcePath);
            File[] files = srcFolder.listFiles();
            System.out.println("Zip directory: " + srcFolder.getName());
            for (File file : files) {
                System.out.println("Adding file: " + file.getName());
                FileInputStream fi = new FileInputStream(file);
                origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(file.getName());
                System.out.println("zipFileAtPath: entry:" + entry);
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }
            out.close();
            return true;
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            return false;
        }
    }

    public static void removeNthLine(String f, int toRemove) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(f, "rw");
        // Leave the n first lines unchanged.
        for (int i = 0; i < toRemove; i++)
            raf.readLine();
        // Shift remaining lines upwards.
        long writePos = raf.getFilePointer();
        raf.readLine();
        long readPos = raf.getFilePointer();

        byte[] buf = new byte[1024];
        int n;
        while (-1 != (n = raf.read(buf))) {
            raf.seek(writePos);
            raf.write(buf, 0, n);
            readPos += n;
            writePos += n;
            raf.seek(readPos);
        }

        raf.setLength(writePos);
        raf.close();
    }

    public static long getUrlFileModified(String urlPath) {
        try {
            URL url = new URL(urlPath);
            URLConnection uc = url.openConnection();
            uc.setUseCaches(false);
            return uc.getLastModified();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static ArrayList<String> getFolderFiles(final File folder) {
        ArrayList<String> files = new ArrayList<>();
        if (folder.listFiles() != null && folder.listFiles().length > 0) {
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    getFolderFiles(fileEntry);
                } else {
                    files.add(fileEntry.getName());
                }
            }
        }

        return files;
    }

    public static String getFileFolder(String path) {
        File file = new File(path);
        String absolutePath = file.getAbsolutePath();
        return absolutePath.substring(0, absolutePath.lastIndexOf(File.separator) - 1) + File.separator;
    }

    public static String getFilePath(File file) {
        String absolutePath = file.getAbsolutePath();
        return absolutePath.substring(0, absolutePath.lastIndexOf(File.separator)) + File.separator;
    }

    public static void removeFileBytes(String pathname) {
        try {
            File path = new File(pathname);
            byte[] bytes = readFile(path);
            byte[] filteredByteArray = Arrays.copyOfRange(bytes, 100, bytes.length);
            FileOutputStream out = new FileOutputStream(pathname, false);
            out.write(filteredByteArray);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeFileLines(String pathname, int cnt) {
        try {
            ArrayList<String> lines = new ArrayList<String>();
            FileInputStream fstream = new FileInputStream(pathname);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                System.out.println(strLine);
                lines.add(strLine);
            }
            //Close the input stream
            in.close();
            ArrayList<String> result = new ArrayList<String>();
            result.addAll(lines);
            lines.clear();
            if (cnt < ((result.size() - 1) / 2)) {
                for (int i = cnt; i < result.size() - 1; i++) {
                    lines.add(result.get(i));
                }
                FileUtils.deleteFile(pathname);
                for (String line : lines) {
                    FileUtils.writeToFile(pathname, line, true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(String fileName, String content, boolean append) {
        try {
            PrintStream p = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File(fileName), append)));
            p.println(content);
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File saveResponseBodyToDisk(ResponseBody body, File file) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = body.byteStream();
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        boolean writeStream = writeStream(inputStream, outputStream);
        return file;
    }

    private static boolean writeStream(InputStream inputStream, OutputStream outputStream) {
        boolean res = false;
        try {
            byte[] bytes = new byte[4096];
            while (true) {
                int read = inputStream.read(bytes);
                if (read == -1) {
                    break;
                }
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
            res = true;
        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

    public static String readFile(String fileName) {
        StringBuilder text = new StringBuilder();
        try {
            File file = new File(fileName);

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
            return text.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static void createNomedia(String path) {
        File NoMedia = new File(getFilePath(path) + File.separator + ".nomedia");
        if (!NoMedia.exists()) {
            try {
                NoMedia.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
