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
        if(isset($_POST["nameFolder"])){
        $folderName=$_POST["nameFolder"];
        $Dir = $_POST["path"]; // /var/www/html/Data/LucaDaniel   /var/www/html/Data/LucaDaniel/5
        //nome inserito corretto
        $dir = $Dir."/".$folderName;
        if(mkdir($dir, 0770)){
            mkdir($dir, 0770);
            echo "Cartella creata";
        }else{
            echo "Cartella non creata";
        }

    ?>
</body>
</html>