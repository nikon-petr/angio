package com.angio.server.util.image;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class ImageOperation {
    public String save(String base64) throws Exception {
        ArrayList<String> imageFormates = new ArrayList<String>();
        imageFormates.add("png");
        imageFormates.add("gif");
        imageFormates.add("jpeg");
        imageFormates.add("bmp");

        String[] splittedBase64 = base64.split(",");
        String base64Data = splittedBase64[1]; //iVBORw0KGgoAAAANSUhEUgAAAToAAA..
        String base64Info = splittedBase64[0].split(":")[1];
        String base64FileType = base64Info.split("/")[0]; //image
        String base64FormatType = base64Info.split("/")[1].split(";")[0]; //png
        String stringType = base64Info.split(";")[1]; //base64

        if (stringType.equals("base64") && base64FileType.equals("image") && imageFormates.contains(base64FormatType)) {
            byte[] imageByte = Base64.decodeBase64(base64Data);

            String generatedMD5Filename = generateMD5Filename();
            String imageName = generatedMD5Filename + "." + base64FormatType;
            File mFile = new File("src/main/resources/static/images/analyses/" + imageName);
            BufferedOutputStream stream_original = new BufferedOutputStream(new FileOutputStream(mFile));
            stream_original.write(imageByte);
            stream_original.close();

            String formattedImageName = generatedMD5Filename + ".png";
            File outputFile = new File("src/main/resources/static/images/analyses/" + formattedImageName);
            try (InputStream is = new FileInputStream(mFile)) {
                BufferedImage image = ImageIO.read(is);
                try (OutputStream os = new FileOutputStream(outputFile)) {
                    ImageIO.write(image, "png", os);
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }

            deleteImage(imageName);

            return formattedImageName;
        } else throw new Exception("Illegal image format");
    }

    public String save(BufferedImage bi) throws IOException {
        String imageName = generateMD5Filename();
        imageName += ".png";

        File outputfile = new File("src/main/resources/static/images/analyses/" + imageName);
        ImageIO.write(bi, "png", outputfile);

        return imageName;
    }

    public void deleteImage(String filename) throws IOException {
        Path deletedPath = Paths.get("src/main/resources/static/images/analyses/" + filename);
        File file = new File("src/main/resources/static/images/analyses/" + filename);
        if (file.exists()) Files.delete(deletedPath);
    }

    public static String getFullFilename(String filename){
        return "src/main/resources/static/images/analyses/" + filename;
    }

    private String generateMD5Filename(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss.SSS");

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String imageName = String.format("%s_%s", sdf.format(timestamp), new Random().nextInt(9));

        String strMD5 = "";
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(imageName.getBytes(), 0, imageName.length());
            strMD5 = new BigInteger(1,m.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return strMD5;
    }
}