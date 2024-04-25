package com.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component // spring bean
public class FileManagerService {
	
	public static final String FILE_UPLOAD_PATH = "D:\\신보람\\6_spring_project\\memo\\workspace\\images/"; // 학원용
	//public static final String FILE_UPLOAD_PATH = "D:\\신보람\\6_spring_project\\memo\\workspace\\images/"; // 집용
	
	// input: file 원본, 로그인 된 사람 아이디(userLoginId)
	// output: 이미지 경로(path)
	public String saveFile(String userLoginId, MultipartFile file) {
		// 폴더(디렉토리) 생성
		// 예: aaaa_18234789023/sun.png
		String directoryName = userLoginId + "_" + System.currentTimeMillis() + "/"; //    aaaa_18234789023/
		String filePath = FILE_UPLOAD_PATH + directoryName; // D:\\신보람\\6_spring_project\\memo\\workspace\\images/aaaa_18234789023/
		
		File directory = new File(filePath);
		if (directory.mkdir() == false) {
			// 폴더 생성 실패시 이미지 경로 null 리턴
			return null;
		}
		
		// 실제 파일 업로드: byte 단위 업로드
		try {
			byte[] bytes = file.getBytes();
			// ★★★★ 한글명 파일은 업로드 불가이므로 나중에 영문자로 바꾸기
			Path path = Paths.get(filePath + file.getOriginalFilename());
			Files.write(path, bytes); // 실제 파일 업로드
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		// 파일 업로드가 성공하면 경로(path) return
		// 주소는 이렇게 될 것이다.(예언)
		//     http://localhost   /images/aaaa_18234789023/sun.png
		return "/images/" + directoryName + file.getOriginalFilename();
	}
}





