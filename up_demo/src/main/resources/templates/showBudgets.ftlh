<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All Budgets</title>
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
    <h1>All Budgets</h1>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Department</th>
                <th>Amount</th>
                <th>Actions</th> </tr>
        </thead>
        <tbody>
            <#list budgets as budget>
                <tr>
                    <td>${budget.id}</td>
                    <td>${budget.department}</td>
                    <td>$${budget.amount}</td>
                    <td>
                        <a href="/edit-budget/${budget.id}">Edit</a> |
                        <a href="/delete-budget/${budget.id}" onclick="return confirm('Are you sure you want to delete this budget?')">Delete</a>
                    </td>
                </tr>
            </#list>
        </tbody>
    </table>

    <div style="margin-top: 20px;">
        <a href="http://localhost:18080/add-budget" class="button-link">Add New Budget</a>
        <a href="http://localhost:18080" class="button-link">Home Page</a>
    </div>
</body>
</html>