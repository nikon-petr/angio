package com.angio.angiobackend.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
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
public class FileUtils {

    private final static Random random = new Random();

    /**
     * Save image to disk.
     *
     * @param image image data
     * @param format format (e.g. jpg)
     * @param uploadFolder upload folder relative to project base dir
     * @return saved file name
     * @throws IOException throwing when io exception occurred
     */
    public static String saveImage(@NonNull BufferedImage image, @NonNull String format, @NonNull String uploadFolder) throws IOException {

        log.trace("saveImage() - start");

        String imageName = generateHashedNameForFile(format);
        File file = new File(uploadFolder, imageName);

        log.trace("saveImage() - save file with path: {}", file.getAbsolutePath());
        ImageIO.write(image, format, file);

        log.trace("saveImage() - end");
        return file.getName();
    }

    /**
     * Save file to disk.
     *
     * @param file file multipart data
     * @param uploadFolder upload folder relative to project base dir
     * @return saved file name
     * @throws IOException throwing when io exception occurred
     */
    public static String saveFile(@NonNull MultipartFile file, @NonNull String uploadFolder) throws IOException {
        log.trace("saveFile() - start");

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String filename = generateHashedNameForFile(extension);

        File localFile = new File(uploadFolder, filename);
        org.apache.commons.io.FileUtils.writeByteArrayToFile(localFile, file.getBytes());
        log.trace("saveFile() - save file with path: {}", localFile.getAbsolutePath());

        log.trace("saveFile() - end");
        return localFile.getName();
    }

    /**
     * Generate file name based on date and random int and apply hash function to it.
     *
     * @param format file extension
     * @return result
     */
    public static String generateHashedNameForFile(String format) {

        log.trace("generateHashedNameForFile() - start");
        String filename = generateFileNameWithTimeAndRandom();

        log.trace("generateHashedNameForFile() - hash file name");
        String fileExtension = String.format(".%s", format);
        filename = HashUtils.hash(filename) + fileExtension;

        log.trace("generateHashedNameForFile() - generated name: {}", filename);
        log.trace("generateHashedNameForFile() - end", filename);
        return filename;
    }

    /**
     * Generate file name based on date and random int.
     *
     * @return result
     */
    public static String generateFileNameWithTimeAndRandom() {
        log.trace("generateFileNameWithTimeAndRandom() - start");
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyyHH:mm:ss.SSS");

        String result = formatter.format(new Date()) + random.nextInt();

        log.trace("generateFileNameWithTimeAndRandom() - result: {}", result);
        log.trace("generateFileNameWithTimeAndRandom() - end");
        return result;
    }
}
