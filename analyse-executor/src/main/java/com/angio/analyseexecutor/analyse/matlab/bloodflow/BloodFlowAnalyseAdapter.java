package com.angio.analyseexecutor.analyse.matlab.bloodflow;

import bloodFlowAnalyse.BloodFlowAnalyser;
import com.mathworks.toolbox.javabuilder.Images;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import lombok.extern.slf4j.Slf4j;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;

@Slf4j
public class BloodFlowAnalyseAdapter {

    public BloodFlowAnalyseResult runAnalyse(URI imageUri) throws IOException, MWException {

        String absoluteImagePath = imageUri.normalize().getPath();

        BloodFlowAnalyser bloodFlowAnalyser = new BloodFlowAnalyser();

        log.info("runAnalyse() - start for image: {}", imageUri);
        Object[] result = bloodFlowAnalyser.runAnalyse(8, absoluteImagePath.toCharArray());

        log.info("runAnalyse() - parse matlab result");
        MWNumericArray ishemiaImageArr = (MWNumericArray) result[0];
        BufferedImage ishemiaImage = Images.renderArrayData(ishemiaImageArr);
        double[] ishemiaArea = (double[]) ((MWNumericArray) result[1]).getData();
        double[][] ishemiaCenter = (double[][]) ((MWNumericArray) result[2]).toArray();
        double makulaArea = ((MWNumericArray) result[3]).getDouble(1);
        double[] makulaCenter = (double[]) ((MWNumericArray) result[4]).getData();
        double makulaRadius = ((MWNumericArray) result[5]).getDouble(1);
        MWNumericArray capillarDensityImageArr = (MWNumericArray) result[6];
        BufferedImage capillarDensityImage = Images.renderArrayData(capillarDensityImageArr);
        double[] capillarDensities = (double[]) ((MWNumericArray) result[7]).getData();

        log.info("runAnalyse() - map matlab result to blood flow analyse result");
        return new BloodFlowAnalyseResult(
                ishemiaArea,
                ishemiaCenter,
                ishemiaImage,
                makulaArea,
                makulaCenter,
                makulaRadius,
                capillarDensityImage,
                capillarDensities
        );
    }
}
