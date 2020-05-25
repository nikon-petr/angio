package com.angio.analyseexecutor.analyse.matlab.geometric;

import GeometricAnalyse.GeometricAnalyser;
import com.angio.analyseexecutor.analyse.matlab.geometric.model.GeometricAnalyseModel;
import com.angio.analyseexecutor.analyse.matlab.geometric.model.VesselModel;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import com.mathworks.toolbox.javabuilder.MWStructArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

import static com.mathworks.toolbox.javabuilder.Images.renderArrayData;

@Slf4j
@Component
public class GeometricAnalyseAdapter {

    public GeometricAnalyseModel runAnalyse(String absoluteImagePath) throws MWException {

        log.info("runAnalyse() - start for image: {}", absoluteImagePath);

        Object[] result = new GeometricAnalyser().test_analyse(1, absoluteImagePath.toCharArray());

        log.info("runAnalyse() - parse matlab result");
        MWStructArray mWStructArray = (MWStructArray) result[0];
        String[] fields_base = mWStructArray.fieldNames();

        MWNumericArray mWNumericArrayOriginal = (MWNumericArray) mWStructArray.getField(1);
        MWNumericArray mWNumericArrayBinarized = (MWNumericArray) mWStructArray.getField(2);
        MWNumericArray mWNumericArraySkel = (MWNumericArray) mWStructArray.getField(3);
        MWStructArray mWStructArrayVessels = (MWStructArray) mWStructArray.getField(4);
        BufferedImage biOriginal = renderArrayData(mWNumericArrayOriginal);
        BufferedImage biBinarized = renderArrayData(mWNumericArrayBinarized);
        BufferedImage biSkel = renderArrayData(mWNumericArraySkel);
        String[] fields = mWStructArrayVessels.fieldNames();

        int size = 1;
        while(true){
            try{
                mWStructArrayVessels.getField(size);
                size++;
            } catch(Exception e){
                break;
            }
        }

        log.info("runAnalyse() - map matlab result to geometric analyse model");
        GeometricAnalyseModel geometricAnalyseModel = new GeometricAnalyseModel(biOriginal, biBinarized, biSkel);
        for (int i = 1; i <= (size-1)/8; i++){
            geometricAnalyseModel.addAnalyseResult(new VesselModel(
                    (long)((double[])mWStructArrayVessels.getField(((i-1)*8)+1).getData())[0],
                    renderArrayData((MWNumericArray) mWStructArrayVessels.getField(((i-1)*8)+2)),
                    renderArrayData((MWNumericArray) mWStructArrayVessels.getField(((i-1)*8)+3)),
                    (int)((double[])mWStructArrayVessels.getField(((i-1)*8)+4).getData())[0],
                    ((double[])mWStructArrayVessels.getField(((i-1)*8)+5).getData())[0],
                    ((double[])mWStructArrayVessels.getField(((i-1)*8)+6).getData())[0],
                    ((double[])mWStructArrayVessels.getField(((i-1)*8)+7).getData())[0],
                    ((double[])mWStructArrayVessels.getField(((i-1)*8)+8).getData())[0]
            ));
        }

        log.info("runAnalyse() - end");
        return geometricAnalyseModel;
    }
}
