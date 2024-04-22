package com.memo.common;

import java.io.File;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component	//	spring bean
public class FileManagerService {
	
	public static final String FILE_UPLOAD_PATH = "D:\\문병권\\6_spring_project\\memo\\workspace\\images/";	// 학원용
	//	public static final String FILE_UPLOAD_PATH = "D:\\문병권\\6_spring_project\\memo\\workspace\\images/";	// 집용
	
	//	input:	file 원본, 로그인 된 사람 아이디(userLoginid)
	//	output:	이미지 경로(path)
	public String saveFile(String userLoginId, MultipartFile file) {
		//	폴더(디렉토리) 생성
		//	예) aaaa_15682584/sun.png
		String directoryName = userLoginId + "_" + System.currentTimeMillis();
		String filePath = FILE_UPLOAD_PATH + directoryName;	//	D:\\문병권\\6_spring_project\\memo\\workspace\\images/aaaa_15682584/sun.png
		
		File directory = new File(filePath);
		if (directory.mkdir() == false) {
			//	폴더 생성 실패시 잉미지 경로 null 리턴
			return null;
		}
		
		//	실제 파일 업로드: byte 단위 업로드
		
		//	파일 업로드가 성공하면 경로(path) return
		return null;
	}
}
