<?php
require_once('../config/database.php');

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        # ottengo le informazioni:
        if(isset($_POST['operazione'])){ //senno mostra echo errore che operazione non e' settata per i casi di MKDIR E UF
            $command = $_POST['operazione'];
        }
        $id = $_POST['id'];
        $path = "/var/www/html/Data".$_POST['path'];
        $username=$_POST['username'];
        $password=$_POST['password'];
        #controllo che id esista e che combaci con username
        #CONTROLLO AUTENTICAZIONE
        $query = "
            SELECT id, username, password
            FROM users
            WHERE username = :username
        ";
        $check = $pdo->prepare($query);
        $check->bindParam(':username', $username, PDO::PARAM_STR);
        $check->execute();
        $user = $check->fetch(PDO::FETCH_ASSOC);
    if(!$user || password_verify($password, $user['password']) === false) {

    //gestione caso file
    if (isset($_FILES['file'])) {
        // se carichiamo un file usando il nuovo metodo

        $file = $_FILES["file"];
        if (UPLOAD_ERR_OK == $file["error"]) { # check upload isset($_FILES['file'])
            $MaxCaratteri = 22;
            if (strlen($file['name']) > $MaxCaratteri) {
                #TRUE then modifica filename = nome troncato a 22+ext
                $FileNameModify = $file['name'];
                #explode per ottenere la parte finale (estensione)
                $fileNameParts = explode('.', $FileNameModify);
                $ext = end($fileNameParts);
                #ottengo substring
                $fileSubstringModify = substr($FileNameModify, 0, $MaxCaratteri);
                #concateno Stringa File Name Modificata
                $FinalNameFileModify = $fileSubstringModify . "." . $ext;
                #$FinalNameFileModify variabile con stringa finale!
                $fileName = $FinalNameFileModify;
            } else {
                #FALSE THEN filename = filename
                $fileName = $file['name'];
            }
            $esito = "Successo";
            # Apertura directory di lavoro
            $folder = opendir($path . "/");
            # check che non sia già presente il file
            while ($f = readdir($folder)) {
                if (is_file($path . "/" . $f)) {
                    # CONTROLLO SOLO I FILE
                    if ($f == $fileName) {
                        # Se trova una corrispondenza variabile check diventa Falsa
                        $esito = "Errore";
                    }
                }
            }
            # Fine lavoro check presenza file
            $folder = closedir($folder);

            if ($esito == "Successo") {
                #check dimensione e DB
                /////////////////////

                $fileTmpName = $_FILES['file']['tmp_name'];
                #caricamento nel FS
                move_uploaded_file($fileTmpName, $path . "/" . $fileName);
                echo "L'operazione di insertimento del file " . $fileName . " è andata a buon fine ";
            } else {
                echo "L'operazione di insertimento del file " . $fileName . " è fallita, perchè è gia presente nella directory ";
            }
        } else {
            #file non caricato correttamente
            echo "L'operazione di insertimento del file " . $file['name'] . " è fallita";
        }

    } else {
        //altrimenti caso switch con le operazioni
        switch ($command) {
            case 'BK':
                $check = "/var/www/html/Data/" . $username;
                if ($path == $check) {
                    echo "Operazione Back non è stata possibile eseguirla" . "-";
                    echo "/" . $username;
                } else {
                    $array = explode("/", $path);
                    $newDir = null;
                    for ($i = 5; $i < sizeof($array) - 1; $i++) {
                        $newDir = $newDir . DIRECTORY_SEPARATOR . $array[$i];
                    }
                    echo "Operazione Back eseguita con successo" . "-";
                    echo $newDir;
                }
                break;
            case 'FW':  #cambio directory
                $FWFolder = $_POST['cartella'];
                $newpath = $path . "/" . $FWFolder;
                $checkingDir = $path . "/";
                $folder = opendir($checkingDir);
                $FolderExist = False;
                while ($fold = readdir($folder)) {
                    if (is_dir($checkingDir . $fold)) {
                        #CONTROLLO SOLO I FOLDER
                        if ($fold == $FWFolder) {
                            $FolderExist = true;
                        }
                    }
                }
                $folder = closedir($folder);
                if ($FolderExist) {
                    echo "Siamo entrati nella cartella: " . $FWFolder . "-";
                    echo $_POST['path'] . "/" . $FWFolder;
                } else {
                    echo "La cartella " . $FWFolder . " non esiste" . "-";
                    echo $_POST['path'];
                }
                break;
            case 'MKDIR':   #creo una nuova cartella
                $nameFolder = $_POST['cartella'];
                //nome inserito corretto
                $dir = $path . "/" . $nameFolder;
                if (mkdir($dir, 0770)) {
                    #operazione eseguita
                    echo "Cartella " . $nameFolder . " creata con successo!";
                } else {
                    #operazione non eseguita
                    echo "Creazione della cartella " . $nameFolder . " è fallita";
                }
                break;
            case 'RMDIR':   #rimuovo una cartella solo se è vuota
                $nameFolder = $_POST['cartella'];
                $Directory = $path . "/";
                $trovato = false;

                //apertura percorso
                $folder = opendir($Directory);
                while ($fold = readdir($folder)) {
                    if (is_dir($Directory . $fold)) {
                        #CONTROLLO SOLO I FOLDER
                        if ($fold == $nameFolder) {
                            #CANCELLO IL FOLDER
                            $trovato = true;
                        }
                    }
                }
                $folder = closedir($folder);
                if ($trovato) {
                    $cancellare = true;
                    $directoryDaControllare = $path . DIRECTORY_SEPARATOR . $nameFolder . DIRECTORY_SEPARATOR;
                    $checkFolder = opendir($directoryDaControllare);
                    while ($f = readdir($checkFolder)) {
                        if ($f != '.' and $f != '..') {
                            $cancellare = false;
                        }
                    }
                    $checkFolder = closedir($checkFolder);
                    if ($cancellare) {
                        //cancella cartella
                        rmdir($Directory . $nameFolder);
                        echo "La cartella " . $nameFolder . " cancellata correttamente";
                    } else {
                        echo "La cartella " . $nameFolder . " non è stata cancellata perchè contiene altri file/folder";
                    }

                } else {
                    echo "L'operazione di rimozione della cartella " . $nameFolder . " è fallita perchè non esiste";
                }
                break;
            case 'RF':     #rimuove un file
                $nameFile = $_POST['nameFile'];
                $trovato = false;
                $Dir = $path;
                //apertura percorso
                $folder = opendir($Dir . "/");
                while ($file = readdir($folder)) {
                    #echo $file."<br/>";
                    if (is_file($Dir . "/" . $file)) {
                        #CONTROLLO SOLO I FILE
                        #echo $file."<br/>";
                        if ($file == $nameFile) {
                            #CANCELLO IL FILE
                            @unlink($Dir . "/" . $file);
                            $trovato = true;
                        }
                    }
                }
                $folder = closedir($folder);

                //DB


                if ($trovato) {
                    echo "L'operazione di rimozione del file " . $nameFile . " è andata a buon fine";
                } else {
                    echo "L'operazione di rimozione del file " . $nameFile . " è fallita";
                }
                break;
            case 'MFDIR':   #modifica il nome di una cartella
                $oldNameFolder = $_POST['oldNameFolder'];
                $newNameFolder = $_POST['newNameFolder'];

                $Directory = $path . "/";


                $problem = true;

                $folder = opendir($Directory);
                while ($fold = readdir($folder)) {
                    if (is_dir($Directory . $fold)) {
                        #CONTROLLO SOLO I FOLDER
                        if ($fold == $newNameFolder) {
                            #NEW NAME GIà OCCUPATO
                            $problem = false;
                        }
                    }
                }
                $folder = closedir($folder);
                $esito = false;
                if ($problem) {
                    $folder = opendir($Directory);
                    while ($fold = readdir($folder)) {
                        if (is_dir($Directory . $fold)) {
                            #CONTROLLO SOLO I FOLDER
                            if ($fold == $oldNameFolder) {
                                #FOLDER TROVATO
                                if (rename($Directory . $fold, $Directory . $newNameFolder)) {
                                    $esito = true;
                                }
                            }
                        }
                    }
                    $folder = closedir($folder);
                }

                if ($esito) {
                    echo "La cartella " . $oldNameFolder . " è stata modificata";
                } else {
                    echo "La cartella " . $oldNameFolder . " non modificata";
                }
                break;
            case 'CurD':
                $vector = explode("/", $path);
                // ricostruzione Dir
                $currentDirVis = "";
                for ($i = 5; $i < count($vector); $i++) {
                    $currentDirVis = $currentDirVis . "/" . $vector[$i];
                }
                echo "operazione eseguita" . "-";
                echo $currentDirVis;
                break;
            default:
                echo "comando non trovato, gestione dell'errore";
                break;
        }
    }
}else{

}
        //sistemare
       /* $query = '
            SELECT username
            FROM users
            WHERE username=:username and iduser=:iduser';
//username = :username AND iduser = :id
        $check = $pdo->prepare($query);
        $check->bindParam(':username', $username, PDO::PARAM_STR);
        $check->bindParam(':iduser', $id, PDO::PARAM_STR);
        $check->execute();

        if ($check->rowCount()== 0){
            echo "Errore, username e credenziali non combaciano";
        }else{*/

                /*case 'UF':  #inserisco un file
                    //fase testing no DB
                    $file = $_FILES["file"];
                    if(UPLOAD_ERR_OK == $file["error"]) { # check upload isset($_FILES['file'])
                        $fileName = $file['name'];
                        $workingDir = $path."/".$fileName;
                        if(fopen($workingDir, "r")){
                            #Non Puoi inserire il file
                            $mex = "Ciao sono nel caso che il file e' gia presente nella working directory";
                            header("Location:./InputFile.php?mex=".$mex);
                        }else{
                            #Puoi inserire il file
                            #caricamento nel FileSystem:
                            #fileName gia' ottenuto sopra con possibile casting se necessario
                            $fileTmpName = $_FILES['file']['tmp_name'];
                            move_uploaded_file($fileTmpName, $path."/".$fileName);
                            // creazione file JSON
                            # Informazioni necessarie: FileName - Path - IdUser
                            $Informazione = array($fileName,$path, $id);
                            // codifica JSON
                            $FileJson = json_encode($Informazione);
                            #delegazione alla pagina UPLOAD.
                            // header("Location: ./upload.php?Json=".$FileJson);
                            $mex = "Ciao sono nel caso corretto";
                            header("Location: ./InputFile.php?mex=".$mex);
                        }

                        //da aggiustare?
                        /* $dimDisponibileFloat = DimensionsUser($id)[5]['y'];   #need trovare ID del user se non usiamo le sessioni
                         $dimDisponibileByte = $dimDisponibileFloat/(1024*1024);
                         if($dimDisponibileByte+$file['size']< getMaxDimension()){ #controllo spazio disponibile
                             #check nome file conforme:
                             #variabile per troncamento:*/
                        /*   $MaxCaratteri = 22;
                           if (strlen($file['name']) > $MaxCaratteri) {
                               #TRUE then modifica filename = nome troncato a 22+ext
                               $FileNameModify = $file['name'];
                               #explode per ottenere la parte finale (estensione)
                               $fileNameParts = explode('.', $FileNameModify);
                               $ext = end($fileNameParts);
                               #ottengo substring
                               $fileSubstringModify = substr($FileNameModify, 0, $MaxCaratteri);
                               #concateno Stringa File Name Modificata
                               $FinalNameFileModify = $fileSubstringModify . "." . $ext;
                               #$FinalNameFileModify variabile con stringa finale!
                               $fileName = $FinalNameFileModify;
                           }else{
                               #FALSE THEN filename = filename
                               $fileName = $file['name'];
                           }
                           #check file non sia uguale ad uno esistente nel path working dir
                           $workingDir = $path."/".$fileName;
                           if(fopen($workingDir, "r")){
                               #Non Puoi inserire il file
                               echo "Ciao sono nel caso che il file e' gia presente nella working directory";
                           }else{
                               #Puoi inserire il file
                               #caricamento nel FileSystem:
                               #fileName gia' ottenuto sopra con possibile casting se necessario
                               $fileTmpName = $_FILES['file']['tmp_name'];
                               //move_uploaded_file($fileTmpName, $path."/".$fileName);   #da abilitare quanto tutto e' sistemato
                               // creazione file JSON
                               # Informazioni necessarie: FileName - Path - IdUser
                               $Informazione = array($fileName,$path, $id);
                               // codifica JSON
                               $FileJson = json_encode($Informazione);
                               #delegazione alla pagina UPLOAD.
                               // header("Location: ./upload.php?Json=".$FileJson);
                               echo "Ciao sono nel caso corretto";
                               // AGGIUNGERE DATABASE
                           }
                       }else{ #if controllo spazione disponibile
                           echo "Ciao sono nel caso dove non c'e enough spazio";
                       }
                    }else{
                        #file non caricato correttamente
                        $mex = "Ciao sono nel caso che il file non e' stato caricato correttamente";
                        header("Location: ./InputFile.php?mex=".$mex);
                    }
                    break;*/

       // }
            } else {
                http_response_code(405); // Metodo non consentito
                echo "Metodo non consentito";
            }
        ?>
