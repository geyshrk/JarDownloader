package ru.msnigirev.tbank.jar;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class ImageDownloader implements Runnable {
    private String folder;
    private String imageURL;
    public ImageDownloader(String folder, String imageURL) {
        this.folder = folder;
        this.imageURL = imageURL;
    }
    public void run(){
        String imageName = imageURL.substring(imageURL.lastIndexOf('/'));
        try(BufferedInputStream in = new BufferedInputStream(((new URI(imageURL)).toURL()).openStream())) {
            Files.copy(in, Path.of(folder + imageName));
        } catch (NoSuchFileException e) {
            System.out.printf("Папка %s не найдена\n", folder);
        } catch (FileAlreadyExistsException e) {
            System.out.printf("Изображение %s уже скачено\n", e.getMessage());
        } catch (UnknownHostException e) {
            System.out.printf("Нет соединения или неправильно указан адрес.\nФайл %s не скачан\n", imageName);
        } catch (ConnectException e) {
            System.out.printf("Соединение было прервано.\nФайл %s не скачан\n", imageName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
