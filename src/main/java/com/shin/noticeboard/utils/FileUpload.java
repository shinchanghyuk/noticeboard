package com.shin.noticeboard.utils;

import com.shin.noticeboard.model.NoticeBoard;
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

@Slf4j
@Component
public class FileUpload {
    @Value("${app.database.uploadFilePath}")
    private String uploadFilePath;

    public void fileUpload(MultipartFile file, NoticeBoard noticeBoard) throws IOException {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;

        String filePath = getFilePath(file, noticeBoard);
        log.info("FileUpload - filePath : {}", filePath);

        file.transferTo(new File(filePath));

        // 업로드 된 파일경로 모델에 추가함
        noticeBoard.setFilePath(filePath);
    }

    private String getFilePath(MultipartFile file, NoticeBoard noticeBoard) {
        log.info("FileUpload - originalFilename : {}", file.getOriginalFilename());

        String originalFileName = file.getOriginalFilename();
        String origianlFileExtension = "";

        if (originalFileName != null) {
            // 모델에 원래 파일이름 적재
            noticeBoard.setOriginalFileName(originalFileName);

            int lastDotIndex = originalFileName.lastIndexOf(".");
            if (lastDotIndex >= 0) {
                origianlFileExtension = originalFileName.substring(lastDotIndex + 1);
            }
        }

        String fileName = noticeBoard.getUserid() + "_" + System.currentTimeMillis() + "." + origianlFileExtension;

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
}
