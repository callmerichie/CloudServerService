<?php
session_start();
$user = $_SESSION['session_user'];

        if(isset($_POST["nomeFolder"])){
            $folderName=$_POST["nomeFolder"];
            $Dir = $_POST["directory"]; // /var/www/html/Data/LucaDaniel   /var/www/html/Data/LucaDaniel/5
            //nome inserito corretto
            $dir = $Dir."/".$folderName;
            if(mkdir($dir, 0770)){
                mkdir($dir, 0770);
                $messaggio = "L'operazione di inserimento della cartella ".$folderName." è andata a buon fine";
                header("Location: http://serverwebuni.ns0.it:580/dashboard.php?esitoOperazione=".$messaggio."&currentdir=".$Dir);
            }else{
                $messaggio = "L'operazione di inserimento della cartella ".$folderName." è fallita";
                header("Location: http://serverwebuni.ns0.it:580/dashboard.php?esitoOperazione=".$messaggio."&currentdir=".$Dir);
            }

            // creata cartella nel path $dir

            // aggiornamento nel database che è statta inserita la cartella

           /* $query = "
                INSERT INTO files
                VALUES (0, :foldername, :extension, :mime, :idusername, :percorso)      //@todo modificare i parametri
            ";
            $check = $pdo->prepare($query);
            $check->bindParam(':filename', $file["name"], PDO::PARAM_STR);
            $check->bindParam(':extension', explode(".",$file["name"])[2], PDO::PARAM_STR);
            $check->bindParam(':mime', $file["type"], PDO::PARAM_STR);
            $check->bindParam(':idusername', $idutente, PDO::PARAM_STR);
            $check->bindParam(':percorso', string($uploadDir.DIRECTORY_SEPARATOR.$file["name"]), PDO::PARAM_STR);
            $check->execute();*/

        }
?>