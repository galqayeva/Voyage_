<?php

require "conn.php";

$pass=md5($_POST["password"]);
$username=$_POST["username"];

$sql = "SELECT * FROM `users` WHERE username='$username' and password='$pass'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {

    while($row = $result->fetch_assoc()) {

        echo json_encode($row);
    }

} else {

    echo "No such Username";

}

$conn->close();

?>