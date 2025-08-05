<?php
    require_once("../config/database.php");
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <?php
        # ottengo info del file JSON
        $FileJson = $_GET['Json'];
        # decodifica
        $infos = json_decode($FileJson);  # array- 0 filename , 1 path, 2 id
        # MIME E' OPZIONALE
        $query = "
                    INSERT INTO files (idfile,filename,extension,pathfile,idusername,creationdate,creationhour)
                    VALUES (0, :filename, :extension, :pathfile,:idusername,:creationdate,:creationhour)
                    ";
        $date = date('Y-m-d');
        $hour = date('H:m:s');
        $fileName=$infos[0];
        $idutente =$infos[2];
        $db_path=$infos[1] . DIRECTORY_SEPARATOR . $infos[0];
        #ottengo estensione:
        $fileextension=explode(".",basename($infos[0]));
        $ext=end($fileextension);
        # Esecuzione query:
        $check = $pdo->prepare($query);
        $check->bindParam(':filename', $fileName, PDO::PARAM_STR);
        $check->bindParam(':extension', $ext, PDO::PARAM_STR);
        $check->bindParam(':pathfile', $db_path, PDO::PARAM_STR);
        $check->bindParam(':idusername', $idutente, PDO::PARAM_STR);
        $check->bindParam(':creationdate', $date, PDO::PARAM_STR);
        $check->bindParam(':creationhour', $hour, PDO::PARAM_STR);
        if(True/*$check->execute()*/){  # messo a true finche' non viene settato correttamente il valore $id (infos[2])
            # IF TRUE THEN
            echo "File inserito correttamente nel database";
        }else{
            # IF FALSE THEN
            echo "File non e' stato caricato, inizio fase di eliminazione file nel FS";
            # eliminare dal file system
            # se arrivati a questo punto: DB: FALLITO, FS: OK
            # Procedo eliminando nel FS:
            //@unlink($path."/" . $fileName);
            echo "Eliminato nel FS";
        }

?>
</body>
</html>