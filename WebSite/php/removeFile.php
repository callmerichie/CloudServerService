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
            #gestire casi
            $nomefile = $_POST["nomeFile"];
            $path = $_POST['path'];
            if(fopen($path."/".$nomefile,"r")){
                # se posso leggerlo, posso eliminarlo
                #teoricamente devo chiuderlo
                fclose($path."/".$nomefile);
                /*$query = "
                DELETE FROM files
                WHERE filename = :nomefile and :idutente = idusername and :pathfile = pathfile
                ";

                $check = $pdo->prepare($query);
                $check->bindParam(':nomefile', $nomefile, PDO::PARAM_STR);
                $check->bindParam(':idutente', $idutente, PDO::PARAM_STR);
                $check->bindParam(':pathfile', $dirFileElimina, PDO::PARAM_STR);
                $check->execute();

                if($check->rowCount() > 0 & @unlink($Dir."/" . $file)){
                    echo "File cancellato correttamente";
                }else{
                    echo "File non e' stato cancellato";
                }*/
            }else{
                echo "ciao, caso che file non esiste";
            }
        ?>
    </body>
</html>
