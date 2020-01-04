<#import "fragment/common.ftl" as frag>


<@frag.page>
<h3>${userChannel.username}</h3>
    <div>${type}</div>
<ul class="list-group">
    <#list users as user>
        <li class="list-group-item">
            <a href="/user-messages/${user.id}">${user.username}</a>
        </li>
    </#list>
</ul>

</@frag.page>