<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Department Budget Comparison</title>
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
        .over-budget {
            background-color: #ff6b6b !important;
            color: white;
        }
        .under-budget {
            background-color: #51cf66 !important;
            color: white;
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
    <h1>Department Budget Comparison</h1>

    <table>
        <thead>
            <tr>
                <th>Department</th>
                <th>Budget</th>
                <th>Confirmed Expenses</th>
                <th>Remaining Budget</th>
                <th>Status</th>
            </tr>
        </thead>
        <tbody>
            <#list budgets as budget>
                <#assign totalExpenses = departmentExpenses[budget.department]!0.0>
                <#assign remainingBudget = budget.amount - totalExpenses>
                <#assign isOverBudget = remainingBudget < 0>
                <tr class="${isOverBudget?string('over-budget', 'under-budget')}">
                    <td>${budget.department}</td>
                    <td>$${budget.amount?string("0.00")}</td>
                    <td>$${totalExpenses?string("0.00")}</td>
                    <td>$${remainingBudget?string("0.00")}</td>
                    <td>${isOverBudget?string('OVER BUDGET', 'Under Budget')}</td>
                </tr>
            </#list>
        </tbody>
    </table>

    <div style="margin-top: 20px;">
        <a href="http://localhost:18080/expenses" class="button-link">Back to Expenses</a>
        <a href="http://localhost:18080" class="button-link">Home Page</a>
    </div>
</body>
</html>