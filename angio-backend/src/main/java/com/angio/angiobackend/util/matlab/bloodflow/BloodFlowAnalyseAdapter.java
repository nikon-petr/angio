package com.angio.angiobackend.util.matlab.bloodflow;

import bloodFlowAnalyse.BloodFlowAnalyser;
import com.mathworks.toolbox.javabuilder.Images;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class BloodFlowAnalyseAdapter {

    public BloodFlowAnalyseResult runAnalyse(String imageAbsolutePath) throws IOException {
        try {
            BloodFlowAnalyser bloodFlowAnalyser = new BloodFlowAnalyser();

            Object[] result = bloodFlowAnalyser.runAnalyse(8, imageAbsolutePath.toCharArray());
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
        } catch (MWException e) {
            e.printStackTrace();
        }
        return null;
    }
}
