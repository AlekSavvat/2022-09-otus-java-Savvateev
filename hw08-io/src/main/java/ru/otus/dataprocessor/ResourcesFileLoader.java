package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.otus.model.Measurement;

import java.io.*;
import java.util.List;

public class ResourcesFileLoader implements Loader {
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        try (
            var inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
            var reader = new BufferedReader(new InputStreamReader(inputStream));){
            return new Gson().fromJson(reader, new TypeToken<List<Measurement>>(){}.getType());
        } catch(Exception ex){
            throw new FileProcessException(ex);
        }
    }
}
