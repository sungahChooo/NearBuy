package com.sungah.aug14pd.report;

import java.io.File;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sungah.aug14pd.member.Member;
import com.sungah.aug14pd.up.ProfileRepo;

import jakarta.servlet.http.HttpServletRequest;


@Service
public class ReportDAO {

	@Autowired
	private ProfileRepo pr;
	
	@Autowired
	private ReportRepo rr;
	
	@Value("${file.dir}")
	private String fileDir;
	
	public Resource getPhoto(String name) {
		try {
			return new UrlResource("file:"+fileDir+"/"+name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ResponseEntity<Resource> getFile(String name){
		try {
			UrlResource ur = new UrlResource("file:"+fileDir+"/"+name);
			String header = "attachment; filename=\"" + URLEncoder.encode(name,"utf-8") + "\"";
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, header).body(ur);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getFileName(MultipartFile mf) {
		String fileName = mf.getOriginalFilename();
		String type = "";
		
		if (fileName != null && fileName.contains(".")) {
			type = fileName.substring(fileName.lastIndexOf("."));
			fileName = fileName.replace(type, "");
		}else {
			return null;
		}
		
		String uuid = UUID.randomUUID().toString();
		return fileName + "_" + uuid + type;
	}
	
	public void report(MultipartFile mf, Report r, HttpServletRequest req) {
		try {
			String fileName = getFileName(mf);
			File f = new File(fileDir+"/"+fileName);
			mf.transferTo(f);
			r.setPhoto(fileName);
			rr.save(r);
			req.setAttribute("result", "등록성공");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "등록실패");
		}
	}
	
	public void getReport(HttpServletRequest req) {
		Member m = (Member) req.getSession().getAttribute("loginMember");
		try {
			List<Report> r = rr.findByWriterId(m);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			for (Report report : r) {
			    String formattedDate = report.getDate().format(formatter);
			    report.setReportTimestampForMatted(formattedDate);
			}

			
			req.setAttribute("report", r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getReportDetail(HttpServletRequest req, Integer rId) {
		try {
			Optional<Report> rOptional = rr.findById(rId);
			if (rOptional.isPresent()) {
				Report report = rOptional.get();
				req.setAttribute("reportDetail", report);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
