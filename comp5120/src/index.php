<?php
require "database.php";
global $tables;
$con = get_connection();
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
if ($tables) {
foreach($tables as $table_name) { ?>
    <h3><?= $table_name ?> </h3>
    <table class="bordered">
        <thead>
        <?php
        $query = "SELECT * FROM ". $table_name;
        $result = execute_query($con, $query);
        if(!$result) {
            die("Query failed to execute: " . mysqli_error($con));
        }
}
        $books = mysqli_fetch_assoc($result);
        $num_fields = mysqli_num_fields($result);

        echo "<tr>";
        for($i = 0; $i < $num_fields; $i++) {
            $field = mysqli_fetch_field_direct($result, $i);
            echo "<th>" . $field->name . "</th>";
        }
        echo "</tr>";


        ?>
        </thead>

        <?php
        $rows = array();
        while($result_row = mysqli_fetch_assoc($result)) {
            $rows[] = $result_row;
        }
        foreach($rows as $row) {
            echo "<tr>";
            foreach($row as $col) {
                echo "<td>" . $col . "</td>";
            }
            echo "</tr>";
        }

        mysqli_free_result($result);

        ?>

    </table>
    <br><br>
    <?php
}
?>
    </body>
    </html>
<?php mysqli_close($con); ?>