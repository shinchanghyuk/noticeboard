package com.shin.noticeboard.utils;

import com.shin.noticeboard.model.NoticeBoardSearchList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Component
public class FileDownload {
    public ResponseEntity<byte[]> fileDownload(NoticeBoardSearchList noticeBoardSearchList) {
        byte[] fileByte = null;

        HttpHeaders headers = new HttpHeaders();
        HttpStatus httpStatus = null;

        if(noticeBoardSearchList.getSearchList() != null && !noticeBoardSearchList.getSearchList().isEmpty()) {
            for (int i = 0; i < noticeBoardSearchList.getSearchList().size(); i++) {
                if (noticeBoardSearchList.getSearchList().get(i).getFilePath() != null && noticeBoardSearchList.getSearchList().get(i).getFilePath() != "") {
                    fileByte = getBytes(noticeBoardSearchList.getSearchList().get(i).getFilePath());

                    String origianlFileExtension = "";

                    int lastDotIndex = noticeBoardSearchList.getSearchList().get(i).getOriginalFileName().lastIndexOf(".");
                    if (lastDotIndex >= 0) {
                        origianlFileExtension = noticeBoardSearchList.getSearchList().get(i).getOriginalFileName().substring(lastDotIndex + 1);
                    }

                    if (origianlFileExtension.equalsIgnoreCase("txt")) {
                        headers.add("Content-Type", "text/plain");
                    } else if (origianlFileExtension.equalsIgnoreCase("png")) {
                        headers.add("Content-Type", "image/png");
                    } else if (origianlFileExtension.equalsIgnoreCase("jpeg")) {
                        headers.add("Content-Type", "image/jpeg");
                    }

                    headers.add("Accept-Ranges", "bytes");
                    headers.add("Content-Length", String.valueOf(fileByte.length));

                    httpStatus = HttpStatus.OK;
                }
            }
        } else {
            log.info("FileDownload - fileDownload file empty");
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<byte[]>(fileByte, headers, httpStatus);
    }

    public byte[] getBytes(String filepath) {
        byte[] byteFile = null;

        try {
            log.info("FileDownload - getBytes");
            File downloadFile = null;
            downloadFile = new File(filepath);

            if(downloadFile != null && downloadFile.exists() && downloadFile.length() > 0) {
                log.info("FileDownload - getBytes downloadFile length : " + downloadFile.length());

                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(downloadFile);
                    byteFile = new byte[(int) downloadFile.length()];

                    fis.read(byteFile);
                } catch (IOException e) {
                    log.info("FileDownload - getBytes IOException : ", e);
                    throw new RuntimeException(e);
                } finally {
                    try {
                        if (fis != null) {
                            fis.close();
                        }
                    } catch (IOException e) {}
                }
            }
        } catch(Exception e) {
            log.error("NoticeBoardController - getBytes Exception : ", e);
            throw new RuntimeException(e);
        }
        return byteFile;
    }
}
