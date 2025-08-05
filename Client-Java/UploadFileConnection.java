import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.lang.Integer;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class UploadFileConnection {
    public File file;
    public int iduser;
    public String path;
    public String username;
    public String password;


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void uploadFile() throws IOException{
        HttpURLConnection conn = (HttpURLConnection) new URL("http", "serverwebuni.ns0.it", 580, "/php/Hub.php").openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        String boundary = "*****" + Long.toString(System.currentTimeMillis()) + "*****";
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        OutputStream outputStream = conn.getOutputStream();

        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write(("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName()+ "\r\n").getBytes());
        outputStream.write(("Content-Type: " + HttpURLConnection.guessContentTypeFromName(file.getName()) + "\r\n").getBytes());
        outputStream.write("\r\n".getBytes());
        FileInputStream inputStream = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.write("\r\n".getBytes());
        //parametri POST da inviare
        String id = Integer.toString(iduser);
        inputStream.close();
        // aggiungo il parametro "path"
        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write("Content-Disposition: form-data; name=\"path\"\r\n\r\n".getBytes());
        outputStream.write(path.getBytes());
        outputStream.write("\r\n".getBytes());
        // aggiungo il parametro "id"
        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write("Content-Disposition: form-data; name=\"id\"\r\n\r\n".getBytes());
        outputStream.write(id.getBytes());
        outputStream.write("\r\n".getBytes());
        // aggiungo il parametro "username"
        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write("Content-Disposition: form-data; name=\"username\"\r\n\r\n".getBytes());
        outputStream.write(username.getBytes());
        outputStream.write("\r\n".getBytes());
        // aggiungo il parametro "password"
        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write("Content-Disposition: form-data; name=\"password\"\r\n\r\n".getBytes());
        outputStream.write(password.getBytes());
        outputStream.write("\r\n".getBytes());
        //fine pacchetto parametri POST
        outputStream.write(("--" + boundary + "--\r\n").getBytes());
        outputStream.flush();
        outputStream.close();
        //lettura risposta da parte del server
        BufferedReader readerUP = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String lineUP ;
        StringBuilder responseUP = new StringBuilder();
        while ((lineUP  = readerUP .readLine()) != null) {
            responseUP.append(lineUP );
        }
        readerUP .close();
        System.out.println("----------------------------------"); // per formattazione
        System.out.println("Status operazione: "+responseUP.toString());
        System.out.println("----------------------------------"); // per formattazione

}



}
