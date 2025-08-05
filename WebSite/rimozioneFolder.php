<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL | E_STRICT);

session_start();
$user = $_SESSION['session_user'];

$nomefolder = $_POST["rimFolder"];
//apro la cartella user
    $Dir = $_POST["directory"];
    $Directory = $Dir. "/";
    $trovato = false;

//apertura percorso
    $folder = opendir($Directory);
    while ($fold = readdir($folder)) {
        if (is_dir($Directory . $fold)) {
            #CONTROLLO SOLO I FOLDER
            if ($fold == $nomefolder) {
                #CANCELLO IL FOLDER
                $trovato = true;
            }
        }
    }
    $folder = closedir($folder);

    if ($trovato) {
        $cancellare = true;
        $directoryDaControllare = $Dir . DIRECTORY_SEPARATOR . $nomefolder . DIRECTORY_SEPARATOR;
        $checkFolder = opendir($directoryDaControllare);
        while ($f = readdir($checkFolder)) {
            if ($f != '.' and $f != '..') {
                $cancellare = false;
            }
        }
        $checkFolder = closedir($checkFolder);
        if ($cancellare) {
            //cancella cartella
            rmdir($Directory . $nomefolder);
            $messaggio = "L'operazione di rimozione del folder ".$nomefolder." è andata a buon fine";
            header("Location: http://serverwebuni.ns0.it:580/dashboard.php?esitoOperazione=".$messaggio."&currentdir=".$Dir);
        } else {
            $messaggio = "L'operazione di rimozione del folder ".$nomefolder." è fallita";
            header("Location: http://serverwebuni.ns0.it:580/dashboard.php?esitoOperazione=".$messaggio."&currentdir=".$Dir);
        }

    }else{
        $messaggio = "L'operazione di rimozione del folder ".$nomefolder." è fallita";
        header("Location: http://serverwebuni.ns0.it:580/dashboard.php?esitoOperazione=".$messaggio."&currentdir=".$Dir);
    }

?>
