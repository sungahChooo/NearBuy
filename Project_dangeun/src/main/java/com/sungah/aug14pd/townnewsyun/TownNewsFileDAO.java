package com.sungah.aug14pd.townnewsyun;

import java.io.File;
import java.net.MalformedURLException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TownNewsFileDAO {

	@Value("${file.dir}")
	private String fileDir;
	
	// 파일 이름 가져오기
	private String getTownNewsFileName(MultipartFile mf) {
		String fileName = mf.getOriginalFilename(); // 업로드한 파일명.png
		String type = fileName.substring(fileName.lastIndexOf(".")); // .png
		fileName = fileName.replace(type, ""); // 업로드한 파일명
		// UUID : 네트워크 같은곳에서 중복안되는 id값 필요할 때 쓰는
		String uuid = UUID.randomUUID().toString(); // 91761586-2443-4e59-ace8-244aedf48853
		return fileName + "_" + uuid + type;
	}
	
	// 게시판 이미지 업로드()
	public void uploadTownNewsFile(MultipartFile mf, TownNewses tn, HttpServletRequest req) {
		try {
			String fileName =getTownNewsFileName(mf);		
			File f = new File(fileDir + "/" + fileName);
			mf.transferTo(f); // 실제 업로드
			tn.setTownNewsPhoto(fileName);
	        req.setAttribute("tn", tn);  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	// 게시판 이미지 띄우기
	public Resource getTownNewsPhoto(String name) {
		try {
			return new UrlResource("file:" + fileDir + "/" + name);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}

