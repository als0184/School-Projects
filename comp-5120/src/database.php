<?php
//servername
$dblink = "sysmysql8.auburn.edu";
//username of database
$dbusername = "als0184";
//password of database
$dbpass = "Sadcarebear21!";
//database name "username-db"
$dbname = "als0184db";

//creates a connection to the database - Reference: "https://phoenixnap.com/kb/connect-mysql-with-php"
function get_connection() {
    global $dblink, $dbusername, $dbpass, $dbname;
    $connection = mysqli_connect($dblink, $dbusername, $dbpass, $dbname);
    if ($connection === false) {
        die("Could not connect: " . mysqli_connect_error(connection));
    }
    return $connection;
}
function execute_query($con, $query)
{
    $result = mysqli_query($con, $query);
    if ($result !== false && $result instanceof mysqli_result) {
        return $result;
    } else {
        if (preg_match("/^\\s*(CREATE|DELETE|UPDATE|INSERT)/i", $query)) {
            if ($result) {
                $queryType = strtoupper(preg_replace("/\\s.*/s", "", $query));
                switch ($queryType) {
                    case "CREATE":
                        return "Table Created";
                    case "DELETE":
                        return "Row(s) Deleted";
                    case "UPDATE":
                        return "Table Updated";
                    case "INSERT":
                        return "Row Inserted";
                }
            } else {
                return false;
            }
        } else {
            return $result;
        }
    }
}
function count_rows($connection) {
    return mysqli_affected_rows($connection);
}
function get_error($connection) {
    return mysqli_error($connection);
}
?>
