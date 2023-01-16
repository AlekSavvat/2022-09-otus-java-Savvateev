package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.otus.model.Measurement;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.net.URL;
import java.util.List;

public class ResourcesFileLoader implements Loader {
    File file;
    public ResourcesFileLoader(String fileName) {
        this.file = checkIfExists(fileName);
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        try (var fileReader = new FileReader(file)) {
            return new Gson().fromJson(fileReader, new TypeToken<List<Measurement>>(){}.getType());
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }

    private File checkIfExists(String fileName) {
        try {
            URL url = this.getClass().getClassLoader().getResource(fileName);
            String path = url.getPath();
            return new File(path);
        } catch (Exception ex){
            throw new FileProcessException(ex);
        }
    }
}
