package com.angio.analyseexecutor.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

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

        log.info("saveImage() - start");

        String imageName = generateHashedNameForFile(format);
        File file = new File(uploadFolder, imageName);

        log.info("saveImage() - save file with path: {}", file.getAbsolutePath());
        ImageIO.write(image, format, file);

        log.info("saveImage() - end");
        return file.getName();
    }

    public static String generateHashedNameForFile(String format) {

        log.info("generateHashedNameForFile() - start");
        String filename = generateFileNameWithTimeAndRandom();

        log.info("generateHashedNameForFile() - hash file name");
        String fileExtension = String.format(".%s", format);
        filename = HashUtil.hash(filename) + fileExtension;

        log.info("generateHashedNameForFile() - generated name: {}", filename);
        log.info("generateHashedNameForFile() - end", filename);
        return filename;
    }

    public static String generateFileNameWithTimeAndRandom() {
        log.info("generateFileNameWithTimeAndRandom() - start");
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyyHH:mm:ss.SSS");

        String result = formatter.format(new Date()) + new Random().nextInt();

        log.info("generateFileNameWithTimeAndRandom() - result: {}", result);
        log.info("generateFileNameWithTimeAndRandom() - end");
        return result;
    }
}
