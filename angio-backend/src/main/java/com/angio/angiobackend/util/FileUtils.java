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

        log.debug("saveImage() - start");

        String imageName = generateHashedNameForFile(format);
        File file = new File(uploadFolder, imageName);

        log.debug("saveImage() - save file with path: {}", file.getAbsolutePath());
        ImageIO.write(image, format, file);

        log.debug("saveImage() - end");
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
        log.debug("saveFile() - start");

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String filename = generateHashedNameForFile(extension);

        File localFile = new File(uploadFolder, filename);
        org.apache.commons.io.FileUtils.writeByteArrayToFile(localFile, file.getBytes());
        log.debug("saveFile() - save file with path: {}", localFile.getAbsolutePath());

        log.debug("saveFile() - end");
        return localFile.getName();
    }

    public static boolean deleteFile(@NonNull String filename, @NonNull String uploadFolder) {
        log.debug("deleteFile() - start");

        File file = new File(uploadFolder, filename);

        return file.delete();
    }

    /**
     * Generate file name based on date and random int and apply hash function to it.
     *
     * @param format file extension
     * @return result
     */
    public static String generateHashedNameForFile(String format) {

        log.debug("generateHashedNameForFile() - start");
        String filename = generateFileNameWithTimeAndRandom();

        log.debug("generateHashedNameForFile() - hash file name");
        String fileExtension = String.format(".%s", format);
        filename = HashUtils.hash(filename) + fileExtension;

        log.debug("generateHashedNameForFile() - generated name: {}", filename);
        log.debug("generateHashedNameForFile() - end", filename);
        return filename;
    }

    /**
     * Generate file name based on date and random int.
     *
     * @return result
     */
    public static String generateFileNameWithTimeAndRandom() {
        log.debug("generateFileNameWithTimeAndRandom() - start");
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyyHH:mm:ss.SSS");

        String result = formatter.format(new Date()) + random.nextInt();

        log.debug("generateFileNameWithTimeAndRandom() - result: {}", result);
        log.debug("generateFileNameWithTimeAndRandom() - end");
        return result;
    }
}
