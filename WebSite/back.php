<?php
session_start();
$session_user = $_SESSION['session_user'];
$nome = $_SESSION['PercorsoAttuale'];
$array = explode('/', $nome);
$newDir = null;
for($i = 1; $i < sizeof($array)-1;$i++){
    $newDir= $newDir.DIRECTORY_SEPARATOR.$array[$i];
}
//echo $newDir;
header("Location: http://serverwebuni.ns0.it:580/dashboard.php?currentdir=".$newDir);


?>
