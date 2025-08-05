<html>
    <head>
		<title><?php echo $_GET["result"]?></title>
		<link rel="stylesheet" href="./style/style.css">
	</head>
	<body>
		<div class="container" id="container2">
			<img src="./img/icon.png" width="100px" height="100px">
			<h1>
			<?php echo $_GET["result"]; ?>
			</h1>
			<h3>
			<?php printf($_GET["msg"]); ?>
			</h3>
			<button type="button" id="button" onclick="javascript:history.back()">OK</button>
		</div>
	</body>
</html>    
