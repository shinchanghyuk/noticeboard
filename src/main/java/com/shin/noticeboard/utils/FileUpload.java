package com.shin.noticeboard.utils;

import com.shin.noticeboard.mapper.NoticeBoardMapper;
import com.shin.noticeboard.model.NoticeBoard;
import com.shin.noticeboard.model.NoticeBoardFileList;
import com.shin.noticeboard.model.NoticeBoardFileNameCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final NoticeBoardMapper noticeBoardMapper;

    @Autowired
    public FileUpload(NoticeBoardMapper noticeBoardMapper) {
        this.noticeBoardMapper = noticeBoardMapper;
    }

    public void fileUpload(MultipartFile[] files, NoticeBoard noticeBoard) throws IOException {
        List<NoticeBoardFileList> fileList = new ArrayList<>();

        String[] filename = new String[files.length];

        filename = getFilename(filename, files, noticeBoard);

        for(int i = 0; i < files.length; i++) {
            if (filename[i] != null) {
                String filepath = getFilePath(filename[i], noticeBoard.getUserid());
                files[i].transferTo(new File(filepath));
                fileList.add(new NoticeBoardFileList(filepath, filename[i]));
            }
        }

        noticeBoard.setFileList(fileList);
    }

    private String getFilePath(String originalFileName, String userid) {
        String originalFileExtension = "";

        int lastDotIndex = originalFileName.lastIndexOf(".");
        if (lastDotIndex >= 0) {
            originalFileExtension = originalFileName.substring(lastDotIndex + 1);
        }

        String fileName = userid + "_" + System.currentTimeMillis() + "." + originalFileExtension;

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

    private String[] getFilename(String[] filename, MultipartFile[] file, NoticeBoard noticeBoard) {

        for(int i = 0; i < filename.length; i++) {
            String originalFilename = file[i].getOriginalFilename();

            NoticeBoardFileNameCheck noticeBoardFileNameCheck = new NoticeBoardFileNameCheck(noticeBoard.getId(), originalFilename);
            int count = noticeBoardMapper.fileNameCheck(noticeBoardFileNameCheck);

            if(count > 0) {
                int lastDotIndex = originalFilename.lastIndexOf(".");
                if (lastDotIndex >= 0) {
                    String originalFileExtension = originalFilename.substring(lastDotIndex + 1);
                    originalFilename = originalFilename + "_" + count + "." + originalFileExtension;
                }
            }

            filename[i] = originalFilename;
        }
        // 같은 이름을 가지고 있을때는 filename_2.jpg 와 같은 형식으로 원본파일 이름이 변경
        return filename;
    }
}
