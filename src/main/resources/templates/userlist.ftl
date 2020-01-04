<#import "fragment/common.ftl" as c>

<@c.page>
    <div>Список пользователей</div>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Role</th>
                <th>Href</th>
            </tr>
        </thead>
        <tbody>
        <#list userlist as user>
            <tr>
                <td>${user.id}</td>
                <td>${user.getUsername()}</td>
                <td>
                    <#list user.roles as role>
                        ${role}
                    </#list>
                </td>
                <td>
                    <a href="/user/${user.id}">Edit</a>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>
