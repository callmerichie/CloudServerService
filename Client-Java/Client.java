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


public class Client {

        public static void main(String[] args) throws Exception {
            System.out.println("----------------------------------"); // per formattazione
            System.out.println("Benvenuto nella demo!");
            System.out.println("----------------------------------"); // per formattazione
            Boolean cicloAccount = true;
            // ciclo verifica Account, caso True: procedo, caso False: reindirizzamento pagina registrazione sito Browser
            while (cicloAccount) {
                Scanner scannerAcc = new Scanner(System.in);
                System.out.println("Hai già un account? [Y][N]");
                String accExists = scannerAcc.nextLine();
                 if (accExists.equals("Y")) {
                     //avvio una connesione via http per verificare login
                     URL url = new URL("http","serverwebuni.ns0.it",580,"/php/TokenGeneratorDK.php");
                     //URL url = new URL("http", "185.230.163.214", 80, "/php/TokenGeneratorDK.php");
                     HttpURLConnection con = (HttpURLConnection) url.openConnection();
                     con.setRequestMethod("POST");
                     //Prendo in input le variabili
                     String username, password;
                     Scanner scanner = new Scanner(System.in);
                     System.out.println("Fornisci Username:");
                     username = scanner.nextLine();
                     System.out.println("Fornisci Password:");
                     password = scanner.nextLine();
                     con.setDoOutput(true);
                     String message = "username=" + username + "&password=" + password;
                     OutputStream os = con.getOutputStream();
                     os.write(message.getBytes());
                     os.flush();
                     os.close();
                     // check connessione server, 200: connesso
                     //return 200
                     //int responseCode = con.getResponseCode();
                     //System.out.println("Response code: " + responseCode);
                     //risposta server
                     BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                     String inputLine;
                     StringBuffer response = new StringBuffer();
                     while ((inputLine = in.readLine()) != null) {
                         response.append(inputLine);
                     }
                     in.close();
                     //String BuffRicevuto = response.toString();
                     //System.out.println("Response Body: " + BuffRicevuto .toString());
                     //splitting
                     String inf = response.toString();
                     String[] parts = inf.split("/");
                     int iduser = -1; //Valore negativo perche' non e' stato ancora modificato
                     String Status = parts[0]; // noi ritorniamo 202  = 'Accepted';

                     //System.out.println(inf);

                     if (Status.equals("202")) { //connessione accettata quindi posso lavorarci (aggiungere su TokenizerGeneratorDK.php
                         String Stringid = parts[1];
                         iduser = Integer.parseInt(Stringid);    //assegnazione valore ID ottenuto dal server
                         // reminder delle informazioni disponibili dal client
                         /*
                         System.out.println("----------------------------------"); // per formattazione
                         System.out.println("Informazioni utili:");
                         System.out.println("ID USER: " + iduser);
                         System.out.println("USERNAME: " + username);
                         System.out.println("----------------------------------"); // per formattazione
                        */
                           String path = "/"+username;
                           //visualizzazione pagina HOME
                         URL urlVis = new URL("http", "serverwebuni.ns0.it", 580, "/php/visualizzazione.php");
                         //URL urlVis = new URL("http", "185.230.163.214", 80, "/php/visualizzazione.php");
                         HttpURLConnection conVis = (HttpURLConnection) urlVis.openConnection();
                         conVis.setRequestMethod("POST");
                         conVis.setDoOutput(true);
                         String messageVis = "username=" + username + "&id=" + iduser + "&path=" + path+"&password="+password;
                         OutputStream osVis = conVis.getOutputStream();
                         osVis.write(messageVis.getBytes());
                         osVis.flush();
                         osVis.close();
                         //int responseCodeVis = conVis.getResponseCode();
                         //System.out.println("Response code: " + responseCodeVis);
                         BufferedReader inVis = new BufferedReader(new InputStreamReader(conVis.getInputStream()));
                         String inputLineVis;
                         StringBuffer responseVis = new StringBuffer();
                         while ((inputLineVis = inVis.readLine()) != null) {
                             responseVis.append(inputLineVis);  // mex risposta dal server dell'operazione eseguita
                         }
                         inVis.close();
                         // arrivati a questo punto ho un formato: file.txt/file2.png/Folder1
                         // Scompattazione per visualizzazione via testuale
                         String feedback = responseVis.toString();
                         String[] visFileFolder = feedback.split("/");

                         System.out.println("Contenuto della working directory HOME:");
                         System.out.println("----------------------------------"); // per formattazione
                         for (int i = 0; i < visFileFolder.length; i++) {
                             // visualizzazione contenuto
                             System.out.println(visFileFolder[i]); // METTERE A CASO
                         }
                         System.out.println("----------------------------------"); // per formattazione
                         System.out.println("Premi invio per continuare...");
                         String spazio = scanner.nextLine();
                         // CICLO OPERAZIONE
                         //System.out.println("Ciclo Operazione inizio:");
                         //System.out.println();
                         String operazione = "";
                         while (!(operazione.equals("exit"))) {
                             // Showing menu operation
                             System.out.println("Seleziona l'operazione disponibile:");
                             System.out.println("----------------------------------"); // per formattazione
                             System.out.println("Elenco: <HM>-Return Home Dir");
                             System.out.println("        <BK>-Return Back Dir");
                             System.out.println("        <FW>-Go Forward Dir");
                             System.out.println("        <CurD>-Show Current Dir");
                             System.out.println("        <Vis>-Visualizza contenuto Current Dir");
                             System.out.println("        <UF>-Upload File");
                             System.out.println("        <DF>-Download File");
                             System.out.println("        <RF>-Rimuovi File");
                             System.out.println("        <MKDIR>-Crea Folder");
                             System.out.println("        <RMDIR>-Rimuovi Folder");
                             System.out.println("        <MFDIR>-Modifica Folder");
                             System.out.println("        <exit>-Chiudi Applicazione");
                             System.out.println("----------------------------------"); // per formattazione
                             // lettura operazione
                             operazione = scanner.nextLine();
                             //visualizzazione operazione selezionata
                             //System.out.println("Operazione selezionata: " + operazione);

                             if (!(operazione.equals("exit"))) {
                                 System.out.println("Esecuzione...");

                                 // Handling della operazione process
                                 //per passare i parametri necessari al fine di completare la operazione
                                 String messageExe = null;
                                 Boolean operazioneValida = true;
                                 // switch gestione iniziale delle operazioni per inoltrare ad Hub.php i corretti parametri
                                 String nameFolder;
                                 switch (operazione) {
                                     case "HM": //case Home
                                         path = "/"+username;
                                         System.out.println("----------------------------------"); // per formattazione
                                         System.out.println("Status operazione: Ritornato nella Home");
                                         System.out.println("----------------------------------"); // per formattazione
                                         operazioneValida = false;
                                         break;
                                     case "BK": //case Back
                                         messageExe = "path=" + path + "&username=" + username + "&id=" + iduser + "&operazione=" + operazione+"&password="+password;
                                         operazioneValida = true;
                                         break;
                                     case "FW": //case Forward
                                         System.out.println("----------------------------------"); // per formattazione
                                         //richiesta cartella per andare avanti
                                         System.out.println("Fornisci il nome della cartella a cui vuoi accedere: ");
                                         nameFolder = scanner.nextLine();
                                         operazioneValida = true;
                                         messageExe = "path=" + path + "&username=" + username + "&id=" + iduser + "&cartella=" + nameFolder + "&operazione=" + operazione+"&password="+password;
                                         break;

                                     case "UF": //case Upload
                                         // controllare che funzioni
                                         // GUI per scegliere file e ottengo path
                                         System.out.println("----------------------------------"); // per formattazione
                                         System.out.println("Sto aprendo il diagolo per effettuare l'Upload del file...");
                                         JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                                         int returnValue = jfc.showOpenDialog(null);
                                         if (returnValue == JFileChooser.APPROVE_OPTION) {
                                             File selectedFile = jfc.getSelectedFile();
                                             String pathFile = selectedFile.getAbsolutePath();
                                             String filePath = pathFile;
                                             File file = new File(filePath);

                                             HttpURLConnection conn = (HttpURLConnection) new URL("http", "serverwebuni.ns0.it", 580, "/php/Hub.php").openConnection();
                                             conn.setRequestMethod("POST");
                                             conn.setDoOutput(true);
                                             String boundary = "*****" + Long.toString(System.currentTimeMillis()) + "*****";
                                             conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                                             OutputStream outputStream = conn.getOutputStream();
                                             //mando il file in Hub.php
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

                                         }else{//se fallisce some how
                                             System.out.println("----------------------------------"); // per formattazione
                                             System.out.println("Status operazione: Operazione Upload File fallita, riprovare");
                                             System.out.println("----------------------------------"); // per formattazione
                                         }

                                         operazioneValida = false;
                                         break;
                                     case "DF": // case Download
                                         System.out.println("----------------------------------"); // per formattazione
                                         System.out.println("Fornisci il nome del file da scaricare:");
                                         String nameFile = scanner.nextLine();

                                         //cartella saveDir
                                         String saveDir = "Download/";
                                         // creazione se non e' presente
                                         File dir = new File(saveDir);
                                         if (!dir.exists()) {
                                             dir.mkdir();
                                             System.out.println("La cartella Download è stata creata con successo!");
                                             System.out.println("----------------------------------"); // per formattazione
                                         }
                                         System.out.println("Inizio fase di download file...");

                                         String postDataDW = "operazione="+operazione+
                                                 "&username="+username+
                                                 "&id="+iduser+
                                                 "&nameFile="+nameFile+
                                                 "&path="+path+
                                                 "&password="+password;

                                         URL urlDW = new URL("http", "serverwebuni.ns0.it", 580, "/php/Hub.php");
                                         HttpURLConnection conDW = (HttpURLConnection) urlDW.openConnection();

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

                                             String saveFilePath = saveDir + nameFile;

                                             BufferedInputStream inputStream = new BufferedInputStream(conDW.getInputStream());
                                             FileOutputStream outputStream = new FileOutputStream(saveFilePath);

                                             byte[] buffer = new byte[1024];
                                             int bytesRead = -1;
                                             while ((bytesRead = inputStream.read(buffer)) != -1) {
                                                 outputStream.write(buffer, 0, bytesRead);
                                             }

                                             inputStream.close();
                                             outputStream.close();
                                             System.out.println("----------------------------------"); // per formattazione
                                             System.out.println("Status operazione: "+ "Il File "+nameFile+" è stato scaricato e si trova nella cartella Download");
                                             System.out.println("----------------------------------"); // per formattazione
                                         } else {
                                             //else
                                             System.out.println("----------------------------------"); // per formattazione
                                             System.out.println("Status operazione: Il File "+nameFile+" non è stato scaricato. Riprovare");
                                             System.out.println("----------------------------------"); // per formattazione
                                         }
                                         conDW.disconnect();
                                         // fine case DW
                                        operazioneValida=false;break;
                                     case "MKDIR":  // case Creazione Folder
                                         System.out.println("----------------------------------"); // per formattazione
                                         System.out.println("Fornisci il nome della cartella che vuoi inserire: ");
                                         nameFolder = scanner.nextLine();
                                         messageExe = "path=" + path + "&username=" + username + "&id=" + iduser + "&cartella=" + nameFolder + "&operazione=" + operazione+"&password="+password;
                                         operazioneValida = true;
                                         break;
                                     case "RMDIR": //case Remove Folder
                                         System.out.println("----------------------------------"); // per formattazione
                                         System.out.println("Fornisci il nome della cartella da rimuovere: ");
                                         nameFolder = scanner.nextLine();
                                         messageExe = "path=" + path + "&username=" + username + "&id=" + iduser + "&cartella=" + nameFolder + "&operazione=" + operazione+"&password="+password;
                                         operazioneValida = true;
                                         break;
                                     case "RF":   //case Remove File
                                         System.out.println("----------------------------------"); // per formattazione
                                         System.out.println("Fornisci il nome del file che vuoi rimuovere:");
                                         String nameFileToRemove = scanner.nextLine();
                                         messageExe ="path=" + path + "&username=" + username + "&id=" + iduser + "&nameFile=" + nameFileToRemove + "&operazione=" + operazione+"&password="+password;
                                         operazioneValida=true;break;
                                     case "MFDIR": //case Modify Folder
                                         System.out.println("----------------------------------"); // per formattazione
                                         System.out.println("Fornisci il nome della cartella da modificare: ");
                                         String oldFolderName = scanner.nextLine();
                                         System.out.println("Fornisci il nuovo nome della cartella: ");
                                         String newFolderName = scanner.nextLine();
                                         messageExe = "path=" + path + "&username=" + username + "&id=" + iduser + "&oldNameFolder=" + oldFolderName + "&newNameFolder=" + newFolderName + "&operazione=" + operazione+"&password="+password;
                                         operazioneValida = true;
                                         break;
                                     case "CurD": //case Current Dir show
                                         messageExe = "path=" + path + "&username=" + username + "&id=" + iduser + "&operazione=" + operazione+"&password="+password;
                                         operazioneValida = true;
                                         break;
                                     // System.out.println("Current Dir: "+path);operazioneValida=true;break;
                                     case "Vis": //case visualizzazione
                                         // stampo come prima:
                                         URL urlVisCiclo = new URL("http", "serverwebuni.ns0.it", 580, "/php/visualizzazione.php");
                                         //URL urlVisCiclo = new URL("http", "185.230.163.214", 80, "/php/visualizzazione.php");
                                         HttpURLConnection conVisCiclo = (HttpURLConnection) urlVisCiclo.openConnection();
                                         conVisCiclo.setRequestMethod("POST");
                                         conVisCiclo.setDoOutput(true);
                                         //GESTIONE PATH?

                                         String messageVisCiclo = "username=" + username + "&id=" + iduser + "&path=" + path+"&password="+password; //if path set uso path, in visualizzazione, if not sono nella home caso principale sopra
                                         OutputStream osVisCiclo = conVisCiclo.getOutputStream();
                                         osVisCiclo.write(messageVisCiclo.getBytes());
                                         osVisCiclo.flush();
                                         osVisCiclo.close();
                                         BufferedReader inVisCiclo = new BufferedReader(new InputStreamReader(conVisCiclo.getInputStream()));
                                         String inputLineVisCiclo = "";
                                         StringBuffer responseVisCiclo = new StringBuffer();
                                         while ((inputLineVisCiclo = inVisCiclo.readLine()) != null) {
                                             responseVisCiclo.append(inputLineVisCiclo);  // mex risposta dal server dell'operazione eseguita
                                         }
                                         inVisCiclo.close();
                                         feedback = responseVisCiclo.toString();
                                         String visFileFolderAttuale[] = feedback.split("/");
                                         //visFileFolder=null;
                                         //visFileFolder = feedback.split("/");
                                         String[] positionFolder = path.split("/");
                                         int pos = positionFolder.length - 1;
                                         System.out.println("Contenuto della working directory: " + positionFolder[pos]);
                                         System.out.println("----------------------------------"); // per formattazione
                                         for (int i = 0; i < visFileFolderAttuale.length; i++) {
                                             // visualizzazione contenuto
                                             System.out.println(visFileFolderAttuale[i]);
                                         }
                                         System.out.println("----------------------------------"); // per formattazione
                                         // linea teorica mi sono perso. quindi gl
                                         operazioneValida = false;
                                         break;

                                     default:
                                         System.out.println("----------------------------------"); // per formattazione
                                         System.out.println("La operazione che hai selezionata non e' possibile eseguila, magari hai sbagliato di scrivere!");
                                         operazioneValida = false;
                                         System.out.println("----------------------------------"); // per formattazione
                                         break;
                                 }// chiuso switch

                                 if (operazioneValida) {
                                     System.out.println("Operazione inoltrata al server");
                                     //System.out.println(messageExe); //check parameter
                                     // invio operazione:
                                     //1 versione gestione, senza controllo ID in Hub.php
                                     // connessione alla pagine Hub.php

                                     URL urlExe = new URL("http", "serverwebuni.ns0.it", 580, "/php/Hub.php");
                                     //SISTEMARE QUA
                                     //URL urlExe = new URL("http", "185.230.163.214", 80, "/php/Hub.php");
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
                                     String handlingResponse = responseExe.toString();
                                     String responseResultExe;

                                     if (operazione.equals("BK") || operazione.equals("FW")) {
                                         // lavoriamo con dir
                                         String[] handlingPath = handlingResponse.split("-"); //MODIFICARE CON ? al posto di /
                                         responseResultExe = handlingPath[0];
                                         // assegnazione new path
                                         path = handlingPath[1]; //quando implementata abilitare
                                     } else if (operazione.equals("CurD")) {
                                         String[] handlingPath = handlingResponse.split("-"); //MODIFICARE CON ? al posto di /
                                         System.out.println("----------------------------------"); // per formattazione
                                         System.out.println("Current Dir: " + handlingPath[1]);
                                         responseResultExe = handlingPath[0];
                                     } else {
                                         // abbiamo solo un mex di avviso risultato operazione
                                         responseResultExe = handlingResponse;
                                     }
                                     //VISUALIZZAZIONE RISPOSTA
                                     System.out.println("----------------------------------"); // per formattazione
                                     System.out.println("Status operazione: " + responseResultExe); // risposta dal server
                                     System.out.println("----------------------------------"); // per formattazione

                                 }// fine if operazione eseguibile sul server*/

                                 System.out.println("Premi invio per continuare...");
                                 spazio = scanner.nextLine();
                             } else {
                                 cicloAccount = false;
                                 System.out.println("Exiting...");
                             } //if exits close
                         }//fine ciclo while operazione
                     } else {
                         // Caso Login errato
                         System.out.println("----------------------------------"); // per formattazione
                         System.out.println("Credenziali sbagliate");
                         System.out.println("----------------------------------"); // per formattazione
                     }
                 }else{// if account ~ Case: non ho account e devo registrarmi nel sito
                     System.out.println("----------------------------------"); // per formattazione
                     System.out.println("Crea il nuovo account e quando hai finito premi invio");
                     System.out.println("Reindirizzamento nella pagina per registrarsi...");

                     try {
                         // Crea un oggetto URI dallo URL
                         URI uriRegistration = new URI("http://serverwebuni.ns0.it:580/register.html"); //specificare path registrazione
                         // Ottiene l'oggetto Desktop
                         Desktop desktopRegistration = Desktop.getDesktop();
                         // Apre la pagina nel browser predefinito del sistema
                         desktopRegistration.browse(uriRegistration);
                     } catch (IOException | URISyntaxException e) {
                         e.printStackTrace();
                     }
                     System.out.println("Reindirizzamento completato");
                     String invio = scannerAcc.nextLine();
                 }
            }//ciclo while account
            System.out.println("----------------------------------"); // per formattazione
            System.out.println("Grazie per aver usato la DEMO");
            System.out.println("----------------------------------"); // per formattazione
        }
}