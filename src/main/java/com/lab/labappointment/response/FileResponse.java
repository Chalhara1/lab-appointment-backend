package com.lab.labappointment.response;

import java.util.ArrayList;
import java.util.List;

public class FileResponse {
    private List<String> filePaths;
    private List<String> fileNames;

    public FileResponse() {
        this.filePaths = new ArrayList<>();
        this.fileNames = new ArrayList<>();
    }

    public List<String> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(List<String> filePaths) {
        this.filePaths = filePaths;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }
}
