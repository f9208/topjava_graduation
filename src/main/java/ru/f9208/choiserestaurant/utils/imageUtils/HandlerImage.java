package ru.f9208.choiserestaurant.utils.imageUtils;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.f9208.choiserestaurant.model.entities.ImageLabel;
import ru.f9208.choiserestaurant.repository.ImageLabelRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Component
public class HandlerImage {
    final static String RESOURCES_UPLOADS_DIR = "\\resources\\uploads";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    ImageLabelRepository imageLabelRepository;
    //    logger

    public String serviceSaveInputFileImage(MultipartFile inputFile, String realPath, String fileName) throws Exception {
        if (fileName == null) {
            throw new Exception("введи имя файла!");
        }
        String uploadsPath = realPath + RESOURCES_UPLOADS_DIR;
        File uploadsDir = new File(uploadsPath);
        if (!uploadsDir.exists()) {
            uploadsDir.mkdir();
        }
        File originFile = saveOriginalImage(uploadsPath, inputFile);
        File reducedFile = reducedAndSaveImage(uploadsPath, originFile); //todo попробовать закидывать тот же inputFile

        String originImagePath = cutAbsolutePath(originFile.getPath(), "/resources");
        String reducedImagePath = cutAbsolutePath(reducedFile.getPath(), "/resources");

        ImageLabel il = new ImageLabel(fileName, originImagePath, reducedImagePath);
        imageLabelRepository.saveImage(il);
        return reducedImagePath;
    }

    private static File saveOriginalImage(String parentDir, MultipartFile inputFile) throws Exception {
        if (inputFile.getOriginalFilename() == null) {
            throw new Exception("input file has not Original Name");
        }
        File originalsDir = new File(parentDir, "originals");
        if (!originalsDir.exists()) {
            System.out.println("создаем папку в " + parentDir + "/originals для оригиналов изображений" + originalsDir.mkdir());
        }
        File originFile = new File(originalsDir, inputFile.getOriginalFilename()); //todo источник ошибок
        originFile.createNewFile();
        inputFile.transferTo(originFile);
        return originFile;
    }

    private static File reducedAndSaveImage(String uploadsDir, File inputFileImage) throws IOException {
        File reducedImageDir = new File(uploadsDir, "reducedImages");
        if (!reducedImageDir.exists()) {
            reducedImageDir.mkdir();
        }

        File imageReduced = new File(reducedImageDir,
                FilenameUtils.getBaseName(inputFileImage.getName()) + "_re."
                        + FilenameUtils.getExtension(inputFileImage.getName()));

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(inputFileImage));
        BufferedImage bi = ImageIO.read(in);
        int x = 2;
        BufferedImage tag = new BufferedImage(bi.getWidth() / x, bi.getHeight() / x, BufferedImage.TYPE_3BYTE_BGR);
        tag.getGraphics().drawImage(bi, 0, 0, bi.getWidth() / x, bi.getHeight() / x, null);
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(imageReduced));
        ImageIO.write(tag, "jpg", out);
        in.close();
        out.close();
        return imageReduced;
    }

    private static String cutAbsolutePath(String absolutePath, String parent) {
        String reducedAbsolutePath = absolutePath.replace("\\", "/");
        int index = reducedAbsolutePath.indexOf(parent);
        return reducedAbsolutePath.substring(index);
    }
}
