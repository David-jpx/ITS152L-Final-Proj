<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All Assets</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background-color: #f5f6fa; }
        h1 { color: #2f3640; }
        table { border-collapse: collapse; width: 100%; background-color: white; box-shadow: 0 2px 6px rgba(0,0,0,0.1); }
        th, td { border: 1px solid #dcdde1; padding: 10px; text-align: left; }
        th { background-color: #40739e; color: white; }
        tr:nth-child(even) { background-color: #f1f2f6; }
        .button-link { display: inline-block; padding: 10px 18px; margin: 10px 5px 0 0; background-color: #487eb0; color: white; text-decoration: none; border-radius: 6px; font-weight: bold; }
        .button-link:hover { background-color: #273c75; }
    </style>
</head>
<body>
    <h1>All Assets</h1>
    <!-- NEW: Search form -->
    <form action="/assets" method="get" style="margin-bottom: 20px;">
        <input type="text" name="department" placeholder="Filter by Department">
        <input type="text" name="status" placeholder="Filter by Status">
        <button type="submit">Search</button>
    </form>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Device Name</th>
                <th>MAC Address</th>
                <th>Department</th>
                <th>Status</th>
                <th>Date Last Maintained</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <#list assets as asset>
                <!-- FIXED: Use passed threshold for comparison -->
                <tr <#if asset.dateLastMaintained?? && asset.dateLastMaintained.isBefore(maintenanceThreshold)>style="background-color: #ffcccc;"</#if>>
                    <td>${asset.id!""}</td>
                    <td>${asset.deviceName!""}</td>
                    <td>${asset.macAddress!""}</td>
                    <td>${asset.department!""}</td>
                    <td>${asset.status!""}</td>
                    <td>${asset.dateLastMaintained!""}</td>
                    <td>
                        <#if asset?? && asset.id?? && asset.id?is_number && asset.id gt 0>
                            <a href="/edit-asset/${asset.id}">Edit</a> |
                            <a href="/delete-asset/${asset.id}" onclick="return confirm('Are you sure you want to delete this asset?')">Delete</a>
                        <#else>
                            N/A (Invalid ID: ${asset.id!""})
                        </#if>
                    </td>
                </tr>
            </#list>
        </tbody>
    </table>
    <div style="margin-top: 20px;">
        <a href="http://localhost:18080/add-asset" class="button-link">Add New Asset</a>
        <a href="http://localhost:18080" class="button-link">Home Page</a>
    </div>
</body>
</html>
