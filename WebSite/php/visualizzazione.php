<?php
require_once('../config/database.php');
// GESTISCO IL PRIMO CASO VISUALIZZAZIONE HOME
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $username=$_POST['username'];
    $iduser=$_POST['id'];
    $password=$_POST['password'];
    $path= "/var/www/html/Data".$_POST['path'];
    $query = "
            SELECT id, username, password
            FROM users
            WHERE username = :username
        ";
    $check = $pdo->prepare($query);
    $check->bindParam(':username', $username, PDO::PARAM_STR);
    $check->execute();
    $user = $check->fetch(PDO::FETCH_ASSOC);
    if(!$user || password_verify($password, $user['password']) === false){
    $folder = opendir($path."/");
    # lettura file and folder presenti nella Dir e visualizzazione:
    while ($f = readdir($folder)) {
        if (is_file($path."/" . $f)) {
            $fileName = $f;
            #CONTROLLO SOLO I FILE
            echo $fileName;
            echo "/";
        }
        if(is_dir($path."/" . $f)){
            #CONTROLLO SOLO LE CARTELLE
            if($f != "." and $f != ".."){
                echo $f;
                echo "/";
            }
        }
    }//chiuso while
    $folder = closedir($folder);
    }else {
        echo "Errore! Credenziali Non Corrispondenti";
    }
}
?>