


<!--  contiene il vecchio form
<div id="barraGridRight" style="overflow-y: scroll; height: 280px;overflow-x:hidden;">
                        <form action="inserimentoFile.php". method="post" enctype="multipart/form-data">
                            <label for="UploadFile" style="font-weight: bold;">Insert file to upload</label><br>
                            <input type="file" name="file" style="height:30px; width:350px"></input>
                            <input type="text" name="directory"  style="visibility:hidden; height:20px; width:0px" " value="<?php echo $_SESSION['PercorsoAttuale'];?>"/>
                            <input type="submit" value="Submit" style="height:20px; width:120px"></input>
                        </form><br>

                        <form action="rimozioneFile.php" method="post">
                            <label for="RemoveFile" style="font-weight: bold;"  >Remove file</label><br>
                            <input type="text" name="nomeFile" placeholder="name file to remove" style="height:20px; width:150px">
                            </input><input type="text" name="directory" style="visibility:hidden;height:20px; width:191px" value="<?php echo $_SESSION['PercorsoAttuale'];?>"/>
                            <input type="submit" value="Remove File"  style="height:20px; width:120px"></input>
                        </form><br>

                        <form action="inserimentoFolder.php" method="post">
                            <label for="InsertFolder" style="font-weight: bold;">Insert folder</label><br>
                            <input type="text" name="nomeFolder" placeholder="name folder to insert" style="height:20px; width:157px">
                            </input><input type="text" name="directory" style="visibility:hidden;height:20px; width:184px" value="<?php echo $_SESSION['PercorsoAttuale'];?>"/>
                            <input type="submit" value="New Folder"  style="height:20px; width:120px"></input>
                        </form><br>

                        <form action="modificaFolder.php" method="post">
                            <label for="ModifyFolder" style="font-weight: bold;">Modify folder</label><br>
                            <input type="text" name="oldname" placeholder="name folder" style="height:20px; width:150px"></input>
                            <input type="text" name="newname" placeholder="new name folder" style="height:20px; width:160px"/>
                            <input type="text"  name="directory" style="visibility:hidden;height:20px; width:18px" value="<?php echo $_SESSION['PercorsoAttuale'];?>"/>
                            <input type="submit" value="Modify Folder"  style="height:20px; width:120px"></input>
                        </form><br>

                        <form action="rimozioneFolder.php" method="post">
                            <label for="RemoveFolder" style="font-weight: bold;">Remove folder</label><br>
                            <input type="text" name="rimFolder" placeholder="name folder to remove"  style="height:20px; width:157px">
                            </input><input type="text" name="directory" style="visibility:hidden;height:20px; width:184px" value="<?php echo $_SESSION['PercorsoAttuale'];?>"/>
                            <input type="submit" value="Remove Folder"  style="height:20px; width:120px"></input>
                        </form><br>

                        <form action="downloadFile.php" method="post">
                            <label for="DownloadFile" style="font-weight: bold;">Download file</label><br>
                            <input type="text" name ="downloadFileName" placeholder="name file to download"  style="height:20px; width:157px">
                            </input><input type="text" name="directory" style="visibility:hidden;height:20px; width:184px" value="<?php echo $_SESSION['PercorsoAttuale'];?>"/>
                            <input type="submit" value="Download File"  style="height:20px; width:120px"></input>
                        </form>

                        <form action="testform.php" method="post">
                            <input type="submit" value="ZONA TESTING :)"  style="height:20px; width:120px"></input>
                        </form>
                    </div> -->