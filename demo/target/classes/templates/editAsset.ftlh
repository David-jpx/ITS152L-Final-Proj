<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Asset</title>
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
    <h1>Asset Management</h1>
    <h2>Edit Asset</h2>
    
    <!-- Current Asset Details -->
    <div style="background-color: #f8f9fa; padding: 15px; border-radius: 5px; margin-bottom: 20px;">
        <h3>Current Asset Details</h3>
        <p><strong>ID:</strong> ${asset.id!""}</p>
        <p><strong>Name:</strong> ${asset.deviceName!""}</p>
        <p><strong>MAC Address:</strong> ${asset.macAddress!""}</p>
        <p><strong>Department:</strong> ${asset.department!""}</p>
        <p><strong>Status:</strong> ${asset.status!""}</p>
        <p><strong>Last Maintained:</strong> ${asset.dateLastMaintained!""}</p>
    </div>
    
    <h3>Edit Asset Details</h3>
    <form action="/edit-asset/${asset.id}" method="post">
        <label for="deviceName">Device Name:</label>
        <input type="text" name="deviceName" id="deviceName" value="${asset.deviceName!""}" required>
        <label for="macAddress">MAC Address:</label>
        <input type="text" name="macAddress" id="macAddress" value="${asset.macAddress!""}" required>
        <label for="department">Department:</label>
        <input type="text" name="department" id="department" value="${asset.department!""}" required>
        <label for="status">Status:</label>
        <select name="status" id="status">
            <option value="pending" <#if asset.status == "pending">selected</#if>>Pending</option>
            <option value="approved" <#if asset.status == "approved">selected</#if>>Approved</option>
            <option value="rejected" <#if asset.status == "rejected">selected</#if>>Rejected</option>
            <option value="active" <#if asset.status == "active">selected</#if>>Active</option>
            <option value="Needs repair" <#if asset.status == "Needs repair">selected</#if>>Needs Repair</option>
            <option value="Marked for repair" <#if asset.status == "Marked for repair">selected</#if>>Marked for Repair</option>
            <option value="Waiting repair" <#if asset.status == "Waiting repair">selected</#if>>Waiting Repair</option>
        </select>
        <label for="dateLastMaintained">Date Last Maintained:</label>
        <input type="date" name="dateLastMaintained" id="dateLastMaintained" value="${asset.dateLastMaintained!""}">
        <br>
        <button type="submit">Update Asset</button>
    </form>
    <div style="margin-top: 20px;">
        <a href="http://localhost:18080/assets" class="button-link">Back to Assets</a>
        <a href="http://localhost:18080" class="button-link">Home Page</a>
    </div>
</body>
</html>