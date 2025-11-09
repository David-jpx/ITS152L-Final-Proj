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
    
    <form method="post" action="/confirm-selected-expenses">
        <h3>All Expenses</h3>
        <p><strong>Total Expenses (All):</strong> ₱${totalExpenses?string("0.00")}</p>
        <table>
            <thead>
                <tr>
                    <th>Select</th>
                    <th>ID</th>
                    <th>Department</th>
                    <th>Description</th>
                    <th>Amount</th>
                    <th>Asset ID</th>
                    <th>Date</th>
                    <th>Confirmed</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <#list allExpenses as expense>
                    <tr>
                        <td>
                            <#if !expense.confirmed>
                                <input type="checkbox" name="expenseIds" value="${expense.id}">
                            </#if>
                        </td>
                        <td>${expense.id}</td>
                        <td>${expense.department}</td>
                        <td>${expense.description}</td>
                        <td>₱${expense.amount?string("0.00")}</td>
                        <td>${expense.assetId}</td>
                        <td>${expense.date!""}</td>
                        <td>
                            <input type="checkbox" ${expense.confirmed?string('checked', '')} disabled>
                        </td>
                        <td>
                            <a href="/edit-expense/${expense.id}">Edit</a>
                        </td>
                    </tr>
                </#list>
            </tbody>
        </table>
        <div style="margin-top: 10px;">
            <button type="submit" style="background-color: #27ae60; color: white; border: none; padding: 8px 16px; border-radius: 4px; cursor: pointer;">Confirm Selected Expenses</button>
        </div>
    </form>



    <!-- Budget Overview Table -->
    <h2 style="margin-top: 30px;">Budget Overview by Department</h2>
    <table style="margin-top: 10px;">
        <thead>
            <tr>
                <th>Department</th>
                <th>Initial Budget</th>
                <th>Current Budget</th>
                <th>Unconfirmed Expenses</th>
                <th>Confirmed Expenses</th>
                <th>Remaining Projected Budget</th>
            </tr>
        </thead>
        <tbody>
            <#list budgetComparison?keys as department>
                <tr>
                    <td>${department}</td>
                    <td>₱${budgetComparison[department].currentBudget?string("0.00")}</td>
                    <td>₱${budgetComparison[department].remainingAfterConfirmed?string("0.00")}</td>
                    <td>₱${budgetComparison[department].unconfirmedExpenses?string("0.00")}</td>
                    <td>₱${budgetComparison[department].confirmedExpenses?string("0.00")}</td>
                    <td>₱${budgetComparison[department].remainingAfterUnconfirmed?string("0.00")}</td>
                </tr>
            </#list>
        </tbody>
    </table>

    <div style="margin-top: 20px;">
        <a href="http://localhost:18080/add-expense" class="button-link">Add New Expense</a>
        <a href="http://localhost:18080" class="button-link">Home Page</a>
    </div>
</body>
</html>