import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
public class ExecutionConnection {

    public String operazione;
    public String messageExe;
    public String handlingResponse;

    public String getOperazione() {
        return operazione;
    }

    public void setOperazione(String operazione) {
        this.operazione = operazione;
    }

    public String getMessageExe() {
        return messageExe;
    }

    public void setMessageExe(String messageExe) {
        this.messageExe = messageExe;
    }

    public String getHandlingResponse(){
        return handlingResponse;
    }


    public void executionOperation()throws IOException {
        URL urlExe = new URL("http", "serverwebuni.ns0.it", 580, "/php/Hub.php");
        HttpURLConnection conExe = (HttpURLConnection) urlExe.openConnection();
        conExe.setRequestMethod("POST");
        conExe.setDoOutput(true);
        OutputStream osExe = conExe.getOutputStream();
        // parametri inviati gestiti con lo switch sopra
        osExe.write(messageExe.getBytes());
        osExe.flush();
        osExe.close();
        BufferedReader inExe = new BufferedReader(new InputStreamReader(conExe.getInputStream()));
        String inputLineExe;
        StringBuffer responseExe = new StringBuffer();
        while ((inputLineExe = inExe.readLine()) != null) {
            responseExe.append(inputLineExe);  // mex risposta dal server dell'operazione eseguita
            // magari nel server metto che aggiunge / per parametri aggiuntivi
        }
        inExe.close();
        //Handling operation on directory
        handlingResponse = responseExe.toString();
    }

}
