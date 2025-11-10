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

    <!-- Budget Overview with new format -->
    <h2>Budget Overview</h2>
    <table style="margin-bottom: 30px;">
        <thead>
            <tr>
                <th>Department</th>
                <th>Initial Budget</th>
                <th>Current Budget</th>
                <th>Unconfirmed Expenses</th>
                <th>Confirmed Expenses</th>
                <th>Remaining Projected Budget</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <#list budgets as budget>
                <#assign confirmedDeptExpense = departmentExpenses[budget.department]!0>
                <#assign unconfirmedDeptExpense = unconfirmedDepartmentTotals[budget.department]!0>
                <#assign currentBudget = budget.amount - confirmedDeptExpense>
                <#assign remainingProjected = budget.amount - (confirmedDeptExpense + unconfirmedDeptExpense)>
                <tr>
                    <td>${budget.department}</td>
                    <td>₱${budget.amount?string("0.00")}</td>
                    <td>₱${currentBudget?string("0.00")}</td>
                    <td>₱${unconfirmedDeptExpense?string("0.00")}</td>
                    <td>₱${confirmedDeptExpense?string("0.00")}</td>
                    <td>₱${remainingProjected?string("0.00")}</td>
                    <td>
                        <#if remainingProjected < 0>
                            <span style="color: red; font-weight: bold;">OVER BUDGET</span>
                        <#elseif remainingProjected < budget.amount * 0.2>
                            <span style="color: orange; font-weight: bold;">LOW BUDGET</span>
                        <#else>
                            <span style="color: green; font-weight: bold;">ON TRACK</span>
                        </#if>
                    </td>
                    <td>
                        <form action="/update-budget/${budget.id}" method="post" style="display: inline;">
                            <input type="number" name="amount" step="0.01" required style="width: 100px; padding: 5px;">
                            <button type="submit" name="action" value="add" style="background-color: #2ecc71; color: white; border: none; padding: 5px 10px; cursor: pointer;">Add</button>
                            <button type="submit" name="action" value="deduct" style="background-color: #e74c3c; color: white; border: none; padding: 5px 10px; cursor: pointer;">Deduct</button>
                        </form>
                    </td>
                </tr>
            </#list>
        </tbody>
    </table>



    <div style="margin-top: 20px;">
        <a href="http://localhost:18080" class="button-link">Home Page</a>
    </div>
</body>
</html>