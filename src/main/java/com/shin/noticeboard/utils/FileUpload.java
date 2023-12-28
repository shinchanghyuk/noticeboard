package com.shin.noticeboard.utils;

import com.shin.noticeboard.model.NoticeBoard;
import com.shin.noticeboard.model.NoticeBoardFileList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class FileUpload {
    @Value("${app.database.uploadFilePath}")
    private String uploadFilePath;

    public void fileUpload(MultipartFile[] files, NoticeBoard noticeBoard) throws IOException {
        List<NoticeBoardFileList> fileList = new ArrayList<>();

        for(int i = 0; i < files.length; i++) {
            String filename = getFilename(files[i]);
            String filepath = getFilePath(filename, noticeBoard.getUserid());

            files[i].transferTo(new File(filepath));

            fileList.add(new NoticeBoardFileList(filepath, filename));
        }

        noticeBoard.setFileList(fileList);
    }

    private String getFilePath(String originalFileName, String userid) {
        String origianlFileExtension = "";

        int lastDotIndex = originalFileName.lastIndexOf(".");
        if (lastDotIndex >= 0) {
            origianlFileExtension = originalFileName.substring(lastDotIndex + 1);
        }

        String fileName = userid + "_" + System.currentTimeMillis() + "." + origianlFileExtension;

        LocalDate currentDate = LocalDate.now();

        // 원하는 형식으로 날짜 포맷팅하기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = currentDate.format(formatter);

        File directoryFile = new File(uploadFilePath + File.separator + formattedDate);

        // 상위 디렉토리가 없으면 생성해줌
        if (!directoryFile.exists()) {
            log.info("NoticeBoardController - directoryFile not exists, directoryFilePath : {}", directoryFile.getAbsolutePath());
            directoryFile.mkdirs();
        }

        return uploadFilePath + File.separator + formattedDate + File.separator + fileName;
    }

    private String getFilename(MultipartFile file) {
        log.info("FileUpload - getFilename : {}", file.getOriginalFilename());

        // 같은 이름을 가지고 있
        return file.getOriginalFilename();
    }
}
