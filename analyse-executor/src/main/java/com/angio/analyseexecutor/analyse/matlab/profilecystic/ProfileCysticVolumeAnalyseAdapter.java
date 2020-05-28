package com.angio.analyseexecutor.analyse.matlab.profilecystic;

import ProfileCysticAnalyser.ProfileCysticAnalyser;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import com.mathworks.toolbox.javabuilder.MWStructArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.mathworks.toolbox.javabuilder.Images.renderArrayData;

@Slf4j
@Component
public class ProfileCysticVolumeAnalyseAdapter {

    public ProfileCysticVolumeAnalyseResult runAnalyse(String absoluteVideoPath) throws MWException {

        log.info("runAnalyse() - start for video: {}", absoluteVideoPath);

        Object[] result = new ProfileCysticAnalyser().analyseProfileCysticVideo(1, absoluteVideoPath.toCharArray());

        log.info("runAnalyse() - parse matlab result");
        MWStructArray mWStructArray = (MWStructArray) result[0];

        double cysticVolume = ((double[]) mWStructArray.getField(1).getData())[0];
        MWNumericArray mWNumericArrayAngio = (MWNumericArray) mWStructArray.getField(2);
        MWNumericArray mWNumericArraySlice = (MWNumericArray) mWStructArray.getField(3);

        log.info("runAnalyse() - map matlab result to profile cystic volume analyse model");
        ProfileCysticVolumeAnalyseResult cysticAnalyseResult = new ProfileCysticVolumeAnalyseResult(
                cysticVolume,
                renderArrayData(mWNumericArrayAngio),
                renderArrayData(mWNumericArraySlice)
        );

        log.info("runAnalyse() - end");
        return cysticAnalyseResult;
    }
}
