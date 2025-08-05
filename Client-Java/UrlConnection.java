import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.lang.Integer;

import java.io.IOException;


public class UrlConnection{

    public String username;
    public String password;
    public String Status;
    public int iduser;

    public void setUsername(String username){
        this.username=username;
    }
    public void setPassword(String password){
        this.password=password;
    }

    public String getResponseCode(){
        return this.Status;
    }
    public int getIduser(){
        return this.iduser;
    }

    public void checkLogin()throws IOException{
        //avvio una connesione via http per verificare login
        URL url = new URL("http", "serverwebuni.ns0.it", 580, "/php/TokenGeneratorDK.php");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        String message = "username=" + username + "&password=" + password;
        OutputStream os = con.getOutputStream();
        os.write(message.getBytes());
        os.flush();
        os.close();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String inf = response.toString();
        String[] parts = inf.split("/");
        iduser = -1; //Valore negativo perche' non e' stato ancora modificato
        Status = parts[0]; // noi ritorniamo 202  = 'Accepted';
        if(parts.length==2) {
            String Stringid = parts[1];
            iduser = Integer.parseInt(Stringid);    //assegnazione valore ID ottenuto dal server
        }
    }

    }

