package com.studentassignment.service;

import java.io.IOException; import java.nio.file.*; import java.util.UUID;
import org.springframework.beans.factory.annotation.Value; import org.springframework.core.io.Resource; import org.springframework.core.io.UrlResource; import org.springframework.stereotype.Service; import org.springframework.web.multipart.MultipartFile;
@Service public class FileStorageService {
    private final Path root;
    public FileStorageService(@Value("${file.upload-dir}") String directory){root=Paths.get(directory).toAbsolutePath().normalize();}
    public StoredFile store(MultipartFile file){if(file==null||file.isEmpty())throw new IllegalArgumentException("A non-empty file is required."); if(file.getSize()>10L*1024*1024)throw new IllegalArgumentException("File size exceeds 10 MB limit."); String original=Path.of(file.getOriginalFilename()==null?"upload":file.getOriginalFilename()).getFileName().toString();String lower=original.toLowerCase(java.util.Locale.ROOT);if(!(lower.endsWith(".pdf")||lower.endsWith(".doc")||lower.endsWith(".docx")))throw new IllegalArgumentException("Only PDF, DOC and DOCX files are accepted."); String stored=UUID.randomUUID()+"_"+original.replaceAll("[^A-Za-z0-9._-]","_"); try{Files.createDirectories(root);Files.copy(file.getInputStream(),root.resolve(stored),StandardCopyOption.REPLACE_EXISTING);return new StoredFile(root.resolve(stored).toString(),original);}catch(IOException e){throw new IllegalStateException("Could not store uploaded file.",e);}}
    public Resource load(String storedPath){try{Path p=Paths.get(storedPath).toAbsolutePath().normalize();if(!p.startsWith(root))throw new IllegalArgumentException("Invalid file path.");Resource r=new UrlResource(p.toUri());if(!r.exists()||!r.isReadable())throw new IllegalArgumentException("File not found.");return r;}catch(IOException e){throw new IllegalArgumentException("File not found.",e);}}
    public record StoredFile(String path,String originalName){}
}
