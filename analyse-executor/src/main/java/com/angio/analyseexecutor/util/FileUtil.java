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

        log.debug("saveImage() - start");

        String imageName = generateHashedNameForFile(format);
        File file = new File(uploadFolder, imageName);

        log.debug("saveImage() - save file with path: {}", file.getAbsolutePath());
        ImageIO.write(image, format, file);

        log.debug("saveImage() - end");
        return file.getName();
    }

    public static String generateHashedNameForFile(String format) {

        log.debug("generateHashedNameForFile() - start");
        String filename = generateFileNameWithTimeAndRandom();

        log.debug("generateHashedNameForFile() - hash file name");
        String fileExtension = String.format(".%s", format);
        filename = HashUtil.hash(filename) + fileExtension;

        log.debug("generateHashedNameForFile() - generated name: {}", filename);
        log.debug("generateHashedNameForFile() - end", filename);
        return filename;
    }

    public static String generateFileNameWithTimeAndRandom() {
        log.debug("generateFileNameWithTimeAndRandom() - start");
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyyHH:mm:ss.SSS");

        String result = formatter.format(new Date()) + new Random().nextInt();

        log.debug("generateFileNameWithTimeAndRandom() - result: {}", result);
        log.debug("generateFileNameWithTimeAndRandom() - end");
        return result;
    }
}
