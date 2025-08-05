<?php
	ini_set('display_errors', '1');
	ini_set('display_startup_errors', '1');
	error_reporting(E_ALL);
	$size = 0;
	$fileCount = 0;
/*
	function recursiveGlob($dir, $type) {
		$globFiles = glob("$dir/*$type");
		$globDirs  = glob("$dir/*", GLOB_ONLYDIR);
		global $size, $fileCount;

		foreach ($globDirs as $dir) {
			recursiveGlob($dir, $type);
		}

		foreach ($globFiles as $file) {
			if(is_file($file)){
				$size += filesize($file);
			}
		}
	}

	function getDimensions($dir, $types){
		global $size;
		$size=0;
		foreach ($types as $type){
			recursiveGlob($dir, $type);
		}
		return $size;
	}

	function DimensionsUser($user){
		$path = "./Data/".$user;
		$sizeTotal = getDimensions($path,['*']);
		$sizeImage = getDimensions($path,['.jpg', '.png', '.jpeg', '.gif']);
		$sizeDocument = getDimensions($path,['.txt', '.doc', '.docx', '.xlsx', '.pptx', '.odt', '.pdf']);
		$sizeVideo = getDimensions($path,['.mp4', '.mov', '.wmv', '.avi', '.mkv']);
		$sizeAudio = getDimensions($path,['.mp3', '.flac', '.pcm', '.wav', '.wma']);
		$sizeOther = $sizeTotal-($sizeImage+$sizeDocument+$sizeAudio+$sizeVideo);
		$sizeEmpty = 5368709120-$sizeTotal;
		$data = array( 
			array("label"=>"Images", "y"=>($sizeImage*100)/5368709120),
			array("label"=>"Documents", "y"=>($sizeDocument*100)/5368709120),
			array("label"=>"Music", "y"=>($sizeAudio*100)/5368709120),
			array("label"=>"Videos", "y"=>($sizeVideo*100)/5368709120),
			array("label"=>"Other", "y"=>($sizeOther*100)/5368709120),
			array("label"=>"Empty", "y"=>($sizeEmpty*100)/5368709120),
			);
		return $data;
	}
*/
	function iconSelection($file){
		$ext = pathinfo($file, PATHINFO_EXTENSION);
		if ($ext == 'txt' || $ext == 'doc' || $ext == 'docx' || $ext == 'xlsx' || $ext == 'pptx' || $ext=='otd' || $ext == 'pdf') {
			echo '<img src="./img/File_Icon.png" width="100px" height="100px">';
		}elseif($ext == 'mp4' || $ext == 'mov' || $ext == 'wmv' || $ext == 'avi' || $ext == 'mkv') {
			echo '<img src="./img/Video_Icon.png" width="100px" height="100px;">';
		}elseif($ext == 'jpg' || $ext == 'png' || $ext == 'jpeg' || $ext =='gif'){
			echo '<img src="./img/icon.png" width="100px" height="100px;">';
		}elseif($ext == 'mp3' || $ext == 'flac' || $ext == 'pcm' || $ext =='wav' || $ext == 'wma'){
			echo '<img src="./img/Music_Icon.png" width="100px" height="100px;">';
		}else{
			echo '<img src="./img/icon.png" width="100px" height="100px;">';
		}
	}
?>
