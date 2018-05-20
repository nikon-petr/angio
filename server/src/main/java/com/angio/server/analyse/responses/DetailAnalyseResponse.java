package com.angio.server.analyse.responses;

import com.angio.server.analyse.entities.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class DetailAnalyseResponse implements Serializable {
    private PatientResponse patient;
    private DetailAnalyseInfoResponse info;
    private AnalyseGeometricResponse geometricAnalyse;
    private AnalyseBloodFlowResponse analyseBloodFlowResponse;
    private String username;

    public DetailAnalyseResponse() {

    }

    public DetailAnalyseResponse(
            PatientEntity patientEntity,
            AnalyseInfoEntity analyseInfoEntity,
            AnalyseGeometricEntity analyseGeometricEntity,
            List<VesselEntity> vesselsEntity,
            String username,
            AnalyseBloodFlowEntity analyseBloodFlowEntity,
            String ishemiaImageFileName,
            String densityImageFileName) {
        this.patient = new PatientResponse(patientEntity);
        this.info = new DetailAnalyseInfoResponse(analyseInfoEntity);
        this.geometricAnalyse = new AnalyseGeometricResponse(analyseGeometricEntity, vesselsEntity, analyseInfoEntity.getImg());
        this.username = username;
        this.analyseBloodFlowResponse = new AnalyseBloodFlowResponse(
                ishemiaImageFileName,
                densityImageFileName,
                analyseBloodFlowEntity.getIschemias(),
                analyseBloodFlowEntity.getMakula(),
                analyseBloodFlowEntity.getDensities());
    }

    public PatientResponse getPatient() {
        return patient;
    }

    public void setPatient(PatientResponse patient) {
        this.patient = patient;
    }

    public DetailAnalyseInfoResponse getInfo() {
        return info;
    }

    public void setInfo(DetailAnalyseInfoResponse info) {
        this.info = info;
    }

    public AnalyseGeometricResponse getGeometricAnalyse() {
        return geometricAnalyse;
    }

    public void setGeometricAnalyse(AnalyseGeometricResponse geometric_analyse) {
        this.geometricAnalyse = geometric_analyse;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private class AnalyseBloodFlowResponse {
        private String ishemiaImageUrl;
        private String densityImageUrl;
        private Set<IshemiaEntity> ischemias;
        private MakulaEntity makula;
        private Set<DensityEntity> densities;

        public AnalyseBloodFlowResponse(
                String ishemiaImageUrl,
                String densityImageUrl,
                Set<IshemiaEntity> ischemias,
                MakulaEntity makula,
                Set<DensityEntity> densities) {
            this.ishemiaImageUrl = ishemiaImageUrl;
            this.densityImageUrl = densityImageUrl;
            this.ischemias = ischemias;
            this.makula = makula;
            this.densities = densities;
        }

        public String getIshemiaImageUrl() {
            return ishemiaImageUrl;
        }

        public void setIshemiaImageUrl(String ishemiaImageUrl) {
            this.ishemiaImageUrl = ishemiaImageUrl;
        }

        public String getDensityImageUrl() {
            return densityImageUrl;
        }

        public void setDensityImageUrl(String densityImageUrl) {
            this.densityImageUrl = densityImageUrl;
        }

        public Set<IshemiaEntity> getIschemias() {
            return ischemias;
        }

        public void setIschemias(Set<IshemiaEntity> ischemias) {
            this.ischemias = ischemias;
        }

        public MakulaEntity getMakula() {
            return makula;
        }

        public void setMakula(MakulaEntity makula) {
            this.makula = makula;
        }

        public Set<DensityEntity> getDensities() {
            return densities;
        }

        public void setDensities(Set<DensityEntity> densities) {
            this.densities = densities;
        }
    }

    public AnalyseBloodFlowResponse getAnalyseBloodFlowResponse() {
        return analyseBloodFlowResponse;
    }

    public void setAnalyseBloodFlowResponse(AnalyseBloodFlowResponse analyseBloodFlowResponse) {
        this.analyseBloodFlowResponse = analyseBloodFlowResponse;
    }
}