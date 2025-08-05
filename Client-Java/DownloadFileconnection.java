import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadFileconnection {
    public String postDataDW;
    public String nameFile;
    public String saveDir;

    public String getSaveDir() {
        return saveDir;
    }

    public void setSaveDir(String saveDir) {
        this.saveDir = saveDir;
    }

    public String getPostDataDW() {
        return postDataDW;
    }

    public void setPostDataDW(String postDataDW) {
        this.postDataDW = postDataDW;
    }


    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public void downloadFile()throws IOException {
        URL urlDW = new URL("http", "serverwebuni.ns0.it", 580, "/php/Hub.php");
        HttpURLConnection conDW = (HttpURLConnection) urlDW.openConnection();

        //se vogliamo cambiare mettiamola pubblica
        //String saveDir = "Download/";

        conDW.setRequestMethod("POST");
        conDW.setDoOutput(true);

        conDW.setRequestProperty("User-Agent", "Mozilla/5.0");
        conDW.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        conDW.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conDW.setRequestProperty("Content-Length", String.valueOf(postDataDW.getBytes().length));

        //invio parametri POST
        OutputStream osDW = conDW.getOutputStream();
        osDW.write(postDataDW.getBytes());
        osDW.flush();
        osDW.close();
        // se connessione OK valore 202
        int responseCode = conDW.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // se connessione OK then
            String disposition = conDW.getHeaderField("Content-Disposition");
            String contentType = conDW.getContentType();
            int contentLength = conDW.getContentLength();
            // Verifica il tipo di file
            String fileType = "";
            if (disposition != null) {
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    nameFile = disposition.substring(index + 10, disposition.length() - 1);
                }
            } else {
                fileType = contentType.substring(contentType.lastIndexOf("/") + 1);
            }

            String saveFilePath = saveDir +"/"+ nameFile;

            BufferedInputStream inputStream = new BufferedInputStream(conDW.getInputStream());
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            byte[] buffer = new byte[1024];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            String[] parts = saveDir.split("\\\\");
            String nomeCartella = parts[parts.length - 1];

            inputStream.close();
            outputStream.close();
            System.out.println("----------------------------------"); // per formattazione
            System.out.println("Status operazione: "+ "Il File "+nameFile+" è stato scaricato e si trova nella cartella "+nomeCartella);
            System.out.println("----------------------------------"); // per formattazione
        } else {
            //else
            System.out.println("----------------------------------"); // per formattazione
            System.out.println("Status operazione: Il File "+nameFile+" non è stato scaricato. Riprovare");
            System.out.println("----------------------------------"); // per formattazione
        }
        conDW.disconnect();
    }






}
