import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.lang.Integer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.File;


public class MainClient extends Component {

    public static void main(String[] args) throws Exception {

        System.out.println("----------------------------------"); // per formattazione
        System.out.println("Benvenuto nella demo!");
        System.out.println("----------------------------------"); // per formattazione
        Boolean cicloAccount = true;
        while (cicloAccount) {
            Scanner scannerAcc = new Scanner(System.in);
            System.out.println("Hai già un account? [Y][N]");
            String accExists = scannerAcc.nextLine();
            if (accExists.equals("Y")) {
                //Prendo in input le variabili
                String username, password;
                Scanner scanner = new Scanner(System.in);
                System.out.println("Fornisci Username:");
                username = scanner.nextLine();
                System.out.println("Fornisci Password:");
                password = scanner.nextLine();

                UrlConnection login = new UrlConnection();
                login.setUsername(username);
                login.setPassword(password);
                login.checkLogin();
                String Status = login.getResponseCode();
                int iduser = login.getIduser();
                if (Status.equals("202")) {
                    String path = "/"+username;
                    Visualize visualizza = new Visualize();
                    visualizza.setIduser(iduser);
                    visualizza.setPath(path);
                    visualizza.setPassword(password);
                    visualizza.setUsername(username);
                    visualizza.visualizzaDirectory();
                    System.out.println("Premi invio per continuare...");
                    String spazio = scanner.nextLine();
                    // CICLO OPERAZIONE
                    String operazione = "";
                    // SELEZIONA LA CARTELLA DI DOWNLOAD PER SEMPRE
                    System.out.println("----------------------------------"); // per formattazione
                    System.out.println("Vuoi selezionare una cartella differente per il download?[Y][N]");
                    String risposta = scanner.nextLine();
                    String saveDir="";
                    if(risposta.equals("Y")){
                        System.out.println("Seleziona la cartella di Download che preferisci");
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        System.out.println("Sto aprendo il diagolo per selezionare la cartella di destinazione...");
                        int result = fileChooser.showSaveDialog(null);
                        if (result == JFileChooser.APPROVE_OPTION) {
                            File downloadDir = fileChooser.getSelectedFile();

                            saveDir = downloadDir.toString();
                            String[] parts = saveDir.split("\\\\");
                            String nomeCartella = parts[parts.length - 1];
                            // creazione se non e' presente
                            File dir = new File(saveDir);
                            if (!dir.exists()) {
                                dir.mkdir();
                                System.out.println("La cartella " + nomeCartella + " è stata creata con successo!");
                                System.out.println("----------------------------------"); // per formattazione
                            }
                        }
                    }else{
                        System.out.println("È stata selezionata la cartella di default presente nella stessa directory del codice sorgente");
                        saveDir = "Download/";
                        System.out.println("----------------------------------"); // per formattazione
                    }

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
                                    System.out.println("Fornisci il nome della cartella a cui vuoi accedere: ");
                                    nameFolder = scanner.nextLine();
                                    operazioneValida = true;
                                    messageExe = "path=" + path + "&username=" + username + "&id=" + iduser + "&cartella=" + nameFolder + "&operazione=" + operazione+"&password="+password;
                                    break;

                                case "UF": //case Upload
                                    System.out.println("----------------------------------"); // per formattazione
                                    System.out.println("Sto aprendo il diagolo per effettuare l'Upload del file...");
                                    JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                                    int returnValue = jfc.showOpenDialog(null);
                                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                                        File selectedFile = jfc.getSelectedFile();
                                        String pathFile = selectedFile.getAbsolutePath();
                                        String filePath = pathFile;
                                        File file = new File(filePath);
                                        UploadFileConnection upload = new UploadFileConnection();
                                        upload.setFile(file);
                                        upload.setIduser(iduser);
                                        upload.setPath(path);
                                        upload.setPassword(password);
                                        upload.setUsername(username);
                                        upload.uploadFile();
                                    }else{//se fallisce
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

                                   /* JFileChooser fileChooser = new JFileChooser();
                                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                                    System.out.println("Sto aprendo il diagolo per selezionare la cartella di destinazione...");
                                    int result = fileChooser.showSaveDialog(null);
                                    if (result == JFileChooser.APPROVE_OPTION) {
                                        File downloadDir = fileChooser.getSelectedFile();

                                        String saveDir= downloadDir.toString();
                                        String[] parts = saveDir.split("\\\\");
                                        String nomeCartella = parts[parts.length - 1];
                                        // creazione se non e' presente
                                        File dir = new File(saveDir);
                                        if (!dir.exists()) {
                                            dir.mkdir();
                                            System.out.println("La cartella "+nomeCartella+" è stata creata con successo!");
                                            System.out.println("----------------------------------"); // per formattazione
                                        }*/
                                        System.out.println("Inizio fase di download file...");
                                        String postDataDW = "operazione="+operazione+
                                                "&username="+username+
                                                "&id="+iduser+
                                                "&nameFile="+nameFile+
                                                "&path="+path+
                                                "&password="+password;

                                        DownloadFileconnection download = new DownloadFileconnection();
                                        download.setSaveDir(saveDir);
                                        download.setPostDataDW(postDataDW);
                                        download.setNameFile(nameFile);
                                        download.downloadFile();

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
                                case "Vis": //case visualizzazione
                                    visualizza.setPath(path);
                                    visualizza.visualizzaDirectory();
                                    operazioneValida = false;
                                    break;
                                default:
                                    System.out.println("----------------------------------"); // per formattazione
                                    System.out.println("La operazione che hai selezionata non e' possibile eseguila, magari hai sbagliato di scrivere!");
                                    operazioneValida = false;
                                    System.out.println("----------------------------------"); // per formattazione
                                    break;
                            }//chiuso switch operazione
                            if (operazioneValida) {
                                System.out.println("Operazione inoltrata al server");
                                ExecutionConnection execution = new ExecutionConnection();
                                execution.setMessageExe(messageExe);
                                execution.setOperazione(operazione);
                                execution.executionOperation();
                                String handlingResponse = execution.getHandlingResponse();
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
                        }else{
                            cicloAccount = false;
                            System.out.println("Exiting...");
                        }//if exits close
                    }//fine ciclo operazione
                }else{
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
            }//case non ho account
        }//fine while cicloAccount
    }//classe main

}
