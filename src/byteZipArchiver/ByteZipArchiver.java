package byteZipArchiver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ByteZipArchiver {

    public static byte[] ZipBytes(String sourceDirPath) throws IOException {
        File file = new File(sourceDirPath);
        byte[] bytes;
        if (file.isDirectory()) {
            bytes = ZipFolderToBytes(sourceDirPath);
        } else {
            bytes = ZipFileToBytes(sourceDirPath);
        }

        return bytes;
    }

    public static void UnzipBytes(byte[] bytes, String targetDir) throws IOException {
        ZipInputStream zipStream = new ZipInputStream(new ByteArrayInputStream(bytes));
        File rootFile = new File(targetDir);
        if (!rootFile.exists()) {
            rootFile.mkdir();
        }

        while(true) {
            ZipEntry entry;
            while((entry = zipStream.getNextEntry()) != null) {
                Path path = Paths.get(targetDir + "\\" + entry.getName());
                byte[] data = new byte[0];
                if (entry.isDirectory()) {
                    path.toFile().mkdir();
                } else {
                    Files.deleteIfExists(path);
                    File f = new File(path.toUri());
                    f.getParentFile().mkdirs();
                    f.createNewFile();
                    FileOutputStream fos = new FileOutputStream(path.toString());
                    InputStream stream = zipStream;
                    data = new byte[zipStream.available()];

                    int nRead;
                    while((nRead = stream.read(data, 0, data.length)) != -1) {
                        fos.write(data, 0, nRead);
                    }
                }
            }

            return;
        }
    }

    private static byte[] ReadBytes(String source) {
        try {
            return Files.readAllBytes(Paths.get(source));
        } catch (IOException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    private static void SaveFile(byte[] bytes, String path) {
        try {
            Files.deleteIfExists(Paths.get(path));
            Files.createFile(Paths.get(path));
        } catch (IOException var17) {
            var17.printStackTrace();
        }

        File outFile = new File(path);

        try {
            FileOutputStream fos = new FileOutputStream(outFile);
            Throwable var4 = null;

            try {
                fos.write(bytes);
            } catch (Throwable var16) {
                var4 = var16;
                throw var16;
            } finally {
                if (fos != null) {
                    if (var4 != null) {
                        try {
                            fos.close();
                        } catch (Throwable var15) {
                            var4.addSuppressed(var15);
                        }
                    } else {
                        fos.close();
                    }
                }

            }
        } catch (FileNotFoundException var19) {
            var19.printStackTrace();
        } catch (IOException var20) {
            var20.printStackTrace();
        }

    }

    private static byte[] ZipFileToBytes(String sourceDirPath) throws IOException {
        byte[] data = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zs = new ZipOutputStream(baos);
        Path path = Paths.get(sourceDirPath);
        ZipEntry zipEntry = new ZipEntry(path.getFileName().toString());
        zs.putNextEntry(zipEntry);
        Files.copy(path, zs);
        zs.closeEntry();
        zs.close();
        data = baos.toByteArray();
        baos.close();
        return data;
    }

    private static byte[] ZipFolderToBytes(String folderPath) throws IOException {
        File folder = new File(folderPath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zs = new ZipOutputStream(baos);
        Path pp = Paths.get(folderPath);
        Req(zs, folder, pp);
        zs.close();
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    private static void Req(ZipOutputStream zs, File folder, Path pp) throws IOException {
        File[] files = folder.listFiles();

        for(int i = 0; i < files.length; ++i) {
            if (files[i].isFile()) {
                Path path = Paths.get(files[i].getAbsolutePath());
                ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
                zs.putNextEntry(zipEntry);
                Files.copy(path, zs);
                zs.closeEntry();
            } else {
                Req(zs, files[i].getAbsoluteFile(), pp);
            }
        }

    }
}

