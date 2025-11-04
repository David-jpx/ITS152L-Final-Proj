<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Asset</title>
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
    <h1>Add New Asset</h1>
    <form action="http://localhost:18080/add-asset" method="post">
        <label for="deviceName">Device Name:</label>
        <input type="text" name="deviceName" id="deviceName" required>
        <label for="macAddress">MAC Address:</label>
        <input type="text" name="macAddress" id="macAddress" required>
        <label for="department">Department:</label>
        <input type="text" name="department" id="department" required>
        <label for="status">Status:</label>
        <select name="status" id="status">
            <option value="pending">Pending</option>
            <option value="active">Active</option>
        </select>
        <label for="dateLastMaintained">Date Last Maintained:</label>
        <input type="date" name="dateLastMaintained" id="dateLastMaintained">
        <br>
        <button type="submit">Add Asset</button>
    </form>
    <div style="margin-top: 20px;">
        <a href="http://localhost:18080/assets" class="button-link">Back to Assets</a>
        <a href="http://localhost:18080" class="button-link">Home Page</a>
    </div>
</body>
</html>