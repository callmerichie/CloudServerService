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
    if(isset($_POST["nameFolder"])) {
        $folderName = $_POST["nameFolder"];
        $Dir = $_POST["path"];
        $Directory = $Dir . "/";
        $trovato = false;

        //apertura percorso
        $folder = opendir($Directory);
        while ($fold = readdir($folder)) {
            if (is_dir($Directory . $fold)) {
                #CONTROLLO SOLO I FOLDER
                if ($fold == $folderName) {
                    #CANCELLO IL FOLDER
                    $trovato = true;
                }
            }
        }
        $folder = closedir($folder);

        if ($trovato) {
            $cancellare = true;
            $directoryDaControllare = $Dir . DIRECTORY_SEPARATOR . $folderName . DIRECTORY_SEPARATOR;
            $checkFolder = opendir($directoryDaControllare);
            while ($f = readdir($checkFolder)) {
                if ($f != '.' and $f != '..') {
                    $cancellare = false;
                }
            }
            $checkFolder = closedir($checkFolder);
            if ($cancellare) {
                //cancella cartella
                rmdir($Directory . $folderName);
                echo "Cartella cancellata";
            } else {
                echo "Cartella non cancellata perche' contiene altri file/folder";
            }

        } else {
            echo "Cartella non cancellata perche' non esiste";
        }
    }else{
        echo "Cartella non impostata";
    }
?>
</body>
</html>