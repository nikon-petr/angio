package com.angio.analyseexecutor.analyse.matlab.profilecystic;

import java.awt.image.BufferedImage;

public class ProfileCysticVolumeAnalyseResult {

    private double cysticVolume;
    private BufferedImage profileImage;
    private BufferedImage angiogramImage;

    public ProfileCysticVolumeAnalyseResult(double cysticVolume, BufferedImage profileImage, BufferedImage angiogramImage) {
        this.cysticVolume = cysticVolume;
        this.profileImage = profileImage;
        this.angiogramImage = angiogramImage;
    }

    public double getCysticVolume() {
        return cysticVolume;
    }

    public void setCysticVolume(double cysticVolume) {
        this.cysticVolume = cysticVolume;
    }

    public BufferedImage getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(BufferedImage profileImage) {
        this.profileImage = profileImage;
    }

    public BufferedImage getAngiogramImage() {
        return angiogramImage;
    }

    public void setAngiogramImage(BufferedImage angiogramImage) {
        this.angiogramImage = angiogramImage;
    }
}
