package com.mike.tanks;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

/**
 * User: mjparme
 * Date: Nov 23, 2009
 * Time: 9:08:37 AM
 */
public class ImageLoader {
    private final static Logger logger = Logger.getLogger(ImageLoader.class);
    private final Map<String, BufferedImage> images;

    public ImageLoader() {
        this.images = new HashMap<String, BufferedImage>();
    }

    public BufferedImage getImage(String imageName) {
        BufferedImage image = this.images.get(imageName);
        if (image == null) {
            try {
                image = ImageIO.read(ImageLoader.class.getClassLoader().getResourceAsStream(imageName));
                if (image != null) {
                    this.images.put(imageName, image);
                }
            } catch (IOException e) {
                logger.error("Exception ", e);
            }
        }

        return image;
    }

    public void clearImageCache() {
        this.images.clear();
    }
}
