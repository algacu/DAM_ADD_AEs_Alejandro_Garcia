<?php
if (isset($_POST["title"])) {

    $title = $_POST["title"];
    $country = $_POST["country"];
    $year = $_POST["year"];
    $runtime = $_POST["runtime"];
    $genre = $_POST["genre"];
    $director = $_POST["director"];
    $writer = $_POST["writer"];
    $actors = $_POST["actors"];
    $plot = $_POST["plot"];
    $rating = $_POST["rating"];
    $poster = $_POST["poster"];
    $servidor = "localhost";
    $usuario = "root";
    $password = "";
    $dbname = "moviedb";

    $conexion = mysqli_connect($servidor, $usuario, $password, $dbname);
    if (!$conexion) {
        echo "Error en la conexiona MySQL: " . mysqli_connect_error();
        exit();
    }
    $sql = "INSERT INTO movies (title, country, release_date, runtime, genre, director, writer, actors, plot, rating, poster) 
    VALUES ('" . addslashes($title) . "','" . addslashes($country) . "','" . addslashes($year) . "','" . addslashes($runtime) . "','" 
    . addslashes($genre) . "','" . addslashes($director) . "','" . addslashes($writer) . "','" . addslashes($actors) . "','"
    . addslashes($plot) . "','" . addslashes($rating) . "','" . addslashes($poster) . "')";
    if (mysqli_query($conexion, $sql)) {
        echo "Registro insertado correctamente.";
    } else {
        echo "Error del php: " . mysqli_error($conexion);
    }
}