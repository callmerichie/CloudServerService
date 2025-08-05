<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL | E_STRICT);
session_start();
require_once("./config/database.php");
//controllo file sia arrivato correttamente

$file = $_FILES["file"];
$user = $_SESSION['session_user'];
//$idutente = $_SESSION['id_user'];
$idutente = 2;
// check  se il file è arrivato correttamente
$uploadDir = $_POST["directory"]; // /var/www/html/Data/LucaDaniel   /var/www/html/Data/LucaDaniel/5

if(UPLOAD_ERR_OK == $file["error"]){

    // PARTE UPLOAD
    //check che non sia già presente il file
    //apertura percorso
    $esito = "Successo";
    $folder = opendir($uploadDir."/");
    while ($f = readdir($folder)) {
        if (is_file($uploadDir."/" . $f)) {
            #CONTROLLO SOLO I FILE
            if ($f == $file["name"]) {
                $esito= "Errore";
            }
        }
    }
    $folder = closedir($folder);

    //echo $esito;
    if($esito == "Successo") {
        $fileName = basename($file['name']);
        move_uploaded_file($file['tmp_name'], $uploadDir . DIRECTORY_SEPARATOR . $fileName);
        //inserimento dati nel database
        $query = "
        INSERT INTO files (idfile,filename,extension,MIME,pathfile,idusername,creationdate,creationhour)
        VALUES (0, :filename, :extension, :mime,:pathfile,:idusername,:creationdate,:creationhour)
        ";
        $date = date('Y/m/d');
        $hour = date('H/i/s');
        $d=$uploadDir . DIRECTORY_SEPARATOR . $fileName;
        $a=explode(".",$file["name"]);
        $ext=end($a);
        $check = $pdo->prepare($query);
        $check->bindParam(':filename', $file["name"], PDO::PARAM_STR);
        $check->bindParam(':extension', $ext, PDO::PARAM_STR);
        $check->bindParam(':mime', $file["type"], PDO::PARAM_STR);
        $check->bindParam(':pathfile', $d, PDO::PARAM_STR);
        $check->bindParam(':idusername', $idutente, PDO::PARAM_STR);
        $check->bindParam(':creationdate', $date, PDO::PARAM_STR);
        $check->bindParam(':creationhour', $hour, PDO::PARAM_STR);
        $check->execute();
        $messaggio = "L'operazione di insertimento del file ".$file['name']." è andata a buon fine";
        header("Location: http://serverwebuni.ns0.it:580/dashboard.php?esitoOperazione=".$messaggio."&currentdir=".$uploadDir);
    }else{
        $messaggio = "L'operazione di insertimento del file ".$file['name']." è fallita";
        header("Location: http://serverwebuni.ns0.it:580/dashboard.php?esitoOperazione=".$messaggio."&currentdir=".$uploadDir);
    }
}else{  // se errore ritorna subito nella pagina precedente
    $messaggio = "L'operazione di insertimento del file ".$file['name']." è fallita";
    header("Location: http://serverwebuni.ns0.it:580/dashboard.php?esitoOperazione=".$messaggio."&currentdir=".$uploadDir);
}
?>

