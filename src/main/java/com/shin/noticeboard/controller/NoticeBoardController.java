package com.shin.noticeboard.controller;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shin.noticeboard.model.NoticeBoard;
import com.shin.noticeboard.model.NoticeBoardSearchList;
import com.shin.noticeboard.service.NoticeBoardService;
import com.shin.noticeboard.service.NoticeCommentService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
 
@RestController
@RequestMapping("/noticeboard")
public class NoticeBoardController {

    private static final Logger logger = LoggerFactory.getLogger(NoticeBoardController.class);

    private ObjectMapper objectMapper;

    @Value("${app.database.uploadFilePath}")
    private String uploadFilePath;

    // 서비스 주입
    @Autowired
    private NoticeBoardService noticeBoardService;

    // 서비스 주입
    @Autowired
    private NoticeCommentService noticeCommentService;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // 게시물 검색
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public NoticeBoardSearchList search(@RequestBody String requestPayload, HttpServletRequest request) {
        NoticeBoard noticeBoard = null;
        NoticeBoardSearchList noticeBoardSearchList = null;

        logger.info("NoticeBoardController - search requestPayload : " + requestPayload);

        try {
            noticeBoard = objectMapper.readValue(requestPayload, NoticeBoard.class);

            logger.info("NoticeBoardController - search noticeBoard : " + noticeBoard.toString());

            noticeBoardSearchList = noticeBoardService.search(noticeBoard);

            logger.info("NoticeBoardController - search result totcnt : " + noticeBoardSearchList.getTotcnt());

        } catch (Exception e) {
            logger.error("NoticeBoardController - search Exception : ", e);
        }

        // 프론트에 데이터 전달 로직추가
        return noticeBoardSearchList;

    }

    // 게시물 생성
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(NoticeBoard noticeBoard, @RequestPart(name = "file", required = false) MultipartFile file) {
       // NoticeBoard noticeBoard = null;

        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        File outputFile = null;

        logger.info("NoticeBoardController - save noticeBoard : " + noticeBoard.toString());

        try {
            // noticeBoard = objectMapper.readValue(requestPayload, NoticeBoard.class);
            if(file != null && file.getSize() > 0) {
                logger.info("NoticeBoardController - save multipartfile  : " + file.getOriginalFilename());
                String originalFileName = file.getOriginalFilename();
                String origianlFileExtension =  "";

                if (originalFileName != null) {
                    // 모델에 원래 파일이름 적재
                    noticeBoard.setOriginalFileName(originalFileName);

                    int lastDotIndex = originalFileName.lastIndexOf(".");
                    if (lastDotIndex >= 0) {
                        origianlFileExtension = originalFileName.substring(lastDotIndex + 1);
                    }
                }
                
                // 서버에 적재될 파일
                String fileName = noticeBoard.getUserid() + "_" + System.currentTimeMillis() + "." + origianlFileExtension;
                
                try {
                    LocalDate currentDate = LocalDate.now();

                    // 원하는 형식으로 날짜 포맷팅하기
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                    String formattedDate = currentDate.format(formatter);
                            
                    inputStream = file.getInputStream();

                    File directoryFile = new File(uploadFilePath + formattedDate);
                    
                    // 상위 디렉토리가 없으면 생성해줌
                    if(!directoryFile.exists()) {
                        logger.info("NoticeBoardController - directoryFile not exists, directoryFilePath : " + directoryFile.getAbsolutePath());
                        directoryFile.mkdirs();
                    }

                    outputFile = new File(uploadFilePath + formattedDate + File.separator + fileName);
                    logger.info("NoticeBoardController - filePath : " + outputFile.getAbsolutePath());

                    fileOutputStream = new FileOutputStream(outputFile);
    
                    byte[] buf = new byte[1024];
    
                    int readData;
                    while ((readData = inputStream.read(buf)) > 0) {
                        fileOutputStream.write(buf, 0, readData);
                    }
                } catch(IOException e) {
                    logger.error("NoticeBoardController - save IOException : ", e);
                } finally {
                    if(inputStream != null) {
                        inputStream.close();
                    }

                    if(fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                }
            
                // 업로드 된 파일경로 모델에 추가함
                noticeBoard.setFilePath(outputFile.getAbsolutePath());
            } else {
                logger.info("NoticeBoardController - save file is empty");
            }
        } catch (Exception e) {
            logger.error("NoticeBoardController - save Exception : ", e);
        }
        
        // Id가 0이면 새로 생성되는 게시글임
        if(noticeBoard.getId() == 0) {
           noticeBoardService.save(noticeBoard);
        } else {
            noticeBoardService.modify(noticeBoard);
        }     
    }

    // // 게시물 삭제
    // @RequestMapping(value = "/modify", method = RequestMethod.POST)
    // public void modify(@RequestBody String requestPayload) {
    //     NoticeBoard noticeBoard = null;

    //     logger.info("NoticeBoardController - modify requestPayload : " + requestPayload);

    //     try {
    //         noticeBoard = objectMapper.readValue(requestPayload, NoticeBoard.class);
    //     } catch (Exception e) {
    //         logger.error("NoticeBoardController - modify Exception : ", e);
    //     }

    //     noticeBoardService.modify(noticeBoard);
    // }

    // 게시물 삭제
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(@RequestBody String requestPayload) {
        List<String> id = new ArrayList<String>();

        logger.info("NoticeBoardController - delete requestPayload : " + requestPayload);

        try {
            id = objectMapper.readValue(requestPayload, new TypeReference<List<String>>() {});

        } catch (Exception e) {
            logger.error("NoticeBoardController - delete Exception : ", e);
        }

        noticeBoardService.delete(id);

        // 게시글에 달린 댓글 또한 삭제되어야 함
        noticeCommentService.allDelete(id);
    }

    @RequestMapping(value = "/fileDownload")
   //@RequestMapping(value = "/fileDownload/{id:.+}/{filename:.+}")
    public ResponseEntity<byte[]> fileDownload(@RequestBody String requestPayload) {
        // public ResponseEntity<byte[]> fileDownload(@PathVariable("id") int id, @PathVariable("filename") String filename) {
        NoticeBoard noticeBoard = null;
        NoticeBoardSearchList noticeBoardSearchList = null;
        byte[] fileByte = null;
        
        HttpHeaders headers = new HttpHeaders();
        HttpStatus httpStatus = null;

        // logger.info("NoticeBoardController - fileDownload id : " + id + ", filename : " + filename);
        logger.info("NoticeBoardController - fileDownload requestPayload : " + requestPayload);

        try {
            noticeBoard = objectMapper.readValue(requestPayload, NoticeBoard.class);

            // noticeBoard = new NoticeBoard();

            // noticeBoard.setId(id);
            // noticeBoard.setOriginalFileName(filename);

            noticeBoardSearchList = noticeBoardService.search(noticeBoard);
            
            if(noticeBoardSearchList.getSearchList() != null && noticeBoardSearchList.getSearchList().size() != 0) {
                for(int i = 0; i < noticeBoardSearchList.getSearchList().size(); i++) {
                    if(noticeBoardSearchList.getSearchList().get(i).getFilePath() != null && noticeBoardSearchList.getSearchList().get(i).getFilePath() != "") {
                        logger.info("NoticeBoardController - fileDownload noticeBoardSearchList : " + noticeBoardSearchList.getSearchList().toString());
                        
                        fileByte = getBytes(noticeBoardSearchList.getSearchList().get(i).getFilePath());
                                               
                        String origianlFileExtension = "";

                        int lastDotIndex = noticeBoardSearchList.getSearchList().get(i).getOriginalFileName().lastIndexOf(".");
                        if (lastDotIndex >= 0) {
                            origianlFileExtension = noticeBoardSearchList.getSearchList().get(i).getOriginalFileName().substring(lastDotIndex + 1);
                        }

                        if(origianlFileExtension.equalsIgnoreCase("txt")) {
                            headers.add("Content-Type", "text/plain");
                        } else if(origianlFileExtension.equalsIgnoreCase("png")){
                            headers.add("Content-Type", "image/png");
                        }

                        // String downloadFileName = "downloadFile." + origianlFileExtension;
                    
                        // headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                        //headers.add("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(noticeBoardSearchList.getSearchList().get(i).getOriginalFileName(), "UTF-8"));
                        // headers.add("Content-Disposition", "attachment; filename=\"" + downloadFileName + "\";");
                        headers.add("Accept-Ranges", "bytes");
                        headers.add("Content-Length", String.valueOf(fileByte.length));

                        httpStatus = HttpStatus.OK;

                        return new ResponseEntity<byte[]>(fileByte, headers, httpStatus);

                        // return ResponseEntity
                        // .ok().headers(headers)
                        // .contentLength(fileByte.length)
                        // .body(fileByte);

                        // httpHeaders.add("Content-Type", FileHelper.getFileContentType(zipFile));
                        // httpHeaders.add("Content-Length", ((Integer) sendSize).toString());
                        // httpHeaders.add("Content-Range", String.format("bytes %d-%d/%d", start, end, fileSize));
                    }   
                }
            } else {
                logger.error("NoticeBoardController - fileDownload noticeBoardSearchList is empty");
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } catch (Exception e) {
            logger.error("NoticeBoardController - fileDownload Exception : ", e);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<byte[]>(fileByte, headers, httpStatus);
    }

    public byte[] getBytes(String filepath) {
        byte[] byteFile = null;
        
        try {
            logger.info("NoticeBoardController - getBytes");
            File downloadFile = null;
            downloadFile = new File(filepath);

            if(downloadFile != null && downloadFile.exists() && downloadFile.length() > 0) {  
                logger.info("NoticeBoardController - getBytes downloadFile length : " + downloadFile.length());

                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(downloadFile);
                    byteFile = new byte[(int) downloadFile.length()];
            
                    fis.read(byteFile);

                    //byte[] byteArray = ByteStreams.toByteArray(fis);
                   
                    // byteFile = Files.readAllBytes(downloadFile.toPath());
                    // byteFile = ByteStreams.toByteArray(fis);
                } catch (IOException e) {
                    logger.info("NoticeBoardController - getBytes IOException : ", e);
                } finally {
                    if (fis != null) {
                        fis.close();
                    }
                }
            }
        } catch(Exception e) {
            logger.error("NoticeBoardController - getBytes Exception : ", e);
        }
        return byteFile;
    }
}
