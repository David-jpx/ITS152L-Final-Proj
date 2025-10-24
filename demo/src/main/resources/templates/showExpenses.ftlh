<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All Expenses</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
            background-color: #f5f6fa;
        }
        h1 {
            color: #2f3640;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            background-color: white;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }
        th, td {
            border: 1px solid #dcdde1;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #40739e;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f1f2f6;
        }
        .button-link {
            display: inline-block;
            padding: 10px 18px;
            margin: 10px 5px 0 0;
            background-color: #487eb0;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-weight: bold;
        }
        .button-link:hover {
            background-color: #273c75;
        }
    </style>
</head>
<body>
    <h1>All Expenses</h1>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Description</th>
                <th>Amount</th>
                <th>Department</th>
                <th>Date</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>1</td>
                <td>Network Cable Purchase</td>
                <td>1500</td>
                <td>IT Department</td>
                <td>2025-10-12</td>
            </tr>
        </tbody>
    </table>

    <div style="margin-top: 20px;">
        <a href="http://localhost:18080/add-expense" class="button-link">Add New Expense</a>
        <a href="http://localhost:18080" class="button-link">Home Page</a>
    </div>
</body>
</html>
