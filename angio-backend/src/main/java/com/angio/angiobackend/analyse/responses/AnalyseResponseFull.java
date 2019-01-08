package com.angio.angiobackend.analyse.responses;

import java.io.Serializable;
import java.util.List;

public class AnalyseResponseFull implements Serializable {
    private List<AnalyseResponse> data;
    private long size;

    public AnalyseResponseFull(){

    }

    public AnalyseResponseFull(List<AnalyseResponse> data, long size) {
        this.data = data;
        this.size = size;
    }

    public List<AnalyseResponse> getData() {
        return data;
    }

    public void setData(List<AnalyseResponse> data) {
        this.data = data;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}