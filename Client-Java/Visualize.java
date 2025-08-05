import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.lang.Integer;
import java.awt.Desktop;
import java.io.IOException;

public class Visualize {

    public String username;
    public String password;
    public String path;
    public int iduser;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public void visualizzaDirectory()throws IOException{
        URL urlVis = new URL("http", "serverwebuni.ns0.it", 580, "/php/visualizzazione.php");
        HttpURLConnection conVis = (HttpURLConnection) urlVis.openConnection();
        conVis.setRequestMethod("POST");
        conVis.setDoOutput(true);
        String messageVis = "username=" + username + "&id=" + iduser + "&path=" + path + "&password=" + password;
        OutputStream osVis = conVis.getOutputStream();
        osVis.write(messageVis.getBytes());
        osVis.flush();
        osVis.close();
        BufferedReader inVis = new BufferedReader(new InputStreamReader(conVis.getInputStream()));
        String inputLineVis;
        StringBuffer responseVis = new StringBuffer();
        while ((inputLineVis = inVis.readLine()) != null) {
            responseVis.append(inputLineVis);  // mex risposta dal server dell'operazione eseguita
        }
        inVis.close();
        String feedback = responseVis.toString();
        String visFileFolderAttuale[] = feedback.split("/");
        String[] positionFolder = path.split("/");
        int pos = positionFolder.length - 1;
        System.out.println("Contenuto della working directory: " + positionFolder[pos]);
        System.out.println("----------------------------------"); // per formattazione
        for (int i = 0; i < visFileFolderAttuale.length; i++) {
            // visualizzazione contenuto
            System.out.println(visFileFolderAttuale[i]);
        }
        System.out.println("----------------------------------"); // per formattazione
    }
}

