<#import "fragment/common.ftl" as c>

<@c.page>
    <p>User Edit</p>
    <form action="/user" method="post">
        Id: <input type="text" name="user_id" value="${user.id}"> <br>
        Username: <input type="text" name="user_name" value="${user.getUsername()}"> <br>
        Password: <input type="text" name="user_pass" value="${user.password}"> <br>
        <input type="hidden" name="_csrf" value="${_csrf.token}">

        <#list roles as role>
            <div>
                <label>
                    <input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}
                </label>
            </div>
        </#list>

        <button type="submit">Change</button>
    </form>

</@c.page>
