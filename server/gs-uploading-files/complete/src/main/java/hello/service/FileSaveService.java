package hello.service;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class FileSaveService {

    @Value("${file.save.dir}")
    String fileSaveDirectory;

    public boolean save(String name, MultipartFile file) {
        if (!isCreate(name)) return false;

        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(FileUtils.getFile(fileSaveDirectory + "/" + name)))) {
            stream.write(file.getBytes());
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isCreate(String name) {
        try {
            File directory = FileUtils.getFile(fileSaveDirectory);
            File uploadFile = FileUtils.getFile(directory, name);
            return uploadFile.createNewFile();
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
