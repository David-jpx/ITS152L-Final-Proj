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
    <h1>Asset Management</h1>
    <h2>All Assets</h2>
    <!-- NEW: Search form with dropdowns -->
    <form action="/assets" method="get" style="margin-bottom: 20px;">
        <select name="department" style="padding: 5px; margin-right: 10px;">
            <option value="">All Departments</option>
            <option value="IT Department" <#if selectedDepartment?? && selectedDepartment == "IT Department">selected</#if>>IT Department</option>
            <option value="Network Infrastructure" <#if selectedDepartment?? && selectedDepartment == "Network Infrastructure">selected</#if>>Network Infrastructure</option>
            <option value="Maintenance" <#if selectedDepartment?? && selectedDepartment == "Maintenance">selected</#if>>Maintenance</option>
        </select>
        
        <select name="status" style="padding: 5px; margin-right: 10px;">
            <option value="">All Statuses</option>
            <option value="active" <#if selectedStatus?? && selectedStatus == "active">selected</#if>>Active</option>
            <option value="Needs repair" <#if selectedStatus?? && selectedStatus == "Needs repair">selected</#if>>Needs Repair</option>
            <option value="Marked for repair" <#if selectedStatus?? && selectedStatus == "Marked for repair">selected</#if>>Marked for Repair</option>
            <option value="approved" <#if selectedStatus?? && selectedStatus == "approved">selected</#if>>Approved</option>
            <option value="rejected" <#if selectedStatus?? && selectedStatus == "rejected">selected</#if>>Rejected</option>
        </select>
        
        <button type="submit" style="padding: 8px 12px; background-color: #40739e; color: white; border: none; border-radius: 4px; cursor: pointer; margin-right: 10px;">Apply Filters</button>
        <a href="/assets?repairFilter=true" style="margin-left: 10px; padding: 8px 12px; background-color: #e74c3c; color: white; text-decoration: none; border-radius: 4px;">Show Assets Needing Repair</a>
        <a href="/assets" style="margin-left: 10px; padding: 8px 12px; background-color: #95a5a6; color: white; text-decoration: none; border-radius: 4px;">Clear Filters</a>
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
                <!-- FIXED: Apply red background only for Needs repair and Marked for repair, reset to white for other statuses -->
                <tr <#if asset.status == "Needs repair" || asset.status == "Marked for repair">style="background-color: #ff6b6b;"<#else>style="background-color: white;"</#if>>
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
                            <#if asset.status == "Needs repair">
                                | <a href="/mark-for-repair/${asset.id}" onclick="return confirm('Mark this asset for repair?')" style="color: #e74c3c;">Mark for Repairs</a>
                            </#if>
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
