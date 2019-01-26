package com.angio.angiobackend.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Slf4j
@UtilityClass
public class FileUtil {

    public static String saveImage(@NonNull BufferedImage image, @NonNull String format, @NonNull String uploadFolder) throws IOException {

        log.trace("saveImage() - start");

        String imageName = generateHashedNameForFile(format);
        File file = new File(uploadFolder, imageName);

        log.trace("saveImage() - save file with path: {}", file.getAbsolutePath());
        ImageIO.write(image, format, file);

        log.trace("saveImage() - end");
        return file.getName();
    }

    public static String saveFile(@NonNull MultipartFile file, @NonNull String[] allowedExtensions, @NonNull String uploadFolder) throws IOException {
        log.trace("saveFile() - start");

        if(!FilenameUtils.isExtension(file.getOriginalFilename(), allowedExtensions)) {
            throw new IllegalArgumentException("The file extension is not allowed");
        }

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String filename = generateHashedNameForFile(extension);

        File localFile = new File(uploadFolder, filename);
        FileUtils.writeByteArrayToFile(localFile, file.getBytes());
        log.trace("saveFile() - save file with path: {}", localFile.getAbsolutePath());

        log.trace("saveFile() - end");
        return localFile.getName();
    }

    public static String generateHashedNameForFile(String format) {

        log.trace("generateHashedNameForFile() - start");
        String filename = generateFileNameWithTimeAndRandom();

        log.trace("generateHashedNameForFile() - hash file name");
        String fileExtension = String.format(".%s", format);
        filename = HashUtil.hash(filename) + fileExtension;

        log.trace("generateHashedNameForFile() - generated name: {}", filename);
        log.trace("generateHashedNameForFile() - end", filename);
        return filename;
    }

    public static String generateFileNameWithTimeAndRandom() {
        log.trace("generateFileNameWithTimeAndRandom() - start");
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyyHH:mm:ss.SSS");

        String result = formatter.format(new Date()) + new Random().nextInt();

        log.trace("generateFileNameWithTimeAndRandom() - result: {}", result);
        log.trace("generateFileNameWithTimeAndRandom() - end");
        return result;
    }
}
