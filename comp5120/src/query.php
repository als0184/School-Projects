<?php
require "database.php";
global $tables;
$con = get_connection();
if (!$con) {
    report_error(mysqli_error($con));
    die();
}

function report_error($msg) {
    echo '<div style="width: 100%; background: #f2dede; padding: 10px; border-radius: 5px">' . $msg . '</div>';
}
?>

    <!DOCTYPE html>
    <html>
    <head>
        <title>COMP 5120 Term Project</title>
        <meta charset="UTF-8">
        <style>
            body {
                font-family: Arial, sans-serif;
                color: #333;
                background-color: #f2f2f2;
                margin: 0;
                padding: 0;
            }
            h1 {
                color: #333;
                font-size: 36px;
                text-align: center;
                margin-top: 50px;
                margin-bottom: 30px;
            }
            h3 {
                color: gray;
                font-size: 18px;
                text-align: center;
                margin-top: 0;
                margin-bottom: 50px;
            }
            form {
                width: 80%;
                max-width: 800px;
                margin: 0 auto;
                padding: 20px;
                background-color: #fff;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                display: flex;
                flex-wrap: wrap;
                justify-content: space-between;
            }
            label {
                display: block;
                font-size: 18px;
                font-weight: bold;
                margin-bottom: 10px;
            }
            textarea {
                display: block;
                height: 200px;
                width: 100%;
                padding: 10px;
                font-size: 16px;
                border: 1px solid #ccc;
                border-radius: 5px;
                resize: vertical;
            }
            input[type="submit"], input[type="button"] {
                display: block;
                margin-top: 20px;
                padding: 10px;
                font-size: 18px;
                font-weight: bold;
                color: #fff;
                background-color: #007bff;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.2s ease;
                width: 48%;
            }
            input[type="submit"]:hover, input[type="button"]:hover {
                background-color: #0056b3;
            }
            table {
                border-collapse: collapse;
                width: 100%;
                border: 1px solid #ddd; /* Add border */
                background-color: #f8f8f8; /* Change background color */
            }

            th, td {
                text-align: center;
                padding: 8px;
                border: 1px solid #ddd; /* Add border */
            }

            th {
                background-color: #f2f2f2;
                font-weight: bold;
            }
            #container {
                margin: 20px auto;
                max-width: 800px; /* Set maximum width */
            }
        </style>
        <script>
            function clearTextBox() {
                document.getElementById("sql").value = "";
            }
        </script>
    </head>
    <body>
    <h1>Database Query Form</h1>
    <h3>by: Austin Smith - als0184</h3>
    <form method="post" action="query.php">
        <label for="sql">Enter your SQL statement:</label>
        <textarea name="sql" id="sql"></textarea>
        <input type="submit" value="Submit">
        <input type="button" value="Clear" onclick="clearTextBox()">
    </form>

        <?php
        if (isset($_POST["sql"])) {
            $query = stripcslashes($_POST["sql"]);
            $q = strtolower($query);
            $forbidden = array("drop");
            foreach($forbidden as $key) {
                if(strpos($q, $key) !== false) {
                    report_error("DROP statements are not allowed! Please try again.");
                    die();
                }
            }

            if ($query !== "") {
                $result = execute_query($con, $query);
                if (!$result) {
                    report_error(mysqli_error($con));
                    die();
                }
                if (strpos(strtolower($query), 'delete') !== false) {
                    echo mysqli_affected_rows($con) . ' Row(s) Deleted';
                    return;
                }
                if (strpos(strtolower($query), 'create') !== false) {
                    echo mysqli_affected_rows($con) . ' Table Created';
                    return;
                }
                if (strpos(strtolower($query), 'insert') !== false) {
                    echo mysqli_affected_rows($con) . ' Row Inserted';
                    return;
                }
                if (strpos(strtolower($query), 'update') !== false) {
                    echo mysqli_affected_rows($con) . ' Table Created';
                    return;
                }
                $numFields = mysqli_num_fields($result);
                $numRows = mysqli_num_rows($result); // Add this line to get the number of rows

                echo "<p>Number of rows retrieved: {$numRows}</p>"; // Display the number of rows

                echo '<table>';
                echo '<thead>';
                echo '<tr>';
                for($i = 0; $i < $numFields; $i++) {
                    $field = mysqli_fetch_field_direct($result, $i);
                    echo '<th>' . $field->name . '</th>';
                }
                echo '</tr>';
                echo '</thead>';

                $rows = array();
                while($resultRow = mysqli_fetch_assoc($result)) {
                    $rows[] = $resultRow;
                }
                foreach($rows as $row) {
                    echo '<tr>';
                    foreach($row as $col) {
                        echo '<td>' . $col . '</td>';
                    }
                    echo '</tr>';
                }

                mysqli_free_result($result);
                echo '</table>';
            }
        }
        ?>

    </div>

    </body>
    </html>
<?php mysqli_close($con); ?>