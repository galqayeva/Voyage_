<?php

require "conn.php";


$name=$_POST["name"];
$surname=$_POST["surname"];
$email=$_POST["username"];
$username=$_POST["username"];
$regstrDate=$_POST["date"];
$pass=md5($_POST["password"]);

$sql = "INSERT INTO `users`( `name`, `surname`, `username`, `gmail`, `password`, `regstrDate`) 
					VALUES ('$name','$surname','$username','$email','$pass','$regstrDate')";

if ($conn->query($sql) === TRUE) {
    echo "ok";
} else {
    echo "This username is used";
}

$conn->close();

?>