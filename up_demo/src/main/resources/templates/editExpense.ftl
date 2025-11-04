<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Expense</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background-color: #f5f6fa; }
        h1 { color: #2f3640; }
        label { display: block; margin-top: 10px; }
        input, select { margin-top: 5px; padding: 6px; width: 250px; }
        button { margin-top: 15px; padding: 10px 18px; background-color: #44bd32; color: white; border: none; border-radius: 6px; font-weight: bold; cursor: pointer; }
        button:hover { background-color: #2d9e23; }
        .button-link { display: inline-block; padding: 10px 18px; margin: 10px 5px 0 0; background-color: #487eb0; color: white; text-decoration: none; border-radius: 6px; font-weight: bold; }
        .button-link:hover { background-color: #273c75; }
    </style>
</head>
<body>
    <h1>Edit Expense</h1>
    <#if error??><p style="color: red;">${error}</p></#if> <!-- Display error if present -->
    <form action="/edit-expense/${expense.id}" method="post">
        <label for="description">Description:</label>
        <input type="text" name="description" id="description" value="${expense.description}" required>
        <label for="amount">Amount:</label>
        <input type="number" name="amount" id="amount" value="${expense.amount}" required>
        <label for="assetId">Asset ID:</label>
        <input type="number" name="assetId" id="assetId" value="${expense.assetId}" required>
        <label for="budgetId">Budget:</label>
        <select name="budgetId" id="budgetId" required>
            <#list budgets as budget>
                <option value="${budget.id}" <#if budget.id == expense.budgetId>selected</#if>>${budget.department} - $${budget.amount}</option>
            </#list>
        </select>
        <label for="date">Date:</label>
        <input type="date" name="date" id="date" value="${expense.date!""}">
        <br>
        <button type="submit">Update Expense</button>
    </form>
    <div style="margin-top: 20px;">
        <a href="http://localhost:18080/expenses" class="button-link">Back to Expenses</a>
        <a href="http://localhost:18080" class="button-link">Home Page</a>
    </div>
</body>
</html>