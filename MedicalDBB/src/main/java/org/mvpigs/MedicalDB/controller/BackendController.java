package org.mvpigs.MedicalDB.controller;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.mvpigs.MedicalDB.domain.File;
import org.mvpigs.MedicalDB.domain.FileStorageService;
import org.mvpigs.MedicalDB.domain.UploadFileResponse;
import org.mvpigs.MedicalDB.repository.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController()
public class BackendController {

    private static final Logger LOG = LoggerFactory.getLogger(BackendController.class);

    public static final String HELLO_TEXT = "Hola";

    @Autowired
    private FileRepository fileRepository;
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(path = "/hola")
    public @ResponseBody String sayHello() {
        LOG.info("GET called on /hello resource");
        return HELLO_TEXT;
    }
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(path = "/addFile", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody long addNewFile (@RequestParam long file_id, @RequestParam String name, @RequestParam String description, @RequestParam String headline, @RequestParam String type, @RequestParam String color, @RequestParam String deleted, @RequestParam String deleted_at, @RequestParam String created_at, @RequestParam String updated_at, @RequestParam String tsv, @RequestParam String tsv_es, @RequestParam long account_owner_id, @RequestParam String username, @RequestParam String email) {
        File file = new File( file_id, name,  description,headline, type, color, deleted, deleted_at, created_at, updated_at, tsv, tsv_es, account_owner_id, username, email,1);
        fileRepository.save(file);
        LOG.info(file.toString() + " successfully saved into DB");
        return file.getFile_id();
    }
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(path = "/searchFile", method = {RequestMethod.POST, RequestMethod.GET} )
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ArrayList<File> searchFiles(@RequestParam String query) {
       query=query.replace('+', ' ');
       query="'"+query+"'";
       
		ArrayList<File> filesList= fileRepository.findFilesByQuery(query) ;

        if(!filesList.isEmpty()) {LOG.info(filesList.get(0).toString() + " successfully find");}
        return filesList;
    }
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(path = "/deleteFile", method = {RequestMethod.POST, RequestMethod.GET} )
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Integer deleteFiles(@RequestParam String file_id) {
        fileRepository.deleteFile(Integer.valueOf(file_id)) ;
        
        fileRepository.refreshDirectory();
        fileRepository.refreshDocument();
        fileRepository.refreshCase();
        fileRepository.refreshStudy();
        fileRepository.refresh();
        LOG.info(String.valueOf(file_id) + " successfully deleted");
        return 0;
    }
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping(path="/file/{id}")
    public @ResponseBody String getUserById(@PathVariable("id") long file_id) {
        LOG.info("Opening file with id " + file_id + " from database.");
        return fileRepository.findByFileId(file_id).toString();
    }
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping(path="/filelastid")
    public @ResponseBody Integer getMaxId() {
        LOG.info("getting de last file id");
        return fileRepository.findMaxID();
    }
    
    
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(path = "/uploadFile", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody long uploadFile(@RequestParam String type, @RequestParam long file_id, @RequestParam String name, @RequestParam String description,@RequestParam long account_owner_id) {
        File file = new File( file_id, name,  description, account_owner_id, "" );
        LOG.info("Valores: "+type+" "+file_id+" "+name);
        OffsetDateTime localDate = OffsetDateTime.now();
        switch(type) {
        	
        case "document":
        	fileRepository.uploadDocument(file_id, name, description,localDate, account_owner_id);
            
        	break;
        case "case":
        	fileRepository.uploadCase(file_id, name, description,localDate, account_owner_id);
            
        	break;
        case "directory":
        	fileRepository.uploadDirectory(file_id, name, description,localDate, account_owner_id);
            
        	break;
        case "study":
        	fileRepository.uploadStudy(file_id, name, description,localDate, account_owner_id);
            	
        	break;
        }
        LOG.info(file.toString() + " successfully saved into DB");
        return file.getFile_id();
    }
  
    @Autowired
    private FileStorageService fileStorageService;
    
    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/actuallyUploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }
    
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            LOG.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}

    