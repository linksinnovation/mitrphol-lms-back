/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mirtphol.lms.controller;

import co.th.linksinnovation.mirtphol.lms.model.Course;
import co.th.linksinnovation.mirtphol.lms.model.Lecture;
import co.th.linksinnovation.mirtphol.lms.model.enumuration.ContentType;
import co.th.linksinnovation.mirtphol.lms.repository.CourseRepository;
import co.th.linksinnovation.mirtphol.lms.repository.LectureRepository;
import co.th.linksinnovation.mirtphol.lms.utils.MD5;
import co.th.linksinnovation.mirtphol.lms.utils.QualitySelect;
import co.th.linksinnovation.mirtphol.lms.utils.mediainfo.MediaInfo;
import co.th.linksinnovation.mirtphol.lms.utils.mediainfo.MediaInfoUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
@RestController
@RequestMapping("/api")
public class ProgressUploadController {

    private static final int BUFFER_SIZE = 1024 * 100;

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private LectureRepository lectureRepository;

    @RequestMapping(value = "/coverupload", method = RequestMethod.PUT)
    public void coverUpload(@RequestBody byte[] file, HttpServletRequest request) throws UnsupportedEncodingException {
        InputStream chunk = new ByteArrayInputStream(file);
        String filename = URLDecoder.decode(request.getHeader("Content-Name"), "UTF-8");
        appendFile(request.getHeader("Content-Start"), chunk, new File("/mnt/data/images/" + request.getHeader("Content-Lecture") + "-" + filename));
        if (request.getHeader("Content-End") != null && request.getHeader("Content-End").equals(request.getHeader("Content-FileSize"))) {
            Course course = courseRepository.findOne(Long.parseLong(request.getHeader("Content-Lecture")));
            course.setCover(request.getHeader("Content-Lecture") + "-" + filename);
            courseRepository.save(course);
        }
    }

    @RequestMapping(value = "/videoupload", method = RequestMethod.PUT)
    public void upload(@RequestBody byte[] file, HttpServletRequest request) throws IOException, InterruptedException {
        InputStream chunk = new ByteArrayInputStream(file);
        String filename = URLDecoder.decode(request.getHeader("Content-Name"), "UTF-8");
        String hexFile = MD5.getMd5(filename);
        appendFile(request.getHeader("Content-Start"), chunk, new File("/mnt/data/source/" + hexFile));
        if (request.getHeader("Content-End") != null && request.getHeader("Content-End").equals(request.getHeader("Content-FileSize"))) {
            final MediaInfo mediaInfo = MediaInfoUtil.getMediaInfo("/mnt/data/source/" + hexFile);
            Lecture lecture = lectureRepository.findOne(Long.parseLong(request.getHeader("Content-Lecture")));
            lecture.setContent(filename);
            lecture.setContentType(ContentType.VIDEO);
            lecture.setDuration(Long.parseLong(mediaInfo.get("Video", "Duration")));
            lecture.setUpdateDate(new Date());
            lecture.setUuid(hexFile);
            lectureRepository.save(lecture);

            RestTemplate rest = new RestTemplate();
            Map<String, Object> map = new HashMap<>();
            map.put("uuid", hexFile);
            map.put("lecture", lecture.getId());
            map.put("quality", QualitySelect.select(mediaInfo.get("Video", "Height")).toString());
            rest.postForEntity("http://10.1.2.202:8080", map, String.class);
        }
    }
    
    @RequestMapping(value = "/pdfupload", method = RequestMethod.PUT)
    public void pdfUpload(@RequestBody byte[] file, HttpServletRequest request) throws UnsupportedEncodingException {
        InputStream chunk = new ByteArrayInputStream(file);
        String filename = URLDecoder.decode(request.getHeader("Content-Name"), "UTF-8");
        appendFile(request.getHeader("Content-Start"), chunk, new File("/mnt/data/files/" + request.getHeader("Content-Lecture") + "-" + filename));
        if (request.getHeader("Content-End") != null && request.getHeader("Content-End").equals(request.getHeader("Content-FileSize"))) {
            Lecture lecture = lectureRepository.findOne(Long.parseLong(request.getHeader("Content-Lecture")));
            lecture.setContent(filename);
            lecture.setContentType(ContentType.PDF);
            lectureRepository.save(lecture);
        }
    }

    private void appendFile(String start, InputStream in, File dest) {
        OutputStream out = null;

        try {
            if (dest.exists()) {
                if (start.equals("0")) {
                    if (dest.delete()) {
                        out = new BufferedOutputStream(new FileOutputStream(dest), BUFFER_SIZE);
                    }
                }
                out = new BufferedOutputStream(new FileOutputStream(dest, true), BUFFER_SIZE);
            } else {
                out = new BufferedOutputStream(new FileOutputStream(dest), BUFFER_SIZE);
            }
            in = new BufferedInputStream(in, BUFFER_SIZE);

            int len = 0;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

}
